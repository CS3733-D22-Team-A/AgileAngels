package edu.wpi.agileAngels;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;

public class MapsController extends MainController {

  @FXML
  private ImageView floorOneMap, floorTwoMap, floorThreeMap, lowerLevelOneMap, lowerLevelTwoMap;

  @FXML private MenuButton mapMenu;

  @FXML private MenuItem floorOne, floorTwo, floorThree, lowerLevelOne, lowerLevelTwo;

  @FXML
  private void changeMap(ActionEvent event) {

    if (event.getSource() == floorOne) {
      floorOneMap.setOpacity(1.0);
      floorTwoMap.setOpacity(0.0);
    }

    if (event.getSource() == floorTwo) {
      floorTwoMap.setOpacity(1.0);
      floorOneMap.setOpacity(0.0);
    }
  }
}
