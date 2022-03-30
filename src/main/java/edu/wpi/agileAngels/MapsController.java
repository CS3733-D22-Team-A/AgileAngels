package edu.wpi.agileAngels;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class MapsController extends MainController {

  @FXML
  private ImageView floorOneMap, floorTwoMap, floorThreeMap, lowerLevelOneMap, lowerLevelTwoMap;

  // @FXML private MenuButton mapMenu;

  @FXML private Button floorOne, floorTwo, floorThree, lowerLevelOne, lowerLevelTwo;

  @FXML
  private void changeMap(ActionEvent event) {

    if (event.getSource() == floorOne) {
      floorOneMap.setOpacity(1.0);
      floorTwoMap.setOpacity(0.0);
      floorThreeMap.setOpacity(0.0);
      lowerLevelOneMap.setOpacity(0.0);
      lowerLevelTwoMap.setOpacity(0.0);
    }

    if (event.getSource() == floorTwo) {
      floorOneMap.setOpacity(0.0);
      floorTwoMap.setOpacity(1.0);
      floorThreeMap.setOpacity(0.0);
      lowerLevelOneMap.setOpacity(0.0);
      lowerLevelTwoMap.setOpacity(0.0);
    }

    if (event.getSource() == floorThree) {
      floorOneMap.setOpacity(0.0);
      floorTwoMap.setOpacity(0.0);
      floorThreeMap.setOpacity(1.0);
      lowerLevelOneMap.setOpacity(0.0);
      lowerLevelTwoMap.setOpacity(0.0);
    }

    if (event.getSource() == lowerLevelOne) {
      floorOneMap.setOpacity(0.0);
      floorTwoMap.setOpacity(0.0);
      floorThreeMap.setOpacity(0.0);
      lowerLevelOneMap.setOpacity(1.0);
      lowerLevelTwoMap.setOpacity(0.0);
    }

    if (event.getSource() == lowerLevelTwo) {
      floorOneMap.setOpacity(0.0);
      floorTwoMap.setOpacity(0.0);
      floorThreeMap.setOpacity(0.0);
      lowerLevelOneMap.setOpacity(0.0);
      lowerLevelTwoMap.setOpacity(1.0);
    }
  }
}
