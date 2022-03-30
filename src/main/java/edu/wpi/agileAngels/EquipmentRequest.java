package edu.wpi.agileAngels;

public class EquipmentRequest extends Request {
  private String name;
  private String available;
  private String status;
  private String description;

  public EquipmentRequest(
      String employeeName,
      String location,
      String type,
      String name,
      String status,
      String available,
      String description) {
    super(employeeName, location, type);
    this.name = name;
    this.available = available;
    this.status = status;
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAvailable() {
    return available;
  }

  public void setAvailable(String available) {
    this.available = available;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
