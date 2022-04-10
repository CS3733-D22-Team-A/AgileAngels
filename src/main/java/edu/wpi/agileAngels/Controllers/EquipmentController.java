package edu.wpi.agileAngels.Controllers;

import edu.wpi.agileAngels.Database.Request;
import edu.wpi.agileAngels.Database.RequestDAOImpl;
import java.net.URL;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

// TODO make sure controllers work without connections :)
// TODO create helper methods to avoid confusion
public class EquipmentController extends MainController implements Initializable {

  @FXML private Button equipDropdown, bed, recliner, xray, infusion, equipDropdownButton;
  @FXML
  private TextField equipLocation, equipmentEmployeeText, equipmentStatus, deleteName, editRequest;
  @FXML private Label equipmentConfirmation, dropdownButtonText;
  @FXML private TableView equipmentTable;
  @FXML Button clear;
  @FXML Pane drop, drop2;

  private RequestDAOImpl MedrequestImpl =
      RequestDAOImpl.getInstance("MedRequest"); // instance of RequestDAOImpl to access functions
  // only way to update the UI is ObservableList
  private static ObservableList<Request> medData =
      FXCollections.observableArrayList(); // list of requests

  @FXML
  private TableColumn nameColumn,
      availableColumn,
      typeColumn,
      locationColumn,
      employeeColumn,
      statusColumn,
      descriptionColumn;

  public EquipmentController() throws SQLException {}

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    // connection = DBconnection.getConnection();

    // Implement DAO here.

    // HashMap<String, MedDevice> data = medDAO.getAllMedicalEquipmentRequests();

    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    availableColumn.setCellValueFactory(new PropertyValueFactory<>("available"));
    typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
    locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
    employeeColumn.setCellValueFactory(new PropertyValueFactory<>("employee"));
    statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    if (medData.isEmpty()) {
      System.out.println("THE TABLE IS CURRENTLY EMPTY I WILL POPuLATE");
      MedrequestImpl.csvRead();
      Iterator var3 = MedrequestImpl.getAllRequests().entrySet().iterator();

      for (Map.Entry<String, Request> entry : MedrequestImpl.getAllRequests().entrySet()) {
        Request req = entry.getValue();
        medData.add(req);
      }
    }

    equipmentTable.setItems(medData);
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
    // first sees if all of the attributes are empty
    // gets all inputs and converts into string
    String dropDownString = dropdownButtonText.getText();
    String locationString = equipLocation.getText();
    String employeeString = equipmentEmployeeText.getText();
    String statusString = equipmentStatus.getText();
    String deleteString = deleteName.getText();
    String editString = editRequest.getText();
    // logic to see if the entries in the buttons are empty
    // boolean logic = (dropDownString.isEmpty() || locationString.isEmpty() ||
    // employeeString.isEmpty());
    // if the fields are empty or to delete input is not empty
    if (!deleteString.isEmpty()) {
      deleteEquipRequest(deleteString);
      // editing a request
    } else if (!editString.isEmpty()) {
      editEquipmentRequest(
          editString, dropDownString, locationString, employeeString, statusString);
    } else {
      addEquipRequest("available", dropDownString, locationString, employeeString, statusString);
    }
  }

  private void addEquipRequest(
      String availableString,
      String dropDownString,
      String locationString,
      String employeeString,
      String statusString) {
    System.out.println("ADD DEVICE");
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
            placeholder, employeeString, locationString, dropDownString, statusString, "", "", "");
    MedrequestImpl.addRequest(medDevice); // add to hashmap
    medData.add(medDevice); // add to the UI
    equipmentTable.setItems(medData);
  }

  private void deleteEquipRequest(String deleteString) {
    if (!deleteString.isEmpty()) {
      System.out.println("DELETE REQUEST");
      for (int i = 0; i < medData.size(); i++) {
        Request object = medData.get(i);
        if (0 == deleteString.compareTo(object.getName())) {
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
    System.out.println("EDIT REQUEST");
    Request found = null;
    int num = 0;
    for (int i = 0; i < medData.size(); i++) {
      Request device = medData.get(i);
      if (0 == editRequest.getText().compareTo(device.getName())) {
        found = device;
        num = i;
      }
    }
    if (found != null) {
      if (!dropDownString.isEmpty()) {
        // String type = dropdownButtonText.getText();
        found.setType(dropDownString);
        MedrequestImpl.updateType(found, dropDownString);
      }
      if (!locationString.isEmpty()) {
        // String location = equipLocation.getText();
        found.setLocation(locationString);
        MedrequestImpl.updateLocation(found, locationString);
      }
      if (!employeeString.isEmpty()) {
        // String employee = emp.getText();
        found.setEmployee(employeeString);
        MedrequestImpl.updateEmployeeName(found, employeeString);
      }
      if (!statusString.isEmpty()) {
        // String employee = emp.getText();
        found.setStatus(statusString);
        MedrequestImpl.updateStatus(found, statusString);
      }
      medData.set(num, found);

      equipmentTable.setItems(medData);
    }
  }
}