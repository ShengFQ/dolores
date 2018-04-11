package com.dolores.store.util;

import android.util.Log;

/**
 * Created by sheng on 18/4/5.
 * 日志只有在调试模式下才有用
 */

public class LogUtils {
    private final static String TAG ="shengfq";
    static boolean debug;
    public boolean isDebug(){
        return this.debug;
    }
    public static void setDebug(boolean debugs){
        debug=debugs;
    }

    public static void d(String msg){
        if(debug){
            Log.d(TAG,msg);
        }
    }

    public static void e(String msg){
        if(debug){
            Log.e(TAG,msg);
        }
    }

    public static void i(String msg){
        if(debug){
            Log.i(TAG,msg);
        }
    }
}
