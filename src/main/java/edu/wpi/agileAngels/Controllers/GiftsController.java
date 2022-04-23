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
import javafx.scene.layout.VBox;

public class GiftsController implements Initializable, PropertyChangeListener {

  @FXML VBox popOut;
  @FXML MenuButton giftID, giftLocation, giftEmployee, giftStatus, giftType;
  @FXML Button modifyButton, cancelRequest, submitRequest, clearRequest, deleteRequest;
  @FXML TableView giftTable;
  @FXML
  private TableColumn nameColumn,
      availableColumn,
      typeColumn,
      locationColumn,
      employeeColumn,
      statusColumn,
      senderColumn,
      descriptionColumn;
  @FXML
  TextField giftDescription, employeeFilterField, statusFilterField, giftSender, giftRecipient;
  @FXML Label notStartedNumber, inProgressNumber, completedNumber;

  HashMap<String, String> locationIDsByLongName = new HashMap<>();

  // DAOs, HashMaps, and Lists required for functionality
  private LocationDAOImpl locDAO = LocationDAOImpl.getInstance();
  private EmployeeManager empDAO = EmployeeManager.getInstance();

  private HashMap<String, Location> locationsHash = locDAO.getAllLocations();
  private ArrayList<Location> locationsList = new ArrayList<>(locationsHash.values());
  private HashMap<String, Employee> employeeHash = empDAO.getAllEmployees();
  private static ObservableList<Request> giftData = FXCollections.observableArrayList();
  private int statusNotStarted, statusInProgress, statusComplete;
  private AppController appController = AppController.getInstance();
  private RequestDAOImpl giftRequestImpl = RequestDAOImpl.getInstance("GiftRequest");

  public GiftsController() throws SQLException {}

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    System.out.println("uhhh: " + giftRequestImpl);
    appController.addPropertyChangeListener(this);
    popOut.setVisible(false);
    statusNotStarted = 0;
    statusInProgress = 0;
    statusComplete = 0;

    for (Location loc : locationsHash.values()) {
      locationIDsByLongName.put(loc.getLongName(), loc.getNodeID());
    }

    nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
    employeeColumn.setCellValueFactory(new PropertyValueFactory<>("employee"));
    locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
    typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
    statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    availableColumn.setCellValueFactory(new PropertyValueFactory<>("attribute1"));
    senderColumn.setCellValueFactory(new PropertyValueFactory<>("attribute2"));

