package edu.wpi.agileAngels.Database;

import edu.wpi.agileAngels.Adb;
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
    Adb.addMedicalEquipment(medicalEquip);
    System.out.println("MedicalEquipment " + medicalEquip.getID() + " is added into the database.");
  }

  @Override
  public void removeEquipment(MedicalEquip medicalEquip) {
    MedEquipData.remove(medicalEquip.getID());
    Adb.removeMedicalEquipment(medicalEquip.getID());
    System.out.println(
        "MedicalEquipment " + medicalEquip.getID() + " is removed from the database.");
  }

  @Override
  public void updateEquipmentLocation(MedicalEquip medicalEquip, Location location) {
    medicalEquip.setLocation(location);
    Adb.updateMedicalEquipment(medicalEquip);
    System.out.println("MedicalEquipment" + medicalEquip.getID() + " location is updated");
  }

  @Override
  public void updateStatus(MedicalEquip medicalEquip, String statusIn) {
    medicalEquip.setStatus(statusIn);
    Adb.updateMedicalEquipment(medicalEquip);
  }

  @Override
  public void updateMedicalCleanliness(MedicalEquip medicalEquip, Boolean clean) {
    medicalEquip.setClean(clean);
    Adb.updateMedicalEquipment(medicalEquip);
    System.out.println("MedicalEquipment" + medicalEquip.isClean() + " is clean");
  }
}
