package edu.wpi.agileAngels;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LabController extends MainController {

  @FXML private MenuButton labDropdown;
  @FXML private MenuItem blood, urine, tumor;
  @FXML private TextField labTestLocation, labEmployeeText;

  @FXML private TextArea restrictions;
  @FXML private Label labTestConfirmation;

  @FXML
  private void submitLabTest() {
    if (labDropdown.getText().isEmpty()
        || labEmployeeText.getText().isEmpty()
        || labEmployeeText.getText().isEmpty()) {
      labTestConfirmation.setText("Please fill out all the required fields");
    } else {
      labTestConfirmation.setText(
          "Thank you! Your "
              + labDropdown.getText()
              + " you requested will be delivered shortly to "
              + labTestLocation.getText()
              + " by "
              + labEmployeeText.getText()
              + ".");
    }
  }

  @FXML
  private void setLabType(ActionEvent event) throws IOException {
    if (event.getSource() == blood) {
      labDropdown.setText("Blood Test");
    }
    if (event.getSource() == urine) {
      labDropdown.setText("Urine Test");
    }
    if (event.getSource() == tumor) {
      labDropdown.setText("Tumor Markup");
    }
  }

  @FXML
  private void clearPage() throws IOException {
    resetPage("views/lab-view.fxml");
  }
}
