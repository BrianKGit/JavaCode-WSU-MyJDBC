package org.totalbeginner.myJDBC;

//import org.totalbeginner.myJDBC.MySQLAccess; // Your other file
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.sql.DriverManager;
//import java.util.Arrays;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.*;

public class TestJDBC {

    public static void main(String[] args) throws Exception {

        boolean flagMan = false;
        boolean flagServ = false;
        boolean flagSec = false;
        boolean loginFlag = false;
        boolean loop;
        String username;
        String passwordInDB = "";
        String salt;
        String passwordToHash;
        String securePassword;
        String sql;
        String answer;
        Connection connect;
        PreparedStatement pst;
        Scanner console = new Scanner(System.in);
        ResultSet resultSet;
        ResultSet resultSet1;

        do {

            //get user input to login
            System.out.println("Please enter your username: ");
            username = console.nextLine();
            System.out.println("Please enter your password: ");
            passwordToHash = console.nextLine();

            // manager login
            if (username.equals("manager")) {

                do {

                    // brians login to the database
                    //connect = DriverManager.getConnection("jdbc:mysql://50.116.3.147/qq8522kn_AMS?user=qq8522kn&password=Spring2019Databass");

                    //local machine log in
                    connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/qq8522kn_AMS","manager","CS485");
                    // authenticate right here
                    String sql8 = "select password from Login where username = ?";
                    pst = connect.prepareStatement(sql8);
                    pst.setObject(1, username);
                    resultSet = pst.executeQuery();
                    resultSet.next();
                    passwordInDB = resultSet.getString(1);

                    String sql15 = "select salt from Login where username = ?";
                    pst = connect.prepareStatement(sql15);
                    pst.setObject(1, username);
                    resultSet1 = pst.executeQuery();
                    resultSet1.next();
                    salt = resultSet1.getString(1);

                    securePassword = get_SHA_512_SecurePassword(passwordToHash, salt);

                    if (securePassword.equals(passwordInDB)) {

                        System.out.println("\nAuthorized user.\n");
                        System.out.println("Please enter a number for which transaction you would like to process.");
                        System.out.println("1: Update baggage." + "\n2: Update Airport Info" + "\n3: Update Employee Info"
                                + "\n4: Add an employee." + "\n5: Delete an employee." + "\n6: Update flight information. "
                                + "\n7: Exit" + "\n\n");
                        int number = console.nextInt();

                        switch (number) {

                            case 1: // this is to append baggage info

                                System.out.println("Update baggage information.");
                                System.out.println("Would you like to view all baggage information or select one?(All/One)");
                                console.nextLine();// clear the buffer
                                answer = console.nextLine();

                                if (answer.equalsIgnoreCase("All")) {

                                    String sql3 = "select * from Baggage";
                                    pst = connect.prepareStatement(sql3);
                                    ResultSet resultSet3 = pst.executeQuery();
                                    manager.writeResultSetBaggageInfo(resultSet3);

                                } else {

                                    System.out.println("Please enter the baggage's ID number: ");
                                    String bagID = console.nextLine();

                                    String sql4 = "select * from Baggage WHERE bagID = ?";
                                    pst = connect.prepareStatement(sql4);
                                    pst.setObject(1, bagID);
                                    ResultSet resultSet4 = pst.executeQuery();
                                    manager.writeResultSetBaggageInfo(resultSet4);
                                    System.out.println("Is this the Bag you would like to update?(Yes/No)");
                                    String answer1 = console.nextLine();

                                    if (answer1.equalsIgnoreCase("Yes")) {

                                        //delete old baggage info
                                        sql = "delete FROM Baggage WHERE Baggage.bagID = ?";
                                        pst = connect.prepareStatement(sql);
                                        pst.setObject(1, bagID);
                                        pst.executeUpdate();

                                        System.out.println("Update baggage information. ");

                                        // get user input for new baggage info
                                        System.out.println("Please enter the baggage ID (b##): ");
                                        bagID = console.nextLine();
                                        System.out.println("Please enter the passengerID (000##): ");
                                        String passengerID = console.nextLine();
                                        System.out.println("Please enter the location ID (Bag#): ");
                                        String locationID = console.nextLine();
                                        pst = connect.prepareStatement("insert into Baggage values( ?, ?, ?)");

                                        // bind the values
                                        pst.setString(1, bagID);
                                        pst.setString(2, passengerID);
                                        pst.setString(3, locationID);

                                        pst.executeUpdate();
                                        connect.close();
                                        System.out.println("\nBaggage info is updated!");

                                    } else {

                                        System.out.println("Cancelled.");

                                    } // end if/else Yes/No

                                } // end if/else All/One

                                break; // end case 1

                            case 2: // change airport info

                                System.out.println("Update Airport Info. ");
                                System.out.println("Would you like to view all airport information or select one to update?(All/One)");
                                console.nextLine();// clear the buffer
                                answer = console.nextLine();

                                if (answer.equalsIgnoreCase("All")) {

                                    sql = "select * from Airport";
                                    pst = connect.prepareStatement(sql);
                                    resultSet = pst.executeQuery();
                                    manager.writeResultSetAirportInfo(resultSet);

                                } else {

                                    System.out.println("Please enter the airport's ID number: ");
                                    String airportID = console.nextLine();

                                    sql = "select * from Airport WHERE airportID = ?";
                                    pst = connect.prepareStatement(sql);
                                    pst.setObject(1, airportID);
                                    ResultSet resultSet4 = pst.executeQuery();
                                    manager.writeResultSetAirportInfo(resultSet4);
                                    System.out.println("Are you sure you would like to update this Airport's Information?(Yes/No)"
                                            + "\nNote: This will permanently change the Airport's location and identifying information.");
                                    answer = console.nextLine();

                                    if (answer.equalsIgnoreCase("Yes")) {

                                        //delete old baggage info
                                        sql = "delete FROM Airport WHERE Airport.airportID = ?";
                                        pst = connect.prepareStatement(sql);
                                        pst.setObject(1, airportID);
                                        pst.executeUpdate();

                                        System.out.println("Please enter street address: ");
                                        String streetAddress = console.nextLine();
                                        System.out.println("Please enter city: ");
                                        String city = console.nextLine();
                                        System.out.println("Please enter country: ");
                                        String country = console.nextLine();
                                        System.out.println("Please enter airport ID(AAA): ");
                                        airportID = console.nextLine();
                                        System.out.println("Please enter airport name: ");
                                        String airportName = console.nextLine();

                                        pst = connect.prepareStatement("insert into Airport values( ?, ?, ?, ?, ?)");

                                        // bind the values
                                        pst.setString(1, streetAddress);
                                        pst.setString(2, city);
                                        pst.setString(3, country);
                                        pst.setString(4, airportID);
                                        pst.setString(5, airportName);

                                        pst.executeUpdate();
                                        connect.close();
                                        System.out.println("\nAirport info is updated!");

                                    } else {

                                        System.out.println("Cancelled.");

                                    } // end if/else Yes or No

                                } // end if/else All/One

                                break;// end case 2

                            case 3: // change employee info

                                System.out.println("Update employee information. ");
                                System.out.println("Would you like to view all employee information or select one to update?(All/One)");
                                console.nextLine();// clear the buffer
                                answer = console.nextLine();

                                if (answer.equalsIgnoreCase("All")) {

                                    sql = "select * from Employee";
                                    pst = connect.prepareStatement(sql);
                                    resultSet = pst.executeQuery();
                                    manager.writeResultSetEmployeeInfo(resultSet);

                                } else {

                                    System.out.println("Please enter the employee's ID number: ");
                                    String employeeID = console.nextLine();

                                    sql = "select * from Employee WHERE employeeID = ?";
                                    pst = connect.prepareStatement(sql);
                                    pst.setObject(1, employeeID);
                                    ResultSet resultSet4 = pst.executeQuery();
                                    manager.writeResultSetEmployeeInfo(resultSet4);

                                    System.out.println("Are you sure you would like to update the employee's Information?(Yes/No)"
                                            + "\nNote: This will permanently change the employee's information.");
                                    answer = console.nextLine();

                                    if (answer.equalsIgnoreCase("Yes")) {

                                        //delete old employee info
                                        sql = "delete FROM Employee WHERE Employee.employeeID = ?";
                                        pst = connect.prepareStatement(sql);
                                        pst.setObject(1, employeeID);
                                        pst.executeUpdate();

                                        //get user input for new employee info
                                        System.out.println("Please enter first name: ");
                                        String firstName = console.nextLine();
                                        System.out.println("Please enter last name: ");
                                        String lastName = console.nextLine();
                                        System.out.println("Please enter employee ID: ");
                                        employeeID = console.nextLine();
                                        System.out.println("Please enter company ID: ");
                                        String companyID = console.nextLine();
                                        System.out.println("Please enter street address: ");
                                        String streetAddress = console.nextLine();
                                        System.out.println("Please enter city: ");
                                        String city = console.nextLine();
                                        System.out.println("Please enter country: ");
                                        String country = console.nextLine();
                                        System.out.println("Please enter phone number: ");
                                        String phone = console.nextLine();
                                        System.out.println("Please enter e-mail address: ");
                                        String email = console.nextLine();
                                        System.out.println("Please enter work title: ");
                                        String title = console.nextLine();
                                        System.out.println("Please enter salary: ");
                                        String salary = console.nextLine();

                                        pst = connect.prepareStatement("insert into Employee values( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

                                        // bind the values
                                        pst.setString(1, firstName);
                                        pst.setString(2, lastName);
                                        pst.setString(3, employeeID);
                                        pst.setString(4, companyID);
                                        pst.setString(5, streetAddress);
                                        pst.setString(6, city);
                                        pst.setString(7, country);
                                        pst.setString(8, phone);
                                        pst.setString(9, email);
                                        pst.setString(10, title);
                                        pst.setString(11, salary);

                                        pst.executeUpdate();
                                        connect.close();
                                        System.out.println("Employee info is updated!");

                                    } else {

                                        System.out.println("Cancelled.");

                                    } // end if/else Yes/No

                                } // end if/else All/One

                                break;// end case 3

                            case 4: // add employee info

                                console.nextLine();
                                System.out.println("Add a new  employee. ");

                                System.out.println("Are you sure you would like to add a new employee?");
                                answer = console.nextLine();

                                if (answer.equalsIgnoreCase("Yes")) {

                                    // create an employee
                                    System.out.println("Please enter first name: ");
                                    String firstName = console.nextLine();
                                    System.out.println("Please enter last name: ");
                                    String lastName = console.nextLine();
                                    System.out.println("Please enter employee ID: ");
                                    String employeeID = console.nextLine();
                                    System.out.println("Please enter company ID: ");
                                    String companyID = console.nextLine();
                                    System.out.println("Please enter street address: ");
                                    String streetAddress = console.nextLine();
                                    System.out.println("Please enter city: ");
                                    String city = console.nextLine();
                                    System.out.println("Please enter country: ");
                                    String country = console.nextLine();
                                    System.out.println("Please enter phone number: ");
                                    String phone = console.nextLine();
                                    System.out.println("Please enter e-mail address: ");
                                    String email = console.nextLine();
                                    System.out.println("Please enter work title: ");
                                    String title = console.nextLine();
                                    System.out.println("Please enter salary: ");
                                    String salary = console.nextLine();

                                    pst = connect.prepareStatement("insert into Employee values( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

                                    // bind the values
                                    pst.setString(1, firstName);
                                    pst.setString(2, lastName);
                                    pst.setString(3, employeeID);
                                    pst.setString(4, companyID);
                                    pst.setString(5, streetAddress);
                                    pst.setString(6, city);
                                    pst.setString(7, country);
                                    pst.setString(8, phone);
                                    pst.setString(9, email);
                                    pst.setString(10, title);
                                    pst.setString(11, salary);

                                    pst.executeUpdate();
                                    connect.close();
                                    System.out.println("\nEmployee info is updated!");

                                } else {

                                    System.out.println("\nCancelled.");

                                }//end if else Yes or No

                                break; // end case 4

                            case 5: // remove employee info

                                System.out.println("Delete an employee: ");

                                System.out.println("Are you sure you would like to delete an employee?");

                                console.nextLine(); //clear buffer
                                answer = console.nextLine();

                                if (answer.equalsIgnoreCase("Yes")) {

                                    System.out.println("Please enter an employeeID to delete(10##):");
                                    String employeeID = console.nextLine();
                                    String deleteSQL = "DELETE FROM Employee WHERE employeeID = ?";
                                    PreparedStatement preparedStatement = connect.prepareStatement(deleteSQL);
                                    preparedStatement.setString(1, employeeID);

                                    // execute delete SQL statement
                                    preparedStatement.executeUpdate();
                                    connect.close();
                                    System.out.println("\nEmployee " + employeeID + " has been removed from the database.");

                                } else {

                                    System.out.println("\nCancelled.");
                                    System.out.println(answer);

                                }//end if else Yes or No

                                break; // case 5

                            case 6: // update flight info

                                System.out.println("Update flight information. ");
                                System.out.println("Would you like to view all flight information or update one?(All/One)");
                                console.nextLine();// clear the buffer
                                answer = console.nextLine();

                                if (answer.equalsIgnoreCase("All")) {

                                    System.out.println("Viewing flight information:");
                                    sql = "select * from Flight";
                                    pst = connect.prepareStatement(sql);
                                    resultSet = pst.executeQuery();
                                    manager.writeResultSetFlightInfo(resultSet);
                                    connect.close();

                                } else {

                                    System.out.println("Please enter the flight's ID number: ");
                                    String flightID = console.nextLine();

                                    sql = "select * from Flight WHERE flightID = ?";
                                    pst = connect.prepareStatement(sql);
                                    pst.setObject(1, flightID);
                                    ResultSet resultSet4 = pst.executeQuery();
                                    manager.writeResultSetFlightInfo(resultSet4);

                                    System.out.println("Are you sure you would like to update the Flight Information?(Yes/No)"
                                            + "\nNote: This will permanently change the Flight's information.");
                                    answer = console.nextLine();

                                    if (answer.equalsIgnoreCase("Yes")) {

                                        //delete old flight info
                                        sql = "delete FROM Flight WHERE Flight.flightID = ?";
                                        pst = connect.prepareStatement(sql);
                                        pst.setObject(1, flightID);
                                        pst.executeUpdate();

                                        System.out.println("Please enter flight status: ");
                                        String status = console.nextLine();
                                        System.out.println("Please enter departure time: ");
                                        String departureTime = console.nextLine();
                                        System.out.println("Please enter departure location ID: ");
                                        String departureLocationID = console.nextLine();
                                        System.out.println("Please enter departure airport ID: ");
                                        String departureAirportID = console.nextLine();
                                        System.out.println("Please enter arrival time: ");
                                        String arrivalTime = console.nextLine();
                                        System.out.println("Please enter arrival location ID: ");
                                        String arrivalLocationID = console.nextLine();
                                        System.out.println("Please enter arrival airport ID: ");
                                        String arrivalAirportID = console.nextLine();
                                        System.out.println("Please enter flight ID: ");
                                        flightID = console.nextLine();

                                        pst = connect.prepareStatement("insert into Flight values( ?, ?, ?, ?, ?, ?, ?, ?)");

                                        // bind the values
                                        pst.setString(1, status);
                                        pst.setString(2, departureTime);
                                        pst.setString(3, departureLocationID);
                                        pst.setString(4, departureAirportID);
                                        pst.setString(5, arrivalTime);
                                        pst.setString(6, arrivalLocationID);
                                        pst.setString(7, arrivalAirportID);
                                        pst.setString(8, flightID);

                                        pst.executeUpdate();
                                        connect.close();
                                        System.out.println("\nFlight info is updated!");

                                    } else {

                                        System.out.println("\nCancelled.");

                                    } // end if/else

                                } // end if/else

                                break;// end case 6

                            case 7: // case to exit the menu

                                flagMan = true;

                                break;

                        }//end manager switches

                    } else {

                        System.out.println("Incorrect Username or Password.");
                        flagMan = true;

                    } // end if else password == password

                } while (!flagMan);

            } //end if else username == manager

            // service login
            if (username.equalsIgnoreCase("service")) {

                do {

                    // brians login to the database
                    //connect = DriverManager.getConnection("jdbc:mysql://50.116.3.147/qq8522kn_AMS?user=qq8522kn&password=Spring2019Databass");

                    //local machine log in
                    connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/qq8522kn_AMS","service","CS485ser");
                    // authenticate right here
                    String sql8 = "select password from Login where username = ?";
                    pst = connect.prepareStatement(sql8);
                    pst.setObject(1, username);
                    resultSet = pst.executeQuery();
                    resultSet.next();
                    passwordInDB = resultSet.getString(1);

                    String sql15 = "select salt from Login where username = ?";
                    pst = connect.prepareStatement(sql15);
                    pst.setObject(1, username);
                    resultSet1 = pst.executeQuery();
                    resultSet1.next();
                    salt = resultSet1.getString(1);

                    securePassword = get_SHA_512_SecurePassword(passwordToHash, salt);

                    if (securePassword.equals(passwordInDB)) {

                        System.out.println("\nAuthorized user.\n");

                        System.out.println("Please enter a number for which transaction you would like to process.");
                        System.out.println("1: View baggage info." + "\n2: View flight info." + "\n3: Update customer information."
                                + "\n4: View airport info." + "\n5: View Company Information." + "\n6: Find Open Tickets to Specified Location" + "\n7: Exit menu.");
                        int number1 = console.nextInt();

                        switch (number1) {

                            case 1: // view baggage info

                                System.out.println("\nViewing baggage info:");
                                console.nextLine();// clear the buffer
                                System.out.println("Would you like to view all baggage information or select one to view? (All/One)");
                                String answer2 = console.nextLine();

                                if (answer2.equalsIgnoreCase("All")) {

                                    // security.writeResultSetCustomerInfo();
                                    // all customer info
                                    sql = "select * from Baggage";
                                    pst = connect.prepareStatement(sql);
                                    resultSet = pst.executeQuery();
                                    service.writeResultSetBaggageInfo(resultSet);

                                } else {

                                    System.out.println("Please enter the ID for the passenger you would like to view baggage info for(000##): ");
                                    String passengerID = console.nextLine();
                                    sql = "select * from Baggage WHERE passengerID = ?";
                                    pst = connect.prepareStatement(sql);
                                    pst.setObject(1, passengerID);
                                    resultSet = pst.executeQuery();
                                    service.writeResultSetBaggageInfo(resultSet);

                                }

                                break;

                            case 2: // view flight info

                                System.out.println("Viewing flight info: ");
                                console.nextLine();// clear the buffer
                                System.out.println("Would you like to view all flights or select one to view? (All/One)");
                                String answer3 = console.nextLine();

                                if (answer3.equalsIgnoreCase("All")) {

                                    // security.writeResultSetCustomerInfo();
                                    // all customer info
                                    sql = "select * from Flight";
                                    pst = connect.prepareStatement(sql);
                                    resultSet = pst.executeQuery();
                                    service.writeResultSetFlightInfo(resultSet);

                                } else {

                                    System.out.println("Please enter the flightID for the flight you would like information on(delta01): ");
                                    String flightID = console.nextLine();
                                    sql = "select * from Flight WHERE flightID = ?";
                                    pst = connect.prepareStatement(sql);
                                    pst.setObject(1, flightID);
                                    resultSet = pst.executeQuery();
                                    service.writeResultSetFlightInfo(resultSet);

                                }//end if else All/One

                                break;

                            case 3: // add remove change customer info

                                System.out.println("\nUpdating passenger information:");
                                System.out.println("Would you like to view all passenger information or select one to update?(All/One)");
                                answer = console.nextLine();
                                answer = console.nextLine();

                                if (answer.equalsIgnoreCase("All")) {

                                    String sql3 = "select * from Passenger";
                                    pst = connect.prepareStatement(sql3);
                                    ResultSet resultSet3 = pst.executeQuery();
                                    service.writeResultSetPassengerInfo(resultSet3);

                                } else {

                                    System.out.println("Please enter the passengerID for the passenger you would like information on(000##): ");
                                    String passengerID = console.nextLine();
                                    sql = "select * from Passenger WHERE passengerID = ?";
                                    pst = connect.prepareStatement(sql);
                                    pst.setObject(1, passengerID);
                                    resultSet = pst.executeQuery();
                                    service.writeResultSetPassengerInfo(resultSet);
                                    System.out.println("Is this the passenger you would like to update?(Yes/No)");
                                    String answer1 = console.nextLine();

                                    if (answer1.equalsIgnoreCase("Yes")) {

                                        System.out.println("Would you like to change passenger information or delete?(Change/Delete)");
                                        String answer4 = console.nextLine();

                                        if (answer4.equalsIgnoreCase("Change")) {

                                            //delete old employee info
                                            sql = "delete FROM Passenger WHERE Passenger.passengerID = ?";
                                            pst = connect.prepareStatement(sql);
                                            pst.setObject(1, passengerID);
                                            pst.executeUpdate();

                                            System.out.println("Please enter first name of passenger: ");
                                            String firstName = console.nextLine();
                                            System.out.println("Please enter last name of passenger: ");
                                            String lastName = console.nextLine();
                                            System.out.println("Please enter phone number of passenger: ");
                                            String phone = console.nextLine();
                                            System.out.println("Please enter email of passenger: ");
                                            String email = console.nextLine();
                                            System.out.println("Please enter street address of passenger: ");
                                            String streetAddress = console.nextLine();
                                            System.out.println("Please enter city of passenger: ");
                                            String city = console.nextLine();
                                            System.out.println("Please enter country of passenger: ");
                                            String country = console.nextLine();
                                            System.out.println("Please enter ID type of passenger: ");
                                            String idType = console.nextLine();
                                            System.out.println("Please enter ID number of passenger: ");
                                            passengerID = console.nextLine();
                                            pst = connect.prepareStatement("insert into Passenger values(?, ?, ?, ?, ?, ?, ?, ?, ?)");

                                            // bind the values
                                            pst.setString(1, firstName);
                                            pst.setString(2, lastName);
                                            pst.setString(3, phone);
                                            pst.setString(4, email);
                                            pst.setString(5, streetAddress);
                                            pst.setString(6, city);
                                            pst.setString(7, country);
                                            pst.setString(8, idType);
                                            pst.setString(9, passengerID);

                                            pst.executeUpdate();
                                            connect.close();

                                        } else {

                                            System.out.println("Enter passengerID for passenger you wish to delete.");
                                            passengerID = console.next();
                                            pst = connect.prepareStatement("delete from Passenger WHERE passengerID = ?");
                                            pst.setString(1, passengerID);
                                            pst.executeUpdate();
                                            System.out.println("Passenger " + passengerID + " deleted.");
                                            connect.close();

                                        }//end if else Update or Delete

                                    } else {

                                        System.out.println("Cancelled.");

                                    }//end if else Yes or No

                                }// end if else All/One

                                break;

                            case 4: // view airport info

                                System.out.println("\nViewing airport info:");
                                console.nextLine(); // clear the buffer
                                System.out.println("Please enter the requested Airport ID to view information: ");
                                String airportID = console.nextLine();
                                sql = "select * from Airport WHERE airportID = ?";
                                pst = connect.prepareStatement(sql);
                                pst.setObject(1, airportID);
                                resultSet = pst.executeQuery();
                                service.writeResultSetAirportInfo(resultSet);
                                connect.close();

                                break;

                            case 5: // view company info

                                System.out.println("Company information: ");
                                console.nextLine(); // clear the buffer
                                System.out.println("Would you like to view all the company information or one? (All/One)");
                                String answer5 = console.nextLine();

                                if (answer5.equalsIgnoreCase("All")) {

                                    // all customer info
                                    String sql2 = "select * from Company";
                                    pst = connect.prepareStatement(sql2);
                                    ResultSet resultSet2 = pst.executeQuery();
                                    service.writeResultSetCompanyInfo(resultSet2);

                                } else {

                                    System.out.println("Please enter the requested company ID for more information: ");
                                    String companyID = console.nextLine();
                                    String sql3 = "select * from Company WHERE companyID = ?";
                                    pst = connect.prepareStatement(sql3);
                                    pst.setObject(1, companyID);
                                    ResultSet resultSet3 = pst.executeQuery();
                                    service.writeResultSetCompanyInfo(resultSet3);

                                }// end if else All/One

                                break;

                            case 6:
                                console.nextLine();
           
                                System.out.println("Please enter the departure airport:");

                                String departureAirportID = console.nextLine();
                                
                                System.out.println("Please enter the arrival airport:");
                                
                                String arrivalAirportID = console.nextLine();
                                String passengerID ="";
                                String sql20 = "SELECT Ticket.flightID, Ticket.price, Ticket.class, Ticket.seatNo , "
                                        + "Flight.departureTime, Flight.departureAirportID, Flight.arrivalTime, Flight.arrivalAirportID "
                                        + "FROM Ticket INNER JOIN Flight on Ticket.flightID=Flight.flightID "
                                        + "WHERE Ticket.passengerID=\"\" AND Flight.departureAirportID = ? AND Flight.arrivalAirportID = ?";
                                pst = connect.prepareStatement(sql20);
                                pst.setObject(1, departureAirportID);
                                pst.setObject(2, arrivalAirportID);
                                ResultSet resultSet5 = pst.executeQuery();
                                service.writeResultSetOpenTicketInfo(resultSet5);
                                
                                break;
                                
                            case 7:

                                flagServ = true;

                                break;

                        }//end service switches

                    } else {

                        System.out.println("Incorrect Username or Password.");
                        flagServ = true;

                    }// end if else password == password

                } while (!flagServ);

            }// end if else username == service

            if (username.equalsIgnoreCase("security")) {

                do {
                    // brians login to the database
                    //connect = DriverManager.getConnection("jdbc:mysql://50.116.3.147/qq8522kn_AMS?user=qq8522kn&password=Spring2019Databass");

                    //local machine log in
                    connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/qq8522kn_AMS","Security","cs485sec");
                    // authenticate right here
                    String sql8 = "select password from Login where username = ?";
                    pst = connect.prepareStatement(sql8);
                    pst.setObject(1, username);
                    resultSet = pst.executeQuery();
                    resultSet.next();
                    passwordInDB = resultSet.getString(1);

                    String sql15 = "select salt from Login where username = ?";
                    pst = connect.prepareStatement(sql15);
                    pst.setObject(1, username);
                    resultSet1 = pst.executeQuery();
                    resultSet1.next();
                    salt = resultSet1.getString(1);

                    securePassword = get_SHA_512_SecurePassword(passwordToHash, salt);

                    if (securePassword.equals(passwordInDB)) {

                        System.out.println("\nAuthorized User.");

                        System.out.println("Please select a number for which transaction you would like to process.");
                        System.out.println("1: View passenger information." + "\n2: View ticket information."
                                + "\n3: View employee information." + "\n4: Find Bag's Owner." + "\n5: Exit.");
                        int number2 = console.nextInt();

                        switch (number2) {

                            case 1: // view customer info

                                System.out.println("Customer information: ");
                                console.nextLine();// clear the buffer
                                System.out.println("Would you like to view all passenger information or one? (All/One)");
                                String answer2 = console.nextLine();

                                if (answer2.equalsIgnoreCase("All")) {

                                    // all customer info
                                    sql = "select * from Passenger";
                                    pst = connect.prepareStatement(sql);
                                    resultSet = pst.executeQuery();
                                    security.writeResultSetPassengerInfo(resultSet);

                                } else {

                                    System.out.println("Please enter the ID for the passenger you would like to view:");
                                    String passengerID = console.nextLine();
                                    sql = "select * from Passenger WHERE passengerID = ?";
                                    pst = connect.prepareStatement(sql);
                                    pst.setObject(1, passengerID);
                                    resultSet = pst.executeQuery();
                                    security.writeResultSetPassengerInfo(resultSet);

                                }//end if else All/One

                                break;

                            case 2: // view ticket info

                                System.out.println("Viewing ticket information: ");
                                console.nextLine();
                                System.out.println("Would you like to view all ticket information or one?(All/One)");
                                String answer1 = console.nextLine();

                                if (answer1.equalsIgnoreCase("All")) {

                                    String sql2 = "select * from Ticket";
                                    pst = connect.prepareStatement(sql2);
                                    ResultSet resultSet2 = pst.executeQuery();
                                    security.writeResultSetTicketInfo(resultSet2);
                                    connect.close();

                                } else {

                                    System.out.println("Please enter the ticket number(00001122 -11234457) : ");
                                    String ticketNo = console.nextLine();
                                    String sql2 = "select * from Ticket WHERE ticketNo= ?";
                                    pst = connect.prepareStatement(sql2);
                                    pst.setObject(1, ticketNo);
                                    ResultSet resultSet2 = pst.executeQuery();
                                    security.writeResultSetTicketInfo(resultSet2);
                                    connect.close();

                                }//end if else All/One

                                break;

                            case 3: // view employee info

                                System.out.println("Viewing employee information: ");
                                System.out.println("Would you like to view all employee information or one?(All/One)");
                                console.nextLine();// clear the buffer
                                answer = console.nextLine();

                                if (answer.equalsIgnoreCase("All")) {

                                    String sql3 = "select * from Employee";
                                    pst = connect.prepareStatement(sql3);
                                    ResultSet resultSet3 = pst.executeQuery();
                                    security.writeResultSetEmployeeInfo(resultSet3);
                                    // security.writeResultSetEmployeeInfo();
                                    connect.close();

                                } else {

                                    System.out.println("Please enter the employee's ID number(1001-1006):");
                                    String employeeID = console.nextLine();

                                    String sql4 = "select * from Employee WHERE employeeID = ?";
                                    pst = connect.prepareStatement(sql4);
                                    pst.setObject(1, employeeID);
                                    ResultSet resultSet4 = pst.executeQuery();
                                    security.writeResultSetEmployeeInfo(resultSet4);
                                    connect.close();

                                }//end if else All/One

                                break;

                            case 4: // find who this bag belongs to

                                console.nextLine();
                                System.out.println("Please enter the bag's ID number(b##):");
                                String bagID = console.nextLine();

                                String sql4 = "SELECT Baggage.bagID, Flight.flightID, Flight.arrivalLocationID, "
                                        + "Flight.arrivalAirportID, Flight.departureLocationID, Flight.departureAirportID, "
                                        + "Passenger.firstName, Passenger.lastName,Passenger.phone, Passenger.passengerID "
                                        + "FROM Flight INNER JOIN Ticket on Flight.flightID = "
                                        + "Ticket.flightID INNER JOIN Passenger on Ticket.passengerID=Passenger.passengerID "
                                        + "INNER JOIN Baggage on Passenger.passengerID=Baggage.passengerID "
                                        + "WHERE Baggage.bagID= ?";
                                pst = connect.prepareStatement(sql4);
                                pst.setObject(1, bagID);
                                ResultSet resultSet4 = pst.executeQuery();
                                security.writeResultSetPassengerBaggageInfo(resultSet4);
                                connect.close();
                                
                                break;

                            case 5: // this is to exit menu

                                flagSec = true;

                                break;

                        }// end security switches

                    } else {

                        System.out.println("Incorrect Username or Password.");
                        flagSec = true;

                    }//end if else password == password

                } while (!flagSec);

            }// end if else username == security
            console.nextLine();
        } while (!loginFlag);

    }//end main()

    private static byte[] getSalt() throws NoSuchAlgorithmException {

        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;

    } //end getSalt()

    //use this method as it has the conversion from string to byte array within it
    public static String get_SHA_512_SecurePassword(String passwordToHash, String salt) {

        String generatedPassword = null;

        try {

            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }//end for loop

            generatedPassword = sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }//end try catch

        return generatedPassword;

    }//end get_SHA_512_SecurePassword

    public void close() {

        ResultSet resultSet = null;
        PreparedStatement pst = null;
        Connection connect = null;
        Console console = null;

        try {

            if (resultSet != null) {
                resultSet.close();
            }

            if (pst != null) {
                pst.close();
            }

            if (connect != null) {
                connect.close();
            }

        } catch (Exception e) {

        }//end try catch

    }// end close

}//end class
