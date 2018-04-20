package com.dolores.store.lightapp.runtime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.dolores.store.ui.NewsWebviewActivity;
import com.hyphenate.chat.EMMessage;

/**
 * Created by sheng on 18/4/20.
 * 使用自定义的webview打开网页服务
 */

public class WebviewJump {
    public static final String INTENT_KEY_WEBVIEW_URL="webviewUrl",INTENT_KEY_TITLE_NAME="titleName",INTENT_KEY_EMMESSAGE="EMMessage";

    public static void gotoNewsWebviewActivity(final Activity activity, String url, String titleName, final EMMessage emMessage){
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(INTENT_KEY_WEBVIEW_URL, url);
        bundle.putString(INTENT_KEY_TITLE_NAME, titleName);
        bundle.putParcelable(INTENT_KEY_EMMESSAGE, emMessage);
        intent.putExtras(bundle);
        intent.setClass(activity, NewsWebviewActivity.class);
        activity.startActivity(intent);
    }
}
