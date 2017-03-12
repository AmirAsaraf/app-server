package com.app.model;

import java.io.Serializable;

/**
 * Created by gs082r on 12/29/2016.
 */
public class UserRecord implements Serializable {
    private Integer userId;
    private String userName;
    private String fullName;
    private String password;
    private String role;
    private boolean active;

    public boolean equals(Object obj) {
        if (!(obj instanceof UserRecord)) {
            return false;
        }

        UserRecord other = (UserRecord) obj;
        if (this.userName.equals(other.userName)) {
            return true;
        }
        return false;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
