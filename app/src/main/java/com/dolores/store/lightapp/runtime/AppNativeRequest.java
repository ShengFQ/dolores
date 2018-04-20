/**
 * Copyright (C) 2016 kingdee Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dolores.store.lightapp.runtime;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by sheng on 18/4/20.
 */

public class AppNativeRequest {

    private AbsWebViewAppActivity context;
    private String method;
    private String callbackId;
    private JSONObject params;

    public void parse(String request) throws Exception {

        if (request == null || request.trim().length() == 0) {
            return;
        }

        String[] reqs = request.split(":");
        if (reqs.length < 3) {
            throw new IllegalArgumentException("输入参数无法识别");
        }

        this.method = reqs[1];
        this.callbackId = reqs[2];

        if (reqs.length > 3) {

            String rawParamters = reqs[3];

            if (rawParamters == null || rawParamters.trim().length() == 0) {
                return;
            }

            String decodeData = null;

            try {
                decodeData = URLDecoder.decode(rawParamters, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new IllegalArgumentException("参数无法解码,编码错误");
            }

            // TR:
            if ("undefined".equals(decodeData)) {
                return;
            }

            this.params = new JSONObject(decodeData);
        }

    }

    public AbsWebViewAppActivity getContext() {
        return context;
    }

    public void setContext(AbsWebViewAppActivity context) {
        this.context = context;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getCallbackId() {
        return callbackId;
    }

    public void setCallbackId(String callbackId) {
        this.callbackId = callbackId;
    }

    public JSONObject getParams() {
        return params;
    }

    public void setParams(JSONObject params) {
        this.params = params;
    }
}
