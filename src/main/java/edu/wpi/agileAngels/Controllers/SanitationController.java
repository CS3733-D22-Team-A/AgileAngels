package edu.wpi.agileAngels.Controllers;

import edu.wpi.agileAngels.Database.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

public class SanitationController implements Initializable, PropertyChangeListener {
  AppController appController = AppController.getInstance();
  // Test
  @FXML private TextField sanIssue, sanLocation, sanitationEmployeeText, sanitationStatus;
  @FXML private Label sanitationConfirmation;
  private LocationDAOImpl locDAO = LocationDAOImpl.getInstance();
  private EmployeeManager empDAO = EmployeeManager.getInstance();
  // @FXML private TableView sanTable;
  private static ObservableList<Request> sanData =
      FXCollections.observableArrayList(); // list of requests
  private RequestDAOImpl sanDAO;
  @FXML
  private TableColumn nameColumn,
      employeeColumn, // change to employeeColumn
      locationColumn,
      typeColumn,
      statusColumn,
      descriptionColumn,
      availableColumn;

  public SanitationController() throws SQLException {}

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    appController.addPropertyChangeListener(this);
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    String changeType = evt.getPropertyName();
    int newValue = (int) evt.getNewValue();
    appController.displayAlert();
  }

  @FXML
  private void submitSanitation() {
    String issue = sanIssue.getText();
    locDAO.getAllLocations();
    empDAO.getAllEmployees();
    Location location = locDAO.getLocation(sanLocation.getText());
    Employee employee = empDAO.getEmployee(sanitationEmployeeText.getText());
    // String status = sanitationStatus.getText(); // This is broken help!
    //  String dropDown = dropdownButtonText.getText();
    /*if (sanIssue.getText().isEmpty()
        || sanLocation.getText().isEmpty()
        || sanitationEmployeeText.getText().isEmpty()) {
      sanitationConfirmation.setText("Please fill out all the required fields");
    } else {
    *?
     */
    System.out.println(employee.getName() + " " + location.getLongName());
    addSaniRequest(employee, location, issue);

    /**
     * SanitationRequest request = new SanitationRequest( "", sanitationEmployeeText.getText(),
     * sanLocation.getText(), sanIssue.getText(), sanitationStatus.getText(), "");
     * sanDAO.addRequest(request);*
     */
    //  Request request = new Request("", employee, location, "dropDown", status, issue, "", "");
    // }
  }

  private void addSaniRequest(Employee employee, Location location, String issue) {
    sanitationConfirmation.setText(
        "Thank you, "
            + sanitationEmployeeText.getText()
            + " will be sent to "
            + location.getLongName()
            + " to sanitize "
            + sanIssue.getText()
            + ".");
    Request request = new Request("", employee, location, "dropDown", "status", issue, "", "");
    sanDAO.addRequest(request); // there is an error here
    // sanData.add(request);
    // sanTable.setItems(sanData);
  }

  public void clearPage(ActionEvent actionEvent) {
    appController.clearPage();
  }
}
