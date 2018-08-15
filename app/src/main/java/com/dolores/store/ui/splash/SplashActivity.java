package com.dolores.store.ui.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dolores.store.DoloHelper;
import com.dolores.store.R;
import com.dolores.store.ui.base.BaseActivity;
import com.dolores.store.ui.main.LoginActivity;
import com.dolores.store.ui.main.MainActivity;
import com.hyphenate.chat.EMClient;
import com.hyphenate.util.EasyUtils;

import butterknife.ButterKnife;

/**
 * @author shengfq
 *         开屏页
 */
public class SplashActivity extends BaseActivity {
    private static final int sleepTime = 2000;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash);
        super.onCreate(savedInstanceState);
        RelativeLayout rootLayout = (RelativeLayout) findViewById(R.id.splash_root);
        TextView versionText = (TextView) findViewById(R.id.tv_version);
        ButterKnife.bind(this);
        versionText.setText(getVersion());
        AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
        animation.setDuration(1500);
        rootLayout.startAnimation(animation);
    }

    @Override
    protected void onStart() {
        super.onStart();

        new Thread(new Runnable() {
            public void run() {
                if (DoloHelper.getInstance().isLoggedIn()) {
                    // 自动登录模式,进入主页前确保所有的群组和会话信息都已经加载完成
                    long start = System.currentTimeMillis();
                    EMClient.getInstance().chatManager().loadAllConversations();
                    EMClient.getInstance().groupManager().loadAllGroups();
                    long costTime = System.currentTimeMillis() - start;
                    //wait
                    if (sleepTime - costTime > 0) {
                        try {
                            Thread.sleep(sleepTime - costTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    String topActivityName = EasyUtils.getTopActivityName(EMClient.getInstance().getContext());
                    //enter main screen
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                } else {
                    //手动登录模式
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                    }
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }
        }).start();

    }

    @Override
    protected void onDestroy() {
        //handler.removeCallbacks(runnable);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.finish();
    }

    /**
     * get sdk version
     */
    private String getVersion() {
        return EMClient.getInstance().VERSION;
    }
}