package com.app.exceptions;

/**
 * Created by asaraf on 20/04/2016.
 */
public class UserAlreadyExistsException extends Exception {

    public static final String USER_ALREADY_EXISTS = "User already exists!";

    public UserAlreadyExistsException() {
        super(USER_ALREADY_EXISTS);
    }
}

