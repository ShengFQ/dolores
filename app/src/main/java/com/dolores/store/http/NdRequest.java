package com.dolores.store.http;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by sheng on 18/3/27.
 */

public abstract class NdRequest {
    // 纯粹的POST
    public static final int MODE_PURE_JSON = 2;
    // 键值对模式
    public static final int MODE_KEY_VALUE = 1;
    protected int mode = MODE_KEY_VALUE;
    public abstract int getMethod();

    public abstract String getUrl();
    public Map<String,String> getHeaderParams() {
        return null;
    }

    public Map<String,String> getPostParams(){
        return null;
    }

}
