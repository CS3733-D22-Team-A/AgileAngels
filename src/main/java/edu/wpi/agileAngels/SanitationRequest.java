package edu.wpi.agileAngels;

public class SanitationRequest extends Request {

  public SanitationRequest(
      String employeeName, String location, String type, String status, String description) {
    super(employeeName, location, type, status, description);
    setRequestType(5);
  }
}
