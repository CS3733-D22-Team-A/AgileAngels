package edu.wpi.agileAngels;

import java.util.HashMap;

public interface MedEquipDAO {

  public HashMap<String, MedicalEquip> getAllMedicalEquipment();

  public void addEquipment(MedicalEquip medicalEquip);

  public void removeEquipment(MedicalEquip medicalEquip);

  public void updateMedicalCleanliness(MedicalEquip medicalEquip, Boolean clean);

  public void updateEquipmentLocation(MedicalEquip medicalEquip, String location);
}
