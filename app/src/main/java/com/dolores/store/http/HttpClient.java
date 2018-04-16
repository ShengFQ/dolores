package com.dolores.store.http;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.dolores.store.DoloresApplication;
import com.dolores.store.util.OSUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
/**
 *
 * @author shengfq 27/3/2018
 * volley lib
 * */
public class HttpClient {
    private static RequestQueue requestQueue=newRequestQueue();
    private static AtomicInteger counter = new AtomicInteger(0);
    private boolean useCache = false;

    private HttpClient(Context context){

    }

    public void clearCache() {
        requestQueue.getCache().clear();
    }

    public void setUseCache(boolean useCache) {
        this.useCache = useCache;
    }

    public void addRequest(Request request) {
        String tag = getTagAndCount();
        request.setTag(tag);
        request.setShouldCache(useCache);
        requestQueue.add(request);
    }

    public static void addRequest(Request request,Object tag){
        if(tag!=null){
            request.setTag(tag);
        }
        requestQueue.add(request);
    }

    public void cancelRequest(Request request) {
        requestQueue.cancelAll(request.getTag());
    }

    public static void cancelAll(Object tag){
        requestQueue.cancelAll(tag);
    }
    private String mapToQueryString(Map<String, Object> params) {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() == null || entry.getValue() instanceof File)
                    continue;
                encodedParams.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode(String.valueOf(entry.getValue()), "UTF-8"));
                encodedParams.append('&');
            }
            return encodedParams.toString();
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: UTF-8", uee);
        }
    }

    private String getTagAndCount() {
        int num = counter.getAndIncrement();
        return "HttpRequest-" + num;
    }

    private static RequestQueue newRequestQueue(){
        RequestQueue requestQueue=new RequestQueue(openCache(), new BasicNetwork(new HurlStack()));
        requestQueue.start();
        return requestQueue;
    }

    private static Cache openCache(){
        return new DiskBasedCache(OSUtils.getExternalCacheDir(DoloresApplication.getmApplicationContext()),10*1024*1024);
    }
}
