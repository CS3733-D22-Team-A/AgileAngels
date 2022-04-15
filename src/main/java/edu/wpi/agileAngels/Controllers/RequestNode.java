package edu.wpi.agileAngels.Controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.agileAngels.Database.Location;
import edu.wpi.agileAngels.Database.Request;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;

public class RequestNode {

  private Request request;
  private Location location;
  private RequestNodeManager requestNodeManager;
  private JFXButton button = new JFXButton();

  double xOffset;
  double yOffset;
  double buttonX;
  double buttonY;
  Boolean dragged = false;

  public RequestNode(Request request, RequestNodeManager requestNodeManager) throws SQLException {
    this.request = request;
    this.requestNodeManager = requestNodeManager;
    this.location = request.getLocation();

    button.setLayoutX((this.location.getXCoord() - 775) / 3.225);
    button.setLayoutY((this.location.getYCoord() - 320) / 3.232);
    button.setText(String.valueOf(request.getName().charAt(0)));
    button.setOnAction(
        (ActionEvent event2) -> {
          isClicked();
        });

    button.setStyle("-fx-background-color: #6a78fc;-fx-font-size: 8");

    button.setStyle(
        "-fx-font-size: 12; -fx-background-radius: 0 8 8 8; -fx-background-color: rgba(129, 239, 219, 1); -fx-text-fill: white");
    button
        .hoverProperty()
        .addListener(
            l -> {
              button.setPrefSize(250, 50);
              button.setStyle(
                  "-fx-font-size: 15; -fx-background-color: rgba(129, 239, 219, 1); -fx-background-radius: 0 25 25 25; -fx-text-alignment: left; -fx-text-fill: white");
              button.setAlignment(Pos.CENTER_LEFT);
              button.setText(location.getLongName());
              button.setViewOrder(-1000);
            });

    button.setOnMouseExited(
        l -> {
          button.setPrefSize(8, 8);
          button.setStyle(
              "-fx-font-size: 12; -fx-background-color: rgba(129, 239, 219, 1) ;-fx-background-radius: 0 5 5 5; -fx-text-alignment: left; -fx-text-fill: white");
          button.setAlignment(Pos.CENTER);
          button.setText(String.valueOf(location.getNodeType().charAt(0)));
          button.setViewOrder(-100);
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
              getPaneXfromcoords((requestNodeManager.getMapXCoordFromClick(mouseEvent))));

          button.setLayoutY(
              getPaneYfromcoords((requestNodeManager.getMapYCoordFromClick(mouseEvent))));
          dragged = true;
        });
    button.setOnMouseReleased(
        (MouseEvent mouseEvent) -> {
          if (dist(
                  buttonX,
                  requestNodeManager.getMapXCoordFromClick(mouseEvent),
                  buttonY,
                  requestNodeManager.getMapYCoordFromClick(mouseEvent))
              < 20) {
            button.setLayoutX(buttonX);
            button.setLayoutY(buttonY);
          } else {
            dragged = false;
            requestNodeManager.setDraggedNodeCoords(mouseEvent);
            System.out.println("hello");
          }
        });
  }

  private double dist(double x1, double x2, double y1, double y2) {
    return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
  }

  public double getPaneXfromcoords(double x) {
    return ((x - requestNodeManager.getCroppedMapXOffset())
        / (requestNodeManager.getCroppedMapWidth() / requestNodeManager.getImagePaneWidth()));
  }

  public double getPaneYfromcoords(double y) {
    return ((y - requestNodeManager.getCroppedMapYOffset())
        / (requestNodeManager.getCroppedMapWidth() / requestNodeManager.getImagePaneWidth()));
  }

  public void resetLocation() {
    button.setLayoutX(getPaneXfromcoords(location.getXCoord()));
    button.setLayoutY(getPaneYfromcoords(location.getYCoord()));

    button.setText(String.valueOf(location.getNodeType().charAt(0)));
  }

  public void isClicked() {
    requestNodeManager.loadNode(this);
  }

  public Location getLocation() {
    return this.location;
  }

  public String getFloor() {
    return this.location.getFloor();
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public String getName() {
    return request.getName();
  }

  public String getEmployee() {
    return request.getEmployee().getName();
  }

  public String getStatus() {
    return request.getStatus();
  }

  public JFXButton getButton() {
    return button;
  }
}
