package edu.wpi.agileAngels.Controllers;

import edu.wpi.agileAngels.Database.DBconnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import edu.wpi.agileAngels.itemsifters.ItemSearch;

// test button on the front end
public class TestController extends MainController {

  @FXML
  public void switchConnection(ActionEvent event) {
    DBconnection.switchConnection();
  }
}
