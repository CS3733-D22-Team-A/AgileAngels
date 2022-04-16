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
import javafx.scene.layout.Pane;

public class MaintenanceController implements Initializable, PropertyChangeListener {
  // FXML Elements for the page
  @FXML
  private Button addMain,
      deleteMain,
      editMain,
      submitRequest,
      clearRequest,
      submitEditRequest,
      clearEditRequest,
      delete;
  @FXML private TableView maintenanceTable;
  @FXML private Pane addPane, editPane, deletePane;
  @FXML
  private Label mainID,
      mainEditID,
      mainDeleteLocation,
      mainDeleteEmployee,
      mainDeleteStatus,
      mainDeleteDescription;
  @FXML
  MenuButton mainLocation,
      mainEmployee,
      mainStatus,
      mainEditLocation,
      mainEditEmployee,
      mainEditStatus,
      mainDeleteID;
  @FXML private TextField mainDescription, mainEditDescription;
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
  private HashMap<String, Location> locationsHash = locDAO.getAllLocations();
  private ArrayList<Location> locationsList = new ArrayList<>(locationsHash.values());
  private HashMap<String, Employee> employeeHash = empDAO.getAllEmployees();

  AppController appController = AppController.getInstance();

  public MaintenanceController() throws SQLException {}

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    appController.addPropertyChangeListener(this);
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
      // Iterator var3 = mainRequestImpl.getAllRequests().entrySet().iterator();

      for (Map.Entry<String, Request> entry : mainRequestImpl.getAllRequests().entrySet()) {
        Request req = entry.getValue();
        maintenanceData.add(req);
      }
    }

    maintenanceTable.setItems(maintenanceData);
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    String changeType = evt.getPropertyName();
    System.out.println(changeType);
    int newValue = (int) evt.getNewValue();
    System.out.println(newValue);
  }

  /**
   * Sets up pane for creating a new maintenance request.
   *
   * @param event ActionEvent
   */
  @FXML
  public void addRequest(ActionEvent event) {
    // Sets other panes (if open) to hidden
    deletePane.setVisible(false);
    editPane.setVisible(false);
    addPane.setVisible(true);

    // Populates locations dropdown
    for (Location loc : locationsList) {
      MenuItem item = new MenuItem(loc.getNodeID());
      item.setOnAction(this::addLocationMenu);
      mainLocation.getItems().add(item);
    }
    System.out.println("Locations?");

    // Populates employees dropdown
    for (Map.Entry<String, Employee> entry : employeeHash.entrySet()) {
      Employee emp = entry.getValue();
      MenuItem item = new MenuItem(emp.getName());
      item.setOnAction(this::addEmployeeMenu);
      mainEmployee.getItems().add(item);
    }

    // Generates a nodeID
    mainID.setText("Main" + (maintenanceData.size() + 1));
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
  public void addLocationMenu(ActionEvent event) {
    MenuItem button = (MenuItem) event.getSource();
    mainLocation.setText(button.getText());
  }

  @FXML
  public void addEmployeeMenu(ActionEvent event) {
    MenuItem button = (MenuItem) event.getSource();
    mainEmployee.setText(button.getText());
  }

  @FXML
  public void addStatusMenu(ActionEvent event) {
    MenuItem button = (MenuItem) event.getSource();
    mainStatus.setText(button.getText());
  }
  // on action event set visibility for vbox
}
