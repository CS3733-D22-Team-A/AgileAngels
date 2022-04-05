package edu.wpi.agileAngels;

import java.awt.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class MapsController extends MainController {
  @FXML
  private ImageView floorOneMap, floorTwoMap, floorThreeMap, lowerLevelOneMap, lowerLevelTwoMap;
  private static ArrayList<ImageView> floors = new ArrayList<>();
  // @FXML private MenuButton mapMenu;

  @FXML private Button floorOne, floorTwo, floorThree, lowerLevelOne, lowerLevelTwo;

  @FXML Pane mapPane;
  @FXML Circle testPoint;
  private ObservableList<Location> locationData = FXCollections.observableArrayList();
  private LocationDAOImpl locationDAO;
  private Connection connection = DriverManager.getConnection("jdbc:derby:myDB;create=true");

  private static ArrayList<Circle> circles = new ArrayList<Circle>();

  public MapsController() throws SQLException {}

  double map(double s, double a1, double a2, double b1, double b2) {
    return b1 + (s - a1) * (b2 - b1) / (a2 - a1);
  }

  public void initialize(int floor) {
    dispFloor(floorToDisp);
  }

  @FXML
  private void displayLocation(Location location) {

    Double XCoord = (Double.parseDouble(location.getXCoord()));
    Double YCoord = (Double.parseDouble(location.getYCoord()));

    if (1415 <= XCoord && (1415 + 580) > XCoord && 638 <= YCoord && (638 + 580) > YCoord) {
      circles.add(new Circle());

      circles.get(circles.size() - 1).setCenterX(map(XCoord, 1415, (1415 + 580), 390, 890));
      circles.get(circles.size() - 1).setCenterY(map(YCoord, 638, (638 + 580), 198, 698));
      circles.get(circles.size() - 1).setRadius(4);
    }
  }

  private void dispFloor(int floor) {
    floors.add(floorOneMap);
    floors.add(floorTwoMap);
    floors.add(floorThreeMap);
    floors.add(lowerLevelOneMap);
    floors.add(lowerLevelTwoMap);

    floorOneMap.setOpacity(0.0);
    floorTwoMap.setOpacity(0.0);
    floorThreeMap.setOpacity(0.0);
    lowerLevelOneMap.setOpacity(0.0);
    lowerLevelTwoMap.setOpacity(0.0);

    floors.get(floor).setOpacity(1.0);
  }

  @FXML
  private void changeMap(ActionEvent event) throws IOException {
    circles.clear();
    locationDAO = new LocationDAOImpl(connection);
    HashMap<String, Location> locationHash = locationDAO.getAllLocations();
    ArrayList<Location> locationList = new ArrayList<>(locationHash.values());

    Group group = new Group();
    group.getChildren().add(new Circle());

    if (event.getSource() == floorOne) {
      floorToDisp = 1;
      for (int i = 0; i < locationList.size(); i++) {
        Location location = locationList.get(i);
        if (location.getFloor().equals("1")) {
          displayLocation(location);
        }
        // System.out.println("This doesn't contain things on floor 1. SOS :3");
      }
      for (int i = 0; i < circles.size(); i++) {
        group.getChildren().add(circles.get(i));
      }
    }

    if (event.getSource() == floorTwo) {
      floorToDisp = 2;
      for (int i = 0; i < locationList.size(); i++) {
        Location location = locationList.get(i);
        if (location.getFloor().equals("2")) {
          displayLocation(location);
        }
        // System.out.println("This doesn't contain things on floor 1. SOS :3");
      }
      for (int i = 0; i < circles.size(); i++) {
        group.getChildren().add(circles.get(i));
      }
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

    loadMapPage("views/map-view.fxml", floorOne, group);
  }
}
