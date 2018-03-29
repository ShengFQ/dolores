package com.dolores.store;

import android.app.Application;
import android.content.Context;

import com.dolores.store.http.HttpClient;
import com.dolores.store.util.OSUtils;
import com.dolores.store.util.SharePref;
import com.dolores.store.util.SharePrefConstant;

public class DoloresApplication extends Application {

    public static SharePref mSharePref = null;
   // public static HttpClient httpClient = null;
    //global Context
    public static Context mApplicationContext=null;
    @Override
    public void onCreate() {
        super.onCreate();
        this.mApplicationContext=getApplicationContext();
        String processName = OSUtils.getProcessName(this, android.os.Process.myPid());
        if (processName.equals(Constants.PACKAGE_NAME)) {
            mSharePref = SharePref.getInstance(SharePrefConstant.PREF_NAME, this);
            Thread.setDefaultUncaughtExceptionHandler(new CrashExceptionHandler(this));
            //httpClient = HttpClient.newInstance(this);
            //tencent bugly crash report framework init
            OSUtils.initCrashReport(mApplicationContext);
        }
    }
    /**
     * get global Context Object
     * */
    public static Context getmApplicationContext() {
        return mApplicationContext;
    }
}
