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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MorgueController implements Initializable {
  AppController appController = AppController.getInstance();
  // @FXML private Button addButton;
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
  @FXML
  private TableColumn nameColumn,
      typeColumn,
      locationColumn,
      employeeColumn,
      statusColumn,
      descriptionColumn;

  public MorgueController() throws SQLException {}

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    locDAO.getAllLocations();
    empDAO.getAllEmployees();
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
    locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
    employeeColumn.setCellValueFactory(new PropertyValueFactory<>("employee"));
    statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    if (morgueData.isEmpty()) {
      Iterator var3 = MorguerequestImpl.getAllRequests().entrySet().iterator();

      for (Map.Entry<String, Request> entry : MorguerequestImpl.getAllRequests().entrySet()) {
        Request req = entry.getValue();

        morgueData.add(req);
      }
    }

    morgueTable.setItems(morgueData);
  }

  @FXML
  private void addRequest() {
    System.out.println("Hello");
  }

  public void clearPage(ActionEvent event) {
    appController.clearPage();
  }
}
