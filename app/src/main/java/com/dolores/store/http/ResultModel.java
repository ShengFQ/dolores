package com.dolores.store.http;

import com.google.gson.JsonObject;

import java.io.Serializable;

/**
 * Created by sheng on 18/3/27.
 */

public class ResultModel<T extends Model> implements Serializable{
    private boolean success;
    private String error;
    private int errorcode;
    private T data;

}
