package com.app.resources;

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
import javax.annotation.security.PermitAll;

@Path("/logout")
public class LogoutResource {

    public static final String ID = "id";

    final Logger logger = LogManager.getLogger(LogoutResource.class.getName());

    @OPTIONS
    public Response corsMyResource(@HeaderParam("Access-Control-Request-Headers") String requestH) {
        return CorsGenerator.makeCORS(Response.ok(), requestH);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response logoutUser(String username) {
        try {
            UsersService.getInstance().Logout(username);
        } catch (Exception e) {
            e.printStackTrace();
            //handle exception
            AuditService.getInstance().AddAuditRecord(-1, "","Logout", "Logout Logout ! Username=" + username);
            return Response.status(500).header("Access-Control-Allow-Origin", "*").build();
        }

        AuditService.getInstance().AddAuditRecord(UsersService.getInstance().getCurrentUser(), "","Logout", "Logout ok ! Username=" + username);
        return Response.status(200).header("Access-Control-Allow-Origin", "*").build();
    }
}
