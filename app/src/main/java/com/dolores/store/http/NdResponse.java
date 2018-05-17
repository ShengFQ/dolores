package com.dolores.store.http;

/**
 * Created by sheng on 18/3/27.
 */

public abstract class NdResponse<T> {
    public T t;
    public  abstract void processData(T response);
    public abstract void processError(Object error);
    public abstract Class getResponseDataClass();
    public T getT() {
        return t;
    }

}
