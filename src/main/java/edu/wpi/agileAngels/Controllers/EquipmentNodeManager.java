package edu.wpi.agileAngels.Controllers;

import edu.wpi.agileAngels.Database.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class EquipmentNodeManager {
  private MapsController mapsController;
  private MedEquipImpl equipDAO = MedEquipImpl.getInstance();
  private HashMap<String, EquipmentNode> nodes = new HashMap<>();
  private LocationDAOImpl locDAO = LocationDAOImpl.getInstance();
  HashMap<String, Location> locationsHash = locDAO.getAllLocations();

  public EquipmentNodeManager(MapsController mapsController) throws SQLException {
    this.mapsController = mapsController;
  }

  // gets all equipment from the DB and creates nodes from them
  void createNodesFromDB() throws SQLException {
    ArrayList<MedicalEquip> equipList = new ArrayList<>(equipDAO.getAllMedicalEquipment().values());
    for (MedicalEquip equip : equipList) {
      mapsController.displayEquipmentNode(addNode(equip));
    }
  }

  EquipmentNode addNode(MedicalEquip medEquip) throws SQLException {
    EquipmentNode equipNode = new EquipmentNode(medEquip, this);
    nodes.put(equipNode.getID(), equipNode);
    return equipNode;
  }

  // gets called on button press and gets the node data
  void loadNode(EquipmentNode equipNode) {
    mapsController.populateEquipmentNodeData(equipNode);
  }

  void makeClean(EquipmentNode node) {
    MedicalEquip medEquip = node.getMedEquip();
    if (!medEquip.isClean()) {
      equipDAO.updateMedicalCleanliness(medEquip, true);
      if (medEquip.getType().equals("XRayMachine") || medEquip.getType().equals("InfusionPump")) {
        if (medEquip.getLocation().getFloor().equals("3")) {
          equipDAO.updateEquipmentLocation(medEquip, locationsHash.get("ASTOR00103"));
          node.setLocation(locationsHash.get("ASTOR00103"));
        } else if (medEquip.getLocation().getFloor().equals("4")) {
          equipDAO.updateEquipmentLocation(medEquip, locationsHash.get("ASTOR00104"));
          node.setLocation(locationsHash.get("ASTOR00104"));
        } else if (medEquip.getLocation().getFloor().equals("5")) {
          equipDAO.updateEquipmentLocation(medEquip, locationsHash.get("ASTOR00105"));
          node.setLocation(locationsHash.get("ASTOR00105"));
        }
      } else {
        if (medEquip.getLocation().getFloor().equals("3")) {
          equipDAO.updateEquipmentLocation(medEquip, locationsHash.get("ASTOR00303"));
          node.setLocation(locationsHash.get("ASTOR00303"));
        } else if (medEquip.getLocation().getFloor().equals("4")) {
          equipDAO.updateEquipmentLocation(medEquip, locationsHash.get("ASTOR00304"));
          node.setLocation(locationsHash.get("ASTOR00304"));
        } else if (medEquip.getLocation().getFloor().equals("5")) {
          equipDAO.updateEquipmentLocation(medEquip, locationsHash.get("ASTOR00305"));
          node.setLocation(locationsHash.get("ASTOR00305"));
        }
      }
    }
  }
}
