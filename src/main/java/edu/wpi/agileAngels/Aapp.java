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

  private MedDAOImpl medDAO;

  @Override
  public void init() {
    log.info("Starting Up");
  }

  public Adb adb;

  // Creates and displays default scene
  @Override
  public void start(Stage primaryStage) throws IOException, SQLException {
    adb = new Adb();
    String[] args = new String[1];
    adb.main(args);
    FXMLLoader loader = new FXMLLoader(getClass().getResource("views/login.fxml")); //replaced with Login.
    Parent root = loader.load();
    Scene defaultScene = new Scene(root);
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
