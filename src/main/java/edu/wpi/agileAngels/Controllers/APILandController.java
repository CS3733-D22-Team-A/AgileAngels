package edu.wpi.agileAngels.Controllers;

import java.sql.SQLException;

public class APILandController {

  AppController appController = AppController.getInstance();

  public APILandController() throws SQLException {}

  public void loadCredits() {
    appController.loadPage("/edu/wpi/agileAngels/views/credits-view.fxml");
  }
}
