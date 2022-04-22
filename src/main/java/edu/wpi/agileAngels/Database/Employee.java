package edu.wpi.agileAngels.Database;

public class Employee {

  private String name;
  private String password;
  private String floorOnDuty;

  public Employee(String name, String password, String floorOnDuty) {
    this.name = name;
    this.password = password;
    this.floorOnDuty = floorOnDuty;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String pass) {
    this.password = pass;
  }

  public String getFloorOnDuty() {
    return floorOnDuty;
  }

  public void setFloorOnDuty(String floorOnDuty) {
    this.floorOnDuty = floorOnDuty;
  }

  @Override
  public String toString() {
    return getName();
  }
}
