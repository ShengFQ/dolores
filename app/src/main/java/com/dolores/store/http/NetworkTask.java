package com.dolores.store.http;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.Map;

/**
 * Created by sheng on 18/3/27.
 * 封装了volley网络请求回调及消息解析
 */

public class NetworkTask<T> {

    /**
     * 启动一个异步http请求栈
     * */
    public AsyncTask<NdRequest, Integer, NdResponse> startHttpTask(final NdRequest ndRequest, final NdResponse ndResponse,
                                                                   final AsynCallback<NdResponse> callback) {
        final String url = ndRequest.getUrl();
        final int method = ndRequest.getMethod();
        final Map<String, String> header = ndRequest.getHeaderParams();
        final Map<String, String> postParams = ndRequest.getPostParams();
        return new AsyncTask<NdRequest, Integer, NdResponse>() {
            protected void onPreExecute() {
                callback.onStart(ndResponse);
            }

            protected void onPostExecute(NdResponse result) {
                try {
                    callback.callback(result);
                } finally {
                    callback.onFinish(result);

                }
            }

            @Override
            protected NdResponse doInBackground(NdRequest... params) {
                GsonRequest<T> request = new GsonRequest<T>(method, url, ndResponse.getResponseDataClass(), header, postParams
                        , new Response.Listener<T>() {
                    @Override
                    public void onResponse(final T response) {
                        ndResponse.processData(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        ndResponse.processError(volleyError);
                    }
                });
                executeRequest(request, 1);
                return ndResponse;
            }

        }.execute(ndRequest);


    }

    private static void executeRequest(Request request, int flag) {
        HttpClient.addRequest(request, flag);
    }
}
