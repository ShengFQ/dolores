package com.dolores.store.http;

import com.google.gson.Gson;

/**
 * Created by sheng on 18/5/16.
 */

public class ResultHeader extends Model {
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    private int status;
    private String msg;
    private int code;
    @Override
    public ResultHeader fromJson(String json) {
        Gson gson=new Gson();
        return gson.fromJson(json,ResultHeader.class);

    }
}
