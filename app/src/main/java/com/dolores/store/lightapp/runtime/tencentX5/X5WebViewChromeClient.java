package com.dolores.store.lightapp.runtime.tencentX5;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.dolores.store.R;
import com.dolores.store.lightapp.runtime.AbsWebViewAppActivity;
import com.dolores.store.util.LogUtils;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

import java.io.File;
import java.io.IOException;

/**
 * Created by sheng on 18/4/18.
 * tencent x5 core webviewchromeclient
 */

public class X5WebViewChromeClient extends WebChromeClient {
    private static String TAG=X5WebViewChromeClient.class.getSimpleName();
    private View videoView;
    private IX5WebChromeClient.CustomViewCallback videoCallBack;
    private ViewGroup parentView;
    private AbsWebViewAppActivity activity;
    private int mOriginalSystemUiVisibility;
    private int mOriginalOrientation;
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
        if(videoView!=null){
            onHideCustomView();
            return;
        }
        //1.stash the current state
        videoView=view;
        activity.getWindow().getDecorView().getSystemUiVisibility();
        activity.getRequestedOrientation();

        //2.stash the custom view callback
        videoCallBack=callback;
        //3.add the custom view to the view hierarchy
        FrameLayout decor=(FrameLayout)activity.getWindow().getDecorView();
        decor.addView(videoView,new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        //4.change the state of the window
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|
        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION|
        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|
        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
        View.SYSTEM_UI_FLAG_FULLSCREEN|
        View.SYSTEM_UI_FLAG_IMMERSIVE);
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }
    //取消全屏方法
    @Override
    public void onHideCustomView() {
        //1.remove the custom view
        FrameLayout decor=(FrameLayout)activity.getWindow().getDecorView();
        decor.removeView(videoView);
        videoView=null;
        //2.restore the state to it's original form
        activity.getWindow().getDecorView().setSystemUiVisibility(mOriginalSystemUiVisibility);
        activity.setRequestedOrientation(mOriginalOrientation);

        //3.call the custom view callback
        videoCallBack.onCustomViewHidden();
        videoCallBack=null;
    }

    //启动文件选择器从 Android 设备中选择图片和文件
    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
        if(activity.mFilePathCallback!=null){
            activity.mFilePathCallback.onReceiveValue(null);
        }
        activity.mFilePathCallback=filePathCallback;
        Intent takePictureIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(activity.getPackageManager()) !=null){
            File photoFile=null;
            try{
                photoFile=activity.createImageFile();
                takePictureIntent.putExtra("PhotoPath",activity.mCameraPhotoPath);

            }catch (IOException ex){
                LogUtils.e(TAG,"unable to create Image file"+ex);
            }
            if(photoFile!=null){
                activity.mCameraPhotoPath="file:"+photoFile.getAbsolutePath();
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(photoFile));
            }else{
                takePictureIntent=null;
            }
        }
        //TODO ????
        Intent contentSelectionIntent=new Intent(Intent.ACTION_GET_CONTENT);
        contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
        contentSelectionIntent.setType("image/*");
        Intent[] intentArray;
        if(takePictureIntent!=null){
            intentArray=new Intent[]{takePictureIntent};
        }else{
            intentArray=new Intent[0];
        }
        Intent chooserIntent=new Intent(Intent.ACTION_CHOOSER);
        chooserIntent.putExtra(Intent.EXTRA_INTENT,contentSelectionIntent);
        chooserIntent.putExtra(Intent.EXTRA_TITLE,"Image Chooser");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,intentArray);
        activity.startActivityForResult(chooserIntent,activity.INPUT_FILE_REQUEST_CODE);
        return true;
    }

    //设置webview视频未播放时默认显示占位图
    @Override
    public Bitmap getDefaultVideoPoster() {
        if(activity==null){
            return null;
        }
        return BitmapFactory.decodeResource(activity.getApplicationContext().getResources(), R.drawable.ic_media_video_poster);
    }
}
