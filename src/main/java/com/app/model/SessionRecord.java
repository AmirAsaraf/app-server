package com.app.model;

/**
 * Created by gs082r on 1/2/2017.
 */
public class SessionRecord {
    private String sessionToken;
    private String userName;

    public boolean equals(Object obj) {
        if (!(obj instanceof SessionRecord)) {
            return false;
        }

        SessionRecord other = (SessionRecord) obj;
        if (this.sessionToken.equals(other.sessionToken)) {
            return true;
        }
        return false;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSessionToken() {

        return sessionToken;
    }

    public String getUserName() {
        return userName;
    }
}

