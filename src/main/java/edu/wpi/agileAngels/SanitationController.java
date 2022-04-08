package edu.wpi.agileAngels;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SanitationController extends MainController {

  @FXML private TextField sanIssue, sanLocation, sanitationEmployeeText, sanitationStatus;
  @FXML private Label sanitationConfirmation;

  private RequestDAOImpl sanDAO;

  public void initialize(URL location, ResourceBundle resources) throws SQLException {
    HashMap<String, Request> sanData = new HashMap<String, Request>();
    // sanDAO = new RequestDAOImpl("./san.csv", sanData, 0);
  }

  @FXML
  private void submitSanitation() {
    String issue = sanIssue.getText();
    String location = sanLocation.getText();
    String employee = sanitationEmployeeText.getText();
    // String status = sanitationStatus.getText(); //This is broken help!
    // String dropDown = dropdownButtonText.getText();
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
      /**
       * SanitationRequest request = new SanitationRequest( "", sanitationEmployeeText.getText(),
       * sanLocation.getText(), sanIssue.getText(), sanitationStatus.getText(), "");
       * sanDAO.addRequest(request);*
       */
      //  Request request = new Request("", employee, location, "dropDown", status, issue, "", "");
    }
  }
  /*
    private void addSaniRequest(
        String employee, String location, String dropDown, String status, String issue) {
      sanitationConfirmation.setText(
          "Thank you, "
              + sanitationEmployeeText.getText()
              + " will be sent to "
              + sanLocation.getText()
              + " to sanitize "
              + sanIssue.getText()
              + ".");
      // Request request = new Request("", employee, location, dropDown, status, issue, "", "");
    }
  */
  @FXML
  private void clearPage() throws IOException, InterruptedException {
    loadPage("views/sanitation-view.fxml", sanitationConfirmation);
  }
}
