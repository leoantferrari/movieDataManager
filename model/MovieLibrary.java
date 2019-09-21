package model;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;

/**
 * a library of movies
 *
 * @author Leo Ferrari
 * @version 1.0
 * @since 07.09.2019
 */

@XmlRootElement
public class MovieLibrary {

   String hostName = "hostName.database.windows.net"; // update me
    String dbName = "databaseName"; // update me
    String user = "username"; // update me
    String password = "pwd"; // update me
    String url = "jdbc:sqlserver://"+hostname+":1433;database="+databaseName+";user="+username+"@"+databaseName+";password="+pwd+";encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
    Connection connection = null;


    private List<Movie> movieList;

    /**
     *
     */
    public MovieLibrary(){

        setMovieList(new ArrayList<Movie>());
        readMovies();
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    /**
     * read all movies from the database, momentarily this causes an internal server error 500.
     */
    private void readMovies2() {

        try {
            // Name("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url);
            String schema = connection.getSchema();
            System.out.println("Successful connection - Schema: " + schema);
            String selectSql2 = "SELECT * FROM movie";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(selectSql2)) {



                while (resultSet.next())
                {


                        Movie book = new Movie();
                        book.setTitle(resultSet.getString("title"));
                        book.setDirector(resultSet.getString("directors"));
                        book.setDescription(resultSet.getString("description"));
                        book.setLength(resultSet.getInt("length"));
                        book.setRating(resultSet.getFloat("rating"));
                        getMovieList().add(book);


                }
                connection.close();
            }
        }
        catch (Exception e) {
            System.out.println("ERROR"+ e.toString());

        }



    }

    /**
     * Old version of readmovies, which reads the data from a .csv file.
     */
    private void readMovies() {

        BufferedReader bufferedReader = null;
        FileReader fileReader = null;

        try {

            fileReader = new FileReader("C:\\Users\\leoan\\Documents\\webProjects\\movieDBthe2nd\\src\\data\\movies.csv");
            bufferedReader = new BufferedReader(fileReader);

            String currentLine;

            while ((currentLine = bufferedReader.readLine()) != null) {
                String[] data = currentLine.split(",");
                Movie book = new Movie();
                book.setTitle(data[0]);
                book.setDirector(data[1]);
                book.setDescription(data[2]);
                book.setLength(Integer.parseInt(data[3]));
                book.setRating(Float.parseFloat(data[4]));
                getMovieList().add(book);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null)
                    bufferedReader.close();

                if (fileReader != null)
                    fileReader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * saves the movie to the file
     */
    private void writeMovies() {
        /*try {

            connection = DriverManager.getConnection(url);
            String schema = connection.getSchema();


            // Create and execute a SELECT SQL statement.

            Movie movie = getMovieList().get(getMovieList().size());
            String selectSql2 = "INSERT INTO movie " +
                    "VALUES ("+movie.getTitle()+","+movie.getDirector()+","+movie.getDescription()+","+movie.getLength()+","+movie.getRating()+");";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(selectSql2)) {
                statement.execute(selectSql2);
                // Print results from select statement


                connection.close();
            }
        }
        catch (Exception e) {
            System.out.println("ERROR"+
                    e.toString());

        }
        */
        /*
        Writer writer = null;

        try {
            FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\leoan\\Desktop\\csv\\movies.csv");
            writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream, "utf-8"));
            for (Movie movie : getMovieList()) {
                String line = movie.getTitle() + ","
                        + movie.getDirector() + ","
                        + movie.getDescription() + ","
                        + movie.getLength() + ","
                        + String.valueOf(movie.getRating()) + "\n";
                writer.write(line);
            }
        } catch (IOException ex) {
            throw new RuntimeException();
        } finally {
            try {
                writer.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }*/

    }

    /**
     * delete a movie from the movieLibrary
     *
     * @param movieId the id of the movie
     * @throws IndexOutOfBoundsException
     */
    public void delete(int movieId) throws IndexOutOfBoundsException {

        try {

            connection = DriverManager.getConnection(url);
            String schema = connection.getSchema();


            // Create and execute a SELECT SQL statement.

            Movie movie = getMovieList().get(movieId);
            String selectSql2 = "DELETE FROM movie WHERE id=movieId; ";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(selectSql2)) {
                statement.execute(selectSql2);
                // Print results from select statement

                connection.close();
            }
        }
        catch (Exception e) {
            System.out.println("ERROR"+
                    e.toString());

        }
        getMovieList().remove(movieId);
        readMovies();
    }

    /**
     * saves a movie in the movieLibrary
     * @param movieId
     * @param movie
     */
    public void save(int movieId, Movie movie) {
        if (movieId == -1) {
            try {

                connection = DriverManager.getConnection(url);
                String schema = connection.getSchema();


                // Create and execute a SELECT SQL statement.
                String selectSql2 = "INSERT INTO movie " +
                        "VALUES ("+movie.getTitle()+","+movie.getDirector()+","+movie.getDescription()+","+movie.getLength()+","+movie.getRating()+");";
                try (Statement statement = connection.createStatement();
                     ResultSet resultSet = statement.executeQuery(selectSql2)) {
                    statement.execute(selectSql2);
                    // Print results from select statement


                    connection.close();
                }
            }
            catch (Exception e) {
                System.out.println("ERROR"+
                        e.toString());

            }
            getMovieList().add(movie);
        } else {
            getMovieList().set(movieId, movie);
            try {

                connection = DriverManager.getConnection(url);
                String schema = connection.getSchema();


                // Create and execute a SELECT SQL statement.


                String selectSql2 = "UPDATE  movie SET title ="+movie.getTitle()+", directors="+movie.getDirector()+", description="+movie.getDescription()+", length="+movie.getLength()+", rating="+movie.getRating()+"WHERE id="+movieId+";";
                try (Statement statement = connection.createStatement();
                     ResultSet resultSet = statement.executeQuery(selectSql2)) {
                    statement.execute(selectSql2);
                    // Print results from select statement


                    connection.close();
                }
            }
            catch (Exception e) {
                System.out.println("ERROR"+
                        e.toString());

            }
        }

        readMovies();
    }







}
