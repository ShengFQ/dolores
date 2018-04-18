package com.dolores.store.ui;

import android.os.Bundle;

import com.dolores.store.R;
import com.dolores.store.lightapp.runtime.AbsWebViewAppActivity;

public class NewsWebviewActivity extends AbsWebViewAppActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_detail_x5);
    }
}
