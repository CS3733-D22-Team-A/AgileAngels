package edu.wpi.agileAngels.Controllers;

import edu.wpi.agileAngels.Database.*;
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
import javafx.scene.layout.VBox;
import org.controlsfx.control.textfield.TextFields;

public class LaundryController implements Initializable {

  @FXML VBox popOut;
  @FXML Label notStartedNumber, inProgressNumber, completedNumber;
  @FXML MenuButton laundryID;

  @FXML
  private TextField laundryDescription,
      deleteName,
      editRequest,
      employeeFilterField,
      statusFilterField,
      // these will have their own string arays to populate their searches
      laundryLocation,
      laundryType,
      laundryStatus,
      laundryEmployee;
  @FXML
  private TableColumn nameColumn,
      availableColumn,
      typeColumn,
      locationColumn,
      employeeColumn,
      statusColumn,
      descriptionColumn;
  // todo add edit/delete?
  @FXML
  Button addButton,
      editButton,
      deleteButton,
      cancelRequest,
      submitRequest,
      clearRequest,
      deleteRequest;
  // @FXML private Label giftConfirm;
  private RequestDAOImpl laundryRequestImpl =
      RequestDAOImpl.getInstance(
          "LaundryRequest"); // instance of RequestDAOImpl to access functions

  private LocationDAOImpl locDAO = LocationDAOImpl.getInstance();
  private HashMap<String, Location> locationsHash = locDAO.getAllLocations();
  private ArrayList<Location> locationsList = new ArrayList<>(locationsHash.values());

  private EmployeeManager employeeDAO = EmployeeManager.getInstance();
  private HashMap<String, Employee> employeesHash = employeeDAO.getAllEmployees();

  @FXML private TableView laundryTable;
  private AppController appController = AppController.getInstance();

  private static ObservableList<Request> laundryData =
      FXCollections.observableArrayList(); // list of requests

  public LaundryController() throws SQLException {}

  private String[] locations = new String[9999];
  private String[] employees = new String[9999];
  private String[] types = {"Whites", "Colors", "Infected", "Towels/Blankets"};
  private String[] status = {"NotStarted", "InProgress", "Complete"};

  private int statusNotStarted, statusInProgress, statusComplete;

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    popOut.setVisible(false);

    statusNotStarted = 0;
    statusInProgress = 0;
    statusComplete = 0;

    // availableColumn.setCellValueFactory(new PropertyValueFactory<>("attribute1"));
    employeeColumn.setCellValueFactory(new PropertyValueFactory<>("employee"));
    locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
    statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

    // To populate dropdowns
    TextFields.bindAutoCompletion(laundryStatus, status);
    TextFields.bindAutoCompletion(laundryType, types);

    int count = 0;
    for (Map.Entry<String, Employee> entry : employeesHash.entrySet()) {
      Employee emp = entry.getValue();
      employees[count] = emp.getName();
      count++;
    }
    count = 0;
    for (Location loc : locationsList) {
      locations[count] = loc.getNodeID();
      count++;
    }

    TextFields.bindAutoCompletion(laundryEmployee, employees);
    TextFields.bindAutoCompletion(laundryLocation, locations);

    TextFields.bindAutoCompletion(employeeFilterField, employees);
    TextFields.bindAutoCompletion(statusFilterField, status);

