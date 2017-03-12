package com.app.resources;

import com.app.exceptions.UserNotExistsException;
import com.app.model.Credentials;
import com.app.model.UserRecord;
import com.app.services.AuditService;
import com.app.services.UsersService;
import com.app.utils.CorsGenerator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.annotation.security.PermitAll;

@Path("/login")
public class LoginResource {

    final Logger logger = LogManager.getLogger(LoginResource.class.getName());

    @OPTIONS
    public Response corsMyResource(@HeaderParam("Access-Control-Request-Headers") String requestH) {
        return CorsGenerator.makeCORS(Response.ok(), requestH);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response Login(Credentials credentials) {
        String authentication = null;
        try {
            authentication = UsersService.getInstance().Login(credentials.getUserName(), credentials.getPassword());
        } catch (UserNotExistsException e) {
            e.printStackTrace();
            //handle exception
            AuditService.getInstance().AddAuditRecord(-1, "","Login", "Login Failed ! Username=" + credentials.getUserName());
            return Response.status(500).header("Access-Control-Allow-Origin", "*").build();
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("authentication", authentication);
        AuditService.getInstance().AddAuditRecord(UsersService.getInstance().getCurrentUser(), "","Login", "Login ok ! Username=" + credentials.getUserName());
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(jsonObject).build();
    }
}
