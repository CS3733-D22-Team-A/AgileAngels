package edu.wpi.agileAngels.Controllers;

import edu.wpi.agileAngels.Database.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

// similar to equip controller
public class LabController extends MainController implements Initializable, PropertyChangeListener {

  @FXML
  private TextField labTestLocation,
      labEmployeeText,
      labStatus,
      labDelete,
      labEdit,
      labDescription,
      employeeFilterField,
      statusFilterField;

  private RequestDAOImpl LabDAO = RequestDAOImpl.getInstance("LabRequest");
  private LocationDAOImpl locDAO = LocationDAOImpl.getInstance();
  private HashMap<String, Employee> employeeHashMap = new HashMap<>();
  private EmployeeManager empDAO = EmployeeManager.getInstance();
  private static ObservableList<Request> labData = FXCollections.observableArrayList();
  private int statusNotStarted, statusInProgress, statusComplete;

  AppController appController = AppController.getInstance();

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
      completedNumber,
      bloodLabel,
      urineLabel,
      tumorLabel,
      covidLabel;

  public LabController() throws SQLException {}

  /**
   * Will check if the table is empty and if so will populate it.Otherwise, just calls upon the
   * database for the data. This also contains a dashboardLoad function that will make the numbers
   * on the dashboard appear.
   *
   * @param location
   * @param resources
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    appController.addPropertyChangeListener(this);

    statusNotStarted = 0;
    statusInProgress = 0;
    statusComplete = 0;
    // nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    availableColumn.setCellValueFactory(new PropertyValueFactory<>("available"));
    typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
    locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
    locDAO.getAllLocations();
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
    employeeColumn.setCellValueFactory(new PropertyValueFactory<>("employee"));
    locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
    typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
    statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    availableColumn.setCellValueFactory(new PropertyValueFactory<>("attribute1"));
    if (labData.isEmpty()) {
      System.out.println("THE TABLE IS CURRENTLY EMPTY I WILL POPuLATE");
      LabDAO.csvRead();
      Iterator var3 = LabDAO.getAllRequests().entrySet().iterator();
      for (Map.Entry<String, Request> entry : LabDAO.getAllRequests().entrySet()) {
        Request req = entry.getValue();
        dashboardLoad();
        labData.add(req);
      }
    }
    // This has to be here for when you do: -> Back -> Lab Request. It'll load the numbers again. -
    // Justin
    dashboardLoad();
    labTable.setItems(labData);
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    String changeType = evt.getPropertyName();
    int newValue = (int) evt.getNewValue();
    appController.displayAlert();
  }

  /**
   * This is the cleaner version of Justin's dashboard code. Note that it may need a for loop as
   * shown on line 83/84 if used elsewhere. Note: Unlikely.
   */
  @FXML
  private void dashboardLoad() {
    if (notStartedNumber.getText().equals("-")
        && inProgressNumber.getText().equals("-")
        && completedNumber.getText().equals("-")) {
      System.out.println("THE NUMBERS ARE EMPTY, RELEASE THE HOUNDS");
      LabDAO.csvRead();
      Iterator var3 = LabDAO.getAllRequests().entrySet().iterator();
      while (var3.hasNext()) {
        Map.Entry<String, Request> entry = (Map.Entry) var3.next();
        Request object = (Request) entry.getValue();
        if (entry.getValue().getStatus().equals("inProgress")) {
          statusInProgress++;
          System.out.println("beep");
        }
        if (entry.getValue().getStatus().equals("notStarted")) {
          statusNotStarted++;
          System.out.println("boop");
        }
        if (entry.getValue().getStatus().equals("Complete")
            || entry.getValue().getStatus().equals("complete")) {
          statusComplete++;
          System.out.println("hi");
        }
        System.out.println(entry.getValue().getStatus());
      }
      setDashboard(statusNotStarted, statusInProgress, statusComplete);
    }
  }

