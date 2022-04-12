package edu.wpi.agileAngels.Controllers;

import edu.wpi.agileAngels.Database.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

// similar to equip controller
public class LabController extends MainController implements Initializable {

  @FXML private TextField labTestLocation, labEmployeeText, labStatus, labDelete, labEdit;

  private RequestDAOImpl LabDAO = RequestDAOImpl.getInstance("LabRequest");
  private LocationDAOImpl locDAO = LocationDAOImpl.getInstance();

  private HashMap<String, Employee> employeeHashMap = new HashMap<>();
  private EmployeeManager empDAO = EmployeeManager.getInstance();
  private static ObservableList<Request> labData = FXCollections.observableArrayList();
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
      bloodLabel,
      urineLabel,
      tumorLabel,
      covidLabel;

  public LabController() throws SQLException {}

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    locDAO.getAllLocations();
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
    employeeColumn.setCellValueFactory(new PropertyValueFactory<>("employee"));
    locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
    typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
    statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    availableColumn.setCellValueFactory(new PropertyValueFactory<>("attribute1"));
    if (labData.isEmpty()) {
      LabDAO.csvRead();
      Iterator var3 = LabDAO.getAllRequests().entrySet().iterator();

      for (Map.Entry<String, Request> entry : LabDAO.getAllRequests().entrySet()) {
        Request req = entry.getValue();

        labData.add(req);
      }
    }

    labTable.setItems(labData);
  }

  @FXML
  private void submitLabTest() {
    String dropDown = dropdownButtonText.getText();
    String location = labTestLocation.getText();
    String employee = labEmployeeText.getText();
    String status = labStatus.getText();
    String delete = labDelete.getText();
    String edit = labEdit.getText();
    //  boolean logic = (dropDown.isEmpty() || location.isEmpty() || employee.isEmpty());
    if (!delete.isEmpty()) {
      deleteLabRequest(delete);
    } else if (!labEdit.getText().isEmpty()) {
      editLabRequest(dropDown, location, employee, status);
    } else {
      System.out.println(locDAO.getLocation(location) + " " + empDAO.getEmployee(employee));
      addLabRequest(
          "available",
          dropDown,
          locDAO.getLocation(location),
          empDAO.getEmployee(employee),
          status);
    }
  }

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

  private void addLabRequest(
      String available, String dropDown, Location location, Employee employee, String status) {

    labTestConfirmation.setText(
        "Thank you! Your "
            + dropDown
            + " you requested will be delivered shortly to "
            + location.getLongName()
            + " by "
            + employee.getName()
            + ".");

    // String loc = location.getLongName();
    // String emp = employee.getName();
    Request request =
        new Request("", employee, location, dropDown, status, "description", "available", "");

    LabDAO.addRequest(request);
    labData.add(request);
    labTable.setItems(labData);
  }

  private void editLabRequest(String dropDown, String location, String employee, String status) {
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
          Employee emp = empDAO.getEmployee(employee);
          found.setEmployee(emp);
          LabDAO.updateEmployeeName(found, employee);
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