    if (laundryData.isEmpty()) {
      System.out.println("THE TABLE IS CURRENTLY EMPTY I WILL POPuLATE");
      laundryRequestImpl.csvRead();
      Iterator var3 = laundryRequestImpl.getAllRequests().entrySet().iterator();

      for (Map.Entry<String, Request> entry : laundryRequestImpl.getAllRequests().entrySet()) {
        Request req = entry.getValue();
        laundryData.add(req);
      }
    }
    dashboardLoad();
    laundryTable.setItems(laundryData);
  }

  @FXML
  /** Submits fields to a Java gifts Request Object */
  private void submitLaundry() {
    String type = laundryType.getText();
    String employee = laundryEmployee.getText();
    String location = laundryLocation.getText();
    String description = laundryDescription.getText();
    // String delete = deleteName.getText();
    // String edit = editRequest.getText();
    String status = laundryStatus.getText();
    // attributes arent all filled

    //  if (!delete.isEmpty()) {
    //    deleteLaundryRequest(delete);
    //    // editing a request
    //  } else if (!edit.isEmpty()) {
    //    editLaundryRequest(edit, type, employee, location, description, status);
    //  } else {
    addLaundryRequest(type, employee, location, description, status);
    // }
  }

  private void addLaundryRequest(
      String type, String employee, String location, String description, String status) {

    String placeholder = "?";
    Request laundry =
        new Request(
            placeholder,
            employeesHash.get(employee),
            locationsHash.get(location),
            type,
            status,
            description,
            "",
            "");
    laundryRequestImpl.addRequest(laundry); // add to hashmap
    laundryData.add(laundry); // add to the UI
    laundryTable.setItems(laundryData);
  }

  @FXML
  private void deleteLaundryRequest(String deleteString) {
    if (!deleteString.isEmpty()) {
      System.out.println("DELETE REQUEST");
      for (int i = 0; i < laundryData.size(); i++) {
        Request object = laundryData.get(i);
        if (0 == deleteString.compareTo(object.getName())) {
          laundryData.remove(i);
          laundryRequestImpl.deleteRequest(object);
        }
      }
      laundryTable.setItems(laundryData);
    }
  }

  @FXML
  private void editLaundryRequest(
      String editString,
      String typeString,
      String employeeString,
      String locationString,
      String descriptionString,
      String statusString) {
    System.out.println("EDIT REQUEST");
    Request found = null;
    int num = 0;
    for (int i = 0; i < laundryData.size(); i++) {
      Request device = laundryData.get(i);
      if (0 == editRequest.getText().compareTo(device.getName())) {
        found = device;
        num = i;
      }
    }
    Employee emp = employeeDAO.getEmployee(employeeString);
    Location loc = locDAO.getLocation(locationString);
    if (found != null) {
      if (!typeString.isEmpty()) {
        // String type = typeButtonText.getText();
        found.setType(typeString);
        // LaundryrequestImpl.updateType(found, typeString);
      }
      if (!locationString.isEmpty()) {
        // String location = equipLocation.getText();
        found.setLocation(loc);
        // LaundryrequestImpl.updateLocation(found, locationsHash.get(locationString));
      }
      if (!employeeString.isEmpty()) {
        // String employee = emp.getText();
        found.setEmployee(emp);
        // LaundryrequestImpl.updateEmployeeName(found, employeeString);
      }
      if (!statusString.isEmpty()) {
        // String employee = emp.getText();
        found.setStatus(statusString);
        //  LaundryrequestImpl.updateStatus(found, statusString);
      }
      if (!descriptionString.isEmpty()) {
        // String description = emp.getText();
        found.setDescription(descriptionString);
        // LaundryrequestImpl.updateStatus(found, descriptionString);
      }
      laundryData.set(num, found);

      laundryTable.setItems(laundryData);
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

      // Directly touching equipment table in n-filter cases.
      laundryTable.setItems(trueFilteredList);
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
    laundryTable.setItems(laundryData);
  }

  // Employee-based

  /**
   * Filters requests in the equipment table so only those with the given Employee remain.
   *
   * @param employeeName The Employee the requests must have to remain on the table.
   */
  private void filterReqsTableEmployee(String employeeName) {
    ObservableList<Request> filteredList = filterReqEmployee(employeeName);

    // Sets table to only have contents of the filtered list.
    laundryTable.setItems(filteredList);
  }

  /**
   * Filters out requests in medData based on the given Employee.
   *
   * @param employeeName The Employee that the requests must have to be in the new list.
   * @return The new filtered list.
   */
  private ObservableList<Request> filterReqEmployee(String employeeName) {
    ObservableList<Request> newList = FXCollections.observableArrayList();

    for (Request req : laundryData) {
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
    laundryTable.setItems(filteredList);
  }

  /**
   * Filters out requests in medData based on the given status.
   *
   * @param reqStatus The status that the requests must have to be in the new list.
   * @return The new filtered list.
   */
  private ObservableList<Request> filterReqStatus(String reqStatus) {
    ObservableList<Request> newList = FXCollections.observableArrayList();

    for (Request req : laundryData) {
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

  public void clearPage(ActionEvent event) {
    appController.clearPage();
  }

  public void laundryType(ActionEvent event) {
    MenuItem button = (MenuItem) event.getSource();
    laundryType.setText(button.getText());
  }

  @FXML
  public void typeMenu(ActionEvent event) {
    MenuItem button = (MenuItem) event.getSource();
    laundryType.setText(button.getText());
  }

  @FXML
  public void employeeMenu(ActionEvent event) {
    MenuItem button = (MenuItem) event.getSource();
    laundryEmployee.setText(button.getText());
  }

  @FXML
  public void statusMenu(ActionEvent event) {
    MenuItem button = (MenuItem) event.getSource();
    laundryStatus.setText(button.getText());
  }

  @FXML
  public void clear(ActionEvent event) {
    // laundryID.setText("ID");
    laundryType.setText("Type");
    laundryEmployee.setText("Employee");
    laundryID.setText("Location");
    laundryStatus.setText("Status");
    laundryDescription.setText("");
    laundryDescription.setPromptText("Description");
  }

  @FXML
  public void cancel(ActionEvent event) {
    clear(event);
    popOut.setVisible(false);
  }

  @FXML
  public void delete(ActionEvent event) {
    String id = laundryID.getText();

    // removes the request from the table and dropdown
    for (int i = 0; i < laundryData.size(); i++) {
      if (laundryData.get(i).getName().equals(id)) {
        laundryData.remove(i);
        // laundryID.getItems().remove(i);
      }
    }

    // delete from hash map and database table
    laundryRequestImpl.deleteRequest(laundryRequestImpl.getAllRequests().get(id));

    clear(event);
  }

  @FXML
  public void modifyRequest(ActionEvent event) {
    // Populates ID dropdown
    for (Request req : laundryData) {
      MenuItem item = new MenuItem(req.getName());
      item.setOnAction(this::laundryIDMenu);
      laundryID.getItems().add(item);
    }
    MenuItem item1 = new MenuItem("Add New Request");
    item1.setOnAction(this::laundryIDMenu);
    laundryID.getItems().add(item1);

    popOut.setVisible(true);
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

      Iterator var3 = laundryRequestImpl.getAllRequests().entrySet().iterator();
      while (var3.hasNext()) {
        Map.Entry<String, Request> entry = (Map.Entry) var3.next();
        Request object = (Request) entry.getValue();
        if (entry.getValue().getStatus().equals("InProgress")) {
          statusInProgress++;
        }
        if (entry.getValue().getStatus().equals("NotStarted")) {
          statusNotStarted++;
        }
        if (entry.getValue().getStatus().equals("Complete")
            || entry.getValue().getStatus().equals("complete")) {
          statusComplete++;
        }
      }
      setDashboard(statusNotStarted, statusInProgress, statusComplete);
    }
  }

  /**
   * Will set the dashboard's numbers to the certain types of statuses.
   *
   * @param notStarted Requests not started
   * @param inProgress Requests in progress
   * @param complete Requests completed
   */
  @FXML
  private void setDashboard(int notStarted, int inProgress, int complete) {
    String notStart = Integer.toString(notStarted);
    String inProg = Integer.toString(inProgress);
    String comp = Integer.toString(complete);
    notStartedNumber.setText(notStart);
    inProgressNumber.setText(inProg);
    completedNumber.setText(comp);
  }

  @FXML
  public void clearFilters(ActionEvent event) {
    laundryTable.setItems(laundryData);
  }

  @FXML
  public void laundryIDMenu(ActionEvent event) {
    MenuItem button = (MenuItem) event.getSource();
    laundryID.setText(button.getText());

    if (!button.getText().equals("Add New Request")) {
      populate(button.getText());
    }
  }

  /**
   * Populates fields once a node id is chosen when editing an existing request.
   *
   * @param id Request ID
   */
  private void populate(String id) {
    Request req = laundryRequestImpl.getAllRequests().get(id);
    laundryLocation.setText(req.getLocation().getNodeID());
    laundryEmployee.setText(req.getEmployee().getName());
    laundryStatus.setText(req.getStatus());
    laundryDescription.setText(req.getDescription());
    laundryType.setText(req.getType());
  }
}
