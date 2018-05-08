package com.dolores.store.http;

import com.android.volley.Request;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sheng on 18/3/27.
 */

public abstract class NdRequest {
    Map<String,String> mHeader=new HashMap<String,String>();
    Map<String,String> mBody=new HashMap<String,String>();

    protected int mMethod= METHOD_POST;
    public static final int METHOD_POST=Request.Method.POST;
    public static final int METHOD_GET=Request.Method.GET;
    //http method
    public abstract int getMethod();
    //complete url
    public abstract String getUrl();
    //header param
    public  Map<String,String> getHeaderParams(){
        mHeader.put("Content-Type","application/json");
        return mHeader;
    } ;
    //body param
    public Map<String,String> getPostParams(){
        return mBody;
    }

}
