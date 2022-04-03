package edu.wpi.agileAngels;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SanitationController extends MainController {

  @FXML private TextField sanIssue, sanLocation, sanitationEmployeeText, sanitationStatus;
  @FXML private Label sanitationConfirmation;

  @FXML
  private void submitSanitation() {
    if (sanIssue.getText().isEmpty()
        || sanLocation.getText().isEmpty()
        || sanitationEmployeeText.getText().isEmpty()) {
      sanitationConfirmation.setText("Please fill out all the required fields");
    } else {
      sanitationConfirmation.setText(
          "Thank you, "
              + sanitationEmployeeText.getText()
              + " will be sent to "
              + sanLocation.getText()
              + " to sanitize "
              + sanIssue.getText()
              + ".");
      SanitationRequest request =
          new SanitationRequest(
              sanitationEmployeeText.getText(),
              sanLocation.getText(),
              sanIssue.getText(),
              sanitationStatus.getText());
    }
  }

  @FXML
  private void clearPage() throws IOException, InterruptedException {
    loadPage("views/sanitation-view.fxml", sanitationConfirmation);
  }
}
