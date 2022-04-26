package edu.wpi.agileAngels.Controllers;

import edu.wpi.agileAngels.Database.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class DashboardController implements Initializable, PropertyChangeListener {

  @FXML
  Label cleanPump,
      cleanBeds,
      cleanXRay,
      cleanRecliner,
      dirtyPump,
      dirtyBeds,
      dirtyXRay,
      dirtyRecliner;
  @FXML Button floor5, floor4, floor3, floor2, floorLL1, floorLL2;

  AppController appController = AppController.getInstance();
  ArrayList<Pane> panes = new ArrayList<>();
  @FXML private ScrollPane scrollPane = new ScrollPane();
  @FXML private Pane cleanDirty, graphs;
  @FXML private GridPane cleanDirtyGrid;
  @FXML private TableView requestTable, employeeTable;
  @FXML
  private TableColumn typeColumn,
      statusColumn,
      employeeColumn,
      floorColumn,
      empEmployeeColumn,
      empFloorColumn;
  @FXML private BarChart requestGraph;
  @FXML private MenuButton graphType;

  private RequestDAOImpl requestDAO = RequestDAOImpl.getInstance("AllRequests");
  private ArrayList<Request> requestsList = new ArrayList<>(requestDAO.getAllRequests().values());
  private static ObservableList<RequestSummary> requestSummaries =
      FXCollections.observableArrayList();

  private EmployeeManager employeeDAO = EmployeeManager.getInstance();
  private ArrayList<Employee> employeeList =
      new ArrayList<>(employeeDAO.getAllEmployees().values());
  private static ObservableList<Employee> employees = FXCollections.observableArrayList();

  private int[] dirtyBedsArray = new int[4];
  private int[] dirtyPumpsArray = new int[4];
  private int[] dirtyReclinersArray = new int[4];
  private int[] dirtyXRaysArray = new int[4];
  private int bedsPerFloor = 10;
  private int pumpsPerFloor = 15;
  private int reclinersPerFloor = 5;
  private int xraysPerFloor = 1;
  private int numFloors = 3;

  private XYChart.Series series1 = new XYChart.Series();
  private XYChart.Series series2 = new XYChart.Series();
  private XYChart.Series series3 = new XYChart.Series();

  public DashboardController() throws SQLException {}

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    cleanDirty.setVisible(true);
    graphs.setVisible(false);
    floor5.setPickOnBounds(false);
    floor4.setPickOnBounds(false);
    floor3.setPickOnBounds(false);
    floor2.setPickOnBounds(false);
    floorLL1.setPickOnBounds(false);
    floorLL2.setPickOnBounds(false);
    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    appController.addPropertyChangeListener(this);

    series1.setName("All Requests");
    series2.setName(graphType.getText());

    try {
      this.updateCleanDirtyFromDB();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
    statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    employeeColumn.setCellValueFactory(new PropertyValueFactory<>("employee"));
    floorColumn.setCellValueFactory(new PropertyValueFactory<>("floor"));
    empEmployeeColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    empFloorColumn.setCellValueFactory(new PropertyValueFactory<>("floorOnDuty"));

    populateRequestTable("All");
    populateEmployeeTable("All");
    graphRequests("All");
  }

  private void populateRequestTable(String floor) {
    requestsList = new ArrayList<>(requestDAO.getAllRequests().values());
    requestSummaries.clear();
    if (floor.equals("All")) {
      for (Request request : requestsList) {
        RequestSummary summary = new RequestSummary(request);
        requestSummaries.add(summary);
      }
    } else {
      for (Request request : requestsList) {
        if (request.getLocation().getFloor().equals(floor)) {
          RequestSummary summary = new RequestSummary(request);
          requestSummaries.add(summary);
        }
      }
    }
    requestTable.setItems(requestSummaries);
  }

  private void populateEmployeeTable(String floor) {
    employeeList = new ArrayList<>(employeeDAO.getAllEmployees().values());
    employees.clear();
    if (floor.equals("All")) {
      for (Employee employee : employeeList) {
        if (!employee.getFloorOnDuty().equals("Off Duty")) {
          employees.add(employee);
        }
      }
    } else {
      for (Employee employee : employeeList) {
        if (employee.getFloorOnDuty().equals(floor)) {
          employees.add(employee);
        }
      }
    }
    employeeTable.setItems(employees);
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    String changeType = evt.getPropertyName();
    int newValue = (int) evt.getNewValue();
    if (changeType.equals("dirtyBedsAll")) {
      dirtyBedsArray[0] = newValue;
    } else if (changeType.equals("dirtyBeds1")) {
      dirtyBedsArray[1] = newValue;
    } else if (changeType.equals("dirtyBeds2")) {
      dirtyBedsArray[2] = newValue;
    } else if (changeType.equals("dirtyBeds3")) {
      dirtyBedsArray[3] = newValue;
    } else if (changeType.equals("dirtyPumpsAll")) {
      dirtyPumpsArray[0] = newValue;
    } else if (changeType.equals("dirtyPumps1")) {
      dirtyPumpsArray[1] = newValue;
    } else if (changeType.equals("dirtyPumps2")) {
      dirtyPumpsArray[2] = newValue;
    } else if (changeType.equals("dirtyPumps3")) {
      dirtyPumpsArray[3] = newValue;
    } else if (changeType.equals("dirtyReclinersAll")) {
      dirtyReclinersArray[0] = newValue;
    } else if (changeType.equals("dirtyRecliners1")) {
      dirtyReclinersArray[1] = newValue;
    } else if (changeType.equals("dirtyRecliners2")) {
      dirtyReclinersArray[2] = newValue;
    } else if (changeType.equals("dirtyRecliners3")) {
      dirtyReclinersArray[3] = newValue;
    } else if (changeType.equals("dirtyXRaysAll")) {
      dirtyXRaysArray[0] = newValue;
    } else if (changeType.equals("dirtyXRays1")) {
      dirtyXRaysArray[1] = newValue;
    } else if (changeType.equals("dirtyXRays2")) {
      dirtyXRaysArray[2] = newValue;
    } else if (changeType.equals("dirtyXRays3")) {
      dirtyXRaysArray[3] = newValue;
    }
    appController.displayAlert();
  }

  @FXML
  public void bigFloor(MouseEvent event) {

    // floor 5
    Timeline timeline5 = new Timeline();
    timeline5
        .getKeyFrames()
        .addAll(
            new KeyFrame(Duration.ZERO, new KeyValue(floor5.translateYProperty(), 0)),
            new KeyFrame(Duration.millis(20), new KeyValue(floor5.translateYProperty(), 3)));

    if (event.getSource() == floor5) {
      timeline5.play();
      displaySingleFloorCounts(3);
      populateRequestTable("5");
      populateEmployeeTable("5");
      graphRequests("5");
    }

    // floor 4
    Timeline timeline4 = new Timeline();
    timeline4
        .getKeyFrames()
        .addAll(
            new KeyFrame(Duration.ZERO, new KeyValue(floor4.translateYProperty(), 0)),
            new KeyFrame(Duration.millis(20), new KeyValue(floor4.translateYProperty(), 3)));

    if (event.getSource() == floor4) {
      timeline4.play();
      displaySingleFloorCounts(2);
      populateRequestTable("4");
      populateEmployeeTable("4");
      graphRequests("4");
    }

    // floor 3
    Timeline timeline3 = new Timeline();
    timeline3
        .getKeyFrames()
        .addAll(
            new KeyFrame(Duration.ZERO, new KeyValue(floor3.translateYProperty(), 0)),
            new KeyFrame(Duration.millis(20), new KeyValue(floor3.translateYProperty(), 3)));

    if (event.getSource() == floor3) {
      timeline3.play();
      displaySingleFloorCounts(1);
      populateRequestTable("3");
      populateEmployeeTable("3");
      graphRequests("3");
    }

    // floor 2
    Timeline timeline2 = new Timeline();
    timeline2
        .getKeyFrames()
        .addAll(
            new KeyFrame(Duration.ZERO, new KeyValue(floor2.translateYProperty(), 0)),
            new KeyFrame(Duration.millis(20), new KeyValue(floor2.translateYProperty(), 3)));

    if (event.getSource() == floor2) {
      timeline2.play();
      displayZeroes();
      populateRequestTable("2");
      populateEmployeeTable("2");
      graphRequests("2");
    }

    // floor LL1
    Timeline timelineLL1 = new Timeline();
    timelineLL1
        .getKeyFrames()
        .addAll(
            new KeyFrame(Duration.ZERO, new KeyValue(floorLL1.translateYProperty(), 0)),
            new KeyFrame(Duration.millis(20), new KeyValue(floorLL1.translateYProperty(), 3)));

    if (event.getSource() == floorLL1) {
      timelineLL1.play();
      displayZeroes();
      populateRequestTable("L1");
      populateEmployeeTable("L1");
      graphRequests("L1");
    }

    // floor LL2
    Timeline timelineLL2 = new Timeline();
    timelineLL2
        .getKeyFrames()
        .addAll(
            new KeyFrame(Duration.ZERO, new KeyValue(floorLL2.translateYProperty(), 0)),
            new KeyFrame(Duration.millis(20), new KeyValue(floorLL2.translateYProperty(), 3)));

    if (event.getSource() == floorLL2) {
      timelineLL2.play();
      displayZeroes();
      populateRequestTable("L2");
      populateEmployeeTable("L2");
      graphRequests("L2");
    }
  }

  @FXML
  public void unhover(MouseEvent event) {

    // floor 5
    Timeline timeline5 = new Timeline();
    timeline5
        .getKeyFrames()
        .addAll(
            new KeyFrame(Duration.ZERO, new KeyValue(floor5.translateYProperty(), 0)),
            new KeyFrame(Duration.millis(20), new KeyValue(floor5.translateYProperty(), -3)));

    if (event.getSource() == floor5) {
      timeline5.play();
    }

    // floor 4
    Timeline timeline4 = new Timeline();
    timeline4
        .getKeyFrames()
        .addAll(
            new KeyFrame(Duration.ZERO, new KeyValue(floor4.translateYProperty(), 0)),
            new KeyFrame(Duration.millis(20), new KeyValue(floor4.translateYProperty(), -3)));

    if (event.getSource() == floor4) {
      timeline4.play();
    }

    // floor 3
    Timeline timeline3 = new Timeline();
    timeline3
        .getKeyFrames()
        .addAll(
            new KeyFrame(Duration.ZERO, new KeyValue(floor3.translateYProperty(), 0)),
            new KeyFrame(Duration.millis(100), new KeyValue(floor3.translateYProperty(), -3)));

    if (event.getSource() == floor3) {
      timeline3.play();
    }

    // floor 2
    Timeline timeline2 = new Timeline();
    timeline2
        .getKeyFrames()
        .addAll(
            new KeyFrame(Duration.ZERO, new KeyValue(floor2.translateYProperty(), 0)),
            new KeyFrame(Duration.millis(20), new KeyValue(floor2.translateYProperty(), -3)));

    if (event.getSource() == floor2) {
      timeline2.play();
    }

    // floor LL1
    Timeline timelineLL1 = new Timeline();
    timelineLL1
        .getKeyFrames()
        .addAll(
            new KeyFrame(Duration.ZERO, new KeyValue(floorLL1.translateYProperty(), 0)),
            new KeyFrame(Duration.millis(20), new KeyValue(floorLL1.translateYProperty(), -3)));

    if (event.getSource() == floorLL1) {
      timelineLL1.play();
    }

    // floor LL2
    Timeline timelineLL2 = new Timeline();
    timelineLL2
        .getKeyFrames()
        .addAll(
            new KeyFrame(Duration.ZERO, new KeyValue(floorLL2.translateYProperty(), 0)),
            new KeyFrame(Duration.millis(20), new KeyValue(floorLL2.translateYProperty(), -3)));

    if (event.getSource() == floorLL2) {
      timelineLL2.play();
    }
    displayAllFloorsCounts();
    populateRequestTable("All");
    populateEmployeeTable("All");
    graphRequests("All");
  }

  @FXML
  public void updateCleanDirtyFromDB() throws SQLException {

    MedEquipImpl equipDAO = MedEquipImpl.getInstance();
    HashMap<String, MedicalEquip> equipHash;
    equipHash = equipDAO.getAllMedicalEquipment();

    if (equipHash == null) {
      System.out.println("It's null");
    } else {

      for (Map.Entry<String, MedicalEquip> entry : equipHash.entrySet()) {
        MedicalEquip equip = entry.getValue();
        if (equip.getType().equals("InfusionPump") && !equip.isClean()) {
          dirtyPumpsArray[0] += 1;
          if (equip.getLocation().getFloor().equals("3")) {
            dirtyPumpsArray[1] += 1;
          } else if (equip.getLocation().getFloor().equals("4")) {
            dirtyPumpsArray[2] += 1;
          } else if (equip.getLocation().getFloor().equals("5")) {
            dirtyPumpsArray[3] += 1;
          }
        } else if (equip.getType().equals("XRayMachine") && !equip.isClean()) {
          dirtyXRaysArray[0] += 1;
          if (equip.getLocation().getFloor().equals("3")) {
            dirtyXRaysArray[1] += 1;
          } else if (equip.getLocation().getFloor().equals("4")) {
            dirtyXRaysArray[2] += 1;
          } else if (equip.getLocation().getFloor().equals("5")) {
            dirtyXRaysArray[3] += 1;
          }
        } else if (equip.getType().equals("Bed") && !equip.isClean()) {
          dirtyBedsArray[0] += 1;
          if (equip.getLocation().getFloor().equals("3")) {
            dirtyBedsArray[1] += 1;
          } else if (equip.getLocation().getFloor().equals("4")) {
            dirtyBedsArray[2] += 1;
          } else if (equip.getLocation().getFloor().equals("5")) {
            dirtyBedsArray[3] += 1;
          }
        } else if (equip.getType().equals("Recliner") && !equip.isClean()) {
          dirtyReclinersArray[0] += 1;
          if (equip.getLocation().getFloor().equals("3")) {
            dirtyReclinersArray[1] += 1;
          } else if (equip.getLocation().getFloor().equals("4")) {
            dirtyReclinersArray[2] += 1;
          } else if (equip.getLocation().getFloor().equals("5")) {
            dirtyReclinersArray[3] += 1;
          }
        }
      }

      displayAllFloorsCounts();
    }
  }

  public void displayAllFloorsCounts() {
    cleanPump.setText(String.valueOf((pumpsPerFloor * numFloors) - dirtyPumpsArray[0]));
    cleanXRay.setText(String.valueOf((xraysPerFloor * numFloors) - dirtyXRaysArray[0]));
    cleanBeds.setText(String.valueOf((bedsPerFloor * numFloors) - dirtyBedsArray[0]));
    cleanRecliner.setText(String.valueOf((reclinersPerFloor * numFloors) - dirtyReclinersArray[0]));
    dirtyBeds.setText(String.valueOf(dirtyBedsArray[0]));
    dirtyRecliner.setText(String.valueOf(dirtyReclinersArray[0]));
    dirtyXRay.setText(String.valueOf(dirtyXRaysArray[0]));
    dirtyPump.setText(String.valueOf(dirtyPumpsArray[0]));
  }

  public void displaySingleFloorCounts(int floorInt) {
    cleanPump.setText(String.valueOf(pumpsPerFloor - dirtyPumpsArray[floorInt]));
    cleanXRay.setText(String.valueOf(xraysPerFloor - dirtyXRaysArray[floorInt]));
    cleanBeds.setText(String.valueOf(bedsPerFloor - dirtyBedsArray[floorInt]));
    cleanRecliner.setText(String.valueOf(reclinersPerFloor - dirtyReclinersArray[floorInt]));
    dirtyBeds.setText(String.valueOf(dirtyBedsArray[floorInt]));
    dirtyRecliner.setText(String.valueOf(dirtyReclinersArray[floorInt]));
    dirtyXRay.setText(String.valueOf(dirtyXRaysArray[floorInt]));
    dirtyPump.setText(String.valueOf(dirtyPumpsArray[floorInt]));
  }

  public void displayZeroes() {
    cleanPump.setText("0");
    cleanXRay.setText("0");
    cleanBeds.setText("0");
    cleanRecliner.setText("0");
    dirtyBeds.setText("0");
    dirtyRecliner.setText("0");
    dirtyXRay.setText("0");
    dirtyPump.setText("0");
  }

  public void goToFloor(ActionEvent event) {
    if (event.getSource() == floor5) {
      appController.setCurrentFloor("5");
    } else if (event.getSource() == floor4) {
      appController.setCurrentFloor("4");
    } else if (event.getSource() == floor3) {
      appController.setCurrentFloor("3");
    } else if (event.getSource() == floor2) {
      appController.setCurrentFloor("2");
    } else if (event.getSource() == floorLL1) {
      appController.setCurrentFloor("L1");
    } else if (event.getSource() == floorLL2) {
      appController.setCurrentFloor("L2");
    }
    appController.loadPage("/edu/wpi/agileAngels/views/map-view.fxml");
  }

  public void changeDisplay() {
    if (cleanDirty.isVisible()) {
      cleanDirty.setVisible(false);
      cleanDirtyGrid.setVisible(false);
      graphs.setVisible(true);
    } else if (graphs.isVisible()) {
      graphs.setVisible(false);
      cleanDirty.setVisible(true);
      cleanDirtyGrid.setVisible(true);
    }
  }

  public void graphRequests(String floor) {
    requestsList = new ArrayList<>(requestDAO.getAllRequests().values());
    int numNotStarted = 0;
    int numComplete = 0;
    int numInProg = 0;
    int numNotStartedType = 0;
    int numCompleteType = 0;
    int numInProgType = 0;
    String type = graphType.getText();
    if (floor.equals("All")) {
      for (Request request : requestsList) {
        if (request.getStatus().equals("notStarted")) {
          numNotStarted++;
        } else if (request.getStatus().equals("complete")) {
          numComplete++;
        } else if (request.getStatus().equals("inProgress")) {
          numInProg++;
        }
        if (request.getName().contains(type.substring(0, 3))) {
          if (request.getStatus().equals("notStarted")) {
            numNotStartedType++;
          } else if (request.getStatus().equals("complete")) {
            numCompleteType++;
          } else if (request.getStatus().equals("inProgress")) {
            numInProgType++;
          }
        }
      }
    } else {
      for (Request request : requestsList) {
        if (request.getLocation().getFloor().equals(floor)) {
          if (request.getStatus().equals("notStarted")) {
            numNotStarted++;
          } else if (request.getStatus().equals("complete")) {
            numComplete++;
          } else if (request.getStatus().equals("inProgress")) {
            numInProg++;
          }
          if (request.getName().contains(type.substring(0, 3))) {
            if (request.getStatus().equals("notStarted")) {
              numNotStartedType++;
            } else if (request.getStatus().equals("complete")) {
              numCompleteType++;
            } else if (request.getStatus().equals("inProgress")) {
              numInProgType++;
            }
          }
        }
      }
    }
    series1.getData().add(new XYChart.Data("Not Started", numNotStarted));
    series1.getData().add(new XYChart.Data("In Progress", numInProg));
    series1.getData().add(new XYChart.Data("Complete", numComplete));
    series2.getData().add(new XYChart.Data("Not Started", numNotStartedType));
    series2.getData().add(new XYChart.Data("In Progress", numInProgType));
    series2.getData().add(new XYChart.Data("Complete", numCompleteType));
    requestGraph.getData().addAll(series1, series2);
  }

  public void typeMenu(ActionEvent event) {
    MenuItem button = (MenuItem) event.getSource();
    graphType.setText(button.getText());
    graphRequests("All");
  }
}
