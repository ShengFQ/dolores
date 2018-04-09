package com.dolores.store.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import com.dolores.store.Constants;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
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
        //api8以上有默认扩展缓存,其他的在package/cache目录
        if (hasExternalCacheDir())
            return context.getExternalCacheDir();

        final String cacheDir="Android/data"+context.getPackageName()+"/cache/";
        return new File(Environment.getExternalStorageDirectory().getPath()+cacheDir);
    }

    public static boolean hasExternalCacheDir(){
        return Build.VERSION.SDK_INT>=Build.VERSION_CODES.FROYO;
    }

    /**
     * 初始化crash report package
     * */
    public static void initCrashReport(Context context){
        //当前包名是否等于当前进程名
        String packageName=context.getPackageName();
        //获取当前进程名
        String processName=getProcessName(android.os.Process.myPid());
        CrashReport.UserStrategy strategy=new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName==null || processName.equals(packageName));
        CrashReport.initCrashReport(context, Constants.APPID_BUGLY,true,strategy);

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
