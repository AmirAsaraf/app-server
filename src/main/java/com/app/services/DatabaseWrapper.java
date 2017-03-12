package com.app.services;

import com.couchbase.client.java.*;
import com.couchbase.client.java.document.*;
import com.couchbase.client.java.document.json.*;
import com.couchbase.client.java.query.*;
import com.app.model.UserRecord;
import java.util.Base64;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gs082r on 12/28/2016.
 */
public class DatabaseWrapper {

    private static DatabaseWrapper dbInstance =      null;

    private DatabaseWrapper() {
        UserRecord dummyUser = new UserRecord();
        dummyUser.setUserName("admin");
        dummyUser.setUserId(0);
        dummyUser.setActive(true);

        String saltedPwd = "sAlT" + "admin" + "SaLt";
        byte[] encodedBytes = Base64.getEncoder().encode(saltedPwd.getBytes());

        dummyUser.setPassword(new String(encodedBytes));

        userList = new ArrayList<UserRecord>();
        userList.add(dummyUser);
    }

    private List<UserRecord> userList = null;

    public static DatabaseWrapper getInstance() {
        if (dbInstance == null) {
            dbInstance = new DatabaseWrapper();
        }
        return dbInstance;
    }

    public void insert() {
        // Initialize the Connection
        Cluster cluster = CouchbaseCluster.create("localhost");
        Bucket bucket = cluster.openBucket("default");

        // Create a JSON Document
        JsonObject arthur = JsonObject.create()
                .put("name", "Arthur")
                .put("email", "kingarthur@couchbase.com")
                .put("interests", JsonArray.from("Holy Grail", "African Swallows"));

        // Store the Document
        bucket.upsert(JsonDocument.create("u:king_arthur", arthur));

        // Load the Document and print it
        // Prints Content and Metadata of the stored Document
        System.out.println(bucket.get("u:king_arthur"));

        // Create a N1QL Primary Index (but ignore if it exists)
        //bucket.bucketManager().createN1qlPrimaryIndex(true, false);

        // Perform a N1QL Query
        N1qlQueryResult result = bucket.query(
                N1qlQuery.parameterized("SELECT name FROM default WHERE $1 IN interests",
                        JsonArray.from("African Swallows"))
        );

        // Print each found Row
        for (N1qlQueryRow row : result) {
            // Prints {"name":"Arthur"}
            System.out.println(row);
        }
    }

    public void AddUser(UserRecord ur) {
        userList.add(ur);
        int index = userList.indexOf(ur);
        ur.setUserId(index);
        userList.set(index, ur);
    }

    public void ReplaceUser(UserRecord ur) {
        int index = userList.indexOf(ur);
        if (index >= 0) {
            userList.set(index, ur);
        }
    }

    public List<UserRecord> GetAllUsers() {
        return userList;
    }
}
