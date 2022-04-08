package edu.wpi.agileAngels;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

// service request and map button (page with two buttons 4/6/2022)
public class HomeController extends MainController {

  @FXML Button serviceButton, mapButton;

  @FXML
  private void homeButton(ActionEvent event) throws IOException {
    if (event.getSource() == serviceButton) {
      loadPage("views/serviceRequest-view.fxml", serviceButton);
    } else if (event.getSource() == mapButton) {
      loadPage("views/dashboard-view.fxml", mapButton);
    }
  }
}
