package com.app.services;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import javax.ws.rs.core.HttpHeaders;

import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;
import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.util.Base64;

/**
 * Created by gs082r on 1/1/2017.
 */

@Provider
@ServerInterceptor
public class AuthenticationService implements PreProcessInterceptor
{
    private static final String AUTHORIZATION_TOKEN = "x-Authorization";
    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Basic";
    private static final ServerResponse ACCESS_DENIED = new ServerResponse("Access denied for this resource", 401, new Headers<Object>());;
    private static final ServerResponse ACCESS_FORBIDDEN = new ServerResponse("Nobody can access this resource", 403, new Headers<Object>());;
    private static final ServerResponse SERVER_ERROR = new ServerResponse("INTERNAL SERVER ERROR", 500, new Headers<Object>());;

    @Override
    public ServerResponse preProcess(HttpRequest request, ResourceMethodInvoker methodInvoked) throws Failure, WebApplicationException
    {
        Method method = methodInvoked.getMethod();

        //Access allowed for all
        if(method.isAnnotationPresent(PermitAll.class))
        {
            return null;
        }
        //Access denied for all
        if(method.isAnnotationPresent(DenyAll.class))
        {
            return ACCESS_FORBIDDEN;
        }

        //Get request headers
        final HttpHeaders headers = request.getHttpHeaders();

        //Fetch authorization header
        final List<String> authorization = headers.getRequestHeader(AUTHORIZATION_TOKEN);

        //If no authorization information present; block access
        if(authorization == null || authorization.isEmpty())
        {
            return ACCESS_DENIED;
        }

        final String token = authorization.get(0);

        //Verify user access
        if(method.isAnnotationPresent(RolesAllowed.class))
        {
            RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
            Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));

            //Is user valid?
            if(token == null || token.isEmpty() || !UsersService.getInstance().isValidSession(token, rolesSet))
            {
                return ACCESS_DENIED;
            }
        }

        //Return null to continue request processing
        return null;
    }
}

