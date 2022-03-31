package edu.wpi.agileAngels;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class HomeController extends MainController {

  @FXML Button serviceButton;

  @FXML
  private void homeButton(ActionEvent event) throws IOException {
    Stage stage = null;
    Parent root = null;

    if (event.getSource() == serviceButton) {
      stage = (Stage) serviceButton.getScene().getWindow();
      root = FXMLLoader.load(getClass().getResource("views/serviceRequest-view.fxml"));
    }

    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
}
