package edu.wpi.agileAngels.Controllers;

import edu.wpi.agileAngels.Database.*;
import javafx.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class MaintenanceController extends MainController implements Initializable, PropertyChangeListener {

  @FXML
  Pane popOut;
  @FXML
  MenuButton mainID, mainLocation, mainEmployee, mainStatus;
  @FXML
  Button modifyButton, cancelRequest, submitRequest, clearRequest;
  @FXML
  TableView mainTable;
  @FXML
  private TableColumn nameColumn,
          availableColumn,
          typeColumn,
          locationColumn,
          employeeColumn,
          statusColumn,
          descriptionColumn;
  @FXML
  TextField mainDescription, employeeFilterField, statusFilterField;
  @FXML
  Label notStartedNumber,
          inProgressNumber,
          completedNumber;

  //DAOs, HashMaps, and Lists required for functionality
  private LocationDAOImpl locDAO = LocationDAOImpl.getInstance();
  private EmployeeManager empDAO = EmployeeManager.getInstance();
  private RequestDAOImpl mainRequestImpl = RequestDAOImpl.getInstance("MaintenanceRequest");
  private HashMap<String, Location> locationsHash = locDAO.getAllLocations();
  private ArrayList<Location> locationsList = new ArrayList<>(locationsHash.values());
  private HashMap<String, Employee> employeeHash = empDAO.getAllEmployees();
  private static ObservableList<Request> maintenanceData = FXCollections.observableArrayList();

  private int statusNotStarted, statusInProgress, statusComplete;

  private AppController appController = AppController.getInstance();

  public MaintenanceController() throws SQLException {
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    appController.addPropertyChangeListener(this);
    popOut.setVisible(false);
    statusNotStarted = 0;
    statusInProgress = 0;
    statusComplete = 0;

    nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
    employeeColumn.setCellValueFactory(new PropertyValueFactory<>("employee"));
    locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
    typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
    statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    availableColumn.setCellValueFactory(new PropertyValueFactory<>("attribute1"));

    //Populates the table from UI list
    if (maintenanceData.isEmpty()) {
      for (Map.Entry<String, Request> entry : mainRequestImpl.getAllRequests().entrySet()) {
        Request req = entry.getValue();
        maintenanceData.add(req);
      }
    }
    dashboardLoad();
    mainTable.setItems(maintenanceData);
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    String changeType = evt.getPropertyName();
    System.out.println(changeType);
    int newValue = (int) evt.getNewValue();
    System.out.println(newValue);
  }

  @FXML
  public void modifyRequest(ActionEvent event){
    popOut.setVisible(true);
  }

  @FXML
  public void submit(ActionEvent event){}

  @FXML
  public void cancel(ActionEvent event){
    popOut.setVisible(false);
  }

  @FXML
  public void clear(ActionEvent event){}

  @FXML
  public void filterReqOnAction(ActionEvent event){}

  @FXML
  public void clearFilters(ActionEvent event){}

  @FXML
  public void mainIDMenu(ActionEvent event){

  }

  @FXML
  public void mainLocationMenu(ActionEvent event){

  }
  @FXML
  public void mainEmployeeMenu(ActionEvent event){

  }
  @FXML
  public void mainStatusMenu(ActionEvent event){

  }

  /**
   * This is the cleaner version of Justin's dashboard code. Note that it may need a for loop as
   * shown on line 83/84 if used elsewhere. Note: Unlikely.
   */
  @FXML
  private void dashboardLoad() {
    if (notStartedNumber.getText().equals("-")
            && inProgressNumber.getText().equals("-")
            && completedNumber.getText().equals("-")) {

      Iterator var3 = mainRequestImpl.getAllRequests().entrySet().iterator();
      while (var3.hasNext()) {
        Map.Entry<String, Request> entry = (Map.Entry) var3.next();
        Request object = (Request) entry.getValue();
        if (entry.getValue().getStatus().equals("inProgress")) {
          statusInProgress++;
        }
        if (entry.getValue().getStatus().equals("notStarted")) {
          statusNotStarted++;
        }
        if (entry.getValue().getStatus().equals("Complete")
                || entry.getValue().getStatus().equals("complete")) {
          statusComplete++;
        }
      }
      setDashboard(statusNotStarted, statusInProgress, statusComplete);
    }
  }


  /**
   * Will set the dashboard's numbers to the certain types of statuses.
   * @param notStarted Requests not started
   * @param inProgress Requests in progress
   * @param complete Requests completed
   */
  @FXML
  private void setDashboard(int notStarted, int inProgress, int complete) {
    String notStart = Integer.toString(notStarted);
    String inProg = Integer.toString(inProgress);
    String comp = Integer.toString(complete);
    notStartedNumber.setText(notStart);
    inProgressNumber.setText(inProg);
    completedNumber.setText(comp);
  }


}
