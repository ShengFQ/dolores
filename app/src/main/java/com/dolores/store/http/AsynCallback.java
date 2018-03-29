package com.dolores.store.http;
/**
 * shengfq 27/3/2018
 * UI线程上的回调接口
 *
 * */
public abstract class AsynCallback<T> {

    public void onStart(T r){};
	public abstract void callback(T r);
	public void onFinish(T r) {

	}
}