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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class EquipmentController implements Initializable, PropertyChangeListener {

  @FXML VBox popOut;
  @FXML HBox tableHBox;
  @FXML private Button equipDropdown, equipDropdownButton;
  @FXML private TextField employeeFilterField, statusFilterField, mainDescription;
  @FXML private Label equipID;
  @FXML private TableView equipmentTable;
  @FXML Button clear, submitFilters, delete;
  @FXML Pane drop, drop2;
  @FXML MenuButton equipLocation, equipmentType, equipmentStatus, equipmentEmployeeText;
  @FXML AnchorPane anchor;
  @FXML MenuItem xRay, infusionPump, recliner, bed;

  private LocationDAOImpl locDAO = LocationDAOImpl.getInstance();
  private EmployeeManager empDAO = EmployeeManager.getInstance();
  private MedEquipImpl equipDAO = MedEquipImpl.getInstance();
  private RequestDAOImpl MedrequestImpl =
      RequestDAOImpl.getInstance("MedRequest"); // instance of RequestDAOImpl to access functions
  // only way to update the UI is ObservableList
  private static ObservableList<Request> medData =
      FXCollections.observableArrayList(); // list of requests
  // hashmap and arrayList of all medical equipment
  HashMap<String, MedicalEquip> equipHash;
  ArrayList<MedicalEquip> allMedEquip;
  // hashMap and arrayList of all locations
  HashMap<String, Location> locationsHash = locDAO.getAllLocations();
  ArrayList<Location> locationsList = new ArrayList<>(locationsHash.values());
  HashMap<String, Employee> employeeHash = empDAO.getAllEmployees();

  HashMap<String, String> locationIDsByLongName = new HashMap<>();

  ArrayList<String> equipTypes = new ArrayList<>();
  HashMap<String, Integer> floorToFloorInt = new HashMap<>();
  private boolean[][] availEquip = new boolean[3][4];

  @FXML Label notStartedNumber, inProgressNumber, completedNumber;
  private int statusNotStarted, statusInProgress, statusComplete;

  AppController appController = AppController.getInstance();
  @FXML
  private TableColumn nameColumn,
      employeeColumn, // change to employeeColumn
      locationColumn,
      typeColumn,
      statusColumn,
      descriptionColumn;

  public EquipmentController() throws SQLException {}

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    appController.addPropertyChangeListener(this);
    hidePopout();

    equipHash = equipDAO.getAllMedicalEquipment();
    allMedEquip = new ArrayList<>(equipHash.values());

    locDAO.getAllLocations();
    empDAO.getAllEmployees();

    for (Location loc : locationsHash.values()) {
      locationIDsByLongName.put(loc.getLongName(), loc.getNodeID());
    }

    nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
    employeeColumn.setCellValueFactory(new PropertyValueFactory<>("employee"));
    locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
    typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
    statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

    medData.clear();
    Iterator var3 = MedrequestImpl.getAllRequests().entrySet().iterator();
    for (Map.Entry<String, Request> entry : MedrequestImpl.getAllRequests().entrySet()) {
      Request req = entry.getValue();
      medData.add(req);
    }
    equipmentTable.setItems(medData);

    dashboardLoad();

    for (Location loc : locationsList) {
      if (loc.getFloor().equals("3") || loc.getFloor().equals("4") || loc.getFloor().equals("5")) {
        MenuItem item = new MenuItem(loc.getLongName());
        item.setOnAction(this::locationMenu);
        equipLocation.getItems().add(item);
      }
    }

    // Populates employees dropdown
    for (Map.Entry<String, Employee> entry : employeeHash.entrySet()) {
      Employee emp = entry.getValue();
      MenuItem item = new MenuItem(emp.getName());
      item.setOnAction(this::employeeMenu);
      equipmentEmployeeText.getItems().add(item);
    }

    setColor(appController.color);

    equipTypes.add("Bed");
    equipTypes.add("Recliner");
    equipTypes.add("InfusionPump");
    equipTypes.add("XRayMachine");

    floorToFloorInt.put("3", 0);
    floorToFloorInt.put("4", 1);
    floorToFloorInt.put("5", 2);

    updateEquipAvail("3");
    updateEquipAvail("4");
    updateEquipAvail("5");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 4; j++) {
        System.out.println(availEquip[i][j]);
      }
    }
  }

  public void updateTypeDropdown(String floor) {
    equipmentType.setText("Type");
    equipmentType.getItems().clear();
    updateEquipAvail(floor);

    // add back the ones that are avail on floor
    for (int i = 0; i < 4; i++) {
      if (availEquip[floorToFloorInt.get(floor)][i]) {
        MenuItem item = new MenuItem(equipTypes.get(i));
        item.setOnAction(this::typeMenu);
        equipmentType.getItems().add(item);
      }
    }
  }

  public void updateEquipAvail(String floor) {
    for (int i = 0; i < 4; i++) {
      Boolean foundEquip = false;
      int j = 0;
      while (!foundEquip && j < allMedEquip.size()) {
        MedicalEquip medEquip = allMedEquip.get(j);
        boolean typeMatches = medEquip.getType().equals(equipTypes.get(i));
        boolean available = medEquip.getStatus().equals("available");
        boolean clean = medEquip.isClean();
        boolean floorMatches = medEquip.getLocation().getFloor().equals(floor);
        if (typeMatches && available && clean && floorMatches) {
          foundEquip = true;
        }
        j++;
      }
      availEquip[floorToFloorInt.get(floor)][i] = foundEquip;
    }
  }

  public void hidePopout() {
    try {
      tableHBox.getChildren().remove(popOut);
    } catch (NullPointerException e) {

    }
  }

  public void showPopout() {
    if (tableHBox.getChildren().get(0) != popOut) {
      tableHBox.getChildren().add(0, popOut);
    }
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    String changeType = evt.getPropertyName();
    int newValue = (int) evt.getNewValue();
    appController.displayAlert();
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
      equipmentTable.setItems(trueFilteredList);
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
    equipmentTable.setItems(medData);
    employeeFilterField.clear();
    statusFilterField.clear();
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
    equipmentTable.setItems(filteredList);
  }

  /**
   * Filters out requests in medData based on the given Employee.
   *
   * @param employeeName The Employee that the requests must have to be in the new list.
   * @return The new filtered list.
   */
  private ObservableList<Request> filterReqEmployee(String employeeName) {
    ObservableList<Request> newList = FXCollections.observableArrayList();

    for (Request req : medData) {
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
    equipmentTable.setItems(filteredList);
  }

  /**
   * Filters out requests in medData based on the given status.
   *
   * @param reqStatus The status that the requests must have to be in the new list.
   * @return The new filtered list.
   */
  private ObservableList<Request> filterReqStatus(String reqStatus) {
    ObservableList<Request> newList = FXCollections.observableArrayList();

    for (Request req : medData) {
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

  @FXML
  public void submit() {
    if (equipID.getText().equals("New Request")) {
      addEquipRequest();
    } else {
      editEquipmentRequest();
    }
    clearFields();
    hidePopout();
  }

  private void addEquipRequest() {
    // gets all inputs and converts into string
    String dropDownString = equipmentType.getText();
    String locationString = locationIDsByLongName.get(equipLocation.getText());
    String employeeString = equipmentEmployeeText.getText();
    String statusString = "Not Started";
    String descriptionString = mainDescription.getText();

    if (dropDownString.equals("Equipment Type")
        || locationString.equals("Delivery Location")
        || statusString.equals("Status")
        || employeeString.equals("Employee")) {
    } else {
      MedicalEquip equip = null;
      Boolean foundEquip = false;
      int i = 0;
      while (!foundEquip && i < allMedEquip.size()) {
        MedicalEquip medEquip = allMedEquip.get(i);
        if (medEquip.getType().equals(dropDownString)
            && medEquip.getStatus().equals("available")
            && medEquip.isClean()
            && medEquip
                .getLocation()
                .getFloor()
                .equals(locationsHash.get(locationString).getFloor())) {
          equip = medEquip;
          foundEquip = true;
        }
        i++;
      }
      if (foundEquip) {

        String placeholder = "?";
        Request medDevice =
            new Request(
                placeholder,
                empDAO.getEmployee(employeeString),
                locDAO.getLocation(locationString),
                dropDownString,
                statusString,
                descriptionString,
                "something",
                "",
                equip);

        updateDashAdding(statusString);

        // set the status and location of the medicalEquipment object corresponding to the request
        if (statusString.equals("Not Started")) {
          equipDAO.updateStatus(equip, "inUse");
        } else if (statusString.equals("In Progress")) {
          equipDAO.updateStatus(equip, "inUse");
          equipDAO.updateEquipmentLocation(equip, medDevice.getLocation());
        } else if (statusString.equals("Complete")) {
          equipDAO.updateMedicalCleanliness(equip, false);
          equipDAO.updateStatus(equip, "available");
          if (locationsHash.get(locationString).getFloor().equals("3")) {
            equipDAO.updateEquipmentLocation(equip, locationsHash.get("ADIRT00103"));
          } else if (locationsHash.get(locationString).getFloor().equals("4")) {
            equipDAO.updateEquipmentLocation(equip, locationsHash.get("ADIRT00104"));
          } else if (locationsHash.get(locationString).getFloor().equals("5")) {
            equipDAO.updateEquipmentLocation(equip, locationsHash.get("ADIRT00105"));
          }
        }

        MedrequestImpl.addRequest(medDevice); // add to hashmap

        // add the new request to the ID dropdown
        MenuItem item = new MenuItem(medDevice.getName());
        item.setOnAction(this::mainIDMenu);

        medData.add(medDevice); // add to the UI
        equipmentTable.setItems(medData);
      }
    }
  }

  @FXML
  private void deleteEquipRequest() {
    // gets all inputs and converts into string
    try {
      String deleteString =
          ((Request) equipmentTable.getSelectionModel().getSelectedItem()).getName();
      for (int i = 0; i < medData.size(); i++) {
        Request object = medData.get(i);
        if (0 == deleteString.compareTo(object.getName())) {
          // update the corresponding medicalEquip object
          if (object.getMedicalEquip() != null) {
            equipDAO.updateMedicalCleanliness(object.getMedicalEquip(), false);
            equipDAO.updateStatus(object.getMedicalEquip(), "available");
          }

          updateDashSubtracting(object.getStatus());

          // delete the request
          medData.remove(i);
          MedrequestImpl.deleteRequest(object);
        }
        equipmentTable.setItems(medData);
      }
    } catch (NullPointerException e) {
      equipmentTable.getSelectionModel().clearSelection();
    }

    clearFields();
    hidePopout();
  }

  private void editEquipmentRequest() {
    // gets all inputs and converts into string
    String editString = equipID.getText();
    String dropDownString = equipmentType.getText();
    String locationString = locationIDsByLongName.get(equipLocation.getText());
    String employeeString = equipmentEmployeeText.getText();
    String statusString = equipmentStatus.getText();
    String descriptionString = mainDescription.getText();

    Request found = MedrequestImpl.getAllRequests().get(editString);

    updateDashSubtracting(found.getStatus());
    updateDashAdding(statusString);

    int num = 0;
    for (int i = 0; i < medData.size(); i++) {
      Request device = medData.get(i);
      if (0 == equipID.getText().compareTo(device.getName())) {
        found = device;
        num = i;
      }
    }

    if (found != null) {
      if (!dropDownString.equals("Equipment Type")) {
        // String type = equipmentType.getText();

        MedicalEquip equip = null;
        Boolean foundEquip = false;
        int i = 0;
        while (!foundEquip && i < allMedEquip.size()) {
          MedicalEquip medEquip = allMedEquip.get(i);
          if (medEquip.getType().equals(dropDownString)
              && medEquip.getStatus().equals("available")
              && medEquip.isClean()
              && medEquip
                  .getLocation()
                  .getFloor()
                  .equals(locationsHash.get(locationString).getFloor())) {
            equip = medEquip;
            foundEquip = true;
          }
          i++;
        }
        if (foundEquip) {
          found.setType(dropDownString);
          found.setMedicalEquip(equip);
          MedrequestImpl.updateType(found, dropDownString);
        }
      }

      if (!locationString.equals("Delivery Location")) {
        Location location = locDAO.getLocation(locationString);
        found.setLocation(location);
        // MedrequestImpl.updateLocation(found, location);
        if (found.getMedicalEquip() != null) {
          equipDAO.updateEquipmentLocation(found.getMedicalEquip(), found.getLocation());
        }
      }
      if (!employeeString.equals("Employee")) {
        Employee employee = empDAO.getEmployee(employeeString);
        found.setEmployee(employee);
        //        MedrequestImpl.updateEmployeeName(found, employee.getName());
      }

      if (!descriptionString.equals("Description")) {
        found.setDescription(descriptionString);
        //        MedrequestImpl.updateDescription(found, descriptionString);
      }

      if (!statusString.equals("Status")) {
        found.setStatus(statusString);
        //  MedrequestImpl.updateStatus(found, statusString);

        // set the status and location of the medicalEquipment object
        // corresponding to the request
        if (found.getAttribute2().equals("Clean")) {
          // if it's a request to clean equipment
          if (found.getMedicalEquip() != null) {
            if (statusString.equals("Not Started")) {
              equipDAO.updateStatus(found.getMedicalEquip(), "inUse");
            } else if (statusString.equals("In Progress")) {
              equipDAO.updateStatus(found.getMedicalEquip(), "inUse");
              if (found.getLocation().getFloor().equals("3")) {
                equipDAO.updateEquipmentLocation(
                    found.getMedicalEquip(), locationsHash.get("ADIRT00103"));
              } else if (found.getLocation().getFloor().equals("4")) {
                equipDAO.updateEquipmentLocation(
                    found.getMedicalEquip(), locationsHash.get("ADIRT00104"));
              } else if (found.getLocation().getFloor().equals("5")) {
                equipDAO.updateEquipmentLocation(
                    found.getMedicalEquip(), locationsHash.get("ADIRT00105"));
              }
            } else if (statusString.equals("Complete")) {
              equipDAO.updateMedicalCleanliness(found.getMedicalEquip(), true);
              equipDAO.updateStatus(found.getMedicalEquip(), "available");
              if (found.getType().equals("InfusionPump")) {
                if (found.getLocation().getFloor().equals("3")) {
                  equipDAO.updateEquipmentLocation(
                      found.getMedicalEquip(), locationsHash.get("ASTOR00103"));
                } else if (found.getLocation().getFloor().equals("4")) {
                  equipDAO.updateEquipmentLocation(
                      found.getMedicalEquip(), locationsHash.get("ASTOR00104"));
                } else if (found.getLocation().getFloor().equals("5")) {
                  equipDAO.updateEquipmentLocation(
                      found.getMedicalEquip(), locationsHash.get("ASTOR00105"));
                }
              } else {
                if (found.getLocation().getFloor().equals("3")) {
                  equipDAO.updateEquipmentLocation(
                      found.getMedicalEquip(), locationsHash.get("ASTOR00303"));
                } else if (found.getLocation().getFloor().equals("4")) {
                  equipDAO.updateEquipmentLocation(
                      found.getMedicalEquip(), locationsHash.get("ASTOR00304"));
                } else if (found.getLocation().getFloor().equals("5")) {
                  equipDAO.updateEquipmentLocation(
                      found.getMedicalEquip(), locationsHash.get("ASTOR00305"));
                }
              }
            }
          }
        } else {
          // if it's not a request to clean equipment
          if (found.getMedicalEquip() != null) {
            if (statusString.equals("Not Started")) {
              equipDAO.updateStatus(found.getMedicalEquip(), "inUse");
            } else if (statusString.equals("In Progress")) {
              equipDAO.updateStatus(found.getMedicalEquip(), "inUse");
              equipDAO.updateEquipmentLocation(found.getMedicalEquip(), found.getLocation());
            } else if (statusString.equals("Complete")) {
              equipDAO.updateMedicalCleanliness(found.getMedicalEquip(), false);
              equipDAO.updateStatus(found.getMedicalEquip(), "available");
              if (found.getLocation().getFloor().equals("3")) {
                equipDAO.updateEquipmentLocation(
                    found.getMedicalEquip(), locationsHash.get("ADIRT00103"));
              } else if (found.getLocation().getFloor().equals("4")) {
                equipDAO.updateEquipmentLocation(
                    found.getMedicalEquip(), locationsHash.get("ADIRT00104"));
              } else if (found.getLocation().getFloor().equals("5")) {
                equipDAO.updateEquipmentLocation(
                    found.getMedicalEquip(), locationsHash.get("ADIRT00105"));
              }
            }
          }
        }
      }
      medData.set(num, found);

      //  equipmentTable.setItems(medData);
    }
  }

  @FXML
  public void locationMenu(ActionEvent event) {
    MenuItem button = (MenuItem) event.getSource();
    equipLocation.setText(button.getText());

    updateTypeDropdown(
        locationsHash.get(locationIDsByLongName.get(equipLocation.getText())).getFloor());
  }

  @FXML
  public void typeMenu(ActionEvent event) {
    MenuItem button = (MenuItem) event.getSource();
    equipmentType.setText(button.getText());
  }

  @FXML
  public void checkTypeMenu() {
    // xRay.setDisable(false);
  }

  @FXML
  public void statusMenu(ActionEvent event) {
    MenuItem button = (MenuItem) event.getSource();
    equipmentStatus.setText(button.getText());
  }

  @FXML
  public void employeeMenu(ActionEvent event) {
    MenuItem button = (MenuItem) event.getSource();
    equipmentEmployeeText.setText(button.getText());
  }

  @FXML
  public void mainIDMenu(ActionEvent event) {
    MenuItem button = (MenuItem) event.getSource();
    equipID.setText(button.getText());
    if (!button.getText().equals("Add New Request")) {
      populate();
    }
  }

  @FXML
  public void modifyRequest(ActionEvent event) {
    showPopout();

    if (equipLocation.getItems().size() == 0) {
      // Populates locations dropdown
      for (Location loc : locationsList) {
        if (loc.getFloor().equals("3")
            || loc.getFloor().equals("4")
            || loc.getFloor().equals("5")) {
          MenuItem item = new MenuItem(loc.getLongName());
          item.setOnAction(this::locationMenu);
          equipLocation.getItems().add(item);
        }
      }
    }
  }

  private void populate() {
    showPopout();
    Request req = ((Request) equipmentTable.getSelectionModel().getSelectedItem());
    equipID.setText(req.getName());
    equipLocation.setText(req.getLocation().getLongName());
    equipmentStatus.setText(req.getStatus());
    equipmentEmployeeText.setText(req.getEmployee().getName());
    equipmentType.setText(req.getType());
    mainDescription.setText(req.getDescription());
  }

  @FXML
  public void clearFields() {
    equipmentType.setText("Type");
    equipLocation.setText("Location");
    equipmentStatus.setText("Status");
    equipmentEmployeeText.setText("Employee");
    equipID.setText("ID");
    mainDescription.setText("");
    mainDescription.setPromptText("Description");
  }

  @FXML
  public void cancel(ActionEvent event) {
    clearFields();
    hidePopout();
  }

  public void clearPage(ActionEvent actionEvent) {
    appController.clearPage();
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

      Iterator var3 = MedrequestImpl.getAllRequests().entrySet().iterator();
      while (var3.hasNext()) {
        Map.Entry<String, Request> entry = (Map.Entry) var3.next();
        Request object = (Request) entry.getValue();
        if (entry.getValue().getStatus().equals("In Progress")) {
          statusInProgress++;
        }
        if (entry.getValue().getStatus().equals("Not Started")) {
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

  @FXML
  public void newRequest() {
    equipmentStatus.setVisible(false);
    delete.setVisible(false);
    showPopout();
    clearFields();
    equipID.setText("New Request");
  }

  public void loadRequest(MouseEvent mouseEvent) {
    // populate(((Request) labTable.getSelectionModel().getSelectedItem()).getName());
    equipmentStatus.setVisible(true);
    delete.setVisible(true);
    try {
      if (mouseEvent.getButton() == MouseButton.PRIMARY) {
        populate();
      }
    } catch (NullPointerException e) {
      hidePopout();
    }
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
    } else if (color.equals("purple")) {
      anchor.getStylesheets().removeAll();
      anchor
          .getStylesheets()
          .add("/edu/wpi/agileAngels/views/stylesheets/ColorSchemes/styleRequestPurpleTest.css");
    }
  }
}
