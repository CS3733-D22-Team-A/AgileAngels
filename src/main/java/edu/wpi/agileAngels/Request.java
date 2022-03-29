package edu.wpi.agileAngels;

public class Request {
  private String employeeName;
  private String location;
  private String type;

  public Request(String employeeName, String location, String type) {
    this.employeeName = employeeName;
    this.location = location;
    this.type = type;
  }

  public String getEmployeeName() {
    return employeeName;
  }

  public String getLocation() {
    return location;
  }

  public String getType() {
    return type;
  }

  public void setEmployeeName(String employeeName) {
    this.employeeName = employeeName;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public void setType(String type) {
    this.type = type;
  }
}
