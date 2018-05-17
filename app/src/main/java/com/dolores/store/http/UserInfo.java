package com.dolores.store.http;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by sheng on 18/5/16.
 */

public class UserInfo extends Model implements Serializable {
    public String userName;
    public String userId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getAccid() {
        return accid;
    }

    public void setAccid(String accid) {
        this.accid = accid;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getUserIdExt() {
        return userIdExt;
    }

    public void setUserIdExt(String userIdExt) {
        this.userIdExt = userIdExt;
    }

    public String getAttachMaxSize() {
        return attachMaxSize;
    }

    public void setAttachMaxSize(String attachMaxSize) {
        this.attachMaxSize = attachMaxSize;
    }

    public String token;
    public String tokenId;
    public String deptName;
    public String accid;
    public String loginId;
    public String userIdExt;
    public String attachMaxSize;
    @Override
    public UserInfo fromJson(String json) {
        Gson gson=new Gson();
       return gson.fromJson(json,UserInfo.class);
    }
}
