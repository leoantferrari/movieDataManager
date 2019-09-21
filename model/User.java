package model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * the userClass used to login to the site. This code has been adapted from a previous project.
 *
 *
 * @author Leo Ferrari, Marcel Suter(previous Project)
 * @version 1.0
 * @since 2019-09-21
 */
public class User {
    private String userRole;
String hostName = "hostName.database.windows.net"; // update me
    String dbName = "databaseName"; // update me
    String user = "username"; // update me
    String password = "pwd"; // update me
    String url = "jdbc:sqlserver://"+hostname+":1433;database="+databaseName+";user="+username+"@"+databaseName+";password="+pwd+";encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
    Connection connection = null;


    /**
     * default constructor
     */
    public User() {

    }

    /**
     * authenticate a user
     *
     * @param username
     * @param password
     * @return the role of this user
     */
    public String authenticate(
            HttpServletRequest request,
            String username,
            String password
    ) {

        String role = "guest";
        BufferedReader bufferedReader = null;
        FileReader fileReader = null;
        try {
            // Name("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url);
            String schema = connection.getSchema();
            System.out.println("Successful connection - Schema: " + schema);
            String selectSql2 = "SELECT * FROM movieUser";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(selectSql2)) {

                // Print results from select statement

                while (resultSet.next())
                {
                    if(username.equals(resultSet.getString("username"))&& password.equals(resultSet.getString("password"))){
                        role=resultSet.getString("role");

                    }

                }
                connection.close();
            }
        }
        catch (Exception e) {
            System.out.println("ERROR"+ e.toString());

        }

        setUserRole(request, role);
        return role;
    }

    /**
     * invalidates the session
     */
    public void logoff(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.invalidate();
    }

    /**
     * @return the userRole
     */
    public String getUserRole(HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        if (session != null) {
            if (session.getAttribute("role") != null) {
                userRole = session.getAttribute("role").toString();
            }
        }
        return userRole;
    }

    /**
     * @param userRole
     *            the userRole to set
     */
    public void setUserRole(HttpServletRequest request, String userRole) {
        HttpSession session = request.getSession(true);
        session.setAttribute("role", userRole);
        this.userRole = userRole;
    }

}