    // giftData.clear();
    // Populates the table from UI list
    if (giftData.isEmpty()) {
      for (Map.Entry<String, Request> entry : giftRequestImpl.getAllRequests().entrySet()) {
        Request req = entry.getValue();
        giftData.add(req);
      }
    }
    dashboardLoad();
    giftTable.setItems(giftData);
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    String changeType = evt.getPropertyName();
    int newValue = (int) evt.getNewValue();
    appController.displayAlert();
  }

  @FXML
  public void modifyRequest(ActionEvent event) {
    popOut.setVisible(true);

    if (giftLocation.getItems().size() == 0) {
      // Populates locations dropdown
      for (Location loc : locationsList) {
        MenuItem item = new MenuItem(loc.getLongName());
        item.setOnAction(this::mainLocationMenu);
        giftLocation.getItems().add(item);
      }

      // Populates employees dropdown
      for (Map.Entry<String, Employee> entry : employeeHash.entrySet()) {
        Employee emp = entry.getValue();
        MenuItem item = new MenuItem(emp.getName());
        item.setOnAction(this::mainEmployeeMenu);
        giftEmployee.getItems().add(item);
      }

      // Populates ID dropdown
      for (Request req : giftData) {
        MenuItem item = new MenuItem(req.getName());
        item.setOnAction(this::mainIDMenu);
        giftID.getItems().add(item);
      }
      MenuItem item1 = new MenuItem("Add New Request");
      item1.setOnAction(this::mainIDMenu);
      giftID.getItems().add(item1);
    }
  }

  @FXML
  public void submit(ActionEvent event) {
    String loc = locationIDsByLongName.get(giftLocation.getText());
    String emp = giftEmployee.getText();
    String stat = giftStatus.getText();
    String type = giftType.getText();
    String desc = giftDescription.getText();
    String send = giftSender.getText();
    String rec = giftRecipient.getText();
    System.out.println(send + " " + rec);
    // Adding
    if (giftID.getText().equals("Add New Request")) {
      Request req =
          new Request(
              "", employeeHash.get(emp), locationsHash.get(loc), type, stat, desc, rec, send);
      giftData.add(req);
      giftRequestImpl.addRequest(req);

      giftID.getItems().remove(0, giftID.getItems().size());
      // Populates ID dropdown
      for (Request request : giftData) {
        MenuItem item = new MenuItem(request.getName());
        item.setOnAction(this::mainIDMenu);
        giftID.getItems().add(item);
      }
      MenuItem item1 = new MenuItem("Add New Request");
      item1.setOnAction(this::mainIDMenu);
      giftID.getItems().add(item1);
      updateDashAdding(stat);
    } else { // Editing
      Request req = giftRequestImpl.getAllRequests().get(giftID.getText());
      if (!req.getLocation().getNodeID().equals(loc)) {
        Location newLoc = locationsHash.get(loc);
        giftRequestImpl.updateLocation(req, newLoc);
      }
      if (!req.getEmployee().getName().equals(emp)) {
        giftRequestImpl.updateEmployeeName(req, emp);
      }
      if (!req.getType().equals(type)) {
        giftRequestImpl.updateType(req, type);
      }
      if (!req.getStatus().equals(stat)) {
        updateDashAdding(stat);
        updateDashSubtracting(req.getStatus());
        giftRequestImpl.updateStatus(req, stat);
      }
      if (!req.getDescription().equals(desc)) {
        giftRequestImpl.updateDescription(req, desc);
      }
      if (!req.getAttribute2().equals(send)) {
        giftRequestImpl.updateAttribute2(req, send);
      }
      if (!req.getAttribute1().equals(rec)) {
        giftRequestImpl.updateAttribute1(req, rec);
      }
      for (int i = 0; i < giftData.size(); i++) {
        if (giftData.get(i).getName().equals(req.getName())) {
          System.out.println("Status: " + req.getStatus() + " Attr1: " + req.getAttribute1());
          giftData.set(i, req);
        }
      }
    }

    clear(event);
  }

  @FXML
  public void cancel(ActionEvent event) {
    clear(event);
    popOut.setVisible(false);
  }

  @FXML
  public void delete(ActionEvent event) {
    String id = giftID.getText();
    updateDashSubtracting(giftRequestImpl.getAllRequests().get(id).getStatus());
    // removes the request from the table and dropdown
    for (int i = 0; i < giftData.size(); i++) {
      if (giftData.get(i).getName().equals(id)) {
        giftData.remove(i);
        giftID.getItems().remove(i);
      }
    }

    // delete from hash map and database table
    giftRequestImpl.deleteRequest(giftRequestImpl.getAllRequests().get(id));

    clear(event);
  }

  @FXML
  public void clear(ActionEvent event) {
    giftID.setText("ID");
    giftLocation.setText("Location");
    giftEmployee.setText("Employee");
    giftStatus.setText("Status");
    giftType.setText("Type");
    giftDescription.setText("");
    giftDescription.setPromptText("Description");
    giftSender.setText("");
    giftRecipient.setText("");
  }

  /** Does filterReqsTable methods when "Submit Filters" is clicked, or "onAction." */
  @FXML
  public void filterReqOnAction(ActionEvent event) {
    if (!employeeFilterField.getText().isEmpty() && !statusFilterField.getText().isEmpty()) {

      ObservableList<Request> empFilteredList = filterReqEmployee(employeeFilterField.getText());
      ObservableList<Request> trueFilteredList =
          filterFilteredReqListStatus(statusFilterField.getText(), empFilteredList);

      giftTable.setItems(trueFilteredList);
    } else if (!employeeFilterField.getText().isEmpty()) {
      filterReqsTableEmployee(employeeFilterField.getText());
    } else if (!statusFilterField.getText().isEmpty()) {
      filterReqsTableStatus(statusFilterField.getText());
    }
  }

  @FXML
  public void clearFilters(ActionEvent event) {
    giftTable.setItems(giftData);
    employeeFilterField.clear();
    statusFilterField.clear();
  }

  @FXML
  public void mainIDMenu(ActionEvent event) {
    MenuItem button = (MenuItem) event.getSource();
    giftID.setText(button.getText());

    // If editing or deleting an existing request:
    if (!button.getText().equals("Add New Request")) {
      populate(button.getText());
    }
  }

  @FXML
  public void mainLocationMenu(ActionEvent event) {
    MenuItem button = (MenuItem) event.getSource();
    giftLocation.setText(button.getText());
  }

  @FXML
  public void mainEmployeeMenu(ActionEvent event) {
    MenuItem button = (MenuItem) event.getSource();
    giftEmployee.setText(button.getText());
  }

  @FXML
  public void giftStatusMenu(ActionEvent event) {
    MenuItem button = (MenuItem) event.getSource();
    giftStatus.setText(button.getText());
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

      Iterator var3 = giftRequestImpl.getAllRequests().entrySet().iterator();
      while (var3.hasNext()) {
        Map.Entry<String, Request> entry = (Map.Entry) var3.next();
        Request object = (Request) entry.getValue();
        if (entry.getValue().getStatus().equals("inProgress")) {
          statusInProgress++;
        }
        if (entry.getValue().getStatus().equals("notStarted")) {
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

  /**
   * Filters requests in the equipment table so only those with the given Employee remain.
   *
   * @param employeeName The Employee the requests must have to remain on the table.
   */
  private void filterReqsTableEmployee(String employeeName) {
    ObservableList<Request> filteredList = filterReqEmployee(employeeName);

    // Sets table to only have contents of the filtered list.
    giftTable.setItems(filteredList);
  }

  /**
   * Filters out requests in labData based on the given Employee.
   *
   * @param employeeName The Employee that the requests must have to be in the new list.
   * @return The new filtered list.
   */
  private ObservableList<Request> filterReqEmployee(String employeeName) {
    ObservableList<Request> newList = FXCollections.observableArrayList();

    for (Request req : giftData) {
      if (req.getEmployee().getName().equals(employeeName)) {
        newList.add(req);
      }
    }

    return newList;
  }

  /**
   * Filters requests in the maintenance table so only those with the given status remain.
   *
   * @param reqStatus The status the requests must have to remain on the table.
   */
  private void filterReqsTableStatus(String reqStatus) {
    ObservableList<Request> filteredList = filterReqStatus(reqStatus);
    // Sets table to only have contents of the filtered list.
    giftTable.setItems(filteredList);
  }

  /**
   * Filters out requests in mainData based on the given status.
   *
   * @param reqStatus The status that the requests must have to be in the new list.
   * @return The new filtered list.
   */
  private ObservableList<Request> filterReqStatus(String reqStatus) {
    ObservableList<Request> newList = FXCollections.observableArrayList();

    for (Request req : giftData) {
      if (req.getStatus().equals(reqStatus)) {
        newList.add(req);
      }
    }
    return newList;
  }

  /* Methods to filter lists n times */

  /**
   * Filters out requests in mainData based on the given status.
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
   * Filters out requests in mainData based on the given Employee.
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

  /**
   * Populates fields once a node id is chosen when editing an existing request.
   *
   * @param id Request ID
   */
  private void populate(String id) {
    Request req = giftRequestImpl.getAllRequests().get(id);
    giftLocation.setText(req.getLocation().getLongName());
    giftEmployee.setText(req.getEmployee().getName());
    giftStatus.setText(req.getStatus());
    giftType.setText(req.getType());
    giftDescription.setText(req.getDescription());
    giftRecipient.setText(req.getAttribute1());
    giftSender.setText(req.getAttribute2());
  }

  public void menuItemSelected(ActionEvent actionEvent) {}

  public void giftTypeMenu(ActionEvent actionEvent) {
    MenuItem button = (MenuItem) actionEvent.getSource();
    giftType.setText(button.getText());
  }

  private void updateDashAdding(String status) {
    if (status.equals("not started")
        || status.equals("Not Started")
        || status.equals("notStarted")) {
      statusNotStarted++;
    }
    if (status.equals("in progress")
        || status.equals("In Progress")
        || status.equals("inProgress")) {
      statusInProgress++;
    }
    if (status.equals("complete") || status.equals("Complete")) {
      statusComplete++;
    }
    setDashboard(statusNotStarted, statusInProgress, statusComplete);
  }

  private void updateDashSubtracting(String status) {
    if (status.equals("not started")
        || status.equals("Not Started")
        || status.equals("notStarted")) {
      statusNotStarted--;
    }
    if (status.equals("in progress")
        || status.equals("In Progress")
        || status.equals("inProgress")) {
      statusInProgress--;
    }
    if (status.equals("complete") || status.equals("Complete")) {
      statusComplete--;
    }
    setDashboard(statusNotStarted, statusInProgress, statusComplete);
  }
}
