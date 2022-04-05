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

  @FXML private MenuButton labDropdown;
  @FXML private MenuItem blood, urine, tumor;
  @FXML private TextField labTestLocation, labEmployeeText, labStatus;

  @FXML private TextArea restrictions;
  @FXML private Label labTestConfirmation;
  private RequestDAOImpl LabDAO;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    HashMap<String, Request> LabData = new HashMap<String, Request>();
    LabDAO = new RequestDAOImpl("./Lab.csv", LabData, 0);
  }
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
      LabRequest request =
          new LabRequest(
              labEmployeeText.getText(),
              labTestLocation.getText(),
              labDropdown.getText(),
              labStatus.getText(),
              " ");
      LabDAO.addRequest(request);
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
  private void clearPage() throws IOException, InterruptedException {
    loadPage("views/lab-view.fxml", labStatus);
  }
}
