package edu.wpi.agileAngels.Controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.agileAngels.Database.Location;
import javafx.event.ActionEvent;

public class LocationNode {

  private Location location;
  private LocationNodeManager locationNodeManager;
  private JFXButton button = new JFXButton();

  private double fontSize = 10;

  public LocationNode(Location location, LocationNodeManager locationNodeManager) {
    this.location = location;
    this.locationNodeManager = locationNodeManager;

    button.setLayoutX((this.getXCoord() - 775) / 3.225);
    button.setLayoutY((this.getYCoord() - 320) / 3.232);
    button.setText(String.valueOf(location.getNodeType().charAt(0)));
    button.setStyle("-fx-font-size: 8");
    button.setOnAction(
        (ActionEvent event2) -> {
          isClicked();
        });
  }

  public void resizeButton(double scaleFactor) {
    // button.setStyle("-fx-font-size: 6");
  }

  public void resetLocation() {
    button.setLayoutX(this.getXCoord());
    button.setLayoutY(this.getYCoord());

    button.setText(String.valueOf(location.getNodeType().charAt(0)));
  }

  public void isClicked() {
    locationNodeManager.loadNode(this);
  }

  public Location getLocation() {
    return location;
  }

  public String getNodeID() {
    return location.getNodeID();
  }

  public String getName() {
    return location.getLongName();
  }

  public String getFloor() {
    return location.getFloor();
  }

  public Double getXCoord() {
    return location.getXCoord();
  }

  public Double getYCoord() {
    return location.getYCoord();
  }

  public String getNodeType() {
    return location.getNodeType();
  }

  public JFXButton getButton() {
    return button;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public void changeLocationXCoord(double x) {
    location.setXCoord(x);
  }

  public void changeLocationYCoord(double y) {
    location.setYCoord(y);
  }

  public void changeLocationType(String nodeType) {
    location.setNodeType(nodeType);
  }

  public void changeLocationName(String name) {
    location.setLongName(name);
  }
}
