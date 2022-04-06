package edu.wpi.agileAngels;
// Mother class for all requests
// TODO find out main attributes for this class and other request classes
public class Request {
  private String name;
  private String employeeName;
  private String location;
  private String type; // TODO change name of this
  private int requestType; // which request it is based on int value
  private String status;
  private String description;
  // constructor
  public Request(
      String Name,
      String employeeName,
      String location,
      String type,
      String status,
      String description) {
    this.name = Name;
    this.employeeName = employeeName;
    this.location = location;
    this.type = type;
    this.status = status;
    this.description = description;
  }

  public void setRequestType(int requestType) {
    this.requestType = requestType;
  }

  public String getStatus() {
    return status;
  }

  public int getRequestType() {
    return requestType;
  }

  public String getDescription() {
    return description;
  }

  public String getEmployee() {
    return employeeName;
  }

  public String getLocation() {
    return location;
  }

  public String getType() {
    return type;
  }

  public void setEmployee(String employeeName) {
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

  public void setDescription(String description) {
    this.description = description;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }
}
