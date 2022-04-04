package edu.wpi.agileAngels;

public class MedDevice extends Request {
  private String name;
  private String available;
  private String type;
  private String location;
  private String employee;
  private String status;
  private String description;
  private int requestType;

  public MedDevice(
      String name,
      String available,
      String type,
      String location,
      String employee,
      String status,
      String description) {
    this.name = name;
    this.available = available;
    this.type = type;
    this.location = location;
    this.employee = employee;
    this.status = status;
    this.description = description;
    this.requestType = 0;
  }

  public String getName() {
    return name;
  }

  public String getAvailable() {
    return available;
  }

  public String getType() {
    return type;
  }

  public String getLocation() {
    return location;
  }

  public String getEmployee() {
    return employee;
  }

  public String getStatus() {
    return status;
  }

  public String getDescription() {
    return description;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAvailable(String available) {
    this.available = available;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public void setEmployee(String employee) {
    this.employee = employee;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
