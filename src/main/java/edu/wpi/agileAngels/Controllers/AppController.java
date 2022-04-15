package edu.wpi.agileAngels.Controllers;

import edu.wpi.agileAngels.Database.DBconnection;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.Stack;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppController {

  public static Stack<String> pageHistory = new Stack<>();
  private static AppController appController = null;
  private MenuController menuController;
  private PropertyChangeSupport support;
  private Stage primaryStage;
  private int[] dirtyBeds = new int[4];
  private int[] dirtyInfusionPumps = new int[4];
  private int[] dirtyRecliners = new int[4];
  private int[] dirtyXRays = new int[4];
  private int[] availableBeds = new int[4];
  private int[] availableInfusionPumps = new int[4];
  private int[] availableRecliners = new int[4];
  private int[] availableXRays = new int[4];

  public AppController() {
    support = new PropertyChangeSupport(this);

    for (int i = 0; i < 4; i++) {
      dirtyBeds[i] = 0;
      dirtyInfusionPumps[i] = 0;
      dirtyRecliners[i] = 0;
      dirtyXRays[i] = 0;
      availableBeds[i] = 0;
      availableInfusionPumps[i] = 0;
      availableRecliners[i] = 0;
      availableXRays[i] = 0;
    }
  }

  public static AppController getInstance() {
    if (appController == null) {
      appController = new AppController();
    }
    return appController;
  }

  public void addPropertyChangeListener(PropertyChangeListener pcl) {
    support.addPropertyChangeListener(pcl);
  }

  public void removePropertyChangeListener(PropertyChangeListener pcl) {
    support.removePropertyChangeListener(pcl);
  }

  public void incrementDirtyBeds(String floor, int increment) {
    int floorInt = getFloorInt(floor);
    try {
      support.firePropertyChange(
          "dirtyBeds" + floor, this.dirtyBeds[floorInt], this.dirtyBeds[floorInt] + increment);
      this.dirtyBeds[floorInt] = this.dirtyBeds[floorInt] + increment;
      support.firePropertyChange("dirtyBedsAll", this.dirtyBeds[0], this.dirtyBeds[0] + increment);
      this.dirtyBeds[0] = this.dirtyBeds[0] + increment;
    } catch (IndexOutOfBoundsException e) {
      e.printStackTrace();
    }
  }

  public void incrementDirtyInfusionPumps(String floor, int increment) {
    int floorInt = getFloorInt(floor);
    try {
      support.firePropertyChange(
          "dirtyPumps" + floor,
          this.dirtyInfusionPumps[floorInt],
          this.dirtyInfusionPumps[floorInt] + increment);
      this.dirtyInfusionPumps[floorInt] = this.dirtyInfusionPumps[floorInt] + increment;
      support.firePropertyChange(
          "dirtyPumpsAll", this.dirtyInfusionPumps[0], this.dirtyInfusionPumps[0] + increment);
      this.dirtyInfusionPumps[0] = this.dirtyInfusionPumps[0] + increment;
    } catch (IndexOutOfBoundsException e) {
      e.printStackTrace();
    }
  }

  public void incrementDirtyRecliners(String floor, int increment) {
    int floorInt = getFloorInt(floor);
    try {
      support.firePropertyChange(
          "dirtyRecliners" + floor,
          this.dirtyRecliners[floorInt],
          this.dirtyRecliners[floorInt] + increment);
      this.dirtyRecliners[floorInt] = this.dirtyRecliners[floorInt] + increment;
      support.firePropertyChange(
          "dirtyReclinersAll", this.dirtyRecliners[0], this.dirtyRecliners[0] + increment);
      this.dirtyRecliners[0] = this.dirtyRecliners[0] + increment;
    } catch (IndexOutOfBoundsException e) {
      e.printStackTrace();
    }
  }

  public void incrementDirtyXRays(String floor, int increment) {
    int floorInt = getFloorInt(floor);
    try {
      support.firePropertyChange(
          "dirtyXRays" + floor, this.dirtyXRays[floorInt], this.dirtyXRays[floorInt] + increment);
      this.dirtyXRays[floorInt] = this.dirtyXRays[floorInt] + increment;
      support.firePropertyChange(
          "dirtyXRaysAll", this.dirtyXRays[0], this.dirtyXRays[0] + increment);
      this.dirtyXRays[0] = this.dirtyXRays[0] + increment;
    } catch (IndexOutOfBoundsException e) {
      e.printStackTrace();
    }
  }

  public void incrementAvailableBeds(String floor, int increment) {
    int floorInt = getFloorInt(floor);
    try {
      support.firePropertyChange(
          "availableBeds" + floor,
          this.availableBeds[floorInt],
          this.availableBeds[floorInt] + increment);
      this.availableBeds[floorInt] = this.availableBeds[floorInt] + increment;
      support.firePropertyChange(
          "availableBedsAll", this.availableBeds[0], this.availableBeds[0] + increment);
      this.availableBeds[0] = this.availableBeds[0] + increment;
    } catch (IndexOutOfBoundsException e) {
      e.printStackTrace();
    }
  }

  public void incrementAvailableInfusionPumps(String floor, int increment) {
    int floorInt = getFloorInt(floor);
    try {
      support.firePropertyChange(
          "availablePumps" + floor,
          this.availableInfusionPumps[floorInt],
          this.availableInfusionPumps[floorInt] + increment);
      this.availableInfusionPumps[floorInt] = this.availableInfusionPumps[floorInt] + increment;
      support.firePropertyChange(
          "availablePumpsAll",
          this.availableInfusionPumps[0],
          this.availableInfusionPumps[0] + increment);
      this.availableInfusionPumps[0] = this.availableInfusionPumps[0] + increment;
    } catch (IndexOutOfBoundsException e) {
      e.printStackTrace();
    }
  }

  public void incrementAvailableRecliners(String floor, int increment) {
    int floorInt = getFloorInt(floor);
    try {
      support.firePropertyChange(
          "availableRecliners" + floor,
          this.availableRecliners[floorInt],
          this.availableRecliners[floorInt] + increment);
      this.availableRecliners[floorInt] = this.availableRecliners[floorInt] + increment;
      support.firePropertyChange(
          "availableReclinersAll",
          this.availableRecliners[0],
          this.availableRecliners[0] + increment);
      this.availableRecliners[0] = this.availableRecliners[0] + increment;
    } catch (IndexOutOfBoundsException e) {
      e.printStackTrace();
    }
  }

  public void incrementAvailableXRays(String floor, int increment) {
    int floorInt = getFloorInt(floor);
    try {
      support.firePropertyChange(
          "availableXRays" + floor,
          this.availableXRays[floorInt],
          this.availableXRays[floorInt] + increment);
      this.availableXRays[floorInt] = this.availableXRays[floorInt] + increment;
      support.firePropertyChange(
          "availableXRaysAll", this.availableXRays[0], this.availableXRays[0] + increment);
      this.availableXRays[0] = this.availableXRays[0] + increment;
    } catch (IndexOutOfBoundsException e) {
      e.printStackTrace();
    }
  }

  private int getFloorInt(String floor) {
    int floorInt = -1;
    if (floor.equals("3")) {
      floorInt = 1;
    } else if (floor.equals("4")) {
      floorInt = 2;
    } else if (floor.equals("5")) {
      floorInt = 3;
    }
    return floorInt;
  }

  public void init(Stage primaryStage) {
    this.primaryStage = primaryStage;
    loadPage("/edu/wpi/agileAngels/views/login.fxml");
  }

  public void closeApp() {
    DBconnection.shutdown();
    Platform.exit();
  }

  public void loadPage(String view) {

    if (pageHistory.isEmpty()) {
      pageHistory.push(view);
    } else if (view != pageHistory.peek()) {
      pageHistory.push(view);
    }

    FXMLLoader loader = new FXMLLoader(getClass().getResource(view));
    Parent root = null;
    try {
      root = loader.load();
      Scene defaultScene = new Scene(root);

      defaultScene
          .getStylesheets()
          .add(
              "https://fonts.googleapis.com/css2?family=Roboto:wght@100;300;400;500;700;900&display=swap");

      primaryStage.setScene(defaultScene);
      primaryStage.setResizable(true);
      primaryStage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void back() {
    pageHistory.pop();
    loadPage(pageHistory.peek());
  }

  public void clearPage() {
    loadPage(pageHistory.peek());
  }

  private void profile() throws IOException {
    loadPage("/edu/wpi/agileAngels/views/login.fxml");
  }

  private void goHome(ActionEvent event) {
    loadPage("/edu/wpi/agileAngels/views/home-view.fxml");
  }

  public void setCurrentMenuController(MenuController menuController) {
    this.menuController = menuController;
  }

  public MenuController getMenuController() {
    return menuController;
  }
}
