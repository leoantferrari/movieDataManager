/**
 * view-controller for login.html
 *
 * @author  Marcel Suter
 * @since   2019-03-19
 * @version 1.0
 */

/**
 * load the book data
 */
$(document).ready(function () {

    /**
     * listener for button [Anmelden] sends the login data to the web service
     */
    $("#logon").click(function () {
        $.ajax({
            url: "./resource/user/login",
            dataType: "text",
            type: "POST",
            data: $("#loginForm").serialize(),
        })
            .done(function (jsonData) {
                window.location.href = "./movieLibrary.html";
            })
            .fail(function (xhr, status, errorThrown) {
                // TODO evaluate the HTTP-Status
                if(xhr.status==401){
                    $('#message').text("Username is wrong");
                }else{
                    $("#message").text("Oops");
                }
            })
    });

    /**
     * listener for button [Abmelden]
     */
    $("#logoff").click(function () {
        $.ajax({
            url: "./resource/user/logoff",
            dataType: "text",
            type: "GET",
            data: $("#loginForm").serialize(),
        })
            .done(function (jsonData) {
                window.location.href = "./login.html";
            })
            .fail(function (xhr, status, errorThrown) {

                $("#message").text("Oops");

            })
    });

});



