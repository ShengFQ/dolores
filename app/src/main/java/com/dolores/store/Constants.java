package com.dolores.store;

import com.hyphenate.easeui.EaseConstant;

public class Constants extends EaseConstant {

    public static final String PACKAGE_NAME = "com.dolores.store";
    public static final String APPID_BUGLY="900002908";
    private static final String VALUE_ENVIRON_TEST = "http://www.sznoda.com:7890/oa/mobile/";
    private static final String VALUE_ENVIRON_RELEASE = "http://www.sznoda.com:7890/oa/mobile/";
    public static final String EASEMOB_APPKEY="1100180329228561#dolores";
    public static final String EASEMOB_ORGNAME="1100180329228561";
    public static final String EASEMOB_APPNAME="dolores";
    public static final String APPID_MIPUSH="2882303761517764189";
    public static final String APPKEY_MIPUSH="5401776414189";

    //from easeuidemo
    public static final String NEW_FRIENDS_USERNAME = "item_new_friends";
    public static final String GROUP_USERNAME = "item_groups";
    public static final String CHAT_ROOM = "item_chatroom";
    public static final String ACCOUNT_REMOVED = "account_removed";
    public static final String ACCOUNT_CONFLICT = "conflict";
    public static final String ACCOUNT_FORBIDDEN = "user_forbidden";
    public static final String ACCOUNT_KICKED_BY_CHANGE_PASSWORD = "kicked_by_change_password";
    public static final String ACCOUNT_KICKED_BY_OTHER_DEVICE = "kicked_by_another_device";
    public static final String CHAT_ROBOT = "item_robots";
    public static final String MESSAGE_ATTR_ROBOT_MSGTYPE = "msgtype";
    public static final String ACTION_GROUP_CHANAGED = "action_group_changed";
    public static final String ACTION_CONTACT_CHANAGED = "action_contact_changed";

    public static final String EXTRA_CONFERENCE_ID = "confId";
    public static final String EXTRA_CONFERENCE_PASS = "password";
    public static final String EXTRA_CONFERENCE_IS_CREATOR = "is_creator";
    public static String BASE_URL = null;

    static {
        switch (BuildConfig.ENV_TYPE) {
            case 1:
                BASE_URL = Constants.VALUE_ENVIRON_TEST;
                break;
            case 2:
                BASE_URL = Constants.VALUE_ENVIRON_RELEASE;
                break;
        }
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static class RequestCode {
        public static final int MODIFY = 1;
    }


    public static String PROFILE_URL = "api/v1/profile";
}
