/**
 * Copyright (C) 2016 kingdee Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dolores.store.lightapp.runtime;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.dolores.store.R;
import com.dolores.store.lightapp.runtime.interfaces.IWebView;
import com.dolores.store.lightapp.runtime.tencentX5.X5WebView;
import com.dolores.store.ui.base.BaseSwipeBackActivity;
import com.dolores.store.util.ToastUtils;
import com.tencent.smtt.sdk.ValueCallback;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sheng on 18/4/18.
 */

public abstract class AbsWebViewAppActivity extends BaseSwipeBackActivity {
    private List<String> historyUrls = new ArrayList<String>();
    private String mLastUrl;//上次webView加载完成后的url
    public boolean isBackDefined = false;
    private boolean isUserClicked = false; //是否用户对界面有点击
    public IWebView webview;
    private ImageView backButton, forwardButton, refreshButton;
    private ProgressBar loadingProgressBar;
    private RelativeLayout bottom_operation_layout;//底部操作栏
    public int INPUT_FILE_REQUEST_CODE=0;
    public ValueCallback<Uri[]> mFilePathCallback;
    public String mCameraPhotoPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(getLayoutId());
        //避免输入法界面弹出后遮挡输入光标的问题
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        webview=new X5WebView(this);
        webview.onCreate(getWebViewId(),this);
        bottom_operation_layout = (RelativeLayout) findViewById(getBottomOperationLayoutId());
        backButton = (ImageView) findViewById(getBottomBackImageId());
        forwardButton = (ImageView) findViewById(getBottomAheadImageId());
        refreshButton = (ImageView) findViewById(getBottomRefreshImageId());
        loadingProgressBar = (ProgressBar) findViewById(getLoadingProgressBar());
        initViewsEvent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webview.onDestroy();
    }

    /**
     * TODO
     * 回退键的特殊处理*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        int size = historyUrls.size();
        String startUrl = getStartUrl();

//		if ((keyCode == KeyEvent.KEYCODE_BACK) && (keyCode == KeyEvent.ACTION_DOWN) && this.webview.canGoBack()) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.webview.canGoBack()) {
            if (isBackDefined) {
                //callBackByData(null, backReq, backResp, true);
                return true;
            }
            if (webview != null) {
                webview.setOnKeyBackFlag(true);
            }
            this.webview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != INPUT_FILE_REQUEST_CODE || mFilePathCallback == null) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }

        Uri[] results = null;

        // Check that the response is a good one
        if (resultCode == Activity.RESULT_OK) {
            if (data == null) {
                // If there is not data, then we may have taken a photo
                if (mCameraPhotoPath != null) {
                    results = new Uri[]{Uri.parse(mCameraPhotoPath)};
                }
            } else {
                String dataString = data.getDataString();
                if (dataString != null) {
                    results = new Uri[]{Uri.parse(dataString)};
                }
            }
        }

        mFilePathCallback.onReceiveValue(results);
        mFilePathCallback = null;
        return;
    }

    //处理控件绑定事件
    private void initViewsEvent(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(webview.canGoBack()){
                    webview.goBack();
                    setOperationImageStatus();
                }
            }
        });

        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(webview.canGoForward()){
                    webview.goForward();
                    setOperationImageStatus();
                }
            }
        });

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webview.reload();
            }
        });

        webview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isUserClicked=true;
                return false;
            }
        });
    }
    public void onProgressChanged(int newProgress) {
        loadingProgressBar.setVisibility(View.VISIBLE);
        if (newProgress >= 0 && newProgress < 100) {
            loadingProgressBar.setProgress(newProgress);
        } else {
            loadingProgressBar.setVisibility(View.GONE);
        }
    }

    private void setOperationImageStatus() {
        if (webview.canGoBack()) {
            backButton.setImageResource(R.drawable.selector_common_btn_back);
            backButton.setEnabled(true);
        } else {
            backButton.setImageResource(R.drawable.common_btn_back_disable);
            backButton.setEnabled(false);
        }

        if (webview.canGoForward()) {
            forwardButton.setImageResource(R.drawable.selector_common_btn_ahead);
            forwardButton.setVisibility(View.VISIBLE);
        } else {
            forwardButton.setVisibility(View.GONE);
        }
    }

    public void openBrowser() {
        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            String url_browser = removeTicketParam(webview.getUrl());
            Uri content_url = Uri.parse(url_browser);
            intent.setData(content_url);
            startActivity(intent);
        } catch (Exception e) {
            ToastUtils.showToast(this, "请安装第三方浏览器后打开!");
        }
    }
    /**
     * 用浏览器打开，去除ticket参数
     *
     * @param url
     * @return
     */
    private String removeTicketParam(final String url) {
        String result = "";
        if (url == null) {
            return result;
        }
        int index = url.lastIndexOf("ticket=");
        if (index > 0) {
            result = url.substring(0, index - 1);
        } else {
            result = url;
        }
        return result;
    }

    public abstract String getStartUrl();
    //抽象方法子类实现获取webview布局
    public abstract int getLayoutId();
    //获取webview容器id
   // public abstract int getRootViewId();
    //获取webviewid
    public abstract int getWebViewId();

    public abstract int getBottomOperationLayoutId();

    public abstract  int getBottomBackImageId();

    public abstract int getBottomAheadImageId();

    public abstract int getBottomRefreshImageId();

    public abstract int getLoadingProgressBar();

    public abstract File createImageFile() throws IOException;

    //public abstract void setTitleBarTitle(String title);

    //public abstract void selectPicFile();
}
