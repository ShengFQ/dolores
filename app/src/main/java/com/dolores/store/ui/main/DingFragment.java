package com.dolores.store.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dolores.store.R;
import com.hyphenate.easeui.ui.EaseConversationListFragment;

import butterknife.ButterKnife;
/**
 *
 * Conversation Fragment by shengfq
 * h会话fragment
 * */
public class DingFragment extends EaseConversationListFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ding, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}