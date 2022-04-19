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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

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
    appController.displayAlert();
  }

  @FXML
  public void bigFloor(MouseEvent event) {

    if (event.getSource() == floor4) {
      floor4.setTranslateY(25);
    }
  }

  public void unhover(MouseEvent event) {

    if (event.getSource() == floor4) {
      floor4.setTranslateY(-25);
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
