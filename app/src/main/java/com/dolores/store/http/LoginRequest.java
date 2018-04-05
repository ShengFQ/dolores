package com.dolores.store.http;

import com.android.volley.Request;

/**
 * Created by sheng on 18/3/30.
 */

public class LoginRequest extends NdRequest {
    @Override
    public int getMethod() {
        return Request.Method.POST;
    }

    @Override
    public String getUrl() {
        return null;
    }
}
