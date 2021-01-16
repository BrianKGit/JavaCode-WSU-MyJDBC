package org.totalbeginner.myJDBC;

import java.sql.*;

public class security {
	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	public PreparedStatement pst = null;

	public void readDataBase() throws Exception {
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			// Setup the connection with the test DataBase - EVERYONE HAS ACCESS, PLEASE BE
			// CAREFUL!!

			//connect = DriverManager.getConnection("jdbc:mysql://50.116.3.147/qq8522kn_AMS?user=manager&password=CS485");
		  connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/qq8522kn_AMS", "security", "CS485sec");
			// connect =
			// DriverManager.getConnection("jdbc:mysql://50.116.3.147/qq8522kn_AMS?user=ak7221os&password=ak7221os");
			// Statements allow to issue SQL queries to the database
			//statement = connect.createStatement();
			// Result set get the result of the SQL query
			//resultSet = pst.executeQuery("select * from Passenger");
			//writeResultSetCustomerInfo(resultSet);
			//resultSet = pst.executeQuery("select * from Employee");
			//writeResultSetEmployeeInfo(resultSet);
			//resultSet = pst.executeQuery("select * from Ticket");
			//writeResultSetTicketInfo(resultSet);

		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}

	}

	// this is to read customer info
	public static ResultSet writeResultSetPassengerInfo(ResultSet resultSet) throws SQLException {
		// ResultSet is initially before the first data set
		while (resultSet.next()) {
			// It is possible to get the columns via name
			// also possible to get the columns via the column number
			// which starts at 1
			
			String firstname = resultSet.getString("firstName");
			String lastname = resultSet.getString("lastName");
			String phone = resultSet.getString("phone");
			String email = resultSet.getString("email");
			String streetAddress = resultSet.getString("streetAddress");
			String city = resultSet.getString("city");
			String country = resultSet.getString("country");
			String idType = resultSet.getString("idType");
			String passengerID = resultSet.getString("passengerID");
                        
			//formatting to print out customer/passenger info
			System.out.println("\n\nFirst Name: " + firstname);
			System.out.println("Last Name: " + lastname);
			System.out.println("Phone Number: " + phone);
			System.out.println("Email Address: " + email);
			System.out.println("Address: " + streetAddress);
			System.out.println("City: " + city);
			System.out.println("Country: " + country);
			System.out.println("ID Type: " + idType);
			System.out.println("ID Number: " + passengerID);
		
		}
		return resultSet;
	}

	// this is to read ticket info
	public static ResultSet writeResultSetTicketInfo(ResultSet resultSet) throws SQLException {
		
		// ResultSet is initially before the first data set
		while (resultSet.next()) {
			// It is possible to get the columns via name
			// also possible to get the columns via the column number
			String ticketNo = resultSet.getString("ticketNo");
			String price = resultSet.getString("price");
			String classType = resultSet.getString("class");
			String flightID = resultSet.getString("flightID");
			String seatNo = resultSet.getString("seatNo");
			String passengerID = resultSet.getString("passengerID");
			//formatting to print out ticket info
			System.out.println("\n\nTicket Number: " + ticketNo);
			System.out.println("Price: " + price);
			System.out.println("Class Assignment: " + classType);
			System.out.println("Flight ID " + flightID);
			System.out.println("Seat Assignment: " + seatNo);
			System.out.println("PassengerID: " + passengerID);

		}
		return resultSet;
	}

	// this is to read the employee info
	public static ResultSet writeResultSetEmployeeInfo(ResultSet resultSet) throws SQLException {
		// ResultSet is initially before the first data set
		while (resultSet.next()) {
			// It is possible to get the columns via name
			// also possible to get the columns via the column number
			// which starts at 1
			// e.g. resultSet.getSTring(2);
			String firstname = resultSet.getString("firstName");
			String lastname = resultSet.getString("lastName");
			String employeeID = resultSet.getString("employeeID");
			String companyID = resultSet.getString("companyID");
			String streetAddress = resultSet.getString("streetAddress");
			String city = resultSet.getString("city");
			String country = resultSet.getString("country");
			String phone = resultSet.getString("phone");
			String email = resultSet.getString("email");
			String title = resultSet.getString("title");
			String salary = resultSet.getString("salary");
                        
				//formatting to print out employee info
			System.out.println("\n\nFirst Name: " + firstname);
			System.out.println("Last Name: " + lastname);
			System.out.println("Employee ID: " + employeeID);
			System.out.println("Company ID: " + companyID);
			System.out.println("Address: " + streetAddress);
			System.out.println("City: " + city);
			System.out.println("Country: " + country);
			System.out.println("Phone Number: " + phone);
			System.out.println("Address: " + email);
			System.out.println("Title: " + title);
			System.out.println("Salary: " + salary);
		}
		return resultSet;
	}
        
        public static ResultSet writeResultSetPassengerBaggageInfo(ResultSet resultSet) throws SQLException {
		// ResultSet is initially before the first data set
		while (resultSet.next()) {
			// It is possible to get the columns via name
			// also possible to get the columns via the column number
			// which starts at 1
			// e.g. resultSet.getSTring(2);
			String bagID = resultSet.getString("bagID");
			String flightID = resultSet.getString("flightID");
			String arrivalLocationID = resultSet.getString("arrivalLocationID");
			String arrivalAirportID = resultSet.getString("arrivalAirportID");
			String departureLocationID = resultSet.getString("departureLocationID");
			String departureAirportID = resultSet.getString("departureAirportID");
			String firstName = resultSet.getString("firstName");
			String lastName = resultSet.getString("lastName");
			String phone = resultSet.getString("phone");
			String passengerID = resultSet.getString("passengerID");
                        
		        //formatting to print out employee info
			System.out.println("\n\nBag ID: " + bagID);
			System.out.println("Flight ID: " + flightID);
			System.out.println("Arrival Location ID: " + arrivalLocationID);
			System.out.println("Arrival Airport ID: " + arrivalAirportID);
			System.out.println("Departure Location ID: " + departureLocationID);
			System.out.println("Departure Airport ID: " + departureAirportID);
			System.out.println("First Name: " + firstName);
			System.out.println("Last Name: " + lastName);
			System.out.println("Phone Number: " + phone);
			System.out.println("Passenger ID: " + passengerID);
		}
		return resultSet;
	}

	// helper to append input
	private static void appendResultSet(ResultSet resultSet) throws SQLException {
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
