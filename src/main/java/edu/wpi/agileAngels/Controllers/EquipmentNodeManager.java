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
    equipDAO.readCSV();
    ArrayList<MedicalEquip> equipList = new ArrayList<>(equipDAO.getAllMedicalEquipment().values());
    System.out.println(equipList);
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
    System.out.println("manager makeClean");
    MedicalEquip equip = node.getMedEquip();
    if (!equip.isClean()) {
      equipDAO.updateMedicalCleanliness(equip, true);
      if (equip.getType().equals("XRayMachine") || equip.getType().equals("InfusionPump")) {
        System.out.println("manager if");
        equipDAO.updateEquipmentLocation(equip, locationsHash.get("ASTOR00103"));
      } else {
        System.out.println("manager else");
        equipDAO.updateEquipmentLocation(equip, locationsHash.get("ASTOR00303"));
      }
    }
  }
}
