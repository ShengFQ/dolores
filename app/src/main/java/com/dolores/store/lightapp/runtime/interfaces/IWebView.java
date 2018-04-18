package com.dolores.store.lightapp.runtime.interfaces;

import android.net.Uri;
import android.os.Build;
import android.view.View;

import com.dolores.store.lightapp.runtime.AbsWebViewAppActivity;

/**
 * @author shengfq
 */
public abstract class IWebView<Wv, Wc, Wcc>{
    protected AbsWebViewAppActivity mActivity;
    protected  Wv webView;
    protected Wc webviewClient;
    protected Wcc webviewChromeClient;
    protected String userAgent =  "Android " + Build.VERSION.RELEASE + ";" + Build.BRAND
            + ";" + Build.MODEL + ";";

    public IWebView(AbsWebViewAppActivity activity) {
        this.mActivity = activity;
    }

    public abstract void onCreate(int resourceId, AbsWebViewAppActivity context);

    public abstract Wv getWebView();

    public abstract Wc getWebViewClient();

    public abstract Wcc getWebViewChromeClient();

    public abstract void disableUseWideViewPort();

    public abstract void loadUrl(String url);

    public abstract void reload();

    public abstract String getUrl();

    public abstract String getWebTitle();

    public abstract boolean canGoForward();

    public abstract void goForward();

    public abstract boolean canGoBack();

    public abstract void goBack();

    //public abstract boolean hasValueCallback();

    //public abstract boolean onWebChromClientBack();

    //public abstract void asynloadResult(LightAppNativeRequest req, LightAppNativeResponse resp, boolean isLoad);

    public abstract void setAsynLoadUrl(boolean isLoad);

    //public abstract void setOnKeyBackFlag(boolean flag);

    //public abstract void onReceiveValue(Uri uri, Uri[] uris);

    public abstract void setOnTouchListener(View.OnTouchListener listener);

    public abstract void setOnLongClickListener(View.OnLongClickListener longClickListener);

    //public abstract void setLightAppListener(LightAppListener lightAppListener);

    public abstract void onDestroy();
}
