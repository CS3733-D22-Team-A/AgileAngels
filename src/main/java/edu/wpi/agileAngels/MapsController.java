package edu.wpi.agileAngels;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

// hello
public class MapsController extends MainController implements Initializable {

  @FXML private Button floorOne, floorTwo, floorThree, lowerLevelOne, lowerLevelTwo;
  @FXML public Pane mapPane;

  public MapsController() {}

  @Override
  public void initialize(URL location, ResourceBundle resources) {}

  /**
   * This is the method to swap the map images around, Currently it the MAIN function for displaying
   * the circles and swapping the maps. Though currently, it refreshes the screen in order to show
   * the circles which causes issues with refreshing the map back to default.
   *
   * @param event
   * @throws IOException
   */
  @FXML
  public void changeMap(ActionEvent event) throws IOException {
    MapsManager.getMapsManager().setMapPane(mapPane);
    MapsManager.getMapsManager().setControlItem(floorOne);
    int floor = 1;
    if (event.getSource() == floorOne) floor = 1;
    else if (event.getSource() == floorTwo) floor = 2;
    else if (event.getSource() == floorThree) floor = 3;
    else if (event.getSource() == lowerLevelOne) floor = 4;
    else if (event.getSource() == lowerLevelTwo) floor = 5;
    MapsManager.getMapsManager().changeMap(floor);
  }
}
