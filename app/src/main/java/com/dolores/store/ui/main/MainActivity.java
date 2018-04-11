package com.dolores.store.ui.main;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dolores.store.Constants;
import com.dolores.store.DoloHelper;
import com.dolores.store.R;
import com.dolores.store.db.InviteMessgeDao;
import com.dolores.store.runtimepermissions.PermissionsManager;
import com.dolores.store.runtimepermissions.PermissionsResultAction;
import com.dolores.store.ui.ChatActivity;
import com.dolores.store.ui.base.BaseActivity;
import com.dolores.store.ui.base.EBaseActivity;
import com.dolores.store.util.LogUtils;
import com.hyphenate.EMClientListener;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.EMMultiDeviceListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.util.EMLog;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends EBaseActivity {
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
    private BroadcastReceiver broadcastReceiver;
    private LocalBroadcastManager broadcastManager;
    private InviteMessgeDao inviteMessgeDao;

    private int currentTabIndex=0;//当前的TAB视图
    private final int TAB_INDEX_CONVERSATION=0;
    private final int TAB_INDEX_CONTACT=1;
    private final int TAB_INDEX_SETTING=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO 电池性能优化白名单
        //TODO 如果账号在其他机器上登录或者被删除的话,UI线程还在继续使用,踢出登录功能
        //TODO 单账号多机器登录冲突
        //运行时动态权限申请
        requestPermissions();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        switchFragment(0);
        inviteMessgeDao = new InviteMessgeDao(this);
        //由于后台或其他UI传递过来的异常处理
        showExceptionDialogFromIntent(getIntent());
        //注册广播接收器接收UI变更群组,联系人,黑名单的事件
        registerBroadcastReceiver();
        EMClient.getInstance().contactManager().setContactListener(new MyContactListener());
        EMClient.getInstance().addMultiDeviceListener(new MyMultiDeviceListener());
    }

    public void registerBroadcastReceiver(){
        broadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.ACTION_CONTACT_CHANAGED);
        intentFilter.addAction(Constants.ACTION_GROUP_CHANAGED);
        broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                updateUnreadLabel();
                updateUnreadAddressLable();
                if (currentTabIndex == TAB_INDEX_CONVERSATION) {
                    // refresh conversation list
                    if (dingFragment != null) {
                        dingFragment.refresh();
                    }
                } else if (currentTabIndex == TAB_INDEX_CONTACT) {
                    if(bookFragment != null) {
                        bookFragment.refresh();
                    }
                }
                String action = intent.getAction();
                if(action.equals(Constants.ACTION_GROUP_CHANAGED)){
                    /*if (EaseCommonUtils.getTopActivity(MainActivity.this).equals(GroupsActivity.class.getName())) {
                        GroupsActivity.instance.onResume();
                    }*/
                }
            }
        };
        broadcastManager.registerReceiver(broadcastReceiver, intentFilter);
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (!isConflict && !isCurrentAccountRemoved) {
            updateUnreadLabel();
            updateUnreadAddressLable();
        }
