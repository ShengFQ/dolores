package com.dolores.store.ui.main;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dolores.store.DoloresApplication;
import com.dolores.store.R;
import com.dolores.store.ui.base.BaseActivity;
import com.dolores.store.util.LogUtils;
import com.dolores.store.util.TitleUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.utils.EaseCommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends BaseActivity {
    private final String TAG=LoginActivity.class.getSimpleName();
    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.et_password)
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

    @OnClick(R.id.tv_register)
    void postRegister() {
        startActivityForResult(new Intent(this, RegisterActivity.class), 0);
    }

    @OnClick(R.id.btn_sign_in)
    void postLogin() {

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
                Toast.makeText(LoginActivity.this, new StringBuffer("登录成功").toString(), Toast.LENGTH_SHORT).show();
                //TODO ** manually load all local groups and conversation
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
                Toast.makeText(LoginActivity.this, new StringBuffer("登录出错").append(error).toString(), Toast.LENGTH_SHORT).show();
                if (!LoginActivity.this.isFinishing() && pd.isShowing()) {
                    pd.dismiss();
                }
            }

            @Override
            public void onProgress(int progress, String status) {
                Toast.makeText(LoginActivity.this, new StringBuffer("登录中..").toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}