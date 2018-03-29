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
import com.dolores.store.util.TitleUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    private final String TAG=LoginActivity.class.getSimpleName();
    @Bind(R.id.et_mobile)
    EditText etMobile;
    @Bind(R.id.et_password)
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initTitle();
    }

    private void initTitle() {
        TitleUtils.hideBack(this);
        TitleUtils.setTitle(this, R.string.action_login);
    }

    @OnClick({R.id.btn_ok, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                postLogin("admin", "123456");
                break;
            case R.id.tv_register:
                break;
        }
    }

    private void postLogin(String username, String password) {
        Map<String, String> map = new HashMap<>();
        map.put("loginId", username);
        map.put("passwd", password);

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
       /* NetworkTask<Object> networkTask=new NetworkTask<Object>();
        NdRequest request=new NdRequest() {
            @Override
            public int getMethod() {
                return Request.Method.POST;
            }

            @Override
            public String getUrl() {
                return Constants.getBaseUrl()+Constants.LOGIN_URL;
            }

            public Map<String,String> getPostParams(){
                return null;
            }
        };
        NdResponse<Object> response=new NdResponse<Object>() {
            @Override
            public void processData(Object response) {
                Log.v(TAG,response.toString());
            }

            @Override
            public void processError(Object error) {

            }

            @Override
            public Class getResponseDataClass() {
                return Object.class;
            }
        };
        AsynCallback<NdResponse> callback=new AsynCallback<NdResponse>() {
            @Override
            public void callback(NdResponse r) {

            }
        };
        networkTask.startHttpTask(request,response,callback);*/
        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}