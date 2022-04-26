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
  @FXML private Label equipmentConfirmation, equipID;
  @FXML private TableView equipmentTable;
  @FXML Button clear, submitFilters, delete;
  @FXML Pane drop, drop2;
  @FXML
  MenuButton equipLocation,
      equipmentType,
      equipmentStatus,
      equipmentEmployeeText,
      employeeFilter,
      statusFilter;
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
    clearFields();
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

  void updateFilters() {
    employeeFilter.getItems().clear();
    ArrayList<String> list = new ArrayList<>();
    for (Request r : medData) {
      if (!list.contains(r.getEmployee().getName())) {
        CheckMenuItem emp = new CheckMenuItem(r.getEmployee().getName());
        emp.setSelected(true);
        emp.setOnAction(
            (ActionEvent event) -> {
              submitFilter();
            });
        employeeFilter.getItems().add(emp);
        list.add(r.getEmployee().getName());
      }
    }
    clearFilters();
  }

  @FXML
  void submitFilter() {
    ObservableList<Request> employeeFilteredList = FXCollections.observableArrayList();
    ObservableList<Request> statusFilterdList = FXCollections.observableArrayList();

    for (MenuItem menuItem : employeeFilter.getItems()) {
      if (((CheckMenuItem) menuItem).isSelected()) {
        for (Request r : medData) {
          if (((CheckMenuItem) menuItem).getText().equals(r.getEmployee().getName())) {
            employeeFilteredList.add(r);
          }
        }
      }
    }
    for (MenuItem menuItem : statusFilter.getItems()) {
      if (((CheckMenuItem) menuItem).isSelected()) {
        for (Request r : employeeFilteredList) {
          if (((CheckMenuItem) menuItem).getText().equals(r.getStatus())) {
            statusFilterdList.add(r);
          }
        }
      }
    }
    equipmentTable.setItems(statusFilterdList);
  }

  /** Puts all of the requests back on the table, "clearing the requests." */
  @FXML
  public void clearFilters() {
    // Puts everything back on table.
    for (MenuItem e : employeeFilter.getItems()) {
      ((CheckMenuItem) e).setSelected(true);
    }
    for (MenuItem e : statusFilter.getItems()) {
      ((CheckMenuItem) e).setSelected(true);
    }
    equipmentTable.setItems(medData);
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    String changeType = evt.getPropertyName();
    int newValue = (int) evt.getNewValue();
    appController.displayAlert();
  }

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
    equipmentConfirmation.setText("");
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
      equipmentConfirmation.setText("One or more of the required fields is not filled in.");
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
        equipmentConfirmation.setText(
            "Thank you, the "
                + dropDownString
                + " you requested will be delivered shortly to "
                + locationString
                + " by "
                + employeeString
                + ".");

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
      } else {
        equipmentConfirmation.setText(
            "Sorry, there are currently no " + dropDownString + "s available. ");
      }
    }
  }

  @FXML
  private void deleteEquipRequest() {
    equipmentConfirmation.setText("");
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
    equipmentConfirmation.setText("");
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
        } else {
          equipmentConfirmation.setText(
              "Sorry, there are currently no " + dropDownString + "s available.");
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
    updateFilters();
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
    }
  }
}
