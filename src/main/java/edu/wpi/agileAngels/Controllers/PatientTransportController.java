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
import javafx.scene.layout.Pane;

public class PatientTransportController implements Initializable, PropertyChangeListener {

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

  private LocationDAOImpl locDAO = LocationDAOImpl.getInstance();
  private EmployeeManager empDAO = EmployeeManager.getInstance();
  private MedEquipImpl equipDAO = MedEquipImpl.getInstance();
  private RequestDAOImpl transportDAO =
      RequestDAOImpl.getInstance("MedRequest"); // instance of RequestDAOImpl to access functions
  // only way to update the UI is ObservableList
  private static ObservableList<Request> transportData =
      FXCollections.observableArrayList(); // list of requests
  // hashmap and arrayList of all medical equipment
  HashMap<String, MedicalEquip> equipHash;
  ArrayList<MedicalEquip> allMedEquip;
  // hashMap and arrayList of all locations
  HashMap<String, Location> locationsHash = locDAO.getAllLocations();
  ArrayList<Location> locationsList = new ArrayList<>(locationsHash.values());
  HashMap<String, Employee> employeeHash = empDAO.getAllEmployees();

  AppController appController = AppController.getInstance();
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

  public PatientTransportController() throws SQLException {}

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
        // equipLocation.getItems().add(item);
      }
    }

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

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    String changeType = evt.getPropertyName();
    System.out.println(changeType);
    int newValue = (int) evt.getNewValue();
    System.out.println(newValue);
  }

  /**
   * TODO split submit equipment into add, edit x field, edit y field etc. Maybe add a dropdown for
   * edit request so we can choose specifically which field we need to change for the request. I
   * think doing this will help avoid confusion on FE
   *
   * <p>Implement little edit section on the left with the table on the right to use as a reference
   */
  @FXML
  public void addRequest(ActionEvent event) {
    popOut.setVisible(true);
  }

  public void submitRequest(ActionEvent actionEvent) {
    popOut.setVisible(false);
  }

  public void clearRequest(ActionEvent actionEvent) {
    popOut.setVisible(false);
  }

  public void deleteRequest(ActionEvent actionEvent) {}

  public void editRequest(ActionEvent actionEvent) {}

  // on action event set visibility for vbox
}
