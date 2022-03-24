package edu.wpi.agileAngels;

import java.io.IOException;
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
  public void start(Stage primaryStage) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("views/default-view.fxml"));
    Scene defaultScene = new Scene(root);
    primaryStage.setScene(defaultScene);
    primaryStage.show();
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }
}
