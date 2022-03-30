package edu.wpi.agileAngels;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    SanitationRequest request =
        new SanitationRequest(
            sanitationEmployeeText.getText(), sanLocation.getText(), sanIssue.getText());
  }

  @FXML
  private void clearPage() throws IOException {

    Stage stage;
    Parent root;

    stage = (Stage) homeButton.getScene().getWindow();
    root = FXMLLoader.load(getClass().getResource("views/sanitation-view.fxml"));

    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
}
