package edu.wpi.agileAngels;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class LabController extends MainController {

  @FXML private Button blood, urine, tumor, covid, labDropdownButton;
  @FXML private TextField labTestLocation, labEmployeeText, labStatus;

  @FXML private TextArea restrictions;
  @FXML private Label labTestConfirmation, dropText, bloodLabel, urineLabel, tumorLabel, covidLabel;
  @FXML Pane drop, drop2;

  @FXML
  private void submitLabTest() {
    if (dropText.getText().isEmpty()
        || labEmployeeText.getText().isEmpty()
        || labEmployeeText.getText().isEmpty()) {
      labTestConfirmation.setText("Please fill out all the required fields");
    } else {
      labTestConfirmation.setText(
          "Thank you! Your "
              + dropText.getText()
              + " you requested will be delivered shortly to "
              + labTestLocation.getText()
              + " by "
              + labEmployeeText.getText()
              + ".");
      LabRequest request =
          new LabRequest(
              labEmployeeText.getText(),
              labTestLocation.getText(),
              dropText.getText(),
              labStatus.getText());
    }
  }

  @FXML
  private void setLabType(ActionEvent event) throws IOException {
    if (event.getSource() == blood) {
      labDropdownButton.setText("Blood Test");
    }
    if (event.getSource() == urine) {
      labDropdownButton.setText("Urine Test");
    }
    if (event.getSource() == tumor) {
      labDropdownButton.setText("Tumor Markup");
    }
  }

  @FXML
  private void clearPage() throws IOException {
    loadPage("views/lab-view.fxml", labStatus);
  }

  public void closeMenu() {
    drop.setVisible(false);
    labDropdownButton.setVisible(true);
  }

  public void labDrop(ActionEvent event) {
    drop2.setViewOrder(-1);
    drop.setViewOrder(-1);
    labDropdownButton.setVisible(false);
    drop.setVisible(true);
  }

  public void menuItemSelected(ActionEvent event) {
    dropText.setTextFill(Color.rgb(0, 0, 0));
    if (event.getSource() == blood) {
      dropText.setText(bloodLabel.getText());
    } else if (event.getSource() == urine) {
      dropText.setText(urineLabel.getText());
    } else if (event.getSource() == tumor) {
      dropText.setText(tumorLabel.getText());
    } else if (event.getSource() == covid) {
      dropText.setText(covidLabel.getText());
    }
    closeMenu();
  }
}
