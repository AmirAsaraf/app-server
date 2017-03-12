package com.app.model;

import com.couchbase.client.java.document.json.*;

/**
 * Created by gs082r on 12/29/2016.
 */
public class User {
    private UserRecord internalDbUser;
    public User(UserRecord user) {
        internalDbUser = user;
    }

    public String GetUserName() {
        return internalDbUser.getUserName();
    }
    public void SetUserName(String name) {
        internalDbUser.setUserName(name);
    }

    public String GetFullName() {
        return internalDbUser.getFullName();
    }
    public void SetFullName(String name) {
        internalDbUser.setFullName(name);
    }

    public boolean isActive() {
        return internalDbUser.isActive();
    }

    public void deActivate() {
        internalDbUser.setActive(false);
    }

    public void setRights(Boolean isAdmin, Boolean canView, Boolean CanModify) {
        JsonObject rights = JsonObject.create()
                .put("isAdmin", isAdmin)
                .put("canView", canView)
                .put("canModify", CanModify);

        internalDbUser.setRole(rights.toString()); // JsonDocument.create("userRights", rights).toString();
    }

    public Boolean isAdmin() {
        JsonObject rights = JsonObject.fromJson(internalDbUser.getRole());
        return rights.getBoolean("isAdmin");
    }
    public Boolean canView() {
        JsonObject rights = JsonObject.fromJson(internalDbUser.getRole());
        return rights.getBoolean("canView");
    }
    public Boolean canModify() {
        JsonObject rights = JsonObject.fromJson(internalDbUser.getRole());
        return rights.getBoolean("canModify");
    }

    public Boolean checkpassword(String pwd) {
        String saltedPwd = "sAlT" + "admin" + "SaLt";
        byte[] encodedBytes = java.util.Base64.getEncoder().encode(saltedPwd.getBytes());

        return internalDbUser.getPassword().equals(new String(encodedBytes));
    }

    public void changepassword(String pwd) {
        String saltedPwd = "sAlT" + "admin" + "SaLt";
        byte[] encodedBytes = java.util.Base64.getEncoder().encode(saltedPwd.getBytes());

        internalDbUser.setPassword(new String(encodedBytes));
    }

    public UserRecord getUser() {
        return internalDbUser;
    }
}