// unregister this event listener when this activity enters the
        // background
        DoloHelper sdkHelper = DoloHelper.getInstance();
        sdkHelper.pushActivity(this);

        EMClient.getInstance().chatManager().addMessageListener(messageListener);

    }

    @Override
    protected void onStop() {
        EMClient.getInstance().chatManager().removeMessageListener(messageListener);
        DoloHelper sdkHelper = DoloHelper.getInstance();
        sdkHelper.popActivity(this);
        super.onStop();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        showExceptionDialogFromIntent(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("isConflict", isConflict);
        outState.putBoolean(Constants.ACCOUNT_REMOVED, isCurrentAccountRemoved);
        super.onSaveInstanceState(outState);
    }
    /**
     * 切换fragment页签
     * */
    private void switchFragment(int which) {
        currentTabIndex=which;
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
            case TAB_INDEX_CONVERSATION://消息
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
            case TAB_INDEX_CONTACT://联系人
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
            case TAB_INDEX_SETTING://我的
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
            LogUtils.e("system.exit()");
            exit();
            //moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void back(View view) {
        super.back(view);
    }
    /**
     * 退出程序
     * */
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
                switchFragment(TAB_INDEX_CONVERSATION);
                break;
            case R.id.book_layout:
                switchFragment(TAB_INDEX_CONTACT);
                break;
            case R.id.mine_layout:
                switchFragment(TAB_INDEX_SETTING);
                break;
        }
    }

    /**
     * update unread message count
     */
    public void updateUnreadLabel() {
        int count = getUnreadMsgCountTotal();
        if (count > 0) {
            unreadLabel.setText(String.valueOf(count));
            unreadLabel.setVisibility(View.VISIBLE);
        } else {
            unreadLabel.setVisibility(View.INVISIBLE);
        }
    }
    /**
     * 收到消息后更新UI
     * */
    private void refreshUIWithMessage() {
        runOnUiThread(new Runnable() {
            public void run() {
                // refresh unread count
                updateUnreadLabel();
                if (currentTabIndex == TAB_INDEX_CONVERSATION) {
                    // refresh conversation list
                    if (dingFragment != null) {
                        dingFragment.refresh();
                    }
                }
            }
        });
    }
    /**
     * get unread message count
     *
     * @return
     */
    public int getUnreadMsgCountTotal() {
        return EMClient.getInstance().chatManager().getUnreadMsgsCount();
    }
    /**
     * update the total unread count
     */
    public void updateUnreadAddressLable() {
        runOnUiThread(new Runnable() {
            public void run() {
                int count = getUnreadAddressCountTotal();
                if (count > 0) {
                    unreadAddressLable.setVisibility(View.VISIBLE);
                } else {
                    unreadAddressLable.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    /**
     * get unread event notification count, including application, accepted, etc
     *
     * @return
     */
    public int getUnreadAddressCountTotal() {
        int unreadAddressCountTotal = 0;
        unreadAddressCountTotal = inviteMessgeDao.getUnreadMessagesCount();
        return unreadAddressCountTotal;
    }
    private boolean isExceptionDialogShow =  false;
    private android.app.AlertDialog.Builder exceptionBuilder;
    /**
     * 根据SDK发出的Intent给EaseBaseActivity的异常信息接收器弹出相应的
     * */
    private void showExceptionDialogFromIntent(Intent intent) {
        EMLog.e("MainActivity", "showExceptionDialogFromIntent");
        if (!isExceptionDialogShow && intent.getBooleanExtra(Constants.ACCOUNT_CONFLICT, false)) {
            showExceptionDialog(Constants.ACCOUNT_CONFLICT);
        } else if (!isExceptionDialogShow && intent.getBooleanExtra(Constants.ACCOUNT_REMOVED, false)) {
            showExceptionDialog(Constants.ACCOUNT_REMOVED);
        } else if (!isExceptionDialogShow && intent.getBooleanExtra(Constants.ACCOUNT_FORBIDDEN, false)) {
            showExceptionDialog(Constants.ACCOUNT_FORBIDDEN);
        } else if (intent.getBooleanExtra(Constants.ACCOUNT_KICKED_BY_CHANGE_PASSWORD, false) ||
                intent.getBooleanExtra(Constants.ACCOUNT_KICKED_BY_OTHER_DEVICE, false)) {
            this.finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
    /**
     * 出现异常时弹出提示框
     */
    private void showExceptionDialog(String exceptionType) {
        isExceptionDialogShow = true;
        DoloHelper.getInstance().logout(false,null);
        String st = getResources().getString(R.string.Logoff_notification);
        if (!MainActivity.this.isFinishing()) {
            // clear up global variables
            try {
                if (exceptionBuilder == null)
                    exceptionBuilder = new android.app.AlertDialog.Builder(MainActivity.this);
                exceptionBuilder.setTitle(st);
                exceptionBuilder.setMessage(getExceptionMessageId(exceptionType));
                exceptionBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        exceptionBuilder = null;
                        isExceptionDialogShow = false;
                        finish();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
                exceptionBuilder.setCancelable(false);
                exceptionBuilder.create().show();
                isConflict = true;
            } catch (Exception e) {
                EMLog.e("", "---------color conflictBuilder error" + e.getMessage());
            }
        }
    }
    private int getExceptionMessageId(String exceptionType) {
        if(exceptionType.equals(Constants.ACCOUNT_CONFLICT)) {
            return R.string.connect_conflict;
        } else if (exceptionType.equals(Constants.ACCOUNT_REMOVED)) {
            return R.string.em_user_remove;
        } else if (exceptionType.equals(Constants.ACCOUNT_FORBIDDEN)) {
            return R.string.user_forbidden;
        }
        return R.string.Network_error;
    }
    //////////////////////////////////////////////////////////
    /**
     * 联系人变更回调监听
     * **/
    public class MyContactListener implements EMContactListener {
        @Override
        public void onContactAdded(String username) {
            LogUtils.i("EMContactListener.onContactAdded"+username);
        }
        @Override
        public void onContactDeleted(final String username) {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (ChatActivity.activityInstance != null && ChatActivity.activityInstance.toChatUsername != null &&
                            username.equals(ChatActivity.activityInstance.toChatUsername)) {
                        String st10 = getResources().getString(R.string.have_you_removed);
                        Toast.makeText(MainActivity.this, ChatActivity.activityInstance.getToChatUsername() + st10, Toast.LENGTH_LONG)
                                .show();
                        ChatActivity.activityInstance.finish();
                    }
                }
            });
            updateUnreadAddressLable();
        }
        @Override
        public void onContactInvited(String username, String reason) {
            LogUtils.i("EMContactListener.onContactInvited"+username);
        }
        @Override
        public void onFriendRequestAccepted(String username) {
            LogUtils.i("EMContactListener.onFriendRequestAccepted"+username);
        }
        @Override
        public void onFriendRequestDeclined(String username) {
            LogUtils.i("EMContactListener.onFriendRequestDeclined"+username);
        }
    }
    /**
     * 单账号多设备踢出
     * */
    public class MyMultiDeviceListener implements EMMultiDeviceListener {

        @Override
        public void onContactEvent(int event, String target, String ext) {
            LogUtils.i("MyMultiDeviceListener.onContactEvent "+event);
        }

        @Override
        public void onGroupEvent(int event, String target, final List<String> username) {
            switch (event) {
                case EMMultiDeviceListener.GROUP_LEAVE:
                    ChatActivity.activityInstance.finish();
                    break;
                default:
                    break;
            }
        }
    }
///////////////////////////////////////
    /***
     * 动态权限获取
     * ****/
    @TargetApi(23)
    private void requestPermissions() {
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {
				Toast.makeText(MainActivity.this, "All permissions have been granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(String permission) {
                Toast.makeText(MainActivity.this, "Permission " + permission + " has been denied", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 消息接收监听器
     * **/
    EMMessageListener messageListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            // notify new message
            for (EMMessage message : messages) {
                DoloHelper.getInstance().getNotifier().onNewMsg(message);
            }
            refreshUIWithMessage();
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            refreshUIWithMessage();
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
        }

        @Override
        public void onMessageRecalled(List<EMMessage> messages) {
            refreshUIWithMessage();
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {}
    };
}