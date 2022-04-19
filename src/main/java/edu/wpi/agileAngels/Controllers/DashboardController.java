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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.util.Duration;

public class DashboardController implements Initializable, PropertyChangeListener {

  @FXML ScrollPane NODscroll, SRscroll;
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

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // This is to get rid of the scroll bars on the dashboard.
    NODscroll.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
    NODscroll.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
    SRscroll.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
    SRscroll.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);

    appController.addPropertyChangeListener(this);

    try {
      this.updateCleanDirty();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    String changeType = evt.getPropertyName();
    int newValue = (int) evt.getNewValue();
    if (changeType.equals("dirtyBedsAll")) {
      dirtyBeds.setText(String.valueOf(newValue));
      cleanBeds.setText(String.valueOf(10 - newValue));
    } else if (changeType.equals("dirtyReclinersAll")) {
      dirtyRecliner.setText(String.valueOf(newValue));
      cleanRecliner.setText(String.valueOf(5 - newValue));
    } else if (changeType.equals("dirtyInfusionPumpsAll")) {
      dirtyPump.setText(String.valueOf(newValue));
      cleanPump.setText(String.valueOf(15 - newValue));
    } else if (changeType.equals("dirtyXRaysAll")) {
      dirtyXRay.setText(String.valueOf(newValue));
      cleanXRay.setText(String.valueOf(1 - newValue));
    }
  }

  @FXML
  public void bigFloor(MouseEvent event) {

    Timeline timeline5 = new Timeline();
    timeline5
        .getKeyFrames()
        .addAll(
            new KeyFrame(
                Duration.ZERO,
                //                new KeyValue(floor5.translateXProperty(), 100),
                new KeyValue(floor4.translateYProperty(), 10)),
            new KeyFrame(
                Duration.millis(1000),
                //               new KeyValue(floor5.translateXProperty(), 100),
                new KeyValue(floor4.translateYProperty(), 10)));

    if (event.getSource() == floor4) {
      timeline5.play();
    }

    //    if (event.getSource() == floor4) {
    //      floor4.setTranslateY(25);
    //    }
  }

  public void unhover(MouseEvent event) {

    Timeline timeline5 = new Timeline();
    Scale scale = new Scale();
    scale.setPivotX(50);
    scale.setPivotY(50);
    floor4.getTransforms().add(scale);

    //    timeline5
    //        .getKeyFrames()
    //        .addAll(
    //            new KeyFrame(
    //                Duration.ZERO,
    //                //                new KeyValue(floor5.translateXProperty(), 100),
    //                new KeyValue(floor4.getTransforms()., -2.5)),
    //            new KeyFrame(
    //                Duration.millis(1000),
    //                //               new KeyValue(floor5.translateXProperty(), 100),
    //                new KeyValue(floor4.translateYProperty(), -2.5)));
    //
    if (event.getSource() == floor4) {
      //      timeline5.play();
      floor4.getTransforms().add(scale);
    }
  }

  /*  public void loadFloorMap(ActionEvent event) throws IOException {

    if (event.getSource() == dash1) {
      appController.loadPage("/edu/wpi/agileAngels/views/map-view.fxml");
    }

    if (event.getSource() == dash2) {
      appController.loadPage("/edu/wpi/agileAngels/views/map-view.fxml");
    }

    if (event.getSource() == dash3) {
      appController.loadPage("/edu/wpi/agileAngels/views/map-view.fxml");
    }

    if (event.getSource() == dash4) {
      appController.loadPage("/edu/wpi/agileAngels/views/map-view.fxml");
    }

    if (event.getSource() == dash5) {
      appController.loadPage("/edu/wpi/agileAngels/views/map-view.fxml");
    }

    if (event.getSource() == dashL1) {
      appController.loadPage("/edu/wpi/agileAngels/views/map-view.fxml");
    }

    if (event.getSource() == dashL2) {
      appController.loadPage("/edu/wpi/agileAngels/views/map-view.fxml");
    }
  }*/

  @FXML
  public void updateCleanDirty() throws SQLException {

    MedEquipImpl equipDAO = MedEquipImpl.getInstance();
    HashMap<String, MedicalEquip> equipHash;
    equipHash = equipDAO.getAllMedicalEquipment();

    int pumpCount = 0;
    int bedCount = 0;
    int XRayCount = 0;
    int recCount = 0;
    int pumpCountdirty = 0;
    int bedCountdirty = 0;
    int XRayCountdirty = 0;
    int recCountdirty = 0;

    if (equipHash == null) {
      System.out.println("It's null loser");
    } else {

      for (Map.Entry<String, MedicalEquip> entry : equipHash.entrySet()) {
        MedicalEquip equip = entry.getValue();
        if (equip.getType().equals("InfusionPump") && equip.isClean()) {
          pumpCount += 1;
        } else if (equip.getType().equals("InfusionPump") && !equip.isClean()) {
          pumpCountdirty += 1;
        } else if (equip.getType().equals("XRayMachine") && equip.isClean()) {
          XRayCount += 1;
        } else if (equip.getType().equals("XRayMachine") && !equip.isClean()) {
          XRayCountdirty += 1;
        } else if (equip.getType().equals("Bed") && equip.isClean()) {
          bedCount += 1;
        } else if (equip.getType().equals("Bed") && !equip.isClean()) {
          bedCountdirty += 1;
        } else if (equip.getType().equals("Recliner") && equip.isClean()) {
          recCount += 1;
        } else if (equip.getType().equals("Recliner") && !equip.isClean()) {
          recCountdirty += 1;
        }
      }

      cleanPump.setText(String.valueOf(pumpCount));
      cleanXRay.setText(String.valueOf(XRayCount));
      cleanBeds.setText(String.valueOf(bedCount));
      cleanRecliner.setText(String.valueOf(recCount));
      dirtyBeds.setText(String.valueOf(bedCountdirty));
      dirtyRecliner.setText(String.valueOf(recCountdirty));
      dirtyXRay.setText(String.valueOf(XRayCountdirty));
      dirtyPump.setText(String.valueOf(pumpCountdirty));
    }
  }
}
