package com.dolores.store.ui.main;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dolores.store.DoloHelper;
import com.dolores.store.DoloresApplication;
import com.dolores.store.R;
import com.dolores.store.http.LoginRequest;
import com.dolores.store.http.NetworkTask;
import com.dolores.store.ui.base.BaseActivity;
import com.dolores.store.util.LogUtils;
import com.dolores.store.util.TitleUtils;
import com.dolores.store.util.ToastUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.utils.EaseCommonUtils;

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
    private boolean progressShow;
    private boolean autoLogin = false;
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
        if (!EaseCommonUtils.isNetWorkConnected(this)) {
            Toast.makeText(this, R.string.network_isnot_available, Toast.LENGTH_SHORT).show();
            return;
        }
        String currentUsername = etMobile.getText().toString().trim();
        String currentPassword = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(currentUsername)) {
            Toast.makeText(this, R.string.User_name_cannot_be_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(currentPassword)) {
            Toast.makeText(this, R.string.Password_cannot_be_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        progressShow = true;
        final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
        pd.setCanceledOnTouchOutside(false);
        pd.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                Log.d(TAG, "EMClient.getInstance().onCancel");
                progressShow = false;
            }
        });
        pd.setMessage(getString(R.string.Is_landing));
        pd.show();
        EMClient.getInstance().login(etMobile.getText().toString().trim(),etPassword.getText().toString().trim(),new EMCallBack(){

            @Override
            public void onSuccess() {
                LogUtils.i(new StringBuffer("登录成功").toString());

                //服务器验证 TODO
                LoginRequest loginRequest=new LoginRequest();
                loginRequest.loginId=etMobile.getText().toString().trim();
                loginRequest.passwd=etPassword.getText().toString().trim();


                // ** manually load all local groups and conversation
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                // update current user's display name for APNs
                boolean updatenick = EMClient.getInstance().pushManager().updatePushNickname(
                        DoloresApplication.currentUserNick.trim());
                if (!updatenick) {
                    LogUtils.e("update current user nick fail");
                }
                //确保loginactivity没有被销毁
                if (!LoginActivity.this.isFinishing() && pd.isShowing()) {
                    pd.dismiss();
                }
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(int code, String error) {
                if (!LoginActivity.this.isFinishing() && pd.isShowing()) {
                    pd.dismiss();
                }
                ToastUtils.showToast(LoginActivity.this,new StringBuffer("登录出错").append(error).toString());
            }

            @Override
            public void onProgress(int progress, String status) {

            }
        });

    }
}