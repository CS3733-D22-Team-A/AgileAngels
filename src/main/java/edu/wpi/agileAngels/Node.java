package edu.wpi.agileAngels;

import java.io.IOException;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

public class Node {
  private final MapsManager maps = MapsManager.getMapsManager();
  private Location location;
  private Circle circle;

  public Node(Location location) {
    this.location = location;
  }

  public Circle createCircle() {
    circle = new Circle();
    circle.setOnMousePressed(
        (MouseEvent event) -> {
          if (event.isSecondaryButtonDown()) {
            delete();
          }
        });
    return circle;
  }

  public void delete() {
    try {
      maps.deleteLocation(this);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public Circle getCircle() {
    return circle;
  }

  public void setCircle(Circle circle) {
    this.circle = circle;
  }
}
