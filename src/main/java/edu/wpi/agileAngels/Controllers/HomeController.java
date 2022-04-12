package edu.wpi.agileAngels.Controllers;

import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

// service request and map button (page with two buttons 4/6/2022)
public class HomeController extends MainController {

  @FXML Button serviceButton, mapButton;

  MainController mainController = MainController.getInstance();

  public HomeController() throws SQLException {}

  @FXML
  private void homeButton(ActionEvent event) throws IOException {
    if (event.getSource() == serviceButton) {
      mainController.loadPage("../views/serviceRequest-view.fxml", serviceButton);
    } else if (event.getSource() == mapButton) {
      mainController.loadPage("../views/dashboard-view.fxml", mapButton);
    }
  }
}
