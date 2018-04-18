package com.dolores.store.lightapp.runtime;

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

/**
 * Created by sheng on 18/4/18.
 */

public abstract class AbsWebViewAppActivity extends BaseSwipeBackActivity {

    private boolean isUserClicked = false; //是否用户对界面有点击
    public IWebView webview;
    private LinearLayout layout_webview;
    private ImageView backButton, forwardButton, refreshButton;
    private ProgressBar loadingProgressBar;
    private RelativeLayout bottom_operation_layout;//底部操作栏

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        this.setContentView(getLayoutId());
        //避免输入法界面弹出后遮挡输入光标的问题
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        webview=new X5WebView(this);
        layout_webview = (LinearLayout) findViewById(getRootViewId());
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
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



    //抽象方法子类实现获取webview布局
    public abstract int getLayoutId();
    //获取webview容器id
    public abstract int getRootViewId();
    //获取webviewid
    public abstract int getWebViewId();

    public abstract int getBottomOperationLayoutId();

    public abstract  int getBottomBackImageId();

    public abstract int getBottomAheadImageId();

    public abstract int getBottomRefreshImageId();

    public abstract int getLoadingProgressBar();
}
