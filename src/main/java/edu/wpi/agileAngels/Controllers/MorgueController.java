package edu.wpi.agileAngels.Controllers;

import edu.wpi.agileAngels.Database.*;
import java.awt.*;
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
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MorgueController implements Initializable, PropertyChangeListener {
  AppController appController = AppController.getInstance();
  // @FXML private Button addButton;
  @FXML private TextField morgueDescription, morgueEditDescription;
  @FXML private VBox addBox, deleteBox, editBox;
  @FXML private Label morgueID, morgueDeleteStatus, morgueDeleteEmployee, morgueDeleteLocation;
  @FXML private Pane addPane, editPane, deletePane;
  @FXML
  private MenuButton morgueLocation,
      morgueEmployee,
      morgueStatus,
      morgueDeleteID,
      morgueEditID,
      morgueEditEmployee,
      morgueEditLocation,
      morgueEditStatus;

  /*
     mainEditLocation,
     mainEditEmployee,
     mainEditStatus,
     mainDeleteID;

  */
  @FXML private TableView morgueTable;
  // @FXML private Label requestConfirmation
  private LocationDAOImpl locDAO = LocationDAOImpl.getInstance();
  private EmployeeManager empDAO = EmployeeManager.getInstance();
  private RequestDAOImpl MorguerequestImpl = RequestDAOImpl.getInstance("MorgueRequest");
  private static ObservableList<Request> morgueData = FXCollections.observableArrayList();
  HashMap<String, Location> locationsHash = locDAO.getAllLocations();
  ArrayList<Location> locationsList = new ArrayList<>(locationsHash.values());
  HashMap<String, Employee> employeeHash = empDAO.getAllEmployees();
  ArrayList<String> freeEmployees = MorguerequestImpl.getFreeEmployees();
  @FXML
  private TableColumn nameColumn,
      typeColumn,
      locationColumn,
      employeeColumn,
      statusColumn,
      descriptionColumn,
      availableColumn;

  public MorgueController() throws SQLException {}

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    appController.addPropertyChangeListener(this);
    addBox.setVisible(false);
    editBox.setVisible(false);
    deleteBox.setVisible(false);
    addPane.setVisible(true);
    editPane.setVisible(true);
    deletePane.setVisible(true);
    locDAO.getAllLocations();
    empDAO.getAllEmployees();
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
    locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
    employeeColumn.setCellValueFactory(new PropertyValueFactory<>("employee"));
    statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    availableColumn.setCellValueFactory(new PropertyValueFactory<>("attribute1"));

    if (morgueData.isEmpty()) {
      Iterator var3 = MorguerequestImpl.getAllRequests().entrySet().iterator();
      System.out.println("Filling Data");

      for (Map.Entry<String, Request> entry : MorguerequestImpl.getAllRequests().entrySet()) {
        Request req = entry.getValue();

        morgueData.add(req);
      }
    }
    morgueTable.setItems(morgueData);
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    String changeType = evt.getPropertyName();
    System.out.println(changeType);
    int newValue = (int) evt.getNewValue();
    System.out.println(newValue);
  }

  @FXML
  private void addRequest() {
    deleteBox.setVisible(false);
    editBox.setVisible(false);
    addBox.setVisible(true);
    morgueLocation.getItems().clear();
    for (Location loc : locationsList) {
      javafx.scene.control.MenuItem item = new MenuItem(loc.getNodeID());
      item.setOnAction(this::locationMenu);
      morgueLocation.getItems().add(item);
    }
    morgueEmployee.getItems().clear();
    for (String emp : freeEmployees) {
      MenuItem item = new MenuItem(emp);
      item.setOnAction(this::employeeMenu);
      morgueEmployee.getItems().add(item);
    }
    morgueID.setText("Morgue" + (morgueData.size() + 1));
  }

  public void submitRequest(ActionEvent actionEvent) {
    addBox.setVisible(false);
    String morgueID = "Morgue";
    String empString = morgueEmployee.getText();
    String locString = morgueLocation.getText();
    String statString = morgueStatus.getText();
    String desString = morgueDescription.getText();
    String typeString = "N/A";
    String att1 = "N/A";
    String att2 = "";

    if (empString.equals("Employee")
        || locString.equals("Location")
        || statString.equals("Status")) {
      System.out.println("Wrong");
    } else {
      Request morgueReq =
          new Request(
              morgueID,
              empDAO.getEmployee(empString),
              locDAO.getLocation(locString),
              typeString,
              statString,
              desString,
              att1,
              att2);
      MorguerequestImpl.addRequest(morgueReq);
      morgueData.add(morgueReq);
      morgueTable.setItems(morgueData);
    }
    morgueLocation.setText("Location");
    morgueStatus.setText("Status");
    morgueEmployee.setText("Employee");
  }

  public void clearRequest(ActionEvent event) {
    morgueLocation.setText("Location");
    morgueStatus.setText("Status");
    morgueEmployee.setText("Employee");
    morgueEditLocation.setText("Location");
    morgueEditStatus.setText("Status");
    morgueEditEmployee.setText("Employee");
  }

  public void submitDelete(ActionEvent actionEvent) {
    String deleteID = morgueDeleteID.getText();
    for (int i = 0; i < morgueData.size(); i++) {
      Request obj = morgueData.get(i);
      if (0 == deleteID.compareTo(obj.getName())) {
        morgueData.remove(i);
        MorguerequestImpl.deleteRequest(obj);
      }
    }
    morgueTable.setItems(morgueData);
    deleteBox.setVisible(false);
    morgueDeleteID.setText("ID");
  }

  public void deleteRequest(ActionEvent actionEvent) {
    addBox.setVisible(false);
    editBox.setVisible(false);
    deleteBox.setVisible(true);
    morgueDeleteID.getItems().clear();
    for (int i = 0; i < morgueData.size(); i++) {
      Request obj = morgueData.get(i);
      MenuItem item = new MenuItem(morgueData.get(i).getName());
      item.setOnAction(this::deleteIDMenu);
      morgueDeleteID.getItems().add(item);
    }
  }

  public void editRequest(ActionEvent actionEvent) {
    deleteBox.setVisible(false);
    addBox.setVisible(false);
    editBox.setVisible(true);
    morgueEditID.getItems().clear();
    for (int i = 0; i < morgueData.size(); i++) {
      Request obj = morgueData.get(i);
      MenuItem item = new MenuItem(morgueData.get(i).getName());
      item.setOnAction(this::editIDMenu);
      morgueEditID.getItems().add(item);
    }
    morgueEditLocation.getItems().clear();
    for (Location loc : locationsList) {
      javafx.scene.control.MenuItem item = new MenuItem(loc.getNodeID());
      item.setOnAction(this::editLocationMenu);
      morgueEditLocation.getItems().add(item);
    }
    morgueEditEmployee.getItems().clear();
    for (String emp : freeEmployees) {
      MenuItem item = new MenuItem(emp);
      item.setOnAction(this::editEmployeeMenu);
      morgueEditEmployee.getItems().add(item);
    }
  }

  public void submitEditReq() {
    String editID = morgueEditID.getText();
    String editEmployee = "";
    String editLocation = "";
    String editStatus = "";
    String editDescription = morgueEditDescription.getText();
    int num = 0;
    if (editID.equals("ID")) {
      System.out.println("Need ID");
      return;
    }
    if (morgueEditLocation.getText().equals("Location")) {
      for (int i = 0; i < morgueData.size(); i++) {
        Request req = morgueData.get(i);
        if (0 == editID.compareTo(req.getName())) {
          editLocation = req.getLocation().getNodeID();
          num = i;
        }
      }
    }
    if (!morgueEditLocation.getText().equals("Location")) {
      editLocation = morgueEditLocation.getText();
    }
    if (morgueEditEmployee.getText().equals("Employee")) {
      for (int i = 0; i < morgueData.size(); i++) {
        Request req = morgueData.get(i);
        if (0 == editID.compareTo(req.getName())) {
          editEmployee = req.getEmployee().getName();
        }
      }
    }
    if (!morgueEditEmployee.getText().equals("Employee")) {
      editEmployee = morgueEditEmployee.getText();
    }
    if (morgueEditStatus.getText().equals("Status")) {
      for (int i = 0; i < morgueData.size(); i++) {
        Request req = morgueData.get(i);
        if (0 == editID.compareTo(req.getName())) {
          editStatus = req.getStatus();
        }
      }
    }
    if (!morgueEditStatus.getText().equals("Status")) {
      editStatus = morgueEditStatus.getText();
    }
    Request edited =
        new Request(
            editID,
            empDAO.getEmployee(editEmployee),
            locDAO.getLocation(editLocation),
            "N/A",
            editStatus,
            editDescription,
            "N/A",
            "");
    morgueData.set(num, edited);
    editBox.setVisible(false);
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
  public void addStatusMenu(ActionEvent event) {
    MenuItem button = (MenuItem) event.getSource();
    morgueStatus.setText(button.getText());
  }

  @FXML
  public void deleteIDMenu(ActionEvent event) {
    MenuItem button = (MenuItem) event.getSource();
    morgueDeleteID.setText(button.getText());
    setDeleteInformation(event);
  }

  @FXML
  public void editIDMenu(ActionEvent event) {
    MenuItem button = (MenuItem) event.getSource();
    morgueEditID.setText(button.getText());
  }

  @FXML
  public void editEmployeeMenu(ActionEvent event) {
    MenuItem button = (MenuItem) event.getSource();
    morgueEditEmployee.setText(button.getText());
  }

  @FXML
  public void editLocationMenu(ActionEvent event) {
    MenuItem button = (MenuItem) event.getSource();
    morgueEditLocation.setText(button.getText());
  }

  @FXML
  public void editStatusMenu(ActionEvent event) {
    MenuItem button = (MenuItem) event.getSource();
    morgueEditStatus.setText(button.getText());
  }

  public void setDeleteInformation(ActionEvent event) {
    String deleteID = morgueDeleteID.getText();
    String deleteLocation = "";
    String deleteEmployee = "";
    String deleteStatus = "";
    for (int i = 0; i < morgueData.size(); i++) {
      Request req = morgueData.get(i);
      if (0 == deleteID.compareTo(req.getName())) {
        deleteStatus = req.getStatus();
        deleteEmployee = req.getEmployee().getName();
        deleteLocation = req.getLocation().getNodeID();
      }
    }
    morgueDeleteLocation.setText(deleteLocation);
    morgueDeleteEmployee.setText(deleteEmployee);
    morgueDeleteStatus.setText(deleteStatus);
  }
}
