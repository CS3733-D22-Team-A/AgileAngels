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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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

  AppController appController = AppController.getInstance();
  ArrayList<Pane> panes = new ArrayList<>();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
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

  //  public void swapFloorDash(MouseEvent event) {
  //
  //    panes.add(stackDash1);
  //    panes.add(stackDash2);
  //    panes.add(stackDash3);
  //    panes.add(stackDash4);
  //    panes.add(stackDash5);
  //    panes.add(stackDashL1);
  //    panes.add(stackDashL2);
  //
  //    if (event.getSource() == dash1) {
  //      for (Pane pane : panes) {
  //        if (pane == stackDash1) {
  //          pane.setVisible(true);
  //        } else {
  //          pane.setVisible(false);
  //        }
  //      }
  //    }
  //
  //    if (event.getSource() == dash2) {
  //      for (Pane pane : panes) {
  //        if (pane == stackDash2) {
  //          pane.setVisible(true);
  //        } else {
  //          pane.setVisible(false);
  //        }
  //      }
  //    }
  //
  //    if (event.getSource() == dash3) {
  //      for (Pane pane : panes) {
  //        if (pane == stackDash3) {
  //          pane.setVisible(true);
  //        } else {
  //          pane.setVisible(false);
  //        }
  //      }
  //    }
  //
  //    if (event.getSource() == dash4) {
  //      for (Pane pane : panes) {
  //        if (pane == stackDash4) {
  //          pane.setVisible(true);
  //        } else {
  //          pane.setVisible(false);
  //        }
  //      }
  //    }
  //
  //    if (event.getSource() == dash5) {
  //      for (Pane pane : panes) {
  //        if (pane == stackDash5) {
  //          pane.setVisible(true);
  //        } else {
  //          pane.setVisible(false);
  //        }
  //      }
  //    }
  //
  //    if (event.getSource() == dashL1) {
  //      for (Pane pane : panes) {
  //        if (pane == stackDashL1) {
  //          pane.setVisible(true);
  //        } else {
  //          pane.setVisible(false);
  //        }
  //      }
  //    }
  //
  //    if (event.getSource() == dashL2) {
  //      for (Pane pane : panes) {
  //        if (pane == stackDashL2) {
  //          pane.setVisible(true);
  //        } else {
  //          pane.setVisible(false);
  //        }
  //      }
  //    }
  //  }

  /* public void unhover(MouseEvent event) {
      stackDash1.setVisible(false);
      stackDash2.setVisible(false);
      stackDash3.setVisible(false);
      stackDash4.setVisible(false);
      stackDash5.setVisible(false);
      stackDashL1.setVisible(false);
      stackDashL2.setVisible(false);
    }
  */
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
