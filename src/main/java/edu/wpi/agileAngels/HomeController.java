package edu.wpi.agileAngels;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class HomeController extends MainController {

  @FXML Button serviceButton;

  @FXML
  private void homeButton(ActionEvent event) throws IOException {
    if (event.getSource() == serviceButton) {
      loadPage("views/serviceRequest-view.fxml", serviceButton);
    }
  }
}
