package edu.wpi.agileAngels;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LabController extends MainController {

  @FXML private TextField labTestLocation, labEmployeeText, labStatus;
  @FXML
  private Label labTestConfirmation,
      dropdownButtonText,
      bloodLabel,
      urineLabel,
      tumorLabel,
      covidLabel;

  @FXML
  private void submitLabTest() {
    if (dropdownButtonText.getText().isEmpty()
        || labEmployeeText.getText().isEmpty()
        || labEmployeeText.getText().isEmpty()) {
      labTestConfirmation.setText("Please fill out all the required fields");
    } else {
      labTestConfirmation.setText(
          "Thank you! Your "
              + dropdownButtonText.getText()
              + " you requested will be delivered shortly to "
              + labTestLocation.getText()
              + " by "
              + labEmployeeText.getText()
              + ".");
      LabRequest request =
          new LabRequest(
              labEmployeeText.getText(),
              labTestLocation.getText(),
              dropdownButtonText.getText(),
              labStatus.getText());
    }
  }
}
