package edu.wpi.agileAngels;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class MainController {

  @FXML Button homeButton, clear;

  @FXML
  private void goHome() throws IOException {

    Stage stage;
    Parent root;

    stage = (Stage) homeButton.getScene().getWindow();
    root = FXMLLoader.load(getClass().getResource("views/default-view.fxml"));

    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  // Make this work It would be awesome!!

  //  @FXML
  //  private void clearPage() {
  //
  //    Stage stage;
  //    stage = (Stage) homeButton.getScene().getWindow();
  //
  //    Scene scene = clear.getScene();
  //    stage.setScene(scene);
  //    stage.show();
  //  }
}
