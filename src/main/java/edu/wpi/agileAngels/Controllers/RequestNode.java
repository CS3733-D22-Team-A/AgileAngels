package edu.wpi.agileAngels.Controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.agileAngels.Database.Location;
import edu.wpi.agileAngels.Database.Request;
import java.sql.SQLException;
import javafx.event.ActionEvent;

public class RequestNode {

  private Request request;
  private Location location;
  private RequestNodeManager requestNodeManager;
  private JFXButton button = new JFXButton();

  public RequestNode(Request request, RequestNodeManager requestNodeManager) throws SQLException {
    this.request = request;
    this.requestNodeManager = requestNodeManager;
    this.location = request.getLocation();

    button.setLayoutX((this.location.getXCoord() - 775) / 3.225);
    button.setLayoutY((this.location.getYCoord() - 320) / 3.232);
    button.setText(String.valueOf(request.getName().charAt(0)));
    button.setStyle("-fx-font-size: 8");
    button.setOnAction(
        (ActionEvent event2) -> {
          isClicked();
        });

    button.setStyle("-fx-background-color: #6a78fc; ");
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