  /**
   * Will set the dashboard's numbers to the certain types of status's, literally is just a setter
   * method to keep things clean. - Justin
   *
   * @param notStarted
   * @param inProgress
   * @param complete
   */
  @FXML
  private void setDashboard(int notStarted, int inProgress, int complete) {
    String notStart = Integer.toString(notStarted);
    String inProg = Integer.toString(inProgress);
    String comp = Integer.toString(complete);
    // Should put the numbers on the not started area on the dashboard.
    notStartedNumber.setText(notStart); // perhaps string value?
    // Should put the numbers on the in progress area of dash.
    inProgressNumber.setText(inProg);
    // Should put the numbers of the completed statuses into dash.
    completedNumber.setText(comp);
  }

  /** Takes in employee fields by the textfields. And submits it. */
  @FXML
  private void submitLabTest() {
    // String dropDown = dropdownButtonText.getText();
    String dropDown = "test";
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
      System.out.println(locDAO.getLocation(location) + " " + empDAO.getEmployee(employee));
      addLabRequest(
          "available",
          dropDown,
          locDAO.getLocation(location),
          empDAO.getEmployee(employee),
          status,
          description);
    }
  }

  /**
   * Removes requests off the UI and the database, will call upon dashBoardLoad() to decrement the
   * dashboard.
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

      // This cannot be a helper since it DECREASES the dashboard. It could but that's just more if
      // statements.
      // This is easier to read :)
      String status = LabDAO.getAllRequests().get(deleteString).getStatus();
      if (status.equals("inProgress")) {
        statusInProgress--;
      }
      if (status.equals("complete")) {
        statusComplete--;
      }
      if (status.equals("notStarted")) {
        statusNotStarted--;
      }
      setDashboard(statusNotStarted, statusInProgress, statusComplete);
    }
  }

  /**
   * Add method for labrequest, will add information onto the UI and database within here and set
   * the confirmation text to display for the user. Note the location has to be specific.. until
   * dropdowns are done. Use this location for debugging: CREST002L1 Will also call upon
   * dashboardLoad() to update the dashboard.
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
      Location location,
      Employee employee,
      String status,
      String description) {
    labTestConfirmation.setText(
        "Thank you! Your "
            + dropDown
            + " you requested will be delivered shortly to "
            + location.getLongName()
            + " by "
            + employee.getName()
            + ".");

    Request request =
        new Request("", employee, location, dropDown, status, description, "available", "");

    LabDAO.addRequest(request);
    labData.add(request);
    labTable.setItems(labData);

    // This is for the dashboard on the LabRequest page. can't have dashboardLoad() because atm am
    // too smol brain.
    // To think of how to know the number increased.
    if (status.equals("inProgress")) {
      statusInProgress++;
    }
    if (status.equals("complete")) {
      statusComplete++;
    }
    if (status.equals("notStarted")) {
      statusNotStarted++;
    }
    setDashboard(statusNotStarted, statusInProgress, statusComplete);
  }

  /**
   * edits any existing requests. Dev Note: Make sure you use the EXACT capitalization or it won't
   * work. This may need to have a dashboardLoad() somewhere.
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
    Employee emp = empDAO.getEmployee(employee);
    Location loc = locDAO.getLocation(location);

    if (found != null) {
      if (!dropDown.isEmpty()) {
        found.setType(dropDown);
        // LabDAO.updateType(found, dropDown);
      }
      if (!location.isEmpty()) {
        // Location loc = locDAO.getLocation(location);
        found.setLocation(loc);
        // LabDAO.updateLocation(found, loc);
      }
      if (!employee.isEmpty()) {
        found.setEmployee(empDAO.getEmployee(employee));
        // LabDAO.updateEmployeeName(found, employee); // uhhh will this work?
      }
      if (!status.isEmpty()) {
        found.setStatus(employee);
        // LabDAO.updateStatus(found, status);
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
          // Location loc = locDAO.getLocation(location);
          found.setLocation(loc);
          LabDAO.updateLocation(found, loc);
        }
        if (!labEmployeeText.getText().isEmpty()) {
          // Employee emp = empDAO.getEmployee(employee);
          found.setEmployee(emp);
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

      Request request = new Request("", emp, loc, dropDown, status, "", "", "");

      LabDAO.addRequest(request);

      labData.add(request);

      labTable.setItems(labData);
    }
  }

  /* FILTER METHODS BEYOND HERE */

  /** Does filterReqsTable methods when "Submit Filters" is clicked, or "onAction." */
  @FXML
  public void filterReqOnAction() {
    if (!employeeFilterField.getText().isEmpty() && !statusFilterField.getText().isEmpty()) {
      ObservableList<Request> empFilteredList = filterReqEmployee(employeeFilterField.getText());
      ObservableList<Request> trueFilteredList =
          filterFilteredReqListStatus(statusFilterField.getText(), empFilteredList);

      // Di-rectly touching equipment table in n-filter cases.
      labTable.setItems(trueFilteredList);
    } else if (!employeeFilterField.getText().isEmpty()) {
      filterReqsTableEmployee(employeeFilterField.getText());
    } else if (!statusFilterField.getText().isEmpty()) {
      filterReqsTableStatus(statusFilterField.getText());
    }
  }

  /** Puts all of the requests back on the table, "clearing the requests." */
  @FXML
  public void clearFilters() {
    // Puts everything back on table.
    labTable.setItems(labData);
  }

  /* Employee-based */

  /**
   * Filters requests in the equipment table so only those with the given Employee remain.
   *
   * @param employeeName The Employee the requests must have to remain on the table.
   */
  private void filterReqsTableEmployee(String employeeName) {
    ObservableList<Request> filteredList = filterReqEmployee(employeeName);

    // Sets table to only have contents of the filtered list.
    labTable.setItems(filteredList);
  }

  /**
   * Filters out requests in labData based on the given Employee.
   *
   * @param employeeName The Employee that the requests must have to be in the new list.
   * @return The new filtered list.
   */
  private ObservableList<Request> filterReqEmployee(String employeeName) {
    ObservableList<Request> newList = FXCollections.observableArrayList();

    for (Request req : labData) {
      if (req.getEmployee().getName().equals(employeeName)) {
        newList.add(req);
      }
    }

    return newList;
  }

  // Status-based

  /**
   * Filters requests in the equipment table so only those with the given status remain.
   *
   * @param reqStatus The status the requests must have to remain on the table.
   */
  private void filterReqsTableStatus(String reqStatus) {
    ObservableList<Request> filteredList = filterReqStatus(reqStatus);

    // Sets table to only have contents of the filtered list.
    labTable.setItems(filteredList);
  }

  /**
   * Filters out requests in medData based on the given status.
   *
   * @param reqStatus The status that the requests must have to be in the new list.
   * @return The new filtered list.
   */
  private ObservableList<Request> filterReqStatus(String reqStatus) {
    ObservableList<Request> newList = FXCollections.observableArrayList();

    for (Request req : labData) {
      if (req.getStatus().equals(reqStatus)) {
        newList.add(req);
      }
    }
    return newList;
  }

  /* Methods to filter lists n times */

  /**
   * Filters out requests in medData based on the given status.
   *
   * @param reqStatus The status that the requests must have to be in the new list.
   * @param filteredList The list that was presumably filtered.
   * @return The new filtered list.
   */
  private ObservableList<Request> filterFilteredReqListStatus(
      String reqStatus, ObservableList<Request> filteredList) {
    ObservableList<Request> newList = FXCollections.observableArrayList();

    for (Request req : filteredList) {
      if (req.getStatus().equals(reqStatus)) {
        newList.add(req);
      }
    }
    return newList;
  }

  /**
   * Filters out requests in medData based on the given Employee.
   *
   * @param employeeName The Employee that the requests must have to be in the new list.
   * @param filteredList The list that was presumably filtered.
   * @return The new filtered list.
   */
  private ObservableList<Request> filterFilteredReqListEmployee(
      String employeeName, ObservableList<Request> filteredList) {
    ObservableList<Request> newList = FXCollections.observableArrayList();

    for (Request req : filteredList) {
      if (req.getEmployee().getName().equals(employeeName)) {
        newList.add(req);
      }
    }

    return newList;
  }

  /* FILTER METHODS ABOVE HERE */

  public void clearPage(ActionEvent actionEvent) {
    appController.clearPage();
  }
}
