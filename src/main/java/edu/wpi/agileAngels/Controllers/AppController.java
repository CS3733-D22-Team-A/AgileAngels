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

  public AppController() {
    support = new PropertyChangeSupport(this);

    for (int i = 0; i < 4; i++) {
      dirtyBeds[i] = 6;
      dirtyInfusionPumps[i] = 10;
      dirtyRecliners[i] = 0;
      dirtyXRays[i] = 0;
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

  public void incrementDirty(String type, String floor, int i) {
    System.out.println("increment dirty");
    if (type.equals("XRayMachine")) {
      incrementDirtyXRays(floor, i);
    } else if (type.equals("InfusionPump")) {
      incrementDirtyInfusionPumps(floor, i);
    } else if (type.equals("Bed")) {
      incrementDirtyBeds(floor, i);
    } else if (type.equals("Recliner")) {
      incrementDirtyRecliners(floor, i);
    }
  }

  public void incrementDirtyBeds(String floor, int increment) {
    System.out.println("increment dirty beds");
    int floorInt = getFloorInt(floor);
    try {
      System.out.println("1");
      this.dirtyBeds[floorInt] = this.dirtyBeds[floorInt] + increment;
      this.dirtyBeds[0] = this.dirtyBeds[0] + increment;
      System.out.println("2");
      support.firePropertyChange(
          "dirtyBeds" + floor, this.dirtyBeds[floorInt], this.dirtyBeds[floorInt] + increment);
      System.out.println("3");
    } catch (IndexOutOfBoundsException e) {
      e.printStackTrace();
    }
    System.out.println("increment dirty beds end");
  }

  public void incrementDirtyInfusionPumps(String floor, int increment) {
    int floorInt = getFloorInt(floor);
    try {
      this.dirtyInfusionPumps[floorInt] = this.dirtyInfusionPumps[floorInt] + increment;
      this.dirtyInfusionPumps[0] = this.dirtyInfusionPumps[0] + increment;
      support.firePropertyChange(
          "dirtyPumps" + floor,
          this.dirtyInfusionPumps[floorInt],
          this.dirtyInfusionPumps[floorInt] + increment);

    } catch (IndexOutOfBoundsException e) {
      e.printStackTrace();
    }
  }

  public void incrementDirtyRecliners(String floor, int increment) {
    int floorInt = getFloorInt(floor);
    try {
      this.dirtyRecliners[floorInt] = this.dirtyRecliners[floorInt] + increment;
      this.dirtyRecliners[0] = this.dirtyRecliners[0] + increment;
      support.firePropertyChange(
          "dirtyRecliners" + floor,
          this.dirtyRecliners[floorInt],
          this.dirtyRecliners[floorInt] + increment);
    } catch (IndexOutOfBoundsException e) {
      e.printStackTrace();
    }
  }

  public void incrementDirtyXRays(String floor, int increment) {
    int floorInt = getFloorInt(floor);
    try {
      this.dirtyXRays[floorInt] = this.dirtyXRays[floorInt] + increment;
      this.dirtyXRays[0] = this.dirtyXRays[0] + increment;
      support.firePropertyChange(
          "dirtyXRays" + floor, this.dirtyXRays[floorInt], this.dirtyXRays[floorInt] + increment);
    } catch (IndexOutOfBoundsException e) {
      e.printStackTrace();
    }
  }

  public boolean displayAlert() {
    boolean alertDisplayed = false;
    String view = "";
    if (dirtyBeds[1] > 6) {
      view = "/edu/wpi/agileAngels/views/bed-alert-view.fxml";
    } else if (dirtyBeds[2] > 6) {
      view = "/edu/wpi/agileAngels/views/bed-alert-view.fxml";
    } else if (dirtyBeds[3] > 6) {
      view = "/edu/wpi/agileAngels/views/bed-alert-view.fxml";
    } else if (dirtyInfusionPumps[1] > 10) {
      view = "/edu/wpi/agileAngels/views/pump-alert-view.fxml";
    } else if (dirtyInfusionPumps[2] > 10) {
      view = "/edu/wpi/agileAngels/views/pump-alert-view.fxml";
    } else if (dirtyInfusionPumps[3] > 10) {
      view = "/edu/wpi/agileAngels/views/pump-alert-view.fxml";
    }
    if (!view.equals("")) {
      alertDisplayed = true;
      FXMLLoader loader = new FXMLLoader(getClass().getResource(view));
      try {
        Scene secondScene = new Scene(loader.load());

        secondScene
            .getStylesheets()
            .add(
                "https://fonts.googleapis.com/css2?family=Roboto:wght@100;300;400;500;700;900&display=swap");

        Stage newWindow = new Stage();
        newWindow.setTitle("Alert");
        newWindow.setScene(secondScene);

        newWindow.setX(primaryStage.getX() + 200);
        newWindow.setY(primaryStage.getY() + 100);

        newWindow.show();

      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    System.out.println("display alert: " + alertDisplayed);
    return alertDisplayed;
  }

  public String getPumpFloor() {
    String floor = "";
    if (dirtyInfusionPumps[1] > 10) {
      floor = "3";
    } else if (dirtyInfusionPumps[2] > 10) {
      floor = "4";
    } else if (dirtyInfusionPumps[3] > 10) {
      floor = "5";
    }
    return floor;
  }

  public String getBedFloor() {
    String floor = "";
    if (dirtyBeds[1] > 6) {
      floor = "3";
    } else if (dirtyBeds[2] > 6) {
      floor = "4";
    } else if (dirtyBeds[3] > 6) {
      floor = "5";
    }
    return floor;
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
