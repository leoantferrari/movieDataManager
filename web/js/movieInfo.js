/**
 * view-controller for movieEdit.html
 *
 * @author  Leo Ferrari
 * @since   07.09.2019
 * @version 1.0
 */

/**
 * load the movie data
 */
$(document).ready(function () {
    var movieId = $.urlParam('id');
    if (movieId === null || movieId == -1) {
        movieId = -1;
    } else {
        $.ajax({
            url: "./resource/movieLibrary/read?id=" + movieId,
            dataType: "json",
            type: "GET",
        })
            .done(function (jsonData) {
                showMovie(jsonData);
            })
            .fail(function (xhr, status, errorThrown) {
                // TODO redirect if not authorised
                if (xhr.status == 404) {
                    $("#message").text("No Movie Found");
                } else {
                    $("#message").text("Error whilst reading the movie");
                }
            })
    }

    /**
     * send the movie data to the webservice to be saved
     */
    $("#save").click(function () {
        $.ajax({
            url: "./resource/movieLibrary/save?id=" + movieId,
            dataType: "text",
            type: "POST",
            data: $("#movieEditForm").serialize(),
        })
            .done(function (jsonData) {
                showMovie(jsonData);
                window.location.href = "./movieLibrary.html";
            })
            .fail(function (xhr, status, errorThrown) {
                // TODO redirect if not authorised
                if (xhr.status == 404) {
                    $("#message").text("This Movie doesnt exist");
                } else {
                    $("#message").text("Error whilst saving the Movie");
                }
            })
    });



    /**
     * listener for button [abbrechen], redirects to movieLibrary.html
     */
    $("#cancel").click(function () {
        window.location.href = "./movieLibrary.html";
    });
    $('#edit').click(function (){
        window.loacation.href = "./movieEdit.html?id="+ movieId;
    });

});

/**
 * show the movie
 * @param movie  the data of the movie
 */
function showMovie(movie) {
    $("#message").empty();
    $('#title').val(movie.title);
    $('#pageTitle').val(movie.title);
    $('#director').val(movie.director);
    $('#description').val(movie.description);
    $('#length').val(movie.length);
    $('#rating').val(movie.rating);
}

