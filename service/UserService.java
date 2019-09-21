package service;

import model.User;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * provides services for authentication
 *
 * M133: Bookshelf_05
 *
 * @author Marcel Suter
 * @version 1.0
 * @since 2019-03-04
 */
@Path("user")
public class UserService {
    @Context
    private HttpServletRequest request;
    private User user;

    /**
     * default constructor
     */
    public UserService() {
        setUser(new User());
    }

    /**
     * authenticate the user with username/password
     *
     * @param username
     * @param password
     * @return empty String
     */
    @POST
    @Path("login")
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(
            @FormParam("username") String username,
            @FormParam("password") String password
    ) {
        Response response;
        int status;



        // TODO authenticate the user
        // TODO if the role is "guest"
        if (getUser().authenticate(getRequest(), username, password).equals("guest")){
            status = 401;
        }
        else{
            status =200;
        }
        response = Response
                .status(status)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Headers",
                        "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods",
                        "GET, POST, DELETE")
                .entity("")
                .build();
        return response;
    }

    /**
     * logoff the user and destroy the session
     *
     * @return
     */
    @GET
    @Path("logoff")
    @Produces(MediaType.TEXT_PLAIN)
    public Response logoff() {
        Response response;
        // TODO logoff the user
        getUser().logoff(getRequest());
        response = Response
                .status(200)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Headers",
                        "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods",
                        "GET, POST, DELETE")
                .entity("")
                .build();
        return response;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }


    /**
     * @return the request
     */
    public HttpServletRequest getRequest() {
        return request;
    }

    /**
     * @param request the request to set
     */
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
}
