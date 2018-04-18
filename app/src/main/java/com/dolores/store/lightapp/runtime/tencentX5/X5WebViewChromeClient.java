package com.dolores.store.lightapp.runtime.tencentX5;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;

import com.dolores.store.lightapp.runtime.AbsWebViewAppActivity;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

/**
 * Created by sheng on 18/4/18.
 * tencent x5 core webviewchromeclient
 */

public class X5WebViewChromeClient extends WebChromeClient {
    private static String TAG=X5WebViewChromeClient.class.getSimpleName();
    private View videoView;
    private ViewGroup parentView;
    private AbsWebViewAppActivity activity;
    public X5WebViewChromeClient(){}
    public X5WebViewChromeClient(AbsWebViewAppActivity activity){
        super();
        this.activity=activity;
    }
    //通知应用程序当前网页加载的进度
    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        activity.onProgressChanged(newProgress);
    }

    //获取网页title标题
    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
    }
    //网页中有H5播放flash video的时候按下全屏按钮将会调用到这个方法，一般用作设置网页播放全屏操作
    @Override
    public void onShowCustomView(View view, IX5WebChromeClient.CustomViewCallback callback) {
        super.onShowCustomView(view, callback);
    }
    //取消全屏方法
    @Override
    public void onHideCustomView() {
        super.onHideCustomView();
    }

    //启动文件选择器从 Android 设备中选择图片和文件
    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {


        return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
    }
}
