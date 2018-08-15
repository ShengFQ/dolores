
package com.dolores.store.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.dolores.store.R;
import com.dolores.store.adapter.NewFriendsMsgAdapter;
import com.dolores.store.db.InviteMessgeDao;
import com.dolores.store.domain.InviteMessage;
import com.dolores.store.ui.base.EBaseActivity;
import java.util.Collections;
import java.util.List;

/**
 * 申请与通知页面
 *
 *
 */
public class NewFriendsMsgActivity extends EBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.em_activity_new_friends_msg);

		ListView listView = (ListView) findViewById(R.id.list);
		InviteMessgeDao dao = new InviteMessgeDao(this);
		List<InviteMessage> msgs = dao.getMessagesList();
		Collections.reverse(msgs);

		NewFriendsMsgAdapter adapter = new NewFriendsMsgAdapter(this, 1, msgs);
		listView.setAdapter(adapter);
		dao.saveUnreadMessageCount(0);
		
	}

	public void back(View view) {
		finish();
	}
}
