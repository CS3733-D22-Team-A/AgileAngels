package edu.wpi.agileAngels.Controllers;

import edu.wpi.agileAngels.Database.MedEquipImpl;
import edu.wpi.agileAngels.Database.MedicalEquip;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
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

  private int[] dirtyBedsArray = new int[4];
  private int[] dirtyPumpsArray = new int[4];
  private int[] dirtyReclinersArray = new int[4];
  private int[] dirtyXRaysArray = new int[4];
  private int bedsPerFloor = 10;
  private int pumpsPerFloor = 15;
  private int reclinersPerFloor = 5;
  private int xraysPerFloor = 1;
  private int numFloors = 3;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    floor5.setPickOnBounds(false);
    floor4.setPickOnBounds(false);
    floor3.setPickOnBounds(false);
    floor2.setPickOnBounds(false);
    floorLL1.setPickOnBounds(false);
    floorLL2.setPickOnBounds(false);
    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    appController.addPropertyChangeListener(this);

    try {
      this.updateCleanDirtyFromDB();
    } catch (SQLException e) {
      e.printStackTrace();
    }
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
            new KeyFrame(Duration.ZERO, new KeyValue(floor5.translateYProperty(), 10)),
            new KeyFrame(Duration.millis(1000), new KeyValue(floor5.translateYProperty(), 10)));

    if (event.getSource() == floor5) {
      timeline5.play();
      displaySingleFloorCounts(3);
    }

    // floor 4
    Timeline timeline4 = new Timeline();
    timeline4
        .getKeyFrames()
        .addAll(
            new KeyFrame(Duration.ZERO, new KeyValue(floor4.translateYProperty(), 10)),
            new KeyFrame(Duration.millis(1000), new KeyValue(floor4.translateYProperty(), 10)));

    if (event.getSource() == floor4) {
      timeline4.play();
      displaySingleFloorCounts(2);
    }

    // floor 3
    Timeline timeline3 = new Timeline();
    timeline3
        .getKeyFrames()
        .addAll(
            new KeyFrame(Duration.ZERO, new KeyValue(floor3.translateYProperty(), 10)),
            new KeyFrame(Duration.millis(1000), new KeyValue(floor3.translateYProperty(), 10)));

    if (event.getSource() == floor3) {
      timeline3.play();
      displaySingleFloorCounts(1);
    }

    // floor 2
    Timeline timeline2 = new Timeline();
    timeline2
        .getKeyFrames()
        .addAll(
            new KeyFrame(Duration.ZERO, new KeyValue(floor2.translateYProperty(), 10)),
            new KeyFrame(Duration.millis(1000), new KeyValue(floor2.translateYProperty(), 10)));

    if (event.getSource() == floor2) {
      timeline2.play();
      displayZeroes();
    }

    // floor LL1
    Timeline timelineLL1 = new Timeline();
    timelineLL1
        .getKeyFrames()
        .addAll(
            new KeyFrame(Duration.ZERO, new KeyValue(floorLL1.translateYProperty(), 10)),
            new KeyFrame(Duration.millis(1000), new KeyValue(floorLL1.translateYProperty(), 10)));

    if (event.getSource() == floorLL1) {
      timelineLL1.play();
      displayZeroes();
    }

    // floor LL2
    Timeline timelineLL2 = new Timeline();
    timelineLL2
        .getKeyFrames()
        .addAll(
            new KeyFrame(Duration.ZERO, new KeyValue(floorLL2.translateYProperty(), 10)),
            new KeyFrame(Duration.millis(1000), new KeyValue(floorLL2.translateYProperty(), 10)));

    if (event.getSource() == floorLL2) {
      timelineLL2.play();
      displayZeroes();
    }
  }

  @FXML
  public void unhover(MouseEvent event) {

    // floor 5
    Timeline timeline5 = new Timeline();
    timeline5
        .getKeyFrames()
        .addAll(
            new KeyFrame(Duration.ZERO, new KeyValue(floor5.translateYProperty(), -2.5)),
            new KeyFrame(Duration.millis(1000), new KeyValue(floor5.translateYProperty(), -2.5)));

    if (event.getSource() == floor5) {
      timeline5.play();
    }

    // floor 4
    Timeline timeline4 = new Timeline();
    timeline4
        .getKeyFrames()
        .addAll(
            new KeyFrame(Duration.ZERO, new KeyValue(floor4.translateYProperty(), -2.5)),
            new KeyFrame(Duration.millis(1000), new KeyValue(floor4.translateYProperty(), -2.5)));

    if (event.getSource() == floor4) {
      timeline4.play();
    }

    // floor 3
    Timeline timeline3 = new Timeline();
    timeline3
        .getKeyFrames()
        .addAll(
            new KeyFrame(Duration.ZERO, new KeyValue(floor3.translateYProperty(), -2.5)),
            new KeyFrame(Duration.millis(1000), new KeyValue(floor3.translateYProperty(), -2.5)));

    if (event.getSource() == floor3) {
      timeline3.play();
    }

    // floor 2
    Timeline timeline2 = new Timeline();
    timeline2
        .getKeyFrames()
        .addAll(
            new KeyFrame(Duration.ZERO, new KeyValue(floor2.translateYProperty(), -2.5)),
            new KeyFrame(Duration.millis(1000), new KeyValue(floor2.translateYProperty(), -2.5)));

    if (event.getSource() == floor2) {
      timeline2.play();
    }

    // floor LL1
    Timeline timelineLL1 = new Timeline();
    timelineLL1
        .getKeyFrames()
        .addAll(
            new KeyFrame(Duration.ZERO, new KeyValue(floorLL1.translateYProperty(), -2.5)),
            new KeyFrame(Duration.millis(1000), new KeyValue(floorLL1.translateYProperty(), -2.5)));

    if (event.getSource() == floorLL1) {
      timelineLL1.play();
    }

    // floor LL2
    Timeline timelineLL2 = new Timeline();
    timelineLL2
        .getKeyFrames()
        .addAll(
            new KeyFrame(Duration.ZERO, new KeyValue(floorLL2.translateYProperty(), -2.5)),
            new KeyFrame(Duration.millis(1000), new KeyValue(floorLL2.translateYProperty(), -2.5)));

    if (event.getSource() == floorLL2) {
      timelineLL2.play();
    }
    displayAllFloorsCounts();
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
    cleanRecliner.setText(
            String.valueOf((reclinersPerFloor * numFloors) - dirtyReclinersArray[0]));
    dirtyBeds.setText(String.valueOf(dirtyBedsArray[0]));
    dirtyRecliner.setText(String.valueOf(dirtyReclinersArray[0]));
    dirtyXRay.setText(String.valueOf(dirtyXRaysArray[0]));
    dirtyPump.setText(String.valueOf(dirtyPumpsArray[0]));
  }

  public void displaySingleFloorCounts(int floorInt) {
    cleanPump.setText(String.valueOf(pumpsPerFloor - dirtyPumpsArray[floorInt]));
    cleanXRay.setText(String.valueOf(xraysPerFloor - dirtyXRaysArray[floorInt]));
    cleanBeds.setText(String.valueOf(bedsPerFloor - dirtyBedsArray[floorInt]));
    cleanRecliner.setText(
            String.valueOf(reclinersPerFloor - dirtyReclinersArray[floorInt]));
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
}
