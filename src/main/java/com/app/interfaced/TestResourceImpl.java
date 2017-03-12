package com.app.interfaced;

import com.app.interfaced.ITestResource;

import javax.ws.rs.core.Response;

public class TestResourceImpl implements ITestResource {

    @Override
    public Response getTest() {
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity("Im a test!").build();
    }

    @Override
    public Response addTest() {
        return Response.status(201).header("Access-Control-Allow-Origin", "*").entity("Created a test!").build();
    }
}
