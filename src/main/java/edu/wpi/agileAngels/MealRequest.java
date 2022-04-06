package edu.wpi.agileAngels;

public class MealRequest extends Request {

  public MealRequest(
      String name,
      String employeeName,
      String location,
      String type,
      String status,
      String description) {
    super(name, employeeName, location, type, status, description);
    setRequestType(3);
  }

  public String getDietryRestrictions() {
    return getDescription();
  }
}
