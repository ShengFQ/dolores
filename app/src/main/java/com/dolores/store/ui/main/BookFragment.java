package com.dolores.store.ui.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dolores.store.DoloHelper;
import com.dolores.store.R;
import com.dolores.store.db.InviteMessgeDao;
import com.dolores.store.widget.ContactItemView;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;

import java.util.Hashtable;
import java.util.Map;

import butterknife.ButterKnife;
/**
 * ContactList Fragment
 * */
public class BookFragment extends EaseContactListFragment {
    private InviteMessgeDao inviteMessgeDao;
    private ContactItemView applicationItem;
    private View loadingView;
    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_book, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }*/

    @SuppressLint("InflateParams")
    @Override
    protected void initView() {
        super.initView();
        @SuppressLint("InflateParams") View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.em_contacts_header, null);
        HeaderItemClickListener clickListener = new HeaderItemClickListener();
        applicationItem = (ContactItemView) headerView.findViewById(R.id.application_item);
        applicationItem.setOnClickListener(clickListener);
        headerView.findViewById(R.id.group_item).setOnClickListener(clickListener);
        headerView.findViewById(R.id.chat_room_item).setOnClickListener(clickListener);
        headerView.findViewById(R.id.robot_item).setOnClickListener(clickListener);
        headerView.findViewById(R.id.conference_item).setOnClickListener(clickListener);
        listView.addHeaderView(headerView);
        //add loading view
        loadingView = LayoutInflater.from(getActivity()).inflate(R.layout.em_layout_loading_data, null);
        contentContainer.addView(loadingView);

        registerForContextMenu(listView);
    }

    @Override
    public void refresh() {
        Map<String, EaseUser> m = DoloHelper.getInstance().getContactList();
        if (m instanceof Hashtable<?, ?>) {
            //noinspection unchecked
            m = (Map<String, EaseUser>) ((Hashtable<String, EaseUser>)m).clone();
        }
        setContactsMap(m);
        super.refresh();
        if(inviteMessgeDao == null){
            inviteMessgeDao = new InviteMessgeDao(getActivity());
        }
        if(inviteMessgeDao.getUnreadMessagesCount() > 0){
            applicationItem.showUnreadMsgView();
        }else{
            applicationItem.hideUnreadMsgView();
        }
    }
    protected class HeaderItemClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.application_item:
                    // 进入申请与通知页面
                   // startActivity(new Intent(getActivity(), NewFriendsMsgActivity.class));
                    break;
                case R.id.group_item:
                    // 进入群聊列表页面
                 //   startActivity(new Intent(getActivity(), GroupsActivity.class));
                    break;
                case R.id.chat_room_item:
                    //进入聊天室列表页面
                 //   startActivity(new Intent(getActivity(), PublicChatRoomsActivity.class));
                    break;
                case R.id.robot_item:
                    //进入Robot列表页面
                 //   startActivity(new Intent(getActivity(), RobotsActivity.class));
                    break;
                case R.id.conference_item:
                  //  startActivity(new Intent(getActivity(), ConferenceActivity.class).putExtra(Constant.EXTRA_CONFERENCE_IS_CREATOR, true));
                    break;
                default:
                    break;
            }
        }

    }
}