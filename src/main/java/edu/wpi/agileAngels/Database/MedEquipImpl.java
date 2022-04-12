package edu.wpi.agileAngels.Database;

import java.sql.SQLException;
import java.util.HashMap;

public class MedEquipImpl implements MedEquipDAO {
  private static MedEquipImpl MedEquipDAO = null;
  private HashMap<String, MedicalEquip> MedEquipData;

  public MedEquipImpl(HashMap<String, MedicalEquip> data) {
    this.MedEquipData = data;
  }

  @Override
  public HashMap<String, MedicalEquip> getAllMedicalEquipment() {
    return MedEquipData;
  }


  public static MedEquipImpl getInstance() {
    if (MedEquipDAO == null) {
      HashMap<String, MedicalEquip> Data = new HashMap<>();
      MedEquipDAO = new MedEquipImpl(Data);
    }
    return MedEquipDAO;
  }


  @Override
  public void addEquipment(MedicalEquip medicalEquip) {
    MedEquipData.put(medicalEquip.getID(), medicalEquip);
    System.out.println("MedicalEquipment " + medicalEquip.getID() + " is added into the database.");
  }

  @Override
  public void removeEquipment(MedicalEquip medicalEquip) {
    MedEquipData.remove(medicalEquip.getID());
    System.out.println(
        "MedicalEquipment " + medicalEquip.getID() + " is removed from the database.");
  }

  @Override
  public void updateEquipmentLocation(MedicalEquip medicalEquip, String location) {
    medicalEquip.setLocation(location);
    System.out.println("MedicalEquipment" + medicalEquip.getID() + " location is updated");
  }

  @Override
  public void updateMedicalCleanliness(MedicalEquip medicalEquip, Boolean clean) {
    medicalEquip.setClean(clean);
    System.out.println("MedicalEquipment" + medicalEquip.isClean() + " is clean");
  }
}
