package edu.wpi.agileAngels;

import java.sql.*;
import java.util.HashMap;
import java.util.Scanner;

public class Adb {
  private HashMap<String, Location> locationData;
  private HashMap<String, MedDevice> medData;

  private LocationDAOImpl LocationDAO;

  private MedDAOImpl MedDAO;

  public void main(String[] args) throws SQLException {

    // Apache Derby and table creation
    System.out.println("-------Embedded Apache Derby Connection Testing --------");
    try {
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
    } catch (ClassNotFoundException e) {
      System.out.println("Apache Derby Driver not found. Add the classpath to your module.");
      System.out.println("For IntelliJ do the following:");
      System.out.println("File | Project Structure, Modules, Dependency tab");
      System.out.println("Add by clicking on the green plus icon on the right of the window");
      System.out.println(
              "Select JARs or directories. Go to the folder where the database JAR is located");
      System.out.println("Click OK, now you can compile your program and run it.");
      e.printStackTrace();
      return;
    }

    System.out.println("Apache Derby driver registered!");

    Statement statementLocations;
    Statement statementMedical;
    try {
      // substitute your database name for myDB
      Connection connection = DBconnection.getConnection();
      statementLocations = connection.createStatement();

      String queryLocations =
              "CREATE TABLE Locations( "
                      + "NodeID VARCHAR(50),"
                      + "xcoord VARCHAR(50),"
                      + "ycoord VARCHAR(50),"
                      + "Floor VARCHAR(50),"
                      + "building VARCHAR(50),"
                      + "NodeType VARCHAR(50),"
                      + "longName VARCHAR(50),"
                      + "shortName VARCHAR(50))";

      statementLocations.execute(queryLocations);

      statementMedical = connection.createStatement();

      String queryMedical =
              "CREATE TABLE MedicalEquipment ( "
                      + "Name VARCHAR(50),"
                      + "available VARCHAR(50),"
                      + "type VARCHAR(50),"
                      + "location VARCHAR(50),"
                      + "employee VARCHAR(50),"
                      + "status VARCHAR(50),"
                      + "description VARCHAR(50))";
      statementMedical.execute(queryMedical);

    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
      return;
    }
    System.out.println("Apache Derby connection established!");
  }





}

