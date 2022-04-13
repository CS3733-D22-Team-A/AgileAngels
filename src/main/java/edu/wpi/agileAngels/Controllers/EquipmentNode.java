package edu.wpi.agileAngels.Controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.agileAngels.Database.Location;
import edu.wpi.agileAngels.Database.LocationDAOImpl;
import edu.wpi.agileAngels.Database.MedicalEquip;
import java.sql.SQLException;
import java.util.HashMap;
import javafx.event.ActionEvent;

public class EquipmentNode {

  private MedicalEquip medEquip;
  private Location location;
  private EquipmentNodeManager equipmentNodeManager;
  private JFXButton button = new JFXButton();
  private LocationDAOImpl locDAO = LocationDAOImpl.getInstance();
  HashMap<String, Location> locationsHash = locDAO.getAllLocations();

  public EquipmentNode(MedicalEquip medEquip, EquipmentNodeManager equipmentNodeManager)
      throws SQLException {
    this.medEquip = medEquip;
    this.equipmentNodeManager = equipmentNodeManager;
    this.location = medEquip.getLocation();

    button.setLayoutX((this.location.getXCoord() - 800) / 5);
    button.setLayoutY((this.location.getYCoord() - 350) / 5);
    button.setText(String.valueOf(medEquip.getType().charAt(0)));
    button.setOnAction(
        (ActionEvent event2) -> {
          isClicked();
        });

    button.setStyle("-fx-background-color: #797979; ");
  }

  public void isClicked() {
    equipmentNodeManager.loadNode(this);
  }

  public void resetLocation() {
    double x = ((this.location.getXCoord() - 800) / 5);
    double y = ((this.location.getYCoord() - 350) / 5);
    button.setLayoutX(x);
    button.setLayoutY(y);
  }

  public MedicalEquip getMedEquip() {
    return medEquip;
  }

  public JFXButton getButton() {
    return this.button;
  }

  public Location getLocation() {
    return this.location;
  }

  public String getFloor() {
    return this.location.getFloor();
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public String getID() {
    String str = medEquip.getID() + " " + medEquip.getType();
    return str;
  }

  public String getClean() {
    String str = "Dirty";
    if (medEquip.isClean()) {
      str = "Clean";
    }
    return str;
  }

  public String getStatus() {
    return medEquip.getStatus();
  }
}
