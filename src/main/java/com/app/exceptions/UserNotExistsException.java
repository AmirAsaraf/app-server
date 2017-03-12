package com.app.exceptions;

/**
 * Created by asaraf on 20/04/2016.
 */
public class UserNotExistsException extends Exception {

    public static final String USER_NOT_FOUND = "User not found!";

    public UserNotExistsException() {
        super(USER_NOT_FOUND);
    }
}
