package com.dolores.store.util;

import android.content.Context;

import com.dolores.store.R;

/**
 * Created by sheng on 18/4/20.
 */

public class MemoryUtils {
    /**
     * 复制文本到剪切板
     * @param context
     * @param text
     */
    public static void copyText(Context context, String text) {

        if(text == null) {
            return;
        }

        if(android.os.Build.VERSION.SDK_INT>=11)
        {
            android.content.ClipboardManager cm = (android.content.ClipboardManager) context
                    .getSystemService(Context.CLIPBOARD_SERVICE);
            cm.setText(text);
        }
        else {
            android.text.ClipboardManager cm = (android.text.ClipboardManager) context
                    .getSystemService(Context.CLIPBOARD_SERVICE);
            cm.setText(text);
        }

        ToastUtils.showToast(context, R.string.toast_copy_text_success);
    }

}
