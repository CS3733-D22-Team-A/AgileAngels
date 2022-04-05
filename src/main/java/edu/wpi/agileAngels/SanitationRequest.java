package edu.wpi.agileAngels;

public class SanitationRequest extends Request {

  public SanitationRequest(
      String name,
      String employeeName,
      String location,
      String type,
      String status,
      String description) {
    super(name, employeeName, location, type, status, description);
    setRequestType(5);
  }
}
