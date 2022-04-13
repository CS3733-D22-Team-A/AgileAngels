package edu.wpi.agileAngels.Database;

public class Employee {

  private String name;
  private String password;

  public Employee(String name, String password) {
    this.name = name;
    this.password = password;
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

  @Override
  public String toString() {
    return getName();
  }
}
