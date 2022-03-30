package edu.wpi.agileAngels;

import java.util.HashMap;

public interface MedDAO {

  public HashMap<String, MedDevice> getAllMedicalEquipmentRequests();

  public void updateName(MedDevice medDevice, String newName);

  public void updateAvailability(MedDevice medDevice, String newAvailability);

  public void updateType(MedDevice medDevice, String newType);

  public void updateLocation(MedDevice medDevice, String newLocation);

  public void updateEmployee(MedDevice medDevice, String newEmployee);

  public void updateStatus(MedDevice medDevice, String newStatus);

  public void deleteMed(MedDevice medDevice);

  public void addMed(MedDevice medDevice);
}
