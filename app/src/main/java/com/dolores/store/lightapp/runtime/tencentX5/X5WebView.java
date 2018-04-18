package com.dolores.store.lightapp.runtime.tencentX5;

import android.content.Context;
import android.view.View;

import com.dolores.store.lightapp.runtime.AbsWebViewAppActivity;
import com.dolores.store.lightapp.runtime.interfaces.IWebView;
import com.tencent.smtt.sdk.WebView;

/**
 * Created by sheng on 18/4/19.
 */

public class X5WebView extends IWebView<WebView,X5WebViewClient,X5WebViewChromeClient>{
    public X5WebView(AbsWebViewAppActivity context) {
        super(context);
    }

    @Override
    public void onCreate(int resourceId, AbsWebViewAppActivity context) {

    }

    @Override
    public WebView getWebView() {
        return null;
    }

    @Override
    public X5WebViewClient getWebViewClient() {
        return null;
    }

    @Override
    public X5WebViewChromeClient getWebViewChromeClient() {
        return null;
    }

    @Override
    public void disableUseWideViewPort() {

    }

    @Override
    public void loadUrl(String url) {

    }

    @Override
    public void reload() {

    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public String getWebTitle() {
        return null;
    }

    @Override
    public boolean canGoForward() {
        return false;
    }

    @Override
    public void goForward() {

    }

    @Override
    public boolean canGoBack() {
        return false;
    }

    @Override
    public void goBack() {

    }

    @Override
    public void setAsynLoadUrl(boolean isLoad) {

    }

    @Override
    public void setOnTouchListener(View.OnTouchListener listener) {

    }

    @Override
    public void setOnLongClickListener(View.OnLongClickListener longClickListener) {

    }

    @Override
    public void onDestroy() {

    }
}
