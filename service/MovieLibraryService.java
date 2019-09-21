package service;



import model.Movie;
import model.MovieLibrary;
import model.User;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("movieLibrary")
public class MovieLibraryService {
    @Context
    private HttpServletRequest request;
    private MovieLibrary movieLibrary;
    private User user;

    /**
     * default constructor
     */
    public MovieLibraryService(){
        setMovieLibrary(new MovieLibrary());
        setUser(new User());
    }

    /**
     * produces a list of all the movies
     * @return Response
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)

    public Response listMovies(){
        int status;
        List<Movie> movieList;

        String userrole = getUser().getUserRole(getRequest());

        /**if(userrole == null || userrole.equals("guest")){
            status = 403;
            movieList = null;
        }
        else{
            status = 200;
            movieList = getMovieLibrary().getMovieList();
        }**/

        status = 200;
        movieList = getMovieLibrary().getMovieList();


        Response response = Response
                .status(status)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Headers",
                        "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods",
                        "GET, POST, DELETE")
                .entity(movieList)
                .build();
        return response;

    }

    /**
     * reads a single movie identified by the movieID
     * @param movieId is the ID of the movie in the URL
     * @return Response
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)

    public Response readMovie(
            @QueryParam("id") int movieId
    ) {
        Movie movie;
        int status;

        String userrole = getUser().getUserRole(getRequest());

        if(userrole == null || userrole.equals("guest")){
            status=403;
            movie=null;
        }
        else{
            try{
                movie = getMovieLibrary().getMovieList().get(movieId);
                status = 200;


            }
            catch(IndexOutOfBoundsException e){
                movie = null;
                status = 404;
            }

        }
        Response response = Response
                .status(status)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Headers",
                        "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods",
                        "GET, POST, DELETE")
                .entity(movie)
                .build();
        return response;


    }

    /**
     * saves a movie in the movieLibrary
     * @param movieId
     * @param title
     * @param director
     * @param description
     * @param length
     * @param rating
     * @return
     */
    @POST
    @Path("save")
    @Produces(MediaType.TEXT_PLAIN)
    public Response saveMovie(
            @QueryParam("id") int movieId,
            @FormParam("title") String title,
            @FormParam("director") String director,
            @FormParam("description") String description,
            @FormParam("length") Integer length,
            @FormParam("rating") Float rating
    ) {
        Movie movie = new Movie();
        int status;
        String userrole = getUser().getUserRole(getRequest());

        if (userrole == null || userrole.equals("guest")){
            status = 403;
            movie = null;
        }
        else{
            movie.setTitle(title);
            movie.setDirector(director);
            movie.setDescription(description);
            movie.setLength(length);
            movie.setRating(rating);

            try{
                getMovieLibrary().save(movieId, movie);
                status = 200;
            }
            catch(IndexOutOfBoundsException e){
                status = 404;
            }
        }

        Response response = Response
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

    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    /**
     * delete a single book identified by the movieId
     * @param  movieId   the movieId in the URL
     * @return Response
     */
    public Response deleteMovie(
            @QueryParam("id") int movieId
    ) {
        int status;

        String userrole = getUser().getUserRole(getRequest());

        // TODO check the userRole for authorization
        try {
            getMovieLibrary().delete(movieId);
            status = 200;
        } catch (IndexOutOfBoundsException e) {
            status = 404;
        }

        Response response = Response
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


    public MovieLibrary getMovieLibrary() {
        return movieLibrary;
    }

    public void setMovieLibrary(MovieLibrary movieLibrary) {
        this.movieLibrary = movieLibrary;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public HttpServletRequest getRequest() {
        return request;
    }
    public void setRequest(HttpServletRequest request){
        this.request=request;
    }





}
