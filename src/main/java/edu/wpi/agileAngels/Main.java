package edu.wpi.agileAngels;

import java.io.IOException;

public class Main {

  public static void main(String[] args) throws IOException, InterruptedException {

    Adb adb = new Adb();
    adb.main(args);
    // Aapp.launch(Aapp.class, args);
  }
}
