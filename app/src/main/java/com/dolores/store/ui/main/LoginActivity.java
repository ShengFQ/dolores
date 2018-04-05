package com.dolores.store.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.dolores.store.Constants;
import com.dolores.store.R;
import com.dolores.store.http.AsynCallback;
import com.dolores.store.http.NdRequest;
import com.dolores.store.http.NdResponse;
import com.dolores.store.http.NetworkTask;
import com.dolores.store.model.User;
import com.dolores.store.ui.base.BaseActivity;
import com.dolores.store.util.LogUtils;
import com.dolores.store.util.TitleUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dolores.store.R.id.et_password;

public class LoginActivity extends BaseActivity {
    private final String TAG=LoginActivity.class.getSimpleName();
    @Bind(R.id.et_mobile)
    EditText etMobile;
    @Bind(et_password)
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(EMClient.getInstance().isLoggedInBefore()){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            return;
        }
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initTitle();
    }

    private void initTitle() {
        TitleUtils.hideBack(this);
        TitleUtils.setTitle(this, R.string.action_login);
    }

    @OnClick({R.id.btn_sign_in, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_sign_in:
                postLogin();
                break;
            case R.id.tv_register:
                postRegister();
                break;
        }
    }

    private void postRegister(){
        startActivityForResult(new Intent(this, RegisterActivity.class), 0);
    }
    private void postLogin() {
        /*StringRequest request = new StringRequest(Constants.LOGIN_URL, Request.Method.POST, map, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtils.showToast(LoginActivity.this, HttpUtil.checkErrorType(error));
            }
        });
        DoloresApplication.httpClient.addRequest(request);*/
        EMClient.getInstance().login(etMobile.getText().toString().trim(),etPassword.getText().toString().trim(),new EMCallBack(){

            @Override
            public void onSuccess() {
                LogUtils.i(new StringBuffer("登录成功").toString());

                // ** manually load all local groups and conversation
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();


                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(int code, String error) {
                LogUtils.e(new StringBuffer("登录出错").append("code ").append(code).append("error ").append(error).toString());
            }

            @Override
            public void onProgress(int progress, String status) {

            }
        });

    }
}