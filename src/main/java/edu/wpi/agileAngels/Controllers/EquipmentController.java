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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class EquipmentController implements Initializable, PropertyChangeListener {

  @FXML private Button equipDropdown, bed, recliner, xray, infusion, equipDropdownButton;
  @FXML private TextField deleteName, editRequest, employeeFilterField, statusFilterField;
  @FXML private Label equipmentConfirmation;
  @FXML private TableView equipmentTable;
  @FXML Button clear, submitFilters;
  @FXML Pane drop, drop2;
  @FXML MenuButton equipLocation, equipmentType, equipmentStatus, equipmentEmployeeText;
  @FXML AnchorPane anchor;

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
  AnchorPane alertPane;

  AppController appController = AppController.getInstance();
  @FXML
  private TableColumn nameColumn,
      employeeColumn, // change to employeeColumn
      locationColumn,
      typeColumn,
      statusColumn,
      descriptionColumn,
      availableColumn;

  public EquipmentController() throws SQLException {}

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    appController.addPropertyChangeListener(this);

    equipHash = equipDAO.getAllMedicalEquipment();
    allMedEquip = new ArrayList<>(equipHash.values());

    locDAO.getAllLocations();
    empDAO.getAllEmployees();
    HashMap<String, Location> locationsHash = locDAO.getAllLocations();
    ArrayList<Location> locationsList = new ArrayList<Location>(locationsHash.values());
    for (Location loc : locationsList) {
      if (loc.getFloor().equals("3") || loc.getFloor().equals("4") || loc.getFloor().equals("5")) {
        MenuItem item = new MenuItem(loc.getNodeID());
        item.setOnAction(this::locationMenu);
        equipLocation.getItems().add(item);
      }
    }

    nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
    employeeColumn.setCellValueFactory(new PropertyValueFactory<>("employee"));
    locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
    typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
    statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    availableColumn.setCellValueFactory(new PropertyValueFactory<>("attribute1"));
    medData.clear();
    Iterator var3 = MedrequestImpl.getAllRequests().entrySet().iterator();

    for (Map.Entry<String, Request> entry : MedrequestImpl.getAllRequests().entrySet()) {
      Request req = entry.getValue();

      medData.add(req);
    }

    equipmentTable.setItems(medData);
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    System.out.println("property change");
    String changeType = evt.getPropertyName();
    int newValue = (int) evt.getNewValue();
    System.out.println("before call");
    appController.displayAlert();
    System.out.println("after call");
  }

  /**
   * TODO split submit equipment into add, edit x field, edit y field etc. Maybe add a dropdown for
   * edit request so we can choose specifically which field we need to change for the request. I
   * think doing this will help avoid confusion on FE
   *
   * <p>Implement little edit section on the left with the table on the right to use as a reference
   */
  @FXML
  private void submitEquipment() throws SQLException {
    equipmentConfirmation.setText("");
    // gets all inputs and converts into string
    String dropDownString = equipmentType.getText();
    String locationString = equipLocation.getText();
    String employeeString = equipmentEmployeeText.getText();
    String statusString = equipmentStatus.getText();
    String deleteString = deleteName.getText();
    String editString = editRequest.getText();

    // if the fields are empty or to delete input is not empty
    if (!deleteString.isEmpty()) {
      deleteEquipRequest(deleteString);
    } else if (!editString.isEmpty()) {
      editEquipmentRequest(
          editString, dropDownString, locationString, employeeString, statusString);
    } else {
      addEquipRequest(dropDownString, locationString, employeeString, statusString);
    }
    equipmentType.setText("Equipment Type");
    equipLocation.setText("Delivery Location");
    equipmentStatus.setText("Status");
    editRequest.setText("");
    deleteName.setText("");
    equipmentEmployeeText.setText("Employee");
  }

  private void addEquipRequest(
      String dropDownString, String locationString, String employeeString, String statusString) {

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
                "describe",
                "something",
                "",
                equip);

        // set the status and location of the medicalEquipment object corresponding to the request
        if (statusString.equals("notStarted")) {
          equipDAO.updateStatus(equip, "inUse");
        } else if (statusString.equals("inProgress")) {
          equipDAO.updateStatus(equip, "inUse");
          equipDAO.updateEquipmentLocation(equip, medDevice.getLocation());
        } else if (statusString.equals("complete")) {
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

        medData.add(medDevice); // add to the UI
        equipmentTable.setItems(medData);
      } else {
        equipmentConfirmation.setText(
            "Sorry, there are currently no " + dropDownString + "s available. ");
      }
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

  private void deleteEquipRequest(String deleteString) {
    if (!deleteString.isEmpty()) {
      for (int i = 0; i < medData.size(); i++) {
        Request object = medData.get(i);
        if (0 == deleteString.compareTo(object.getName())) {
          // update the corresponding medicalEquip object
          if (object.getMedicalEquip() != null) {
            equipDAO.updateMedicalCleanliness(object.getMedicalEquip(), false);
            equipDAO.updateStatus(object.getMedicalEquip(), "available");
          }
          // delete the request
          medData.remove(i);
          MedrequestImpl.deleteRequest(object);
        }
      }
      equipmentTable.setItems(medData);
    }
  }

  private void editEquipmentRequest(
      String editString,
      String dropDownString,
      String locationString,
      String employeeString,
      String statusString) {

    Request found = MedrequestImpl.getAllRequests().get(editString);

    // null;
    int num = 0;
    for (int i = 0; i < medData.size(); i++) {
      Request device = medData.get(i);
      if (0 == editRequest.getText().compareTo(device.getName())) {
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

      if (!statusString.equals("Status")) {
        found.setStatus(statusString);
        //  MedrequestImpl.updateStatus(found, statusString);

        // set the status and location of the medicalEquipment object
        // corresponding to the request
        if (found.getAttribute2().equals("Clean")) {
          // if it's a request to clean equipment
          if (found.getMedicalEquip() != null) {
            if (statusString.equals("notStarted")) {
              equipDAO.updateStatus(found.getMedicalEquip(), "inUse");
            } else if (statusString.equals("inProgress")) {
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
            } else if (statusString.equals("complete")) {
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
            if (statusString.equals("notStarted")) {
              equipDAO.updateStatus(found.getMedicalEquip(), "inUse");
            } else if (statusString.equals("inProgress")) {
              equipDAO.updateStatus(found.getMedicalEquip(), "inUse");
              equipDAO.updateEquipmentLocation(found.getMedicalEquip(), found.getLocation());
            } else if (statusString.equals("complete")) {
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
  public void statusMenu(ActionEvent event) {
    MenuItem button = (MenuItem) event.getSource();
    equipmentStatus.setText(button.getText());
  }

  @FXML
  public void employeeMenu(ActionEvent event) {
    MenuItem button = (MenuItem) event.getSource();
    equipmentEmployeeText.setText(button.getText());
  }

  public void clearPage(ActionEvent actionEvent) {
    appController.clearPage();
  }
}
