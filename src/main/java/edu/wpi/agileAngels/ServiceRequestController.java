package edu.wpi.agileAngels;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ServiceRequestController extends MainController {

  @FXML Button equipmentRequest, labRequest, sanRequest, mealRequest, giftRequest, testButton;

  public void requestButton(ActionEvent event) throws IOException, InterruptedException {
    if (event.getSource() == equipmentRequest) {
      loadPage("views/equipment-view.fxml", equipmentRequest);
    }
    if (event.getSource() == labRequest) {
      loadPage("views/lab-view.fxml", equipmentRequest);
    }
    if (event.getSource() == sanRequest) {
      loadPage("views/sanitation-view.fxml", equipmentRequest);
    }
    if (event.getSource() == mealRequest) {
      loadPage("views/mealRequest-view.fxml", equipmentRequest);
    }
    if (event.getSource() == giftRequest) {
      loadPage("views/gifts-view.fxml", equipmentRequest);
    }
    if (event.getSource() == testButton) {
      loadPage("views/test-view.fxml", equipmentRequest);
    }
  }
}
