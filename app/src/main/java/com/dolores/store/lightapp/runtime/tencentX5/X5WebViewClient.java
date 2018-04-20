package com.dolores.store.lightapp.runtime.tencentX5;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.dolores.store.lightapp.runtime.AbsWebViewAppActivity;
import com.dolores.store.lightapp.runtime.AppNativeRequest;
import com.dolores.store.lightapp.runtime.AppNativeResponse;
import com.dolores.store.util.LogUtils;
import com.dolores.store.util.ToastUtils;
import com.hyphenate.chat.EMClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by sheng on 18/4/19.
 */

public class X5WebViewClient extends WebViewClient {
    private  final String TAG=X5WebViewClient.class.getSimpleName();
    public static int NUM_THREADS = 20;
    public static String SCHEMA = "dolores:";
    protected ExecutorService executorSevice;
    protected AbsWebViewAppActivity activity;

    //请求重定向url
    private String reDirectedUrl = null;
    //是否第一次加载的url
    private boolean isFirstUrl = true;
    //第一次加载的url存储
    private String mFirstUrl = "";
    //是否返回键
    private boolean mOnKeyBack = false;
    //是否需要异步处理
    private boolean isAsynLoadUrl = false;

    //jsbridge provider

    //load image listener

    public X5WebViewClient(AbsWebViewAppActivity activity){
        this.activity=activity;
        //jsbridge
        executorSevice= Executors.newFixedThreadPool(NUM_THREADS);
    }

    @Override
    public void onPageStarted(WebView webView, String url, Bitmap bitmap) {
        if(isFirstUrl){
            mFirstUrl=url;
            isFirstUrl=false;
        }
        super.onPageStarted(webView, url, bitmap);
    }

    @Override
    public void onPageFinished(WebView webView, String s) {
        LogUtils.d(TAG,"onPageFinished()");
        super.onPageFinished(webView, s);
        mOnKeyBack=false;
    }

    @Override
    public void onLoadResource(WebView webView, String url) {
        LogUtils.d(TAG,"onLoadResource()"+url);
        super.onLoadResource(webView, url);
        paserJSBirdgeSchema(url);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        LogUtils.d(TAG,"shouldOverrideUrlLoading()");
        if (paserJSBirdgeSchema(url)) { //兼容6.0，6。0的webview中使用js桥的时候，会随机走到这里
            return true;
        }
        //一.一键返回,取消加载
        if(url!=null && url.equals(reDirectedUrl) && webView.getUrl().equals(mFirstUrl) && mOnKeyBack){
            webView.stopLoading();
            if(null !=activity){
                activity.finish();
            }
            mOnKeyBack=false;
            return true;
        }
        mOnKeyBack=false;
        //二.首次加载
        if (!url.startsWith(SCHEMA) && reDirectedUrl == null) {
            reDirectedUrl = url;
            return true;
        }
        //三.拨打电话,发送邮件,微信,支付宝等私有协议请求
        if (url.startsWith("tel:") || url.startsWith("sms:") || url.startsWith("smsto:") || url.startsWith("mailto:")|| url.startsWith("weixin:")|| url.startsWith("alipays:")) {
            try{
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                if(null!=null){
                    activity.startActivity(intent);
                }
            }catch(Exception ex){
                LogUtils.e(TAG, ex.getMessage());
            }
            return true;
        }else{
            return super.shouldOverrideUrlLoading(webView,url);
        }
    }

    /**
     *
    释放资源,在调用者销毁的时候一起销毁
     */
    public void onDestroy(){
        if(executorSevice!=null){
            executorSevice.shutdown();
        }
    }
    /**
     * 注意区分刷新加载和JS bridge请求加载
     * */
    private boolean paserJSBirdgeSchema(final String url) {
        //刷新加载不带schema请求头
        if (!url.startsWith(SCHEMA)) {
            return false;
        }
        LogUtils.d("X5WebViewClient", "onLoadResource url:" + url);
        // 开启线程处理结果
        try {
            if (null != activity) {
                if (!executorSevice.isShutdown() && !activity.isFinishing()) {
                    executorSevice.execute(new Runnable() {
                        @Override
                        public void run() {
                            process(url);
                        }
                    });
                }
            }

        } catch (Exception e) {
            LogUtils.e("X5WebViewClient", "X5WebViewClient:" + e.getMessage());
        }
        return true;
    }

    /**
     * 处理javascript 调用本地方法
     * 该方法是在异步线程中执行,所以如果要响应UI,要通过handler处理 or context.runonUIThread
     * */
    private void process(String reqString){
        setAsynLoadUrl(false);
        //将url请求数据解析到request对象
        final AppNativeRequest request=new AppNativeRequest();
        //将返回结果解析到response对象
        final AppNativeResponse response=new AppNativeResponse();
        if(null !=activity){
            request.setContext(activity);
        }
        try{
            //1检查请求的可靠性
            request.parse(reqString);
            //2执行本地方法调用
            if("getPersonInfo".equals(request.getMethod())){
                String currentUser = EMClient.getInstance().getCurrentUser();
                ToastUtils.showToast(activity,currentUser);
            }
        }catch(Exception ex){
            response.fail(ex.getMessage());
        }
        //3.返回处理结果
        AsynloadResult(request, response, !isAsynLoadUrl());
    }

    /**
     * 返回js bridge 异步消息
     * */
    public void AsynloadResult(AppNativeRequest req, AppNativeResponse resp, boolean isLoad) {
        if (isLoad) {
            final String callbackId = req.getCallbackId();
            final String data = resp.encode();
            if (null != activity) {
                this.activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String ret = String
                                .format("javascript:JSBridge.handleMessageFrom(%s,%s)",
                                        callbackId, data);
                        activity.webview.loadUrl(ret);
                    }
                });
            }
        }
    }



    public void setOnKeyBackFlag(boolean flag) {
        mOnKeyBack = flag;
    }

    public boolean isAsynLoadUrl() {
        return isAsynLoadUrl;
    }

    public void setAsynLoadUrl(boolean isAsynLoadUrl) {
        this.isAsynLoadUrl = isAsynLoadUrl;
    }
}
