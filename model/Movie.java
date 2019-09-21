package model;

/**
 *  the class for an individual movie in a library of movies
 *
 * @author Leo Ferrari
 * @version 1.0
 * @since 7.09.2019
 */
public class Movie {

    private String title;
    private String director;
    private String description;
    private Float rating;
    private Integer length;

    /**
     * default constructor
     */
    public Movie(){

    }

    /**
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title is the title to be set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return the director of the movie
     */
    public String getDirector() {
        return director;
    }

    /**
     *
     * @param director is the director to be set
     */
    public void setDirector(String director) {
        this.director = director;
    }

    /**
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description is the description of a movie
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return the rating
     */
    public Float getRating() {
        return rating;
    }

    /**
     *
     * @param rating is the rating of the movie
     */
    public void setRating(Float rating) {
        this.rating = rating;
    }

    /**
     *
     * @return the length of the movie
     */
    public Integer getLength() {
        return length;
    }

    /**
     *
     * @param length is the length of a movie
     */
    public void setLength(Integer length) {
        this.length = length;
    }





}
