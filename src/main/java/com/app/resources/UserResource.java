package com.app.resources;

import com.app.exceptions.UserAlreadyExistsException;
import com.app.exceptions.UserNotExistsException;
import com.app.model.UserRecord;
import com.app.services.AuditService;
import com.app.services.UsersService;
import com.app.utils.CorsGenerator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import javax.annotation.security.RolesAllowed;

@Path("/users")
public class UserResource {
    public static final String ID = "id";

    final Logger logger = LogManager.getLogger(UserResource.class.getName());

    @OPTIONS
    public Response corsMyResource(@HeaderParam("Access-Control-Request-Headers") String requestH) {
        return CorsGenerator.makeCORS(Response.ok(), requestH);
    }

    @OPTIONS
    @Path("{id}")
    public Response corsMyResourceId(@HeaderParam("Access-Control-Request-Headers") String requestH) {
        return CorsGenerator.makeCORS(Response.ok(), requestH);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam(ID) String id) {
        AuditService.getInstance().AddAuditRecord(UsersService.getInstance().getCurrentUser(), "","GetUser", "UserId=" + id);
        UserRecord ur = null;
        try {
            ur = UsersService.getInstance().getUserById(id);
        } catch (UserNotExistsException e) {
            e.printStackTrace();
            return Response.status(404).header("Access-Control-Allow-Origin", "*").build();
        }

        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(ur).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        AuditService.getInstance().AddAuditRecord(UsersService.getInstance().getCurrentUser(),"", "GetUsers", "");
        List<UserRecord> tmpUserList;
        try {
            tmpUserList = UsersService.getInstance().GetAllUsers();
        } catch (UserNotExistsException e) {
            e.printStackTrace();
            //handle exception
            return Response.status(500).header("Access-Control-Allow-Origin", "*").build();
        }

        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(tmpUserList).build();
    }

    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam(ID) String id, UserRecord userRecord) {
        AuditService.getInstance().AddAuditRecord(UsersService.getInstance().getCurrentUser(), "","UpdateUser", "UserId=" + id);
        try {
            UsersService.getInstance().changeUser(id, userRecord);
        } catch (UserNotExistsException e) {
            e.printStackTrace();
            //handle exception
            return Response.status(404).header("Access-Control-Allow-Origin", "*").build();
        }


        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity("Welcome TA Team!").build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(UserRecord userRecord) {
        AuditService.getInstance().AddAuditRecord(UsersService.getInstance().getCurrentUser(), "","AddUser", "UserId=" + userRecord.getUserId());
        UserRecord ur = null;
        try {
            ur = UsersService.getInstance().addUser(userRecord);
        } catch (UserAlreadyExistsException e) {
            e.printStackTrace();
            //handle exception
            return Response.status(500).header("Access-Control-Allow-Origin", "*").build();
        }

        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(ur).build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeUser(@PathParam(ID) String id) {
        AuditService.getInstance().AddAuditRecord(UsersService.getInstance().getCurrentUser(),"", "RemoveUser", "UserId=" + id);
        try {
            UsersService.getInstance().deleteUser(id);
        } catch (UserNotExistsException e) {
            e.printStackTrace();
            //handle exception
            return Response.status(404).header("Access-Control-Allow-Origin", "*").build();
        }

        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity("Welcome TA Team!").build();
    }
}
