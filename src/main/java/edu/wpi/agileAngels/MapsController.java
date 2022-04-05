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
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class MapsController extends MainController {
  @FXML
  private ImageView floorOneMap, floorTwoMap, floorThreeMap, lowerLevelOneMap, lowerLevelTwoMap;

  // @FXML private MenuButton mapMenu;

  @FXML private Button floorOne, floorTwo, floorThree, lowerLevelOne, lowerLevelTwo;

  @FXML Pane mapPane;
  @FXML Circle testPoint;
  private ObservableList<Location> locationData = FXCollections.observableArrayList();
  private LocationDAOImpl locationDAO;
  private Connection connection = DriverManager.getConnection("jdbc:derby:myDB;create=true");

  private static ArrayList<Circle> circles = new ArrayList<Circle>();

  public MapsController() throws SQLException {}

  @FXML
  private void displayLocation(Location location) {
    circles.add(new Circle());

    Double XCoord = (Double.parseDouble(location.getXCoord()));
    Double YCoord = (Double.parseDouble(location.getYCoord()));

    circles.get(circles.size() - 1).setCenterX(XCoord);
    circles.get(circles.size() - 1).setCenterY(YCoord);
    circles.get(circles.size() - 1).setRadius(4);
  }

  private void loadMap(Group group) throws java.io.IOException {
    Stage stage;
    Parent root;
    stage = (Stage) floorOne.getScene().getWindow();
    root = FXMLLoader.load(getClass().getResource("views/map-view.fxml"));
    group.getChildren().add(root);
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.setResizable(true);
    stage.show();
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
      floorOneMap.setOpacity(1.0);
      floorTwoMap.setOpacity(0.0);
      floorThreeMap.setOpacity(0.0);
      lowerLevelOneMap.setOpacity(0.0);
      lowerLevelTwoMap.setOpacity(0.0);
      System.out.println(locationList.size());
      for (int i = 0; i < locationList.size(); i++) {
        Location location = locationList.get(i);
        if (location.getFloor().equals("1")) {
          displayLocation(location);
          group.getChildren().add(circles.get(circles.size() - 1));
          System.out.println(circles);
          // Stinker gone moved to line 55.
        }
        // System.out.println("This doesn't contain things on floor 1. SOS :3");
      }
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

    loadMapPage("views/map-view.fxml", floorOne, group);
  }
}
