package edu.wpi.agileAngels.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class BedAlertController {

  @FXML Button requestButton;
  private AppController appController = AppController.getInstance();

  @FXML
  public void makeRequests() {
    String floor = appController.getBedFloor();

    closeWindow();
  }

  public void closeWindow() {
    Stage stage = (Stage) requestButton.getScene().getWindow();
    stage.close();
  }
}
