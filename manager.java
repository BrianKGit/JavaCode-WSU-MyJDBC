package org.totalbeginner.myJDBC;

import java.sql.*;

public class manager {

    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement pst = null;
    private ResultSet resultSet = null;
    private String driver = "com.mysql.cj.jdbc.Driver";

    private String user = "manager";
    private String password = "cs485";
    //Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/qq8522kn_AMS", "user", "password");

    public void readDataBase() throws Exception {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Setup the connection with the test DataBase - EVERYONE HAS ACCESS, PLEASE BE
            // CAREFUL!!
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/qq8522kn_AMS", "manager", "CS485");
            //	connect = DriverManager.getConnection("jdbc:mysql://50.116.3.147/qq8522kn_AMS?user=manager&password=CS485");
            // DriverManager.getConnection("jdbc:mysql://50.116.3.147/qq8522kn_AMS?user=ak7221os&password=ak7221os");
            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            resultSet = pst.executeQuery("select * from Airport");
            //writeResultSet(resultSet);

        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }

    }
    
    // this is to read the baggage info
    public static ResultSet writeResultSetBaggageInfo(ResultSet resultSet) throws SQLException {
        // ResultSet is initially before the first data set
        while (resultSet.next()) {
            // It is possible to get the columns via name
            // also possible to get the columns via the column number
            // which starts at 1
            // e.g. resultSet.getString(2);
            String bagID = resultSet.getString("bagID");
            String passengerID = resultSet.getString("passengerID");
            String locationID = resultSet.getString("locationID");
            System.out.println("\n\nBag ID: " + bagID);
            System.out.println("Passenger ID: " + passengerID);
            System.out.println("Location ID: " + locationID);


        }
        return resultSet;
    }
    
    public static ResultSet writeResultSetAirportInfo(ResultSet resultSet) throws SQLException {
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
            String airportName = resultSet.getString("airportName");
            
            System.out.println("\n\nStreet Address: " + streetAddress);
            System.out.println("City: " + city);
            System.out.println("Country: " + country);
            System.out.println("Airport ID: " + airportID);
            System.out.println("Airport Name: " + airportName);
        }
        return resultSet;
    }
    
    public static ResultSet writeResultSetEmployeeInfo(ResultSet resultSet) throws SQLException {
        // ResultSet is initially before the first data set
        while (resultSet.next()) {
            
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String employeeID = resultSet.getString("employeeID");
            String companyID = resultSet.getString("companyID");
            String streetAddress = resultSet.getString("streetAddress");
            String city = resultSet.getString("city");
            String country = resultSet.getString("country");
            String phone = resultSet.getString("phone");
            String email = resultSet.getString("email");
            String title = resultSet.getString("title");
            String salary = resultSet.getString("salary");
            
            System.out.println("\n\nFirst Name: " + firstName);
            System.out.println("Last Name: " + lastName);
            System.out.println("Employee ID: " + employeeID);
            System.out.println("Company ID: " + companyID);
            System.out.println("Street Address: " + streetAddress);
            System.out.println("City: " + city);
            System.out.println("Country: " + country);
            System.out.println("Phone Number((###)#######): " + phone);
            System.out.println("E-mail: " + email);
            System.out.println("Work Title: " + title);
            System.out.println("Salary: " + salary);
        }
        return resultSet;
    }
    
    public static ResultSet writeResultSetFlightInfo(ResultSet resultSet) throws SQLException {
        // ResultSet is initially before the first data set
        while (resultSet.next()) {
            
            String status = resultSet.getString("status");
            String departureTime = resultSet.getString("departureTime");
            String departureLocationID = resultSet.getString("departureLocationID");
            String departureAirportID = resultSet.getString("departureAirportID");
            String arrivalTime = resultSet.getString("arrivalTime");
            String arrivalLocationID = resultSet.getString("arrivalLocationID");
            String arrivalAirportID = resultSet.getString("arrivalAirportID");
            String flightID = resultSet.getString("flightID");

            System.out.println("\n\nStatus: " + status);
            System.out.println("Departure Time: " + departureTime);
            System.out.println("Departure Location ID: " + departureLocationID);
            System.out.println("Departure Airport ID: " + departureAirportID);
            System.out.println("Arrival Time: " + arrivalTime);
            System.out.println("Arrival Location ID: " + arrivalLocationID);
            System.out.println("Arrival Airport ID: " + arrivalAirportID);
            System.out.println("Flight ID: " + flightID);

        }
        return resultSet;
    }

    // helper to append input
    private void appendResultSet(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            // this is a method that allows the user to append information
            // in terms of the manager this should allow them to append information in the
            // db
            // for service, the passenger table should be able to be appended
        }
    }

    // You need to close the resultSet
    public void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {

        }
    }
}
