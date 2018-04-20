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

/**
 * Created by sheng on 18/4/20.
 */

public class AppNativeResponse {

    private boolean success=true;
    private String error;
    private int errorCode;
    private JSONObject data;
    public void fail(String msg) {

        this.success = false;
        this.errorCode = 1;
        this.error = msg;

    }

    public String encode() {

        JSONObject resp = new JSONObject();

        try {
            resp.put("success", success);
            resp.put("error", error);
            resp.put("errorCode", errorCode);
            resp.put("data", data);
        } catch (Exception e) {
            this.fail("返回结果编码失败");
            System.out.print(e.getMessage());
        }
        return resp.toString();

    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }
}
