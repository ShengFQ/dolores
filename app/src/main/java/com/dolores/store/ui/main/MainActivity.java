package com.dolores.store.ui.main;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dolores.store.R;
import com.dolores.store.ui.base.BaseActivity;
import com.hyphenate.chat.EMClient;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @Bind(R.id.unread_msg_number)
    TextView unreadLabel;
    // textview for unread event message
    @Bind(R.id.unread_address_number)
     TextView unreadAddressLable;
    @Bind(R.id.ding_layout)
    RelativeLayout dingLayout;
    @Bind(R.id.book_layout)
    RelativeLayout bookLayout;
    @Bind(R.id.mine_layout)
    RelativeLayout mineLayout;
    private DingFragment dingFragment;
    private BookFragment bookFragment;
    private MineFragment mineFragment;
    private long exitTime;
    // user logged into another device
    public boolean isConflict = false;
    // user account was removed
    private boolean isCurrentAccountRemoved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO 电池性能优化白名单
        //TODO 如果账号在其他机器上登录或者被删除的话,UI线程还在继续使用,踢出登录功能
        //TODO 单账号多机器登录冲突
        //TODO 运行时动态权限申请

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        switchFragment(0);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 切换fragment页签
     * */
    private void switchFragment(int which) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (dingFragment != null) {
            ft.hide(dingFragment);
        }
        if (bookFragment != null) {
            ft.hide(bookFragment);
        }
        if (mineFragment != null) {
            ft.hide(mineFragment);
        }
        switch (which) {
            case 0://消息
                dingLayout.setSelected(true);
                bookLayout.setSelected(false);
                mineLayout.setSelected(false);
                if (dingFragment == null) {
                    dingFragment = new DingFragment();
                    ft.add(R.id.fragment_container, dingFragment);
                } else {
                    ft.show(dingFragment);
                }
                break;
            case 1://联系人
                dingLayout.setSelected(false);
                bookLayout.setSelected(true);
                mineLayout.setSelected(false);
                if (bookFragment == null) {
                    bookFragment = new BookFragment();
                    ft.add(R.id.fragment_container, bookFragment);
                } else {
                    ft.show(bookFragment);
                }
                break;
            case 2://我的
                dingLayout.setSelected(false);
                bookLayout.setSelected(false);
                mineLayout.setSelected(true);
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                    ft.add(R.id.fragment_container, mineFragment);
                } else {
                    ft.show(mineFragment);
                }
                break;
        }
        ft.commitAllowingStateLoss();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, R.string.msg_quit, Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

    @OnClick({R.id.ding_layout, R.id.book_layout, R.id.mine_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ding_layout:
                switchFragment(0);
                break;
            case R.id.book_layout:
                switchFragment(1);
                break;
            case R.id.mine_layout:
                switchFragment(2);
                break;
        }
    }


}