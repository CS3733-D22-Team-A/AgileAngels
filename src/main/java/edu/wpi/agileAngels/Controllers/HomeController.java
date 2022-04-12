package edu.wpi.agileAngels.Controllers;

import edu.wpi.agileAngels.AppController;
import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class HomeController {

  @FXML Button serviceButton, mapButton;

  AppController appController = AppController.getInstance();

  public HomeController() throws SQLException {}

  @FXML
  private void homeButton(ActionEvent event) throws IOException {
    if (event.getSource() == serviceButton) {
      appController.loadPage("../views/serviceRequest-view.fxml");
    } else if (event.getSource() == mapButton) {
      appController.loadPage("../views/dashboard-view.fxml");
    }
  }

  public void goHome(ActionEvent event) {}

  public void menuItem(ActionEvent event) {}

  public void profile(ActionEvent event) {}

  public void closeApp(ActionEvent event) {}
}
