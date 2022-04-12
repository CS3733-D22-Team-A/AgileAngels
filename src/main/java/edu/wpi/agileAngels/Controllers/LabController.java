package edu.wpi.agileAngels.Controllers;

import edu.wpi.agileAngels.Database.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

// similar to equip controller
public class LabController extends MainController implements Initializable {

  @FXML
  private TextField labTestLocation, labEmployeeText, labStatus, labDelete, labEdit, labDescription;

  private RequestDAOImpl LabDAO = RequestDAOImpl.getInstance("LabRequest");
  private LocationDAOImpl locDAO = LocationDAOImpl.getInstance();
  private HashMap<String, Employee> employeeHashMap = new HashMap<>();
  private EmployeeManager empDAO = EmployeeManager.getInstance();
  private static ObservableList<Request> labData = FXCollections.observableArrayList();
  private int statusNotStarted, statusInProgress, statusComplete;
  @FXML private TableView labTable;

  @FXML
  private TableColumn nameColumn,
      availableColumn,
      typeColumn,
      locationColumn,
      employeeColumn,
      statusColumn,
      descriptionColumn;
  @FXML
  private Label labTestConfirmation,
      dropdownButtonText,
      completedLabel,
      inProgressLabel,
      notStartedNumber,
      inProgressNumber,
      completedNumbers,
      bloodLabel,
      urineLabel,
      tumorLabel,
      covidLabel;

  public LabController() throws SQLException {}

