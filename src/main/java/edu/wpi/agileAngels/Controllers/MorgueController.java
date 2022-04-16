package edu.wpi.agileAngels.Controllers;

import edu.wpi.agileAngels.Database.*;
import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class MorgueController implements Initializable {
  AppController appController = AppController.getInstance();
  // @FXML private Button addButton;
  @FXML private Pane addPane;
  @FXML private MenuButton morgueLocation, morgueEmployee, morgueStatus;
  @FXML private TableView morgueTable;
  // @FXML private Label requestConfirmation
  private LocationDAOImpl locDAO = LocationDAOImpl.getInstance();
  private EmployeeManager empDAO = EmployeeManager.getInstance();
  private MedEquipImpl equipDAO = MedEquipImpl.getInstance();
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
    addPane.setVisible(false);
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

      for (Map.Entry<String, Request> entry : MorguerequestImpl.getAllRequests().entrySet()) {
        Request req = entry.getValue();

        morgueData.add(req);
      }
    }

    for (Location loc : locationsList) {
      javafx.scene.control.MenuItem item = new MenuItem(loc.getNodeID());
      item.setOnAction(this::locationMenu);
      morgueLocation.getItems().add(item);
    }

    for (String emp : freeEmployees) {
      MenuItem item = new MenuItem(emp);
      item.setOnAction(this::employeeMenu);
      morgueEmployee.getItems().add(item);
    }

    morgueTable.setItems(morgueData);
  }

  @FXML
  private void addRequest() {
    addPane.setVisible(true);
    System.out.println("Hello");
  }

  public void submitRequest(ActionEvent actionEvent) {
    addPane.setVisible(false);
  }

  public void clearRequest(ActionEvent event) {
    morgueLocation.setText("Location");
    morgueStatus.setText("Status");
    morgueEmployee.setText("Employee");
  }

  public void deleteRequest(ActionEvent actionEvent) {
    addPane.setVisible(false);
  }

  public void editRequest(ActionEvent actionEvent) {
    addPane.setVisible(false);
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
}
