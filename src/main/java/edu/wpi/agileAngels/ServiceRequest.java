package edu.wpi.agileAngels;

public class ServiceRequest extends Request {
  private String employee;
  private String type;
  private String location;
  private String status;
  private String description;
  private int requestType;

  public ServiceRequest(
      String employeeName, String location, String type, String status, String description) {
    super(employeeName, location, type, status, description);
    setRequestType(1);
  }
}
