package edu.wpi.agileAngels;

public class LabRequest extends Request {

  public LabRequest(
      String employeeName, String location, String type, String status, String description) {
    super(employeeName, location, type, status, description);
    setRequestType(4);
  }
}
