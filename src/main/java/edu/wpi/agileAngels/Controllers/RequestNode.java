package edu.wpi.agileAngels.Controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.agileAngels.Database.Location;
import edu.wpi.agileAngels.Database.LocationDAOImpl;
import edu.wpi.agileAngels.Database.Request;
import java.util.HashMap;
import javafx.event.ActionEvent;

public class RequestNode {

  private Request request;
  private Location location;
  private RequestNodeManager requestNodeManager;
  private JFXButton button = new JFXButton();
  private LocationDAOImpl locationDAO = new LocationDAOImpl();
  HashMap<String, Location> locationsHash = new HashMap<>();

  public RequestNode(Request request, RequestNodeManager requestNodeManager) {
    this.request = request;
    this.requestNodeManager = requestNodeManager;

    this.locationsHash = locationDAO.getAllLocations();
    this.location = locationsHash.get(this.request.getLocation());

    button.setLayoutX((this.location.getXCoord() - 800) / 5);
    button.setLayoutY((this.location.getYCoord() - 350) / 5);
    button.setText(String.valueOf(request.getRequestType()));
    button.setOnAction(
        (ActionEvent event2) -> {
          isClicked();
        });

    // set the circle color to coordinate with the node type
    // if(this.getNodeType() == "Test") {
    button.setId("blue"); // this ID will be used in the CSS file to style the button
    // }

  }

  public void resetLocation() {
    button.setLayoutX((this.location.getXCoord() - 800) / 5);
    button.setLayoutY((this.location.getYCoord() - 350) / 5);
    button.setText(String.valueOf(request.getRequestType()));
  }

  public void isClicked() {
    requestNodeManager.loadNode(this);
  }

  public Location getLocation() {
    return this.location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public String getName() {
    return request.getName();
  }

  public String getEmployee() {
    return request.getEmployee();
  }

  public String getStatus() {
    return request.getStatus();
  }

  public int getRequestType() {
    return request.getRequestType();
  }

  public JFXButton getButton() {
    return button;
  }

  public void changeLocation(String locationID) {
    this.request.setLocation(locationID);
    this.location = locationsHash.get(locationID);
  }

  public void changeEmployee(String employee) {
    this.request.setEmployee(employee);
  }

  public void changeStatus(String status) {
    this.request.setStatus(status);
  }
}
