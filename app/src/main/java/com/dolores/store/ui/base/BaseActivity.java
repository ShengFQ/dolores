package com.dolores.store.ui.base;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.dolores.store.R;
import com.hyphenate.easeui.ui.EaseBaseActivity;


/**
 * Created by shengfq on 23/3/18
 * */
public abstract class BaseActivity extends EaseBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void hideKeyboard(View v) {
        if (this.getCurrentFocus() != null) {
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onBackPressed() {
        super.finish();
        overridePendingTransition(0, R.anim.slide_out_right);
    }

    public void onOkButtonClick() {
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}