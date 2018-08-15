package com.dolores.store.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dolores.store.Constants;
import com.dolores.store.R;
import com.dolores.store.db.InviteMessgeDao;
import com.dolores.store.ui.ChatActivity;
import com.dolores.store.util.LogUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.easeui.model.EaseDingMessageHelper;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.util.NetUtils;

/**
 *
 * @author shengfq
 * 首页页签-消息
 * */
public class DingFragment extends EaseConversationListFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        LogUtils.i("DingFragment.onCreate()");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        LogUtils.i("DingFragment.onActivityCreated()");
        super.onActivityCreated(savedInstanceState);
    }

   /* @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LogUtils.i("DingFragment.onCreateView()");
        View rootView = inflater.inflate(R.layout.fragment_ding, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }*/
    TextView errorText;

    @Override
    protected void initView() {
        LogUtils.i("DingFragment.initView()");
        super.initView();
        View errorView = (LinearLayout) View.inflate(getActivity(),R.layout.em_chat_neterror_item, null);
        errorItemContainer.addView(errorView);
        errorText = (TextView) errorView.findViewById(R.id.tv_connect_errormsg);
    }

    @Override
    protected void setUpView(){
        //注册上下文菜单
        registerForContextMenu(conversationListView);
        conversationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EMConversation conversation=conversationListView.getItem(position);
               String username= conversation.conversationId();
                if(username.equals(EMClient.getInstance().getCurrentUser())){
                    //don't talk to yourself

                }else{
                    Intent intent=new Intent(getActivity(), ChatActivity.class);
                    if(conversation.isGroup()){
                        if(conversation.getType()== EMConversation.EMConversationType.ChatRoom){
                            intent.putExtra(Constants.EXTRA_CHAT_TYPE,Constants.CHATTYPE_CHATROOM);
                        }else{
                            intent.putExtra(Constants.EXTRA_CHAT_TYPE,Constants.CHATTYPE_GROUP);
                        }
                    }
                    //other is single chat
                    intent.putExtra(Constants.EXTRA_CHAT_TYPE,Constants.CHATTYPE_SINGLE);
                    intent.putExtra(Constants.EXTRA_USER_ID, username);
                    startActivity(intent);
                }
            }
        });
        super.setUpView();
    }

    @Override
    protected void onConnectionDisconnected() {
        LogUtils.i("DingFragment.onConnectionDisconnected()");
        super.onConnectionDisconnected();
        if (NetUtils.hasNetwork(getActivity())){
            errorText.setText(R.string.can_not_connect_chat_server_connection);
        } else {
            errorText.setText(R.string.the_current_network);
        }
    }
    /**
     * FragmentState method
     * */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.em_delete_message, menu);
    }

    /**
     * FragmentState method
     * */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        boolean deleteMessage = false;
        if (item.getItemId() == R.id.delete_message) {
            deleteMessage = true;
        } else if (item.getItemId() == R.id.delete_conversation) {
            deleteMessage = false;
        }
        EMConversation tobeDeleteCons = conversationListView.getItem(((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position);
        if (tobeDeleteCons == null) {
            return true;
        }
        if(tobeDeleteCons.getType() == EMConversation.EMConversationType.GroupChat){
            EaseAtMessageHelper.get().removeAtMeGroup(tobeDeleteCons.conversationId());
        }
        try {
            // delete conversation
            EMClient.getInstance().chatManager().deleteConversation(tobeDeleteCons.conversationId(), deleteMessage);
            InviteMessgeDao inviteMessgeDao = new InviteMessgeDao(getActivity());
            inviteMessgeDao.deleteMessage(tobeDeleteCons.conversationId());
            // To delete the native stored adked users in this conversation.
            if (deleteMessage) {
                EaseDingMessageHelper.get().delete(tobeDeleteCons);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        refresh();

        // update unread count
        ((MainActivity) getActivity()).updateUnreadLabel();
        return true;
    }

}