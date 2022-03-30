package edu.wpi.agileAngels;

import java.io.IOException;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Aapp extends Application {

  @Override
  public void init() {
    log.info("Starting Up");
  }

  // Creates and displays default scene
  @Override
  public void start(Stage primaryStage) throws IOException, SQLException {
    Adb adb = new Adb();
    String[] args = new String[1];
    adb.main(args);
    Parent root = FXMLLoader.load(getClass().getResource("views/default-view.fxml"));
    // Parent root = FXMLLoader.load(getClass().getResource("views/gifts-view.fxml"));
    Scene defaultScene = new Scene(root);
    primaryStage.setScene(defaultScene);
    primaryStage.show();
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }
}
