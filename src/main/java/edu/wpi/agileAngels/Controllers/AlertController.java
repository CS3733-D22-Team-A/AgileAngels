package edu.wpi.agileAngels.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AlertController {

  @FXML Button requestButton;

  @FXML
  public void makeRequests() {
    // make requests
    System.out.println("make requests here");

    closeWindow();
  }

  public void closeWindow() {
    Stage stage = (Stage) requestButton.getScene().getWindow();
    stage.close();
  }
}
