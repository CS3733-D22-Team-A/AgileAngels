package edu.wpi.agileAngels;

public class Request {
  private String employeeName;
  private String location;
  private String type;
  private int requestType;
  private String status;

  public Request(String employeeName, String location, String type, String status) {
    this.employeeName = employeeName;
    this.location = location;
    this.type = type;
    this.status = status;
  }

  public Request() {}

  public String getStatus() {
    return status;
  }

  public int getRequestType() {
    return requestType;
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

  public void setStatus(String status) {
    this.status = status;
  }
}
