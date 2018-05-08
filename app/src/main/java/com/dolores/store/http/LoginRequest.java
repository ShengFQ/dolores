package com.dolores.store.http;

import com.android.volley.Request;
import com.dolores.store.Constants;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sheng on 18/3/30.
 */

public class LoginRequest extends NdRequest {
    private  final String LOGIN_URL = "loginModule";
    @Override
    public int getMethod() {
        return Request.Method.POST;
    }

    @Override
    public String getUrl() {
        return Constants.getBaseUrl()+LOGIN_URL;
    }

    @Override
    public Map<String, String> getHeaderParams() {
     return  super.getHeaderParams();
    }

    @Override
    public Map<String, String> getPostParams() {
        mBody.put("loginId",loginId);
        mBody.put("passwd",passwd);
        mBody.put("appName","android");
        mBody.put("mobileVer","1.3.1");
        mBody.put("deviceId","");
        return super.getPostParams();
    }

    public String loginId;
    public String passwd;

    @Override
    public String toString() {
        return "http "+getMethod()+getUrl()+new Gson().toJson(mBody);
    }
}
