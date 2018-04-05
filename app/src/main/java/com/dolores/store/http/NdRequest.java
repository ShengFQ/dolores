package com.dolores.store.http;

import com.android.volley.Request;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sheng on 18/3/27.
 */

public abstract class NdRequest {
    protected int mMethod= METHOD_POST;
    public static final int METHOD_POST=Request.Method.POST;
    public static final int METHOD_GET=Request.Method.GET;
    public abstract int getMethod();

    public abstract String getUrl();
    public Map<String,String> getHeaderParams() {
        return null;
    }

    public Map<String,String> getPostParams(){
        return null;
    }

}