  /**
   * Will check if the table is empty and if so will populate it.Otherwise, just calls upon the
   * database for the data.
   *
   * @param location
   * @param resources
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {

    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    availableColumn.setCellValueFactory(new PropertyValueFactory<>("available"));
    typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
    locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
    employeeColumn.setCellValueFactory(new PropertyValueFactory<>("employee"));
    statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    if (labData.isEmpty()) {
      System.out.println("THE TABLE IS CURRENTLY EMPTY I WILL POPuLATE");
      LabDAO.csvRead();
      Iterator var3 = LabDAO.getAllRequests().entrySet().iterator();

      while (var3.hasNext()) {
        Map.Entry<String, Request> entry = (Map.Entry) var3.next();
        Request object = (Request) entry.getValue();
        labData.add(object);
        /*
               if (entry.getValue().getStatus().equals("inProgress")) {
                 statusInProgress++;
               }
               if (entry.getValue().getStatus().equals("notStarted")) {
                 statusNotStarted++;
               }
               if (entry.getValue().getStatus().equals("completed")) {
                 statusComplete++;
               }
               setDashboard(statusNotStarted, statusInProgress, statusComplete);

        */
      }
    }
  }

  /**
   * Will set the dashboard's numbers to the certain types of status's.
   *
   * @param notStarted
   * @param inProgress
   * @param complete
   */
  @FXML
  private void setDashboard(int notStarted, int inProgress, int complete) {
    // Should put the numbers on the not started area on the dashboard.
    notStartedNumber.setText(String.valueOf(notStarted));
    // Should put the numbers on the in progress area of dash.
    inProgressNumber.setText(String.valueOf(inProgress));
    // Should put the numbers of the completed statuses into dash.
    completedNumbers.setText(String.valueOf(complete));
  }

  @FXML
  private void submitLabTest() {
    String dropDown = dropdownButtonText.getText();
    String location = labTestLocation.getText();
    String employee = labEmployeeText.getText();
    String status = labStatus.getText();
    String delete = labDelete.getText();
    String edit = labEdit.getText();
    String description = labDescription.getText();
    //  boolean logic = (dropDown.isEmpty() || location.isEmpty() || employee.isEmpty());
    if (!delete.isEmpty()) {
      deleteLabRequest(delete);
    } else if (!labEdit.getText().isEmpty()) {
      editLabRequest(dropDown, location, employee, status, description);
    } else {
      addLabRequest("available", dropDown, location, employee, status, description);
    }
  }

  /**
   * Removes requests off the UI and the database.
   *
   * @param deleteString
   */
  private void deleteLabRequest(String deleteString) {
    if (!deleteString.isEmpty()) {
      System.out.println("DELETE REQUEST");
      for (int i = 0; i < labData.size(); i++) {
        Request object = labData.get(i);
        if (0 == deleteString.compareTo(object.getName())) {
          labData.remove(i);
          LabDAO.deleteRequest(object);
        }
      }
      labTable.setItems(labData);
    }
  }

  /**
   * Add method for labrequest, will add information onto the UI and database within here and set
   * the confirmation text to display for the user.
   *
   * @param available
   * @param dropDown
   * @param location
   * @param employee
   * @param status
   */
  private void addLabRequest(
      String available,
      String dropDown,
      String location,
      String employee,
      String status,
      String description) {
    labTestConfirmation.setText(
        "Thank you! Your "
            + dropDown
            + " you requested will be delivered shortly to "
            + location
            + " by "
            + employee
            + ".");
    Location loc = locDAO.getLocation(location);
    Employee emp = empDAO.getEmployee(employee);
    Request request = new Request("", emp, loc, dropDown, status, description, "", "");

    LabDAO.addRequest(request);
    labData.add(request);
    labTable.setItems(labData);
  }

  /**
   * edits the labRequest on the UI/database, takes in elements from the textfield and sets them.
   *
   * @param dropDown
   * @param location
   * @param employee
   * @param status
   * @param description
   */
  private void editLabRequest(
      String dropDown, String location, String employee, String status, String description) {
    Request found = null;
    int num = 0;
    for (int i = 0; i < labData.size(); i++) {
      Request device = labData.get(i);
      if (0 == labEdit.getText().compareTo(device.getName())) {
        found = device;
        num = i;
      }
    }
    if (found != null) {
      if (!dropDown.isEmpty()) {
        found.setType(dropDown);
        LabDAO.updateType(found, dropDown);
      }
      if (!location.isEmpty()) {
        Location loc = locDAO.getLocation(location);
        found.setLocation(loc);
        LabDAO.updateLocation(found, loc);
      }
      if (!employee.isEmpty()) {
        found.setEmployee(empDAO.getEmployee(employee));
        LabDAO.updateEmployeeName(found, employee); // uhhh will this work?
      }
      if (!status.isEmpty()) {
        found.setStatus(employee);
        LabDAO.updateStatus(found, status);
      }
      if (!description.isEmpty()) { // New description field.
        found.setDescription(description);
        LabDAO.updateDescription(found, description);
      }
      labData.set(num, found);
      // Request found = null;
      // int num = 0;
      for (int i = 0; i < labData.size(); i++) {
        Request device = labData.get(i);
        if (0 == labEdit.getText().compareTo(device.getName())) {
          found = device;
          num = i;
        }
      }
      if (found != null) {
        if (!dropdownButtonText.getText().isEmpty()) {
          String type = dropdownButtonText.getText();
          found.setType(type);
          LabDAO.updateType(found, type);
        }
        if (!labTestLocation.getText().isEmpty()) {
          Location loc = locDAO.getLocation(location);
          found.setLocation(loc);
          LabDAO.updateLocation(found, loc);
        }
        if (!labEmployeeText.getText().isEmpty()) {
          // String employee = labEmployeeText.getText();
          found.setEmployee(empDAO.getEmployee(employee));
          LabDAO.updateEmployeeName(found, employee);
        }
        if (!labDescription.getText().isEmpty()) { // New Description for whatever this part is.
          found.setDescription(description);
          found.setDescription(description); // I am unsure if this is correct.
        }
        labData.set(num, found);

        labTable.setItems(labData);
      }

    } else {
      labTestConfirmation.setText(
          "Thank you! Your "
              + dropdownButtonText.getText()
              + " you requested will be delivered shortly to "
              + labTestLocation.getText()
              + " by "
              + labEmployeeText.getText()
              + ".");
      Location loc = locDAO.getLocation(location);
      Employee emp = empDAO.getEmployee(employee);
      Request request = new Request("", emp, loc, dropDown, status, "", "", "");

      LabDAO.addRequest(request);

      labData.add(request);

      labTable.setItems(labData);
    }
  }
}
