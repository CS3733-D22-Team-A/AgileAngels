package edu.wpi.agileAngels.Controllers;

import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class HomeController {

  @FXML Button serviceButton, mapButton;

  AppController appController = AppController.getInstance();

  public HomeController() throws SQLException {}

  @FXML
  private void homeButton(ActionEvent event) {
    if (event.getSource() == serviceButton) {
      appController.loadPage("/edu/wpi/agileAngels/views/serviceRequest-view.fxml");
    } else if (event.getSource() == mapButton) {
      appController.loadPage("/edu/wpi/agileAngels/views/map-dashboard.fxml");
    }
  }

  @FXML
  public void goHome(ActionEvent event) {}

  @FXML
  public void menuItem(ActionEvent event) {}

  @FXML
  public void profile(ActionEvent event) {}

  @FXML
  public void closeApp(ActionEvent event) {}
}
