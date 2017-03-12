package com.app.model;

import java.io.Serializable;

/**
 * Created by aasaraf on 1/1/2017.
 */
public class Credentials implements Serializable {

    private String userName;

    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
