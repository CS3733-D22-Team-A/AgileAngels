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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class MaintenanceController implements Initializable {
  @FXML private Button addMaintenanceRequest;
  @FXML private TableView maintenanceTable;
  @FXML private Pane popOut;
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
    popOut.setVisible(false);
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

    maintenanceTable.setItems(maintenanceData);
  }

  @FXML
  public void addRequest(ActionEvent event) {
    if (popOut.visibleProperty().get()) {
      popOut.setVisible(false);
    } else {
      popOut.setVisible(true);
    }
  }

  public void submitRequest(ActionEvent actionEvent) {
    popOut.setVisible(false);
  }

  public void clearRequest(ActionEvent actionEvent) {
    popOut.setVisible(false);
  }

  public void deleteRequest(ActionEvent actionEvent) {
    // when button is clicked
  }

  public void editRequest(ActionEvent actionEvent) {
    // when button is clicked
  }

  // on action event set visibility for vbox
}
