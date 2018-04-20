package com.dolores.store.lightapp.runtime.tencentX5;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

import com.dolores.store.R;
import com.dolores.store.lightapp.runtime.AbsWebViewAppActivity;
import com.dolores.store.lightapp.runtime.AppNativeRequest;
import com.dolores.store.lightapp.runtime.AppNativeResponse;
import com.dolores.store.lightapp.runtime.interfaces.IWebView;
import com.dolores.store.util.LogUtils;
import com.hyphenate.chat.EMClient;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

import java.io.File;

/**
 * Created by sheng on 18/4/19.
 */

public class X5WebView extends IWebView<WebView,X5WebViewClient,X5WebViewChromeClient>{
    private WebView x5WebView;
    public X5WebView(AbsWebViewAppActivity context) {
        super(context);
    }

    @Override
    public void onCreate(int resourceId, AbsWebViewAppActivity context) {
        x5WebView=(WebView)context.findViewById(resourceId);
        webviewClient=new X5WebViewClient(context);
        webviewChromeClient=new X5WebViewChromeClient(context);
        //setting webview
        x5WebView.getSettings().setJavaScriptEnabled(true);
        x5WebView.setWebViewClient(webviewClient);
        userAgent=userAgent+x5WebView.getSettings().getUserAgentString();
        x5WebView.getSettings().setUserAgentString(userAgent);
        x5WebView.getSettings().setSupportZoom(true);
        x5WebView.getSettings().setBuiltInZoomControls(true);
        x5WebView.getSettings().setUseWideViewPort(true);
        x5WebView.getSettings().setSavePassword(false);
        x5WebView.setBackgroundColor(mActivity.getResources().getColor(R.color.bg_white));
        x5WebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
            x5WebView.getSettings().setDisplayZoomControls(false);
            //android webview组件包含3个隐藏的系统接口：“accessibility”和和“ccessibilityaversal”以及“searchBoxJavaBridge_”，同样会造成远程代码执行。
            x5WebView.removeJavascriptInterface("accessibility");
            x5WebView.removeJavascriptInterface("accessibilityTraversal");
            x5WebView.removeJavascriptInterface("searchBoxJavaBridge_");
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            //关闭自动播放，需要用户确认
            try {
                x5WebView.getSettings().setMediaPlaybackRequiresUserGesture(true);
            } catch (NoSuchMethodError e) {

            }
        }
        //To allow https to redirect to http you need to set the mixed content mode to MIXED_CONTENT_ALWAYS_ALLOW
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            x5WebView.getSettings().setMixedContentMode( android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW );
        }

        int minimumFontSize = 8;
        int minimumLogicalFontSize = 8;
        int defaultFontSize = 16;
        int defaultFixedFontSize = 13;
        WebSettings webSettings = x5WebView.getSettings();
        webSettings.setMinimumFontSize(minimumFontSize);
        webSettings.setMinimumLogicalFontSize(minimumLogicalFontSize);
        webSettings.setDefaultFontSize(defaultFontSize);
        webSettings.setDefaultFixedFontSize(defaultFixedFontSize);

        /***打开本地缓存提供JS调用**/
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheMaxSize(1024 * 1024 * 10);
        // This next one is crazy. It's the DEFAULT location for your app's cache
        // But it didn't work for me without this line.
        // UPDATE: no hardcoded path. Thanks to Kevin Hawkins
        String lightAppPath = mActivity.getApplicationContext().getDir("x5cacheDir", Context.MODE_PRIVATE).getPath();

        String appCachePath = lightAppPath + File.separator + EMClient.getInstance().getCurrentUser() != null ? EMClient.getInstance().getCurrentUser() : "";
        webSettings.setAppCachePath(appCachePath);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAllowFileAccess(true);
//		//启用数据库
        webSettings.setDatabaseEnabled(true);
        String databasePath = lightAppPath + File.separator + "database";

        //启用地理定位
        webSettings.setGeolocationEnabled(true);
        //设置定位的数据库路径
        webSettings.setGeolocationDatabasePath(databasePath);
        x5WebView.setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(String arg0, String arg1, String arg2,
                                        String arg3, long arg4) {
                try {
                    Uri uri = Uri.parse(arg0);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    mActivity.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
        x5WebView.setWebChromeClient(webviewChromeClient);
    }

    @Override
    public WebView getWebView() {
        return x5WebView;
    }

    @Override
    public X5WebViewClient getWebViewClient() {
        return webviewClient;
    }

    @Override
    public X5WebViewChromeClient getWebViewChromeClient() {
        return webviewChromeClient;
    }

    @Override
    public void disableUseWideViewPort() {
        if (x5WebView != null) {
            x5WebView.getSettings().setUseWideViewPort(false);
        }
    }

    @Override
    public void loadUrl(String url) {
        x5WebView.loadUrl(url);
    }

    @Override
    public void reload() {
        x5WebView.reload();
    }

    @Override
    public String getUrl() {
        return x5WebView.getUrl();
    }

    @Override
    public String getWebTitle() {
        return x5WebView.getTitle();
    }

    @Override
    public boolean canGoForward() {
        return x5WebView.canGoForward();
    }

    @Override
    public void goForward() {
        x5WebView.goForward();
    }

    @Override
    public boolean canGoBack() {
        return x5WebView.canGoBack();
    }

    @Override
    public void goBack() {
        x5WebView.goBack();
    }
    @Override
    public  void asynloadResult(AppNativeRequest request, AppNativeResponse response, boolean isLoad){
        webviewClient.AsynloadResult(request,response,isLoad);
    }

    @Override
    public void setAsynLoadUrl(boolean isLoad) {
        webviewClient.setAsynLoadUrl(isLoad);
    }

    @Override
    public void setOnKeyBackFlag(boolean flag) {
        if (webviewClient != null) {
            webviewClient.setOnKeyBackFlag(true);
        }
    }

    @Override
    public void setOnTouchListener(View.OnTouchListener listener) {
        x5WebView.setOnTouchListener(listener);
    }

    @Override
    public void setOnLongClickListener(View.OnLongClickListener longClickListener) {
    x5WebView.setOnLongClickListener(longClickListener);
    }

    @Override
    public void onDestroy() {
        try {
            x5WebView.stopLoading();
            ViewGroup parent = (ViewGroup) x5WebView.getParent();
            if (parent != null)
                parent.removeView(x5WebView);
//			x5WebView.clearCache(true);
            x5WebView.clearHistory();
            x5WebView.destroy();
            webviewClient.onDestroy();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("X5WebView",e.getMessage());
        }
    }
}
