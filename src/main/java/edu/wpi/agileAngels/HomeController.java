package edu.wpi.agileAngels;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

// service request and map button (page with two buttons 4/6/2022)
public class HomeController extends MainController {

  private final NodeManager nodeManager = NodeManager.getNodeManager();

  @FXML Button serviceButton, mapButton;

  @FXML
  private void homeButton(ActionEvent event) throws IOException {
    if (event.getSource() == serviceButton) {
      loadPage("views/serviceRequest-view.fxml", serviceButton);
    } else if (event.getSource() == mapButton) {
      // nodeManager.createNodesFromDB();
      Location location1 =
          new Location("test1", "100", "100", "1", "TOWER", "ELV", "Elevator", "ELV1");
      nodeManager.addNode(location1);

      loadPage("views/map-view.fxml", mapButton);
    }
  }
}
