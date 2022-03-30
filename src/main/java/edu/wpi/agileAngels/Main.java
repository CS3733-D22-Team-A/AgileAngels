package edu.wpi.agileAngels;

import java.sql.SQLException;

public class Main {

  public static void main(String[] args) throws SQLException {

    Adb adb = new Adb();
    adb.main(args);
    Aapp.launch(Aapp.class, args);
  }
}
