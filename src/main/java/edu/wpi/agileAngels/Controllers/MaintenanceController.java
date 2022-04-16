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
import javafx.scene.layout.Pane;

public class MaintenanceController implements Initializable {
  @FXML private Button addMain, deleteMain, editMain, submitRequest, clearRequest;
  @FXML private TableView maintenanceTable;
  @FXML private Pane addPane, editPane, deletePane;
  @FXML private Label mainID;
  @FXML MenuButton mainLocation, mainEmployee, mainStatus;
  @FXML private TextField mainDescription;
  @FXML
  private TableColumn nameColumn,
      employeeColumn, // change to employeeColumn
      locationColumn,
      typeColumn,
      statusColumn,
      descriptionColumn,
      availableColumn;

  private static ObservableList<Request> maintenanceData = FXCollections.observableArrayList();

  private LocationDAOImpl locDAO = LocationDAOImpl.getInstance();
  private EmployeeManager empDAO = EmployeeManager.getInstance();
  private RequestDAOImpl mainRequestImpl = RequestDAOImpl.getInstance("MaintenanceRequest");
  HashMap<String, Location> locationsHash = locDAO.getAllLocations();
  ArrayList<Location> locationsList = new ArrayList<>(locationsHash.values());
  HashMap<String, Employee> employeeHash = empDAO.getAllEmployees();

  AppController appController = AppController.getInstance();

  public MaintenanceController() throws SQLException {}

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    addPane.setVisible(false);
    editPane.setVisible(false);
    deletePane.setVisible(false);
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
    employeeColumn.setCellValueFactory(new PropertyValueFactory<>("employee"));
    locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
    typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
    statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    availableColumn.setCellValueFactory(new PropertyValueFactory<>("attribute1"));

    if (maintenanceData.isEmpty()) {
      Iterator var3 = mainRequestImpl.getAllRequests().entrySet().iterator();

      for (Map.Entry<String, Request> entry : mainRequestImpl.getAllRequests().entrySet()) {
        Request req = entry.getValue();

        maintenanceData.add(req);
      }
    }

    for (Location loc : locationsList) {
      MenuItem item = new MenuItem(loc.getNodeID());
      item.setOnAction(this::locationMenu);
      mainLocation.getItems().add(item);
    }

    for (Map.Entry<String, Employee> entry : employeeHash.entrySet()) {
      Employee emp = entry.getValue();
      MenuItem item = new MenuItem(emp.getName());
      item.setOnAction(this::employeeMenu);
      mainEmployee.getItems().add(item);
    }

    maintenanceTable.setItems(maintenanceData);
  }

  @FXML
  public void addRequest(ActionEvent event) {
    deletePane.setVisible(false);
    editPane.setVisible(false);
    addPane.setVisible(true);
  }

  public void submitRequest(ActionEvent actionEvent) {
    addPane.setVisible(false);
  }

  public void clearRequest(ActionEvent actionEvent) {
    // addPane.setVisible(false);
    mainLocation.setText("Location");
    mainStatus.setText("Status");
    mainEmployee.setText("Employee");
  }

  public void deleteRequest(ActionEvent actionEvent) {
    addPane.setVisible(false);
    editPane.setVisible(false);
    deletePane.setVisible(true);
  }

  public void editRequest(ActionEvent actionEvent) {
    deletePane.setVisible(false);
    addPane.setVisible(false);
    editPane.setVisible(true);
  }

  @FXML
  public void locationMenu(ActionEvent event) {
    MenuItem button = (MenuItem) event.getSource();
    mainLocation.setText(button.getText());
  }

  @FXML
  public void employeeMenu(ActionEvent event) {
    MenuItem button = (MenuItem) event.getSource();
    mainEmployee.setText(button.getText());
  }

  @FXML
  public void statusMenu(ActionEvent event) {
    MenuItem button = (MenuItem) event.getSource();
    mainStatus.setText(button.getText());
  }
  // on action event set visibility for vbox
}
