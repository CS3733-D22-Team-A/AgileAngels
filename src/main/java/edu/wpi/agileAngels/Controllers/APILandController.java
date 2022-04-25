package edu.wpi.agileAngels.Controllers;

import edu.wpi.cs3733.D22.teamA.*;
import java.sql.SQLException;
import javafx.event.ActionEvent;

public class APILandController {

  AppController appController = AppController.getInstance();

  public APILandController() throws SQLException {}

  public void mgbAction(ActionEvent event) {

    API.run(0, 0, 900, 900, "./MGBEmployees.csv");
  }

  public void loadCredits() {
    appController.loadPage("/edu/wpi/agileAngels/views/credits-view.fxml");
  }
}
