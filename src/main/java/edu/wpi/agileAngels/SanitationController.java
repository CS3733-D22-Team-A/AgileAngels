package edu.wpi.agileAngels;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SanitationController extends MainController {

  @FXML private TextField sanIssue, sanLocation;
  @FXML private Label sanitationConfirmation;

  @FXML
  private void submitSanitation() {
    sanitationConfirmation.setText(
        "Thank you, someone will be sent to "
            + sanLocation.getText()
            + " to sanitize "
            + sanIssue.getText()
            + ".");
  }
}
