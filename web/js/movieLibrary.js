/**
 * view-controller for movieLibrary.html
 *
 * @author  Leo Ferrari
 * @since   07.09.2019
 * @version 1.0
 */

/**
 * loads all the movies
 */
$(document).ready(function () {
    loadMovies();
    /**
     * listener for buttons within shelfForm
     */
    $("#libraryForm").on("click", "button", function () {
        deleteMovie(this.value);
    });
});

/**
 * request the movie data from webservice
 */
function loadMovies() {
    $.ajax({
        url: "./resource/movieLibrary/list",
        dataType: "json",
        type: "GET",
    })
        .done(function (jsonData) {
            showMovies(jsonData);
        })
        .fail(function (xhr, status, errorThrown) {
            // TODO redirect if not authorised
            if(xhr.status==403){
                window.location.href = "./login.html";
            }
            $("#message").text("Error loading the movies");

        })

}

/**
 * add table rows/cells for movies
 * @param movieData  array of movies as objects
 */
function showMovies(movieData) {
    $("#message").val("");
    $("#movieLibrary > tbody").html("");
    $.each(movieData, function (index, movie) {
        $("#movieLibrary > tbody").append("<tr>");
        $("#movieLibrary > tbody").append("<td>" + movie.title + "</td>");
        $("#movieLibrary > tbody").append("<td>" + movie.director + "</td>");
        $("#movieLibrary > tbody").append("<td>" + movie.description + "</td>");
        $("#movieLibrary > tbody").append("<td>" + movie.length + "</td>");
        $("#movieLibrary > tbody").append("<td>" + movie.rating + "</td>");
        $("#movieLibrary > tbody").append("<td><a href='./movieEdit.html?id=" + index + "'>Edit</a></td>");
        $("#movieLibrary > tbody").append("<td><button type='button' id='delete_" + index + "' value='" + index + "'>Delete</button></td>");
        $("#movieLibrary > tbody").append("</tr>");
    });
}

/**
 * send delete request for a movie
 * @param movieId
 */
function deleteMovie(movieId) {
    $.ajax({
        url: "./resource/movieLibrary/delete?id=" + movieId,
        dataType: "text",
        type: "DELETE",
    })
        .done(function (data) {
            loadMovies();
            $("#message").text("Movie Deleted");
        })
        .fail(function (xhr, status, errorThrown) {
            // TODO redirect if not authorised
            $("#message").text("Error Deleting the Movie");
        })
}