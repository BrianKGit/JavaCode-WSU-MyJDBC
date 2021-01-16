package org.totalbeginner.myJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class MySQLAccess{
    private Connection connect = null;
    private PreparedStatement pst = null;
    private ResultSet resultSet = null;

    public void readDataBase() throws Exception {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Setup the connection with the test DataBase - EVERYONE HAS ACCESS, PLEASE BE CAREFUL!!
            // Obviously, if you were distributing this file, you would not include the username and password. There are other ways...
            //connect = DriverManager.getConnection("jdbc:mysql://localhost/University?user=root&password=");
          //  connect = DriverManager.getConnection("jdbc:mysql://50.116.3.147/CS485?user=CS485&password=WinonaState");
            connect = DriverManager.getConnection("jdbc:mysql://50.116.3.147/qq8522kn_AMS?user=manager&password=CS485");
           // connect = DriverManager.getConnection("jdbc:mysql://50.116.3.147/qq8522kn_AMS?user=ak7221os&password=ak7221os");
            // Statements allow to issue SQL queries to the database
            //statement = connect.createStatement();
            // Result set get the result of the SQL query
            //resultSet = statement.executeQuery("select * from Airport");
            writeResultSet(resultSet);

        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }

    }

    private void writeResultSet(ResultSet resultSet) throws SQLException {
        // ResultSet is initially before the first data set
        while (resultSet.next()) {
            // It is possible to get the columns via name
            // also possible to get the columns via the column number
            // which starts at 1
            // e.g. resultSet.getSTring(2);
            String streetAddress = resultSet.getString("streetAddress");
            String city = resultSet.getString("city");
            String country = resultSet.getString("country");
            String airportID = resultSet.getString("airportID");
        //print the airport info
            System.out.println("Aiport Address: " + streetAddress);
            System.out.println("City: " + city);
            System.out.println("Country: " + country);
            System.out.println("AirportID: " + airportID);

        }
    }

    	private void appendResultSet(ResultSet resultSet) throws SQLException{
    			while(resultSet.next()) {
    				//this is a method that allows the user to append information
    				//in terms of the manager this should allow them to append information in the db
    				//for service, the passenger table should be able to be appended
    			}
    	}
    // You need to close the resultSet
    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            //if (stmt != null) {
            //   stmt.close();
           // }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {

        }
    }

}
