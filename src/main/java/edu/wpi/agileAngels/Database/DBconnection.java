package edu.wpi.agileAngels.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


// Singleton connection
// One connection, will not create multiple, eliminates repetitive code
// TODO make sure connections are good, most important part
public class DBconnection {
  private static DBConnectionEnum database;
  private static Connection connection;

  //Embedded is the default connection
  static {
    try {
      database = DBConnectionEnum.EMBEDDED;
      connection = DriverManager.getConnection("jdbc:derby:myDB;create=true");

    } catch (SQLException e) {
      System.out.println("Establishing embedded connection failed.");
      e.printStackTrace();
    }
  }

  //Switching from current connection to the other connection
  public static void switchConnection(){
    try {
      switch (database) {
        case EMBEDDED:
          connection.close();
          connection = DriverManager.getConnection("jdbc:derby://localhost:1527/myCSDB;create=true");
          break;
        case CLIENT_SERVER:
          connection.close();
          connection = DriverManager.getConnection("jdbc:derby:myDB;create=true");
          break;
        default:
          return;
      }
    }catch(SQLException sqlException){
      System.out.println("Switching the connection failed.");
      //sqlException.printStackTrace();
    }
  }


  public static Connection getConnection() {
    return connection;
  }

  /** Shuts the connection down */
  public static void shutdown() {
    try {
      connection.close();
    } catch (SQLException e) {
      System.out.println("Shutdown unsuccessful.");
    }
  }
}
