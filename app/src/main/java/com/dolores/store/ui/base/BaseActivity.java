package com.dolores.store.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.android.volley.Request;
import com.dolores.store.R;
import com.dolores.store.http.HttpClient;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by shengfq on 23/3/18
 * */
public abstract class BaseActivity extends AppCompatActivity {

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
        HttpClient.cancelAll(this);
    }

}