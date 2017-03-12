package com.app.services;

import com.app.exceptions.UserAlreadyExistsException;
import com.app.exceptions.UserNotExistsException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.app.model.User;
import com.app.model.UserRecord;
import com.app.model.SessionRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by asaraf on 28/12/2016.
 */
public class UsersService {

    final           Logger          logger          = LogManager.getLogger(UsersService.class.getName());

    private static UsersService usersService =      null;
    private static List<UserRecord> userList;
    private static List<SessionRecord> sessionList = new ArrayList<>();

    private Integer currentUser;

    private UsersService() {
        DatabaseWrapper d = DatabaseWrapper.getInstance();
        userList = d.GetAllUsers();
    }

    public List<UserRecord> GetAllUsers() throws UserNotExistsException {
        if (userList.isEmpty()) {
            throw new UserNotExistsException();
        }

        List<UserRecord> newlist = new ArrayList<UserRecord>();

        for (UserRecord user:userList) {
            if (user.isActive()) {
                newlist.add(user);
            }
        }

        if (newlist.isEmpty()) {
            throw new UserNotExistsException();
        }

        return newlist;
    }

    public static UsersService getInstance() {
        if (usersService == null) {
            usersService = new UsersService();
        }
        return usersService;
    }

    public Boolean isValidSession(String sessionId, Set<String> rights) {
        currentUser = -1;

        int index = sessionList.indexOf(sessionId);
        if (index < 0) {
            return false;
        }

        SessionRecord session = sessionList.get(index);
        UserRecord record = new UserRecord();
        record.setUserName(session.getUserName());

        int userindex = userList.indexOf(record);
        if (userindex < 0) {
            return false;
        }

        record = userList.get(userindex);
        User u = new User(record);
        currentUser = record.getUserId();

        for (String right: rights) {
            if (right == "ADMIN" && record.isActive() && u.isAdmin()) {
                return true;
            }

            if (right == "MODIFY" && record.isActive() && u.canModify()) {
                return true;
            }

            if (right == "VIEW" && record.isActive() && u.canView()) {
                return true;
            }
        }

        return false;
    }

    public UserRecord getUserById(String id) throws UserNotExistsException {
        try {
            UserRecord ur = userList.get(Integer.getInteger(id));
            return ur;
        }
        catch(Exception e) {
            throw new UserNotExistsException();
        }
    }

    public UserRecord addUser(UserRecord userRecord) throws UserAlreadyExistsException {
        int index = userList.indexOf(userRecord);
        if (index >= 0) {
            throw new UserAlreadyExistsException();
        }

        User user = new User(userRecord);

        user.changepassword(userRecord.getPassword());

        DatabaseWrapper d = DatabaseWrapper.getInstance();
        d.AddUser(user.getUser());

        index = userList.indexOf(userRecord);
        return userList.get(index);
    }

    public void changeUser(String id, UserRecord userRecord) throws UserNotExistsException {
        int index = userList.indexOf(userRecord);
        if (index < 0) {
            throw new UserNotExistsException();
        }

        User user = new User(userRecord);
        user.changepassword(userRecord.getPassword());

        DatabaseWrapper d = DatabaseWrapper.getInstance();
        d.ReplaceUser(user.getUser());
    }

    public void deleteUser(String id) throws UserNotExistsException {
        UserRecord ur = null;
        try {
            ur = userList.get(Integer.parseInt(id));
        }
        catch(Exception e) {
            throw new UserNotExistsException();
        }

        User user = new User(ur);
        user.deActivate();

        DatabaseWrapper d = DatabaseWrapper.getInstance();
        d.ReplaceUser(user.getUser());
    }

    public Boolean Logout(String username) throws Exception {
        return true;
    }

    public String Login(String username, String password) throws UserNotExistsException {
        currentUser = -1;
        UserRecord user = new UserRecord();
        user.setUserName(username);

        int index = userList.indexOf(user);

        if (index < 0) {
            throw new UserNotExistsException();
        }

        user = userList.get(index);
        User u = new User(user);

        if (!u.checkpassword(password)) {
            throw new UserNotExistsException();
        }

        String newSessionToken = UUID.randomUUID().toString();
        SessionRecord record = new SessionRecord();
        record.setUserName(u.GetUserName());
        record.setSessionToken(newSessionToken);
        sessionList.add(record);
        currentUser = user.getUserId();

        return newSessionToken;
    }

    public Integer getCurrentUser() {
        return currentUser;
    }
}
