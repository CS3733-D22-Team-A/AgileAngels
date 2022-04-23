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

// similar to equip controller
public class LabController implements Initializable, PropertyChangeListener {

  @FXML VBox popOut;
  @FXML MenuButton labID, labLocation, labEmployee, labStatus, labType;
  @FXML Button modifyButton, cancelRequest, submitRequest, clearRequest, deleteRequest;
  @FXML TableView labTable;
  @FXML
  private TableColumn nameColumn,
      typeColumn,
      locationColumn,
      employeeColumn,
      statusColumn,
      descriptionColumn;
  @FXML TextField labDescription, employeeFilterField, statusFilterField;
  @FXML Label notStartedNumber, inProgressNumber, completedNumber;

  private RequestDAOImpl labRequestImpl = RequestDAOImpl.getInstance("LabRequest");
  private LocationDAOImpl locDAO = LocationDAOImpl.getInstance();
  private HashMap<String, Location> locationsHash = locDAO.getAllLocations();
  private ArrayList<Location> locationsList = new ArrayList<>(locationsHash.values());
  private EmployeeManager empDAO = EmployeeManager.getInstance();
  private HashMap<String, Employee> employeeHash = empDAO.getAllEmployees();
  private static ObservableList<Request> labData = FXCollections.observableArrayList();
  private int statusNotStarted, statusInProgress, statusComplete;

  HashMap<String, String> locationIDsByLongName = new HashMap<>();

  AppController appController = AppController.getInstance();

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
    popOut.setVisible(false);
    statusNotStarted = 0;
    statusInProgress = 0;
    statusComplete = 0;
    // nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

    for (Location loc : locationsHash.values()) {
      locationIDsByLongName.put(loc.getLongName(), loc.getNodeID());
    }

    nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
    employeeColumn.setCellValueFactory(new PropertyValueFactory<>("employee"));
    locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
    typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
    statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

    labData.clear();

    for (Map.Entry<String, Request> entry : labRequestImpl.getAllRequests().entrySet()) {
      Request req = entry.getValue();
      labData.add(req);
    }
    dashboardLoad();
    labTable.setItems(labData);
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
    if (labLocation.getItems().size() == 0) {
      // Populates locations dropdown
      for (Location loc : locationsList) {
        MenuItem item = new MenuItem(loc.getLongName());
        item.setOnAction(this::locationMenu);
        labLocation.getItems().add(item);
      }

      // Populates employees dropdown
      for (Map.Entry<String, Employee> entry : employeeHash.entrySet()) {
        Employee emp = entry.getValue();
        MenuItem item = new MenuItem(emp.getName());
        item.setOnAction(this::employeeMenu);
        labEmployee.getItems().add(item);
      }

      // Populates ID dropdown
      for (Request req : labData) {
        MenuItem item = new MenuItem(req.getName());
        item.setOnAction(this::labIDMenu);
        labID.getItems().add(item);
      }
      MenuItem item1 = new MenuItem("Add New Request");
      item1.setOnAction(this::labIDMenu);
      labID.getItems().add(item1);
    }
  }

  @FXML
  public void submit(ActionEvent event) {
    String loc = locationIDsByLongName.get(labLocation.getText());
    String emp = labEmployee.getText();
    String stat = labStatus.getText();
    String desc = labDescription.getText();
    String type = labType.getText();

    // Adding
    if (labID.getText().equals("Add New Request")) {
      Request req =
          new Request(
              "", employeeHash.get(emp), locationsHash.get(loc), type, stat, desc, "N/A", "N/A");
      labData.add(req);
      labRequestImpl.addRequest(req);

      labID.getItems().remove(0, labID.getItems().size());
      // Populates ID dropdown
      for (Request request : labData) {
        MenuItem item = new MenuItem(request.getName());
        item.setOnAction(this::labIDMenu);
        labID.getItems().add(item);
      }
      MenuItem item1 = new MenuItem("Add New Request");
      item1.setOnAction(this::labIDMenu);
      labID.getItems().add(item1);
      updateDashAdding(stat);
    } else { // Editing
      Request req = labRequestImpl.getAllRequests().get(labID.getText());
      if (!req.getLocation().getNodeID().equals(loc)) {
        Location newLoc = locationsHash.get(loc);
        labRequestImpl.updateLocation(req, newLoc);
      }
      if (!req.getEmployee().getName().equals(emp)) {
        labRequestImpl.updateEmployeeName(req, emp);
      }
      if (!req.getStatus().equals(stat)) {
        System.out.println(stat + " " + req.getStatus());
        updateDashAdding(stat);
        updateDashSubtracting(req.getStatus());
        labRequestImpl.updateStatus(req, stat);
      }
      if (!req.getDescription().equals(desc)) {
        labRequestImpl.updateDescription(req, desc);
      }
      if (!req.getType().equals(type)) {
        labRequestImpl.updateType(req, type);
      }

      for (int i = 0; i < labData.size(); i++) {
        if (labData.get(i).getName().equals(req.getName())) {
          labData.set(i, req);
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
    String id = labID.getText();

    // removes the request from the table and dropdown
    for (int i = 0; i < labData.size(); i++) {
      if (labData.get(i).getName().equals(id)) {
        labData.remove(i);
        labID.getItems().remove(i);
      }
    }
    updateDashSubtracting(labRequestImpl.getAllRequests().get(id).getStatus());
    // delete from hash map and database table
    labRequestImpl.deleteRequest(labRequestImpl.getAllRequests().get(id));

    clear(event);
  }

  @FXML
  public void clear(ActionEvent event) {
    labID.setText("ID");
    labType.setText("Type");
    labEmployee.setText("Employee");
    labLocation.setText("Location");
    labStatus.setText("Status");
    labDescription.setText("");
    labDescription.setPromptText("Description");
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
      Iterator var3 = labRequestImpl.getAllRequests().entrySet().iterator();
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
    employeeFilterField.clear();
    statusFilterField.clear();
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

  public void labTypeMenu(ActionEvent event) {
    MenuItem button = (MenuItem) event.getSource();
    labType.setText(button.getText());
  }

  @FXML
  public void locationMenu(ActionEvent event) {
    MenuItem button = (MenuItem) event.getSource();
    labLocation.setText(button.getText());
  }

  @FXML
  public void employeeMenu(ActionEvent event) {
    MenuItem button = (MenuItem) event.getSource();
    labEmployee.setText(button.getText());
  }

  @FXML
  public void labStatusMenu(ActionEvent event) {
    MenuItem button = (MenuItem) event.getSource();
    labStatus.setText(button.getText());
  }

  @FXML
  public void labIDMenu(ActionEvent event) {
    MenuItem button = (MenuItem) event.getSource();
    labID.setText(button.getText());

    // If editing or deleting an existing request:
    if (!button.getText().equals("Add New Request")) {
      populate(button.getText());
    }
  }

  private void populate(String id) {
    Request req = labRequestImpl.getAllRequests().get(id);
    labLocation.setText(req.getLocation().getLongName());
    labEmployee.setText(req.getEmployee().getName());
    labStatus.setText(req.getStatus());
    labDescription.setText(req.getDescription());
    labType.setText(req.getType());
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
