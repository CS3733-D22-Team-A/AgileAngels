package edu.wpi.agileAngels;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class MapsController extends MainController implements Initializable {

  // @FXML private MenuButton mapMenu;

  //private static MapsController mapsController = new MapsController();

  @FXML private Button floorOne, floorTwo, floorThree, lowerLevelOne, lowerLevelTwo;

  @FXML Pane mapPane;
  @FXML Circle testPoint;
  private ObservableList<Location> locationData = FXCollections.observableArrayList();
  private LocationDAOImpl locationDAO;
  private Connection connection = DriverManager.getConnection("jdbc:derby:myDB;create=true");

  private static ArrayList<Circle> circles = new ArrayList<Circle>();

  public static MapsController getMapsController() {
    //return mapsController;
    return null;
  }

  private MapsController() throws SQLException {}
  // Does map scaling.
  double map(double s, double a1, double a2, double b1, double b2) {
    return b1 + (s - a1) * (b2 - b1) / (a2 - a1);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
  }
  /**
   * @param location displayLocation helps display the circles on top of the map to their
   *     corresponding location based on scaling.
   */
  @FXML
  private void displayLocation(Location location) {
    Double XCoord = (Double.parseDouble(location.getXCoord()));
    Double YCoord = (Double.parseDouble(location.getYCoord()));

    if (1415 <= XCoord && (1415 + 580) > XCoord && 638 <= YCoord && (638 + 580) > YCoord) {
      Node node = new Node(location, this);
      Circle circle = node.getDot();

      circles.add(circle);

      circle.setCenterX(map(XCoord, 1415, (1415 + 580), 390, 890));
      circle.setCenterY(map(YCoord, 638, (638 + 580), 198, 698));
      circle.setRadius(4);
    }
  }

  @FXML
  private void addLocation() {

  }

  @FXML
  private void editLocation(ActionEvent event) {

  }

  @FXML
  private void deleteLocation() {
    // root.getChildren().remove(circle);
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
  private void changeMap(ActionEvent event) throws IOException {
    circles.clear();
    locationDAO = new LocationDAOImpl();
    HashMap<String, Location> locationHash = locationDAO.getAllLocations();
    ArrayList<Location> locationList = new ArrayList<>(locationHash.values());

    Group group = new Group();
    group.getChildren().add(new Circle());

    int floorToDisp = 0;

    if (event.getSource() == floorOne) {
      /*
      floorOneMap.setOpacity(1.0);
      floorTwoMap.setOpacity(0.0);
      floorThreeMap.setOpacity(0.0);
      lowerLevelOneMap.setOpacity(0.0);
      lowerLevelTwoMap.setOpacity(0.0);
       */
      floorToDisp = 0;
      for (Location location : locationList) {
        if (location.getFloor().equals("1")) {
          displayLocation(location);
        }
        // System.out.println("This doesn't contain things on floor 1. SOS :3");
      }
      for (Circle circle : circles) {
        group.getChildren().add(circle);
      }
    }

    if (event.getSource() == floorTwo) {
      /*
      floorOneMap.setOpacity(0.0);
      floorTwoMap.setOpacity(1.0);
      floorThreeMap.setOpacity(0.0);
      lowerLevelOneMap.setOpacity(0.0);
      lowerLevelTwoMap.setOpacity(0.0);
       */
      floorToDisp = 1;
      for (Location location : locationList) {
        if (location.getFloor().equals("2")) {
          displayLocation(location);
        }
        // System.out.println("This doesn't contain things on floor 2. SOS :3");
      }
      for (Circle circle : circles) {
        group.getChildren().add(circle);
      }
    }

    if (event.getSource() == floorThree) {
      /*
      floorOneMap.setOpacity(0.0);
      floorTwoMap.setOpacity(0.0);
      floorThreeMap.setOpacity(1.0);
      lowerLevelOneMap.setOpacity(0.0);
      lowerLevelTwoMap.setOpacity(0.0);

       */
      floorToDisp = 2;
    }

    if (event.getSource() == lowerLevelOne) {
      /*
      floorOneMap.setOpacity(0.0);
      floorTwoMap.setOpacity(0.0);
      floorThreeMap.setOpacity(0.0);
      lowerLevelOneMap.setOpacity(1.0);
      lowerLevelTwoMap.setOpacity(0.0);

       */
      floorToDisp = 3;
    }

    if (event.getSource() == lowerLevelTwo) {
      /*
      floorOneMap.setOpacity(0.0);
      floorTwoMap.setOpacity(0.0);
      floorThreeMap.setOpacity(0.0);
      lowerLevelOneMap.setOpacity(0.0);
      lowerLevelTwoMap.setOpacity(1.0);
       */
      floorToDisp = 4;
    }

    loadMapPage("views/map-view.fxml", floorOne, group);
    // dispFloor(floorToDisp);
  }


  /** Guess. */
  @FXML
  private void clearMaps() {
    circles.clear();
  }
}