package com.dolores.store;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;

import com.dolores.store.util.LogUtils;
import com.dolores.store.util.OSUtils;
import com.dolores.store.util.SharePref;
import com.dolores.store.util.SharePrefConstant;

import java.util.Iterator;
import java.util.List;

public class DoloresApplication extends Application {

    public static SharePref mSharePref = null;
    // public static HttpClient httpClient = null;
    //global Context
    public static Context mApplicationContext=null;

    public static String currentUserNick = "";


    /**
     * APP启动 APP的初始化启动方法入口，如果是有第三方服务启动例如百度推送，也
     * 会调用该方法启动，但是需要避免启动两个实例。
     */
    @Override
    public void onCreate() {
        super.onCreate();
        this.mApplicationContext=this;
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        //如果APP启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回
        if (processAppName == null || !processAppName.equalsIgnoreCase(mApplicationContext.getPackageName())) {
            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }
        //init begin
        LogUtils.setDebug(BuildConfig.DEBUG);
        mSharePref = SharePref.getInstance(SharePrefConstant.PREF_NAME, this);
        Thread.setDefaultUncaughtExceptionHandler(new CrashExceptionHandler(this));
        //tencent bugly crash report framework init
        OSUtils.initCrashReport(mApplicationContext);
        OSUtils.initX5WebView(mApplicationContext);
        DoloHelper.getInstance().init(mApplicationContext);
    }

    /**
     * get global Context Object
     * */
    public static Context getmApplicationContext() {
        return mApplicationContext;
    }

    private String getAppName(int pid) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(this.ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pid) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                LogUtils.e("application", "e:" + e.getMessage());
            }
        }
        return processName;
    }
}
