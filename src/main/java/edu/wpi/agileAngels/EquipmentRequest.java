package edu.wpi.agileAngels;

public class EquipmentRequest extends Request {

  public EquipmentRequest(
      String name,
      String employeeName,
      String location,
      String type,
      String status,
      String description) {
    super(name, employeeName, location, type, status, description);
  }
}
