package com.dolores.store.http;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.Serializable;

/**
 * Created by sheng on 18/3/27.
 */

public class ResultModel extends Model implements Serializable{
    public ResultHeader getRspHeader() {
        return rspHeader;
    }

    public void setRspHeader(ResultHeader rspHeader) {
        this.rspHeader = rspHeader;
    }

    public UserInfo getRspBody() {
        return rspBody;
    }

    public void setRspBody(UserInfo rspBody) {
        this.rspBody = rspBody;
    }

    private ResultHeader rspHeader;
    private UserInfo rspBody;

    @Override
    public ResultModel fromJson(String json) {
        Gson gson=new Gson();
        return gson.fromJson(json,ResultModel.class);
    }
}
