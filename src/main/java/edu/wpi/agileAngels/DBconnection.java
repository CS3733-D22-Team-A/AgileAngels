package edu.wpi.agileAngels;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
}
