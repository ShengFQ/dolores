package com.dolores.store.lightapp.runtime.androidwk;

import android.view.View;
import android.webkit.WebView;

import com.dolores.store.lightapp.runtime.AbsWebViewAppActivity;
import com.dolores.store.lightapp.runtime.interfaces.IWebView;

/**
 * Created by sheng on 18/4/18.
 */

public class OriginalWebView extends IWebView<WebView,OriginalWebviewClient,OriginalWebviewChromeClient> {

    public OriginalWebView(AbsWebViewAppActivity activity) {
        super(activity);
    }
    @Override
    public void onCreate(int resourceId, AbsWebViewAppActivity context) {

    }

    @Override
    public WebView getWebView() {
        return null;
    }

    @Override
    public OriginalWebviewClient getWebViewClient() {
        return null;
    }

    @Override
    public OriginalWebviewChromeClient getWebViewChromeClient() {
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
