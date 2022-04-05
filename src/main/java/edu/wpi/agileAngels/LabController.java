package edu.wpi.agileAngels;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;


public class LabController extends MainController implements Initializable {

  @FXML private TextField labTestLocation, labEmployeeText, labStatus;

  private RequestDAOImpl LabDAO;
  @FXML
  private Label labTestConfirmation,
      dropdownButtonText,
      bloodLabel,
      urineLabel,
      tumorLabel,
      covidLabel;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    HashMap<String, Request> LabData = new HashMap<String, Request>();
    LabDAO = new RequestDAOImpl("./Lab.csv", LabData, 0);
  }
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
