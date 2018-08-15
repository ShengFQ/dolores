
package com.dolores.store.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.dolores.store.R;
import com.dolores.store.lightapp.runtime.AbsWebViewAppActivity;
import com.dolores.store.lightapp.runtime.WebviewJump;
import com.dolores.store.util.FileUtils;
import com.dolores.store.util.LogUtils;
import com.dolores.store.util.MemoryUtils;
import com.dolores.store.util.ToastUtils;
import com.hyphenate.chat.EMMessage;
import com.tencent.smtt.sdk.WebView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shengfq
 *webview容器
 * */
public class NewsWebviewActivity extends AbsWebViewAppActivity {
    private final String TAG=NewsWebviewActivity.class.getSimpleName();
    private String url, backName, titleName;
    private EMMessage recMsg=null;
    private GestureDetector mGestureDetector;
    private boolean mSupportLongtouchMenu = true;
    private boolean isUseX5core=true;
    File file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getIntent() == null ? "" : getIntent().getStringExtra(WebviewJump.INTENT_KEY_WEBVIEW_URL) == null ?
                "" : getIntent().getStringExtra(WebviewJump.INTENT_KEY_WEBVIEW_URL);
        if ((url == null || "".equals(url)) && getIntent().getData() != null) {//获取微博中的链接
            url = getIntent().getData().getPath();
            url = url == null ? "" : (url.substring(1, url.length()));//getLastPathSegment()

        }
        LogUtils.d("webview", "URL:" + url);
        titleName = getIntent().getStringExtra(WebviewJump.INTENT_KEY_TITLE_NAME);
        recMsg = (EMMessage) getIntent().getSerializableExtra(WebviewJump.INTENT_KEY_EMMESSAGE);

        if (!mSupportLongtouchMenu) {
            webview.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return true;
                }
            });
        }else{
            if(isUseX5core) {
                //x5 设置longClickListener,点击图片等不回调的问题
                mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public void onLongPress(MotionEvent e) {
                        super.onLongPress(e);
                        com.tencent.smtt.sdk.WebView webView = (com.tencent.smtt.sdk.WebView) webview.getWebView();
                        onWebViewLongClick(webView.getHitTestResult().getType(), webView.getHitTestResult().getExtra());
                    }
                });
                webview.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        mGestureDetector.onTouchEvent(event);
                        return false;
                    }
                });
            }
        }
        this.webview.loadUrl(url);


    }

    @Override
    public int getRootViewId() {
        return R.id.layout_rootview;
    }


    private void onWebViewLongClick(int type,String extra){
        List<Integer> items = new ArrayList<>();
        switch (type) {
            case WebView.HitTestResult.ANCHOR_TYPE:
            case WebView.HitTestResult.SRC_ANCHOR_TYPE: {
                //点击的是链接
                items.add(R.string.webview_copy_link);
                showSaveDialog(items, extra);
                break;
            }
            case WebView.HitTestResult.IMAGE_TYPE: {
                //点击的是图片
              //  onLongClickCallBack(extra);
                break;
            }
            case WebView.HitTestResult.IMAGE_ANCHOR_TYPE:
            case WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE: {
                //点击的是图片
                items.add(R.string.webview_save_image);
                items.add(R.string.webview_copy_image_url);
                showSaveDialog(items, extra);
                break;
            }
            default:
                //点击的是空白处
                break;
        }
    }


    public void showSaveDialog(final List<Integer> datas, final String extral) {
        final String[] items = new String[datas.size()];
        for (int i = 0; i < datas.size(); i++) {
            items[i] = getResources().getString(datas.get(i));
        }
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (datas.get(which)) {
                    case R.string.webview_save_image:
                       // downloadImage(extral);
                        ToastUtils.showToast(NewsWebviewActivity.this,"download image succ");
                        break;
                    case R.string.webview_copy_image_url:
                        MemoryUtils.copyText(NewsWebviewActivity.this, extral);
                        break;
                    case R.string.webview_copy_link:
                        MemoryUtils.copyText(NewsWebviewActivity.this, extral);
                        break;
                    default:
                        break;
                }
//                ToastUtils.showMessage(NewsWebViewActivity.this, items[which]);
            }
        });
        builder.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void setTitleBarTitle(String title) {
        //TODO
    }

    @Override
    public String getStartUrl() {
        return url;
    }

    @Override
    public int getLayoutId() {
       if(isUseX5core){
           return R.layout.activity_webview_detail_x5;
       }
        return R.layout.activity_webview_detail_wv;
    }

    @Override
    public int getWebViewId() {
        return R.id.app_detaill_wv;
    }

    @Override
    public int getBottomOperationLayoutId() {
        return R.id.bottom_operation_layout;
    }

    @Override
    public int getBottomBackImageId() {
        return R.id.bottom_back_imageview;
    }

    @Override
    public int getBottomAheadImageId() {
        return R.id.bottom_ahead_imageview;
    }

    @Override
    public int getBottomRefreshImageId() {
        return R.id.bottom_refresh_imageview;
    }

    @Override
    public int getLoadingProgressBar() {
        return R.id.webview_progressbar;
    }

    @Override
    public File createImageFile() throws IOException {
        file = new File(FileUtils.IMAGE_PATH, FileUtils.getPhotoFileName());
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (Exception e) {
            LogUtils.e(TAG, "createFilePhoto:" + e.getMessage());
        }
        return file;
    }
}
