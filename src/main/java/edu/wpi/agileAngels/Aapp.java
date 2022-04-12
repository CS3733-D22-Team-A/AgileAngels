package edu.wpi.agileAngels;

import java.io.IOException;
import java.sql.SQLException;

import edu.wpi.agileAngels.Controllers.AppController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    FXMLLoader loader = new FXMLLoader(getClass().getResource("views/login.fxml"));
    Parent root = loader.load();
    Scene defaultScene = new Scene(root);
    appController.setPrimaryStage(primaryStage);

    defaultScene
        .getStylesheets()
        .add(
            "https://fonts.googleapis.com/css2?family=Roboto:wght@100;300;400;500;700;900&display=swap");

    primaryStage.setScene(defaultScene);
    primaryStage.setResizable(false);
    primaryStage.show();
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }
}
