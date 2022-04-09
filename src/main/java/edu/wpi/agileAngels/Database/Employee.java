package edu.wpi.agileAngels.Database;

import java.util.ArrayList;

public class Employee {

  private String name;
  private String password;
  private final ArrayList<Request> requests;

  public Employee(String name, String password, ArrayList<Request> requests) {
    this.name = name;
    this.password = password;
    this.requests = requests;
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

  public ArrayList<Request> getRequests() {
    return this.requests;
  }

  public void addRequest(Request newReq) {
    this.requests.add(newReq);
  }

  public void removeRequest(Request oldReq) {
    this.requests.remove(oldReq);
  }
}
