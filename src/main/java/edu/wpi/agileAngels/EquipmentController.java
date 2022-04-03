package edu.wpi.agileAngels;

import java.awt.*;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class EquipmentController extends MainController {
  // @FXML private MenuButton eqptDropdown;
  //  @FXML private MenuItem bed, recliner, xray, infusion;
  @FXML private Button equipDropdown, bed, recliner, xray, infusion, equipDropdownButton;
  @FXML private TextField equipLocation, equipmentEmployeeText, equipmentStatus;
  @FXML private Label equipmentConfirmation, dropText;
  @FXML private TableView equipmentTable;
  @FXML Pane drop;

  @FXML
  private TableColumn nameColumn,
      availableColumn,
      typeColumn,
      locationColumn,
      employeeColumn,
      statusColumn,
      descriptionColumn;

  @FXML
  private void submitEquipment() {

    if (dropText.getText().isEmpty()
        || equipLocation.getText().isEmpty()
        || equipmentEmployeeText.getText().isEmpty()) {
      equipmentConfirmation.setText("Please fill out all the require fields");
    } else {
      equipmentConfirmation.setText(
          "Thank you, the "
              + dropText.getText()
              + " you requested will be delivered shortly to "
              + equipLocation.getText()
              + " by "
              + equipmentEmployeeText.getText()
              + ".");
      EquipmentRequest request =
          new EquipmentRequest(
              equipmentEmployeeText.getText(),
              equipLocation.getText(),
              dropText.getText(),
              equipmentStatus.getText());
    }
  }

  public void eqpDrop() throws IOException {
    //    drop.setLayoutX(equipDropdownButton.getParent().getParent().getLayoutX());
    //    drop.setLayoutY(equipDropdownButton.getParent().getParent().getLayoutY());

    // drop.setLayoutX(getPositionX(equipDropdownButton));
    // drop.setLayoutY(getPositionY(equipDropdownButton));
    drop.setViewOrder(0);
    drop.setVisible(true);
  }

  public void closeMenu(MouseEvent mouseEvent) {
    drop.setVisible(false);
  }

  public void menuItemSelected(ActionEvent event) {
    dropText.setTextFill(Color.rgb(0, 0, 0));
    if (event.getSource() == bed) {
      dropText.setText("Bed");
    } else if (event.getSource() == recliner) {
      dropText.setText("Recliner");
    } else if (event.getSource() == xray) {
      dropText.setText("XRay Machine");
    } else if (event.getSource() == infusion) {
      dropText.setText("Infusion Pump");
    }
    drop.setVisible(false);
  }

  @FXML
  private void clearPage() throws IOException, InterruptedException {

    loadPage("views/equipment-view.fxml", equipmentStatus);
  }
}
