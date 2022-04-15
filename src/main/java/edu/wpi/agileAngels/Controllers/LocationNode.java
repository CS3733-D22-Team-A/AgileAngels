package edu.wpi.agileAngels.Controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.agileAngels.Database.Location;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;

public class LocationNode {

  private Location location;
  private LocationNodeManager locationNodeManager;
  private JFXButton button = new JFXButton();

  private double fontSize = 10;

  double xOffset;
  double yOffset;
  double buttonX;
  double buttonY;
  Boolean dragged = false;

  public LocationNode(Location location, LocationNodeManager locationNodeManager) {
    this.location = location;
    this.locationNodeManager = locationNodeManager;

    // button.setLayoutX((this.getXCoord() - croppedMapXOffset) / (croppedMapWidth /
    // imagePaneWidth));
    button.setLayoutX(getPaneXfromcoords(this.getXCoord()));
    button.setLayoutY(getPaneYfromcoords(this.getYCoord()));
    button.setText(String.valueOf(location.getNodeType().charAt(0)));
    button.setStyle(
        "-fx-font-size: 12; -fx-background-radius: 0 8 8 8; -fx-background-color: rgba(73, 67, 112, 1); -fx-text-fill: white");
    button
        .hoverProperty()
        .addListener(
            l -> {
              button.setPrefSize(250, 50);
              button.setStyle(
                  "-fx-font-size: 15; -fx-background-color: rgba(73, 67, 112, 1); -fx-background-radius: 0 25 25 25; -fx-text-alignment: left; -fx-text-fill: white");
              button.setAlignment(Pos.CENTER_LEFT);
              button.setText(location.getLongName());
              button.setViewOrder(-1000);
            });

    button.setOnMouseExited(
        l -> {
          button.setPrefSize(8, 8);
          button.setStyle(
              "-fx-font-size: 12; -fx-background-color: rgba(73, 67, 112, 1) ;-fx-background-radius: 0 5 5 5; -fx-text-alignment: left; -fx-text-fill: white");
          button.setAlignment(Pos.CENTER);
          button.setText(String.valueOf(location.getNodeType().charAt(0)));
          button.setViewOrder(-100);
        });

    button.setOnAction(
        (ActionEvent event2) -> {
          isClicked();
        });

    button.setOnMousePressed(
        (MouseEvent mouseEvent) -> {
          xOffset = (button.getLayoutX() - mouseEvent.getSceneX());
          yOffset = (button.getLayoutY() - mouseEvent.getSceneY());

          buttonX = button.getLayoutX();
          buttonY = button.getLayoutY();
        });
    button.setOnMouseDragged(
        (MouseEvent mouseEvent) -> {
          button.setLayoutX(
              getPaneXfromcoords((locationNodeManager.getMapXCoordFromClick(mouseEvent))));

          button.setLayoutY(
              getPaneYfromcoords((locationNodeManager.getMapYCoordFromClick(mouseEvent))));
          dragged = true;
        });
    button.setOnMouseReleased(
        (MouseEvent mouseEvent) -> {
          if (dist(
                  buttonX,
                  locationNodeManager.getMapXCoordFromClick(mouseEvent),
                  buttonY,
                  locationNodeManager.getMapYCoordFromClick(mouseEvent))
              < 20) {
            button.setLayoutX(buttonX);
            button.setLayoutY(buttonY);
          } else {
            dragged = false;
            locationNodeManager.setDraggedNodeCoords(mouseEvent);
            System.out.println("hello");
          }
        });
  }

  private double dist(double x1, double x2, double y1, double y2) {
    return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
  }

  public double getPaneXfromcoords(double x) {
    return ((x - locationNodeManager.getCroppedMapXOffset())
        / (locationNodeManager.getCroppedMapWidth() / locationNodeManager.getImagePaneWidth()));
  }

  public double getPaneYfromcoords(double y) {
    return ((y - locationNodeManager.getCroppedMapYOffset())
        / (locationNodeManager.getCroppedMapWidth() / locationNodeManager.getImagePaneWidth()));
  }

  public void resetLocation() {
    button.setLayoutX(getPaneXfromcoords(this.getXCoord()));
    button.setLayoutY(getPaneYfromcoords(this.getYCoord()));

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
