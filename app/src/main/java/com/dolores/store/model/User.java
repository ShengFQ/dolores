package com.dolores.store.model;

import com.dolores.store.http.Model;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;

/**
 * Created by sheng on 18/3/27.
 */

public class User extends Model{
    private static final HashMap<String, User> CACHE = new HashMap<String, User>();

    private String passwd;
    private String znName;
    private String userId;
    private String token;
    private String tokenId;
    private String deptName;
    private String accid;
    private String loginId;
    private String attachMaxSize;
    public String loginString(){
        String postdata="{\"loginId\":\""+loginId+"\",\"passwd\":\""
                +passwd+"\",\"appName\":\"android\",\"mobileVer\":\"1.3.1\",\"deviceId\":\"\"}";
        return postdata;
    }
    private static void addToCache(User user) {
        CACHE.put(user.getUserId(), user);
    }

    private static User getFromCache(long id) {
        return CACHE.get(id);
    }

    @Override
    public Model fromJson(String json) {
         return new Gson().fromJson(json, User.class);
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getZnName() {
        return znName;
    }

    public void setZnName(String znName) {
        this.znName = znName;
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

    public String getAttachMaxSize() {
        return attachMaxSize;
    }

    public void setAttachMaxSize(String attachMaxSize) {
        this.attachMaxSize = attachMaxSize;
    }

}
