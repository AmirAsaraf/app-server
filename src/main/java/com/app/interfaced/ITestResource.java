package com.app.interfaced;


import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//@Path("/tests")
public interface ITestResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTest();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTest();

}
