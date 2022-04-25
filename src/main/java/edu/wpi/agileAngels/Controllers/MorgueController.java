package edu.wpi.agileAngels.Controllers;

import edu.wpi.agileAngels.Database.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class MorgueController implements Initializable, PropertyChangeListener {
  AppController appController = AppController.getInstance();
  // @FXML private Button addButton;
  @FXML AnchorPane anchor;
  @FXML VBox popOut;
  @FXML MenuButton morgueID, morgueLocation, morgueEmployee, morgueStatus;
  @FXML Button modifyButton, cancelRequest, submitRequest, clearRequest, deleteRequest;
  @FXML TableView morgueTable;
  @FXML
  private TableColumn nameColumn,
      availableColumn,
      typeColumn,
      locationColumn,
      employeeColumn,
      statusColumn,
      descriptionColumn;
  @FXML TextField morgueDescription, employeeFilterField, statusFilterField;
  @FXML Label notStartedNumber, inProgressNumber, completedNumber;

  private LocationDAOImpl locDAO = LocationDAOImpl.getInstance();
  private EmployeeManager empDAO = EmployeeManager.getInstance();
  private RequestDAOImpl MorguerequestImpl = RequestDAOImpl.getInstance("MorgueRequest");
  private static ObservableList<Request> morgueData = FXCollections.observableArrayList();
  HashMap<String, Location> locationsHash = locDAO.getAllLocations();
  ArrayList<Location> locationsList = new ArrayList<>(locationsHash.values());
  private HashMap<String, Employee> employeeHash = empDAO.getAllEmployees();
  ArrayList<String> freeEmployees = MorguerequestImpl.getFreeEmployees();
  private int statusNotStarted, statusInProgress, statusComplete;
  HashMap<String, String> locationIDsByLongName = new HashMap<>();

  public MorgueController() throws SQLException {}

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    appController.addPropertyChangeListener(this);
    popOut.setVisible(false);
    statusNotStarted = 0;
    statusInProgress = 0;
    statusComplete = 0;
    locDAO.getAllLocations();
    empDAO.getAllEmployees();

    for (Location loc : locationsHash.values()) {
      locationIDsByLongName.put(loc.getLongName(), loc.getNodeID());
    }

    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    typeColumn.setCellValueFactory(new PropertyValueFactory<>("attribute2"));
    locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
    employeeColumn.setCellValueFactory(new PropertyValueFactory<>("employee"));
    statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    availableColumn.setCellValueFactory(new PropertyValueFactory<>("attribute1"));

    if (morgueData.isEmpty()) {
      System.out.println("Filling Data");

      for (Map.Entry<String, Request> entry : MorguerequestImpl.getAllRequests().entrySet()) {
        Request req = entry.getValue();
        morgueData.add(req);
      }
    }
    dashboardLoad();
    morgueTable.setItems(morgueData);
    setColor(appController.color);
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
    // Populates locations dropdown
    morgueLocation.getItems().clear();
    for (Location loc : locationsList) {
      MenuItem item = new MenuItem(loc.getLongName());
      item.setOnAction(this::locationMenu);
      morgueLocation.getItems().add(item);
    }
    // Populates employees dropdown
    morgueEmployee.getItems().clear();
    for (String emp : freeEmployees) {
      MenuItem item = new MenuItem(emp);
      item.setOnAction(this::employeeMenu);
      morgueEmployee.getItems().add(item);
    }
    // Populates ID dropdown
    morgueID.getItems().clear();
    for (Request req : morgueData) {
      MenuItem item = new MenuItem(req.getName());
      item.setOnAction(this::morgueIDMenu);
      morgueID.getItems().add(item);
    }
    MenuItem item1 = new MenuItem("Add New Request");
    item1.setOnAction(this::morgueIDMenu);
    morgueID.getItems().add(item1);
  }

  @FXML
  public void submit(ActionEvent event) throws SQLException {
    String loc = locationIDsByLongName.get(morgueLocation.getText());
    String emp = morgueEmployee.getText();
    String stat = morgueStatus.getText();
    String desc = morgueDescription.getText();
    ZoneId z = ZoneId.of("America/Montreal");
    LocalDate today = LocalDate.now(z);
    LocalTime currentTime = LocalTime.now(z);
    String date = today.toString();
    String time = currentTime.toString().substring(0, 8);

    // Adding
    if (morgueID.getText().equals("Add New Request")) {
      Request req =
          new Request(
              "", employeeHash.get(emp), locationsHash.get(loc), "N/A", stat, desc, date, time);
      morgueData.add(req);
      MorguerequestImpl.addRequest(req);

      morgueID.getItems().remove(0, morgueID.getItems().size());
      // Populates ID dropdown
      for (Request request : morgueData) {
        MenuItem item = new MenuItem(request.getName());
        item.setOnAction(this::morgueIDMenu);
        morgueID.getItems().add(item);
      }
      MenuItem item1 = new MenuItem("Add New Request");
      item1.setOnAction(this::morgueIDMenu);
      morgueID.getItems().add(item1);
      updateDashAdding(stat);
    } else { // Editing
      Request req = MorguerequestImpl.getAllRequests().get(morgueID.getText());
      if (!req.getLocation().getNodeID().equals(loc)) {
        Location newLoc = locationsHash.get(loc);
        MorguerequestImpl.updateLocation(req, newLoc);
      }
      if (!req.getEmployee().getName().equals(emp)) {
        MorguerequestImpl.updateEmployeeName(req, emp);
      }
      if (!req.getStatus().equals(stat)) {
        updateDashAdding(stat);
        updateDashSubtracting(req.getStatus());
        MorguerequestImpl.updateStatus(req, stat);
      }
      if (!req.getDescription().equals(desc)) {
        MorguerequestImpl.updateDescription(req, desc);
      }

      for (int i = 0; i < morgueData.size(); i++) {
        if (morgueData.get(i).getName().equals(req.getName())) {
          morgueData.set(i, req);
        }
      }
    }
    freeEmployees.clear();
    freeEmployees = MorguerequestImpl.getFreeEmployees();
    clear(event);
    popOut.setVisible(false);
  }

  @FXML
  public void clear(ActionEvent event) {
    morgueID.setText("ID");
    morgueLocation.setText("Location");
    morgueEmployee.setText("Employee");
    morgueStatus.setText("Status");
    morgueDescription.setText("");
    morgueDescription.setPromptText("Patient Name");
  }

  @FXML
  public void cancel(ActionEvent event) {
    clear(event);
    popOut.setVisible(false);
  }

  @FXML
  public void filterReqOnAction(ActionEvent event) {
    if (!employeeFilterField.getText().isEmpty() && !statusFilterField.getText().isEmpty()) {

      ObservableList<Request> empFilteredList = filterReqEmployee(employeeFilterField.getText());
      ObservableList<Request> trueFilteredList =
          filterFilteredReqListStatus(statusFilterField.getText(), empFilteredList);

      morgueTable.setItems(trueFilteredList);
    } else if (!employeeFilterField.getText().isEmpty()) {
      filterReqsTableEmployee(employeeFilterField.getText());
    } else if (!statusFilterField.getText().isEmpty()) {
      filterReqsTableStatus(statusFilterField.getText());
    }
  }

  private void filterReqsTableEmployee(String employeeName) {
    ObservableList<Request> filteredList = filterReqEmployee(employeeName);

    // Sets table to only have contents of the filtered list.
    morgueTable.setItems(filteredList);
  }

  @FXML
  public void clearFilters(ActionEvent event) {
    morgueTable.setItems(morgueData);
    employeeFilterField.clear();
    statusFilterField.clear();
  }

  @FXML
  public void delete(ActionEvent event) throws SQLException {

    String id = morgueID.getText();
    updateDashSubtracting(MorguerequestImpl.getAllRequests().get(id).getStatus());
    // removes the request from the table and dropdown
    for (int i = 0; i < morgueData.size(); i++) {
      if (morgueData.get(i).getName().equals(id)) {
        morgueData.remove(i);
        morgueID.getItems().remove(i);
      }
    }
    // delete from hash map and database table
    MorguerequestImpl.deleteRequest(MorguerequestImpl.getAllRequests().get(id));
    freeEmployees.clear();
    freeEmployees = MorguerequestImpl.getFreeEmployees();
    clear(event);
    popOut.setVisible(false);
  }

  private ObservableList<Request> filterReqEmployee(String employeeName) {
    ObservableList<Request> newList = FXCollections.observableArrayList();

    for (Request req : morgueData) {
      if (req.getEmployee().getName().equals(employeeName)) {
        newList.add(req);
      }
    }

    return newList;
  }

  private void filterReqsTableStatus(String reqStatus) {
    ObservableList<Request> filteredList = filterReqStatus(reqStatus);
    // Sets table to only have contents of the filtered list.
    morgueTable.setItems(filteredList);
  }

  private ObservableList<Request> filterReqStatus(String reqStatus) {
    ObservableList<Request> newList = FXCollections.observableArrayList();

    for (Request req : morgueData) {
      if (req.getStatus().equals(reqStatus)) {
        newList.add(req);
      }
    }
    return newList;
  }

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

  @FXML
  public void locationMenu(ActionEvent event) {
    MenuItem button = (MenuItem) event.getSource();
    morgueLocation.setText(button.getText());
  }

  @FXML
  public void employeeMenu(ActionEvent event) {
    MenuItem button = (MenuItem) event.getSource();
    morgueEmployee.setText(button.getText());
  }

  @FXML
  public void statusMenu(ActionEvent event) {
    MenuItem button = (MenuItem) event.getSource();
    morgueStatus.setText(button.getText());
  }

  @FXML
  private void dashboardLoad() {
    if (notStartedNumber.getText().equals("-")
        && inProgressNumber.getText().equals("-")
        && completedNumber.getText().equals("-")) {

      Iterator var3 = MorguerequestImpl.getAllRequests().entrySet().iterator();
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
  public void morgueIDMenu(ActionEvent event) {
    MenuItem button = (MenuItem) event.getSource();
    morgueID.setText(button.getText());

    // If editing or deleting an existing request:
    if (!button.getText().equals("Add New Request")) {
      populate(button.getText());
    }
  }

  private void populate(String id) {
    Request req = MorguerequestImpl.getAllRequests().get(id);
    morgueLocation.setText(req.getLocation().getLongName());
    morgueEmployee.setText(req.getEmployee().getName());
    morgueStatus.setText(req.getStatus());
    morgueDescription.setText(req.getDescription());
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

  public void setColor(String color) {
    if (color.equals("green")) {
      anchor.getStylesheets().removeAll();
      anchor
          .getStylesheets()
          .add("/edu/wpi/agileAngels/views/stylesheets/ColorSchemes/styleRequestGreenTest.css");
    } else if (color.equals("red")) {
      anchor.getStylesheets().removeAll();
      anchor
          .getStylesheets()
          .add("/edu/wpi/agileAngels/views/stylesheets/ColorSchemes/styleRequestRedTest.css");

    } else if (color.equals("blue")) {
      anchor.getStylesheets().removeAll();
      anchor.getStylesheets().add("/edu/wpi/agileAngels/views/stylesheets/styleRequest.css");
    }
  }
}
