package org.totalbeginner.myJDBC;

import java.sql.*;

public class service {

    private Connection connect = null;
    private PreparedStatement pst = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    public void readDataBase() throws Exception {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Setup the connection with the test DataBase - EVERYONE HAS ACCESS, PLEASE BE CAREFUL!!
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/qq8522kn_AMS", "service", "CS485ser");
            // connect = DriverManager.getConnection("jdbc:mysql://50.116.3.147/qq8522kn_AMS?user=manager&password=CS485");
            // connect = DriverManager.getConnection("jdbc:mysql://50.116.3.147/qq8522kn_AMS?user=ak7221os&password=ak7221os");
            // Statements allow to issue SQL queries to the database
            //PreparedStatement query = connect.executeQuery();

            // Result set get the result of the SQL query
//	            resultSet = pst.executeQuery("select * from Flight");
//	            writeResultSetFlightInfo(resultSet);
//	            resultSet = pst.executeQuery("select * from Airport");
//	            writeResultSetAirportInfo(resultSet);
//	            resultSet = pst.executeQuery("select * from Company");
//	            writeResultSetCompanyInfo(resultSet);
//	            resultSet = pst.executeQuery("select * from Baggage");
//	            writeResultSetBaggageInfo(resultSet);
        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }

    }
    //this is to read airport information to customer..such as when ordering a taxi

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
            //formatting for airport info

            System.out.println("\n\nAddress: " + streetAddress);
            System.out.println("City: " + city);
            System.out.println("Country: " + country);
            System.out.println("AirportID: " + airportID);
            System.out.println("AirportName: " + airportName);
            //System.out.println("DepartmentID: " + departmentID);
            //System.out.println("AdvisorID: " + advisorID);
        }
        return resultSet;
    }

    //this is to read flight info, if a customer loses their ticket
    public static ResultSet writeResultSetFlightInfo(ResultSet resultSet) throws SQLException {
        // ResultSet is initially before the first data set
        while (resultSet.next()) {
            // It is possible to get the columns via name
            // also possible to get the columns via the column number
            String status = resultSet.getString("status");
            String departureTime = resultSet.getString("departureTime");
            String departureLocationID = resultSet.getString("departureLocationID");
            String departureAirportID = resultSet.getString("departureAirportID");
            String arrivalTime = resultSet.getString("arrivalTime");
            String arrivalLocationID = resultSet.getString("arrivalLocationID");
            String arrivalAirportID = resultSet.getString("arrivalAirportID");
            String flightID = resultSet.getString("flightID");
            //formatting for flight info
            System.out.println("\n\nFlight Status: " + status);
            System.out.println("Depart Time: " + departureTime);
            System.out.println("Departure Location: " + departureLocationID);
            System.out.println("Departing Airport" + departureAirportID);
            System.out.println("Flight Arrives: " + arrivalTime);
            System.out.println("Arrival Location: " + arrivalLocationID);
            System.out.println("Arrival Airport: " + arrivalAirportID);
            System.out.println("Flight: " + flightID);

        }
        return resultSet;
    }
    

    //this is to read the company info if a passenger wants to find a specific company location
    public static ResultSet writeResultSetCompanyInfo(ResultSet resultSet) throws SQLException {
        // ResultSet is initially before the first data set
        while (resultSet.next()) {
            // It is possible to get the columns via name
            // also possible to get the columns via the column number
            // which starts at 1
            // e.g. resultSet.getSTring(2);
            String companyID = resultSet.getString("companyID");
            String companyName = resultSet.getString("companyName");
            String locationID = resultSet.getString("locationID");
            String serviceType = resultSet.getString("serviceType");
            //formatting for company info
            System.out.println("\n\nCompany ID: " + companyID);
            System.out.println("Company Name: " + companyName);
            System.out.println("Location: " + locationID);
            System.out.println("Service: " + serviceType);

        }
        return resultSet;
    }
    //read the baggage info if passenger needs help locating bag

    public static ResultSet writeResultSetBaggageInfo(ResultSet resultSet) throws SQLException {

        while (resultSet.next()) {

            String bagID = resultSet.getString("bagID");
            String passengerID = resultSet.getString("passengerID");
            String locationID = resultSet.getString("locationID");
            
            //formatting for baggage info
            System.out.println("\n\nBaggage ID: " + bagID);
            System.out.println("passengerID: " + passengerID);
            System.out.println("Baggage Location: " + locationID);

        }
        return resultSet;
    }

    // to append input for passenger info
    public static ResultSet writeResultSetPassengerInfo(ResultSet resultSet) throws SQLException {

        while (resultSet.next()) {

            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String phone = resultSet.getString("phone");
            String email = resultSet.getString("email");
            String streetAddress = resultSet.getString("streetAddress");
            String city = resultSet.getString("city");
            String country = resultSet.getString("country");
            String idType = resultSet.getString("idType");
            String passengerID = resultSet.getString("passengerID");

            //formatting for baggage info

            System.out.println("\n\nFirst Name: " + firstName);
            System.out.println("Last Name: " + lastName);
            System.out.println("Phone Number ((###)#######): " + phone);
            System.out.println("E-mail Address: " + email);
            System.out.println("Street Address: " + streetAddress);
            System.out.println("City: " + city);
            System.out.println("Country: " + country);
            System.out.println("ID Type: " + idType);
            System.out.println("ID Number: " + passengerID);
            
        }
        return resultSet;
    }
    
    public static ResultSet writeResultSetOpenTicketInfo(ResultSet resultSet) throws SQLException {

        while (resultSet.next()) {

            String flightID = resultSet.getString("flightID");
            String price = resultSet.getString("price");
            String class1 = resultSet.getString("class");
            String seatNo = resultSet.getString("seatNo");
            String departureTime = resultSet.getString("departureTime");
            String departureAirportID = resultSet.getString("departureAirportID");
            String arrivalTime = resultSet.getString("arrivalTime");
            String arrivalAirportID = resultSet.getString("arrivalAirportID");

            //formatting for baggage info

            System.out.println("\n\nFlight ID: " + flightID);
            System.out.println("Price: $" + price);
            System.out.println("Class: " + class1);
            System.out.println("Seat Number: " + seatNo);
            System.out.println("Departure Time: " + departureTime);
            System.out.println("Departure Airport ID: " + departureAirportID);
            System.out.println("Arrival Time: " + arrivalTime);
            System.out.println("Arrival Airport ID: " + arrivalAirportID);
            
        }
        return resultSet;
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
