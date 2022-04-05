package edu.wpi.agileAngels;

public class LabRequest extends Request {

  public LabRequest(
      String name,
      String employeeName,
      String location,
      String type,
      String status,
      String description) {
    super(name, employeeName, location, type, status, description);
    setRequestType(4);
  }
}
