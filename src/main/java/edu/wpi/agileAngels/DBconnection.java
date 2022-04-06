package edu.wpi.agileAngels;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Singleton connection
// One connection, will not create multiple, eliminates repetitive code
// TODO make sure connections are good, most important part
public class DBconnection {
  private static Connection connection;

  static {
    try {
      connection = DriverManager.getConnection("jdbc:derby:myDB;create=true");

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static Connection getConnection() {
    return connection;
  }

  /** Shuts the connection down */
  public static void shutdown() {
    try {
      connection = DriverManager.getConnection("jdbc:derby:myDB;shutdown=true");
    } catch (SQLException e) {
      System.out.println("Shutdown unsuccessful.");
    }
  }
}
