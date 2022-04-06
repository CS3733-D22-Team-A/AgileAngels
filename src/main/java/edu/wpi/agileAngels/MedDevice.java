package edu.wpi.agileAngels;

public class MedDevice extends Request {
  private String name;
  private String available;

  public MedDevice(
      String name,
      String available,
      String type,
      String location,
      String employeeName,
      String status,
      String description) {

    super(name, employeeName, location, type, status, description);
    setRequestType(0);
    this.available = available;
  }

  public String getName() {
    return name;
  }

  public String getAvailable() {
    return available;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAvailable(String available) {
    this.available = available;
  }
}
