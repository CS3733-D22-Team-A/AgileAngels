package edu.wpi.agileAngels;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SanitationController extends MainController {

  @FXML private TextField sanIssue, sanLocation, sanitationEmployeeText;
  @FXML private Label sanitationConfirmation;

  @FXML
  private void submitSanitation() {
    sanitationConfirmation.setText(
        "Thank you, "
            + sanitationEmployeeText.getText()
            + " will be sent to "
            + sanLocation.getText()
            + " to sanitize "
            + sanIssue.getText()
            + ".");
  }

  @FXML
  private void clearPage() throws IOException {
    resetPage("views/sanitation-view.fxml");
  }
}
