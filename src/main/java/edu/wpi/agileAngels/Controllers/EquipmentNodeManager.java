package edu.wpi.agileAngels.Controllers;

import edu.wpi.agileAngels.Database.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class EquipmentNodeManager {
    private MapsController mapsController;
    private MedEquipImpl equipDAO = MedEquipImpl.getInstance();
    private HashMap<String, EquipmentNode> nodes = new HashMap<>();

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
}
