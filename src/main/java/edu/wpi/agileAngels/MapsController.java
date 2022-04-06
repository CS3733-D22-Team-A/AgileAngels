package edu.wpi.agileAngels;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

// @TODO hot mess, sayonara

public class MapsController extends MainController implements Initializable {

  @FXML private Button floorOne, floorTwo, floorThree, lowerLevelOne, lowerLevelTwo;
  @FXML
  public ImageView floorOneMap, floorTwoMap, floorThreeMap, lowerLevelOneMap, lowerLevelTwoMap;

  @FXML public Pane mapPane;

  private ObservableList<Location> locationData = FXCollections.observableArrayList();
  private LocationDAOImpl locationDAO;
  private Connection connection = DriverManager.getConnection("jdbc:derby:myDB;create=true");

  private static ArrayList<Circle> circles = new ArrayList<Circle>();

  public MapsController() throws SQLException {}

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    //    circles.clear();
    //    locationDAO = new LocationDAOImpl();
    //    HashMap<String, Location> locationHash = locationDAO.getAllLocations();
    //    ArrayList<Location> locationList = new ArrayList<>(locationHash.values());
    //
    //    for (Location location1 : locationList) {
    //      Double XCoord = (Double.parseDouble(location1.getXCoord()));
    //      Double YCoord = (Double.parseDouble(location1.getYCoord()));
    //
    //      if (1415 <= XCoord && (1415 + 580) > XCoord && 638 <= YCoord && (638 + 580) > YCoord) {
    //        Node node = new Node(location1);
    //        Circle circle = node.createCircle();
    //
    //        mapPane.getChildren().add(circle);
    //        // circles.add(circle);
    //
    //        circle.setCenterX(map(XCoord, 1415, (1415 + 580), 0, 500));
    //        circle.setCenterY(map(YCoord, 638, (638 + 580), 0, 500));
    //        circle.setRadius(4);
    //      }
    //    }
  }

  double map(double s, double a1, double a2, double b1, double b2) {
    return b1 + (s - a1) * (b2 - b1) / (a2 - a1);
  }

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

    if (floor == 1) {

      floorOneMap.setOpacity(1.0);
      floorTwoMap.setOpacity(0.0);
      floorThreeMap.setOpacity(0.0);
      lowerLevelOneMap.setOpacity(0.0);
      lowerLevelTwoMap.setOpacity(0.0);

      floorToDisp = 0;
      //      for (Location location : locationList) {
      //        if (location.getFloor().equals("1")) {
      //          // displayLocation(location);
      //        }
      // System.out.println("This doesn't contain things on floor 1. SOS :3");
    }
    //      for (Circle circle : circles) {
    //        group.getChildren().add(circle);
    //      }

    else if (floor == 2) {

      floorOneMap.setOpacity(0.0);
      floorTwoMap.setOpacity(1.0);
      floorThreeMap.setOpacity(0.0);
      lowerLevelOneMap.setOpacity(0.0);
      lowerLevelTwoMap.setOpacity(0.0);

      // floorToDisp = 1;
      //      for (Location location : locationList) {
      //        if (location.getFloor().equals("2")) {
      //          // displayLocation(location);
      //        }
      //        // System.out.println("This doesn't contain things on floor 2. SOS :3");
      //      }
      //      for (Circle circle : circles) {
      //        group.getChildren().add(circle);
      //      }
    }

    if (floor == 3) {

      floorOneMap.setOpacity(0.0);
      floorTwoMap.setOpacity(0.0);
      floorThreeMap.setOpacity(1.0);
      lowerLevelOneMap.setOpacity(0.0);
      lowerLevelTwoMap.setOpacity(0.0);

      // floorToDisp = 2;
    }

    if (floor == 4) {

      floorOneMap.setOpacity(0.0);
      floorTwoMap.setOpacity(0.0);
      floorThreeMap.setOpacity(0.0);
      lowerLevelOneMap.setOpacity(1.0);
      lowerLevelTwoMap.setOpacity(0.0);

      // floorToDisp = 3;
    }

    if (floor == 5) {

      floorOneMap.setOpacity(0.0);
      floorTwoMap.setOpacity(0.0);
      floorThreeMap.setOpacity(0.0);
      lowerLevelOneMap.setOpacity(0.0);
      lowerLevelTwoMap.setOpacity(1.0);

      // MapsManager.getMapsManager().changeMap(floor);
    }
  }
}
