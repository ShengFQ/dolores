package com.dolores.store.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.dolores.store.BuildConfig;
import com.dolores.store.Constants;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.sdk.QbSdk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * shengfq 3/27/2018
 * APP shell壳常量
 * */
public class OSUtils {


    public static String getVersionName(Context context) {
        if (context == null) return "";
        PackageManager packageManager = context.getPackageManager();
        String version = "";
        try {
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            version = packInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }
    /**
     * get the external app cache directory
     *
     * */
    public static File getExternalCacheDir(final Context context){
       return getExternalCacheDir(context,null);
    }
    /**
     * get the external app path directory
     *
     * */
    public static File getExternalCacheDir(final Context context,String path){
        String defaultCache="cache/";
        //api8以上有默认扩展缓存,其他的在package/cache目录
        if (hasExternalCacheDir())
            return context.getExternalCacheDir();
            if(path==null){
                path=defaultCache;
            }
        String cacheDir="Android/data"+context.getPackageName()+path;
        return new File(Environment.getExternalStorageDirectory().getPath()+File.separator+cacheDir);
    }
    public static boolean hasExternalCacheDir(){
        return Build.VERSION.SDK_INT>=Build.VERSION_CODES.FROYO;
    }

    /**
     * 初始化crash report package
     * */
    public static void initCrashReport(final Context context){
        //当前包名是否等于当前进程名
        String packageName=context.getPackageName();
        //获取当前进程名
        String processName=getProcessName(android.os.Process.myPid());
        CrashReport.UserStrategy strategy=new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName==null || processName.equals(packageName));
        //TBS x5 crash report
        strategy.setCrashHandleCallback(new CrashReport.CrashHandleCallback() {
            public Map<String, String> onCrashHandleStart(int crashType, String errorType, String errorMessage, String errorStack) {
                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                String x5CrashInfo = com.tencent.smtt.sdk.WebView.getCrashExtraMessage(context);
                map.put("x5crashInfo", x5CrashInfo);
                return map;
            }
            @Override
            public byte[] onCrashHandleStart2GetExtraDatas(int crashType, String errorType, String errorMessage, String errorStack) {
                try {
                    return "Extra data.".getBytes("UTF-8");
                } catch (Exception e) {
                    return null;
                }
            }
        });
        CrashReport.initCrashReport(context, Constants.APPID_BUGLY, BuildConfig.DEBUG,strategy);

    }

    public static void initX5WebView(Context context) {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(context, cb);
    }
    /**
     * 获取APP进程号
     * */
    /**
     * 获取进程号对应的进程名
     * */
    public static String getProcessName(int pid){
        BufferedReader reader=null;
        try{
            reader=new BufferedReader(new FileReader("/proc/"+pid+"/cmdline"));
            String processName=reader.readLine();
            if(!TextUtils.isEmpty(processName)){
                processName=processName.trim();
            }
            return processName;
        }catch(Throwable throwable){
            throwable.printStackTrace();
        }finally{
            try{
                if(reader!=null){
                    reader.close();
                }
            }catch(IOException exception){
                exception.printStackTrace();
            }
        }
        return null;
    }


}
