package edu.wpi.agileAngels;

import java.util.ArrayList;

public class Employee {

  private String name;
  private String username;
  private String password;
  private ArrayList<Request> requests;

  public Employee(String name, String username, String password, ArrayList<Request> requests) {
    this.name = name;
    this.username = username;
    this.password = password;
    this.requests = requests;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String newUN) {
    this.username = newUN;
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
