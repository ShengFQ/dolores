package com.dolores.store.http;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Volley adapter for JSON requests that will be parsed into Java objects by Gson.
 */
public class GsonRequest<T> extends Request<T> {
    private final Gson gson = new Gson();
    private final Class<T> clazz;
    private final Map<String, String> mPostParam;
    private final Map<String, String> headers;
    private final Listener<T> listener;

    /**
     * Make a GET request and return a parsed object from JSON.
     *  @param url URL of the request to make
     * @param clazz Relevant class object, for Gson's reflection
     * @param headers header params
     * @param params Map of body params
     */
    public GsonRequest(int method, String url, Class<T> clazz, Map<String,String> headers, Map<String,String> params,
                       Listener<T> listener, ErrorListener errorListener) {
        super(method, url, errorListener);
        this.clazz = clazz;
        this.headers=headers;
        this.mPostParam = params;
        this.listener = listener;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        headers.put("Content-Type", "application/json");
        return headers != null ? headers : super.getHeaders();
    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mPostParam;
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(
                    gson.fromJson(json, clazz), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    public void deliverError(VolleyError error) {
        int statusCode = error.networkResponse.statusCode;
        if (statusCode == 401 || statusCode == 500) {
            String statusMsg;
            try {
                statusMsg = new String(error.networkResponse.data, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                statusMsg = new String(error.networkResponse.data);
            }
            try {
                JSONObject jsonObject = new JSONObject(statusMsg);
                String msg = jsonObject.optString("message");
                if (!TextUtils.isEmpty(msg)) {
                    Response.error(new HttpError(statusCode, msg));
                } else {
                    Response.error(new VolleyError(statusMsg));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Response.error(new VolleyError(statusMsg));
            }

        } else {
            super.deliverError(error);
        }
    }
}