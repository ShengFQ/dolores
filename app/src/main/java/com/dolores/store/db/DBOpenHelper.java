package com.dolores.store.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dolores.store.DoloHelper;

/**
 * Created by sheng on 18/4/9.
 * 负责数据库表的建立升级
 */

public class DbOpenHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 6;
    private static DbOpenHelper instance;

    private static final String USER_TABLE_CREATE=new StringBuffer().append("CREATE TABLE ")
            .append(UserDao.USERS_TABLE_NAME).append(" (")
            .append(UserDao.COLUMN_NAME_NICK).append(" TEXT, ")
            .append(UserDao.COLUMN_NAME_AVATAR).append(" TEXT, ")
            .append(UserDao.COLUMN_NAME_ID).append(" TEXT PRIMARY KEY );").toString();

    private static final String INIVTE_MESSAGE_TABLE_CREATE=new StringBuffer().append("CREATE TABLE ")
            .append(InviteMessgeDao.TABLE_NAME).append(" (")
            .append(InviteMessgeDao.COLUMN_NAME_ID).append("  INTEGER PRIMARY KEY AUTOINCREMENT, ")
            .append(InviteMessgeDao.COLUMN_NAME_FROM).append(" TEXT, ")
            .append(InviteMessgeDao.COLUMN_NAME_GROUP_ID).append(" TEXT, ")
            .append(InviteMessgeDao.COLUMN_NAME_GROUP_Name).append(" TEXT, ")
            .append(InviteMessgeDao.COLUMN_NAME_REASON).append(" TEXT, ")
            .append(InviteMessgeDao.COLUMN_NAME_STATUS).append(" INTEGER, ")
            .append(InviteMessgeDao.COLUMN_NAME_ISINVITEFROMME).append(" INTEGER, ")
            .append(InviteMessgeDao.COLUMN_NAME_UNREAD_MSG_COUNT).append(" INTEGER, ")
            .append(InviteMessgeDao.COLUMN_NAME_TIME).append(" TEXT, ")
            .append(InviteMessgeDao.COLUMN_NAME_GROUPINVITER).append(" TEXT );").toString();

    private static final String ROBOT_TABLE_CREATE=new StringBuffer().append("CREATE TABLE ")
            .append(UserDao.ROBOT_TABLE_NAME).append(" (")
            .append(UserDao.ROBOT_COLUMN_NAME_ID).append(" TEXT PRIMARY KEY, ")
            .append(UserDao.ROBOT_COLUMN_NAME_NICK).append(" TEXT, ")
            .append(UserDao.ROBOT_COLUMN_NAME_AVATAR).append(" TEXT );").toString();

    private static final String CREATE_PREF_TABLE=new StringBuffer().append("CREATE TABLE ")
            .append(UserDao.PREF_TABLE_NAME).append(" (")
            .append(UserDao.COLUMN_NAME_DISABLED_GROUPS).append(" TEXT, ")
            .append(UserDao.COLUMN_NAME_DISABLED_IDS).append(" TEXT );").toString();

    private DbOpenHelper(Context context) {
        super(context, getUserDatabaseName(), null, DATABASE_VERSION);
    }
    public static DbOpenHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DbOpenHelper(context.getApplicationContext());
        }
        return instance;
    }
    private static String getUserDatabaseName() {
        return  DoloHelper.getInstance().getCurrentUsernName() + "_demo.db";
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USER_TABLE_CREATE);
        db.execSQL(INIVTE_MESSAGE_TABLE_CREATE);
        db.execSQL(ROBOT_TABLE_CREATE);
        db.execSQL(CREATE_PREF_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion < 2){
            db.execSQL("ALTER TABLE "+ UserDao.USERS_TABLE_NAME +" ADD COLUMN "+
                    UserDao.COLUMN_NAME_AVATAR + " TEXT ;");
        }

        if(oldVersion < 3){
            db.execSQL(CREATE_PREF_TABLE);
        }
        if(oldVersion < 4){
            db.execSQL(ROBOT_TABLE_CREATE);
        }
        if(oldVersion < 5){
            db.execSQL("ALTER TABLE " + InviteMessgeDao.TABLE_NAME + " ADD COLUMN " +
                    InviteMessgeDao.COLUMN_NAME_UNREAD_MSG_COUNT + " INTEGER ;");
        }
        if (oldVersion < 6) {
            db.execSQL("ALTER TABLE " + InviteMessgeDao.TABLE_NAME + " ADD COLUMN " +
                    InviteMessgeDao.COLUMN_NAME_GROUPINVITER + " TEXT;");
        }
    }

    public void closeDB() {
        if (instance != null) {
            try {
                SQLiteDatabase db = instance.getWritableDatabase();
                db.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            instance = null;
        }
    }

}
