package edu.wpi.agileAngels;

import edu.wpi.agileAngels.Controllers.AppController;
import java.io.IOException;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Aapp extends Application {

  public Aapp() throws SQLException {}

  @Override
  public void init() {
    log.info("Starting Up");
  }

  public Adb adb;

  AppController appController = AppController.getInstance();

  // Creates and displays default scene
  @Override
  public void start(Stage primaryStage) throws IOException, SQLException {
    adb = new Adb(); // ADB class
    adb.initialize();

    appController.init(primaryStage);
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }
}
