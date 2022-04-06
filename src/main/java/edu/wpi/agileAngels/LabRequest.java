package edu.wpi.agileAngels;

public class LabRequest extends Request {
  private String available;

  public LabRequest(
      String name,
      String available,
      String employeeName,
      String location,
      String type,
      String status,
      String description) {
    super(name, employeeName, location, type, status, description);
    this.available = available;
    setRequestType(4);
  }

  public String getAvailable() {
    return this.available;
  }
}
