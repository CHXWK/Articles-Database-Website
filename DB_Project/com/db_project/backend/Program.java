package com.db_project.backend;

import java.lang.Thread.State;
import java.sql.*;

import javax.swing.JOptionPane;

public class Program
{

    // Variables
    private final String host = "localhost";
    private final String port = "3306";
    private final String dbname = "mydb";
    private final String user = "root";
    private final String password = "Abgh15hjk67";

    Connection connection; // USE THIS TO CREATE A SECOND STATEMENT IF NEEDED! CLOSE THE TEMPORARY STATEMENT BEFORE EXITING METHOD
    boolean userAdminPriv;

    /**
     * Creates connection to database.
     * ENSURE THAT YOU CHANGED THE PASSWORD VARIABLE!
     * 
     * @throws SQLException
     */
    public Program () throws SQLException
    {

        connectProgram();

    }
    

    public void connectProgram () throws SQLException
    {
        String connectionString = String.format("jdbc:mysql://localhost:3306/%s?autoReconnect=true&useSSL=false", dbname);
        connection = DriverManager.getConnection(connectionString, user, password);
    }
    
    /**
     * https://docs.oracle.com/javase/tutorial/jdbc/basics/retrieving.html
     * The link goes to an example of how to get data from ResultSet
     * https://docs.oracle.com/javase/7/docs/api/java/sql/ResultSet.html
     * The link above goes to ResultSet documentation
     * 
     * @param username
     * @param password
     * @throws Exception
     */
    public void login (String username, String password) throws Exception
    {
        String sql = String.format("select AdminPriv from webadmin where Username=\"%s\" and Password=\"%s\"", username, password);
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        
        if (rs.first())
        {
            userAdminPriv = rs.getBoolean(1);
            return;
        }
        else
        {
            statement.close();
            throw new Exception();
        }
    }

    /**
     * Checks 
     * @return true if current WebUser has admin privilige, false if WebUser doesn't
     */
    public boolean hasAdminPrivilage ()
    {
        return userAdminPriv;
    }

    public ResultSet runQuery (String sql) throws SQLException
    {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        return rs;
    }

    
    public void closeConnection ()
    {
        try
        {
            connection.close();
        }
        catch (Exception e) {}

        connection = null;
    }

    public boolean reconnect ()
    {
        boolean ret = true;

        try 
        {
            connectProgram();
        }
        catch (SQLException e)
        {
            ret = false;
        }

        return ret;
    }
}