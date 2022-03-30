package edu.wpi.agileAngels;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

public class EquipmentController extends MainController {
  @FXML private MenuButton eqptDropdown;
  @FXML private MenuItem bed, recliner, xray, infusion;
  @FXML private TextField equipLocation, equipmentEmployeeText;
  @FXML private Label equipmentConfirmation;

  @FXML
  private void submitEquipment() {

    if (eqptDropdown.getText().isEmpty()
        || equipLocation.getText().isEmpty()
        || equipmentEmployeeText.getText().isEmpty()) {
      equipmentConfirmation.setText("Please fill out all the require fields");
    } else {
      equipmentConfirmation.setText(
          "Thank you, the "
              + eqptDropdown.getText()
              + " you requested will be delivered shortly to "
              + equipLocation.getText()
              + " by "
              + equipmentEmployeeText.getText()
              + ".");
    }
  }

  @FXML
  private void setEquipmentType(ActionEvent event) throws IOException {
    if (event.getSource() == bed) {
      eqptDropdown.setText("Bed");
    }
    if (event.getSource() == recliner) {
      eqptDropdown.setText("Recliner");
    }
    if (event.getSource() == xray) {
      eqptDropdown.setText("X-Ray Machine");
    }
    if (event.getSource() == infusion) {
      eqptDropdown.setText("Infusion");
    }
  }

  @FXML
  private void clearPage() throws IOException {

    resetPage("views/equipment-view.fxml");
  }
}
