package com.dolores.store.http;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Created by sheng on 18/3/27.
 */

public abstract class Model {
    //json序列化
  public abstract Model fromJson(String json);
    //json反序列化
    public String toJson() {
        return new Gson().toJson(this);
    }
}
