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
package com.dolores.store.lightapp.runtime.androidwk;

import android.view.View;
import android.webkit.WebView;

import com.dolores.store.lightapp.runtime.AbsWebViewAppActivity;
import com.dolores.store.lightapp.runtime.AppNativeRequest;
import com.dolores.store.lightapp.runtime.AppNativeResponse;
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
    public void asynloadResult(AppNativeRequest req, AppNativeResponse resp, boolean isLoad) {

    }

    @Override
    public void setAsynLoadUrl(boolean isLoad) {

    }

    @Override
    public void setOnKeyBackFlag(boolean flag) {

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
