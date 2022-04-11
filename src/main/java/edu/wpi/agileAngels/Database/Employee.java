package edu.wpi.agileAngels.Database;

import java.util.ArrayList;

public class Employee {

  private String name;
  private String password;

  public Employee(String name, String password, ArrayList<Request> requests) {
    this.name = name;
    this.password = password;
  }

  public String getName() {
    return this.name;
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

}
