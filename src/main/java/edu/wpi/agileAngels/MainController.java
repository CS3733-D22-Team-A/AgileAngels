package edu.wpi.agileAngels;

import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainController {

  @FXML
  private Button homeButton,
      clear,
      openScene,
      equipmentButton,
      labButton,
      sanitationButton,
      giftButton,
      foodButton,
      locationButton,
      mapButton,
      home,
      close,
      equipRequest,
      viewRequest,
      map;
  @FXML private MenuItem equipmentMenu, labMenu, sanitationMenu, mealMenu, giftMenu;

  @FXML AnchorPane anchor;

  @FXML
  private void closeApp() {
    Platform.exit();
  }

  @FXML
  private void back(ActionEvent event) throws IOException {
    if (event.getSource() == home) {
      loadPage("views/home-view.fxml", close);
    }
  }

  @FXML
  private void menuItem(ActionEvent event) throws IOException {
    if (event.getSource() == equipRequest) {
      loadPage("views/equipment-view.fxml", close);
    }
    if (event.getSource() == viewRequest) {
      loadPage("views/home-view.fxml", close);
    }
    if (event.getSource() == map) {
      loadPage("views/maps.fxml", close);
    }
  }

  // delete start
  @FXML
  private void openScene(ActionEvent event) throws IOException {
    Stage stage;
    Parent root;
    // If the equipment request button on the default scene is pressed,
    // switch to the equipment scene
    if (event.getSource() == equipmentButton) {
      stage = (Stage) equipmentButton.getScene().getWindow();
      root = FXMLLoader.load(getClass().getResource("views/equipment-view.fxml"));
    } else if (event.getSource() == locationButton) {
      stage = (Stage) locationButton.getScene().getWindow();
      root = FXMLLoader.load(getClass().getResource("views/locations-view.fxml"));
    } else if (event.getSource() == equipmentMenu) {
      stage =
          (Stage)
              ((MenuItem) event.getTarget())
                  .getParentPopup()
                  .getOwnerWindow()
                  .getScene()
                  .getWindow();
      root = FXMLLoader.load(getClass().getResource("views/equipment-view.fxml"));
    }
    // If the lab request button on the default scene is pressed,
    // switch to the lab scene
    else if (event.getSource() == labMenu) {
      stage =
          (Stage)
              ((MenuItem) event.getTarget())
                  .getParentPopup()
                  .getOwnerWindow()
                  .getScene()
                  .getWindow();
      root = FXMLLoader.load(getClass().getResource("views/lab-view.fxml"));
    } else if (event.getSource() == labButton) {
      stage = (Stage) labButton.getScene().getWindow();
      root = FXMLLoader.load(getClass().getResource("views/lab-view.fxml"));
    }
    // If the meal request button on the default scene is pressed,
    // switch to the meal scene
    else if (event.getSource() == mealMenu) {
      stage =
          (Stage)
              ((MenuItem) event.getTarget())
                  .getParentPopup()
                  .getOwnerWindow()
                  .getScene()
                  .getWindow();
      root = FXMLLoader.load(getClass().getResource("views/mealRequest-view.fxml"));
    } else if (event.getSource() == foodButton) {
      stage = (Stage) foodButton.getScene().getWindow();
      root = FXMLLoader.load(getClass().getResource("views/mealRequest-view.fxml"));
    }

    // If the sanitation request button on the default scene is pressed,
    // switch to the sanitation scene
    else if (event.getSource() == sanitationMenu) {
      stage =
          (Stage)
              ((MenuItem) event.getTarget())
                  .getParentPopup()
                  .getOwnerWindow()
                  .getScene()
                  .getWindow();
      root = FXMLLoader.load(getClass().getResource("views/sanitation-view.fxml"));
    } else if (event.getSource() == sanitationButton) {
      stage = (Stage) sanitationButton.getScene().getWindow();
      root = FXMLLoader.load(getClass().getResource("views/sanitation-view.fxml"));
    }
    // If the gift request button on the default scene is pressed,
    // switch to the gift scene
    else if (event.getSource() == giftMenu) {
      stage =
          (Stage)
              ((MenuItem) event.getTarget())
                  .getParentPopup()
                  .getOwnerWindow()
                  .getScene()
                  .getWindow();
      root = FXMLLoader.load(getClass().getResource("views/gifts-view.fxml"));
    } else if (event.getSource() == giftButton) {
      stage = (Stage) giftButton.getScene().getWindow();
      root = FXMLLoader.load(getClass().getResource("views/gifts-view.fxml"));
    } else if (event.getSource() == mapButton) {
      stage = (Stage) mapButton.getScene().getWindow();
      root = FXMLLoader.load(getClass().getResource("views/maps.fxml"));
    }
    // If the home button is pressed, switch to the default scene
    else {
      stage = (Stage) homeButton.getScene().getWindow();
      root = FXMLLoader.load(getClass().getResource("views/default-view.fxml"));
    }

    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  // delete end

  @FXML
  private void profile() throws IOException {
    loadPage("views/home-view.fxml", close);
  }

  @FXML
  private void goHome(ActionEvent event) throws IOException {
    loadPage("views/home-view.fxml", close);
  }

  void loadPage(String view, Control item) throws IOException {

    Stage stage;
    Parent root;

    stage = (Stage) close.getScene().getWindow();
    root = FXMLLoader.load(getClass().getResource(view));

    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.setResizable(false);
    stage.show();
  }

  static double pos = 0;

  //  public double getPositionX(Node node) {
  //
  //    pos = pos + getPositionX(node);
  //    return node.getParent() == anchor ? node.getLayoutX() : getPositionX(node.getParent()) + pos
  // ;
  //
  //  }

  public double getPositionX(Node node) {
    if (node.getParent() == anchor) {
      double position = pos + node.getLayoutX();
      pos = 0;
      return position;
    } else {
      pos += node.getLayoutX();
      return getPositionX(node.getParent());
    }
  }

  public double getPositionY(Node node) {
    if (node.getParent() == anchor) {
      double position = pos + node.getLayoutY();
      pos = 0;
      return position;
    } else {
      pos += node.getLayoutY();
      return getPositionY(node.getParent());
    }
  }

  //  public double getPositionY(Node node) {
  //    if (node.getParent() == anchor) {
  //      return node.getLayoutY();
  //    } else {
  //      return getPositionY(node.getParent(), node.getLayoutY());
  //    }
  //  }
  //
  //  public double getPositionY(Node node, double pos) {
  //    if (node.getParent() == anchor) {
  //      return pos + node.getLayoutY();
  //    } else {
  //      return getPositionY(node.getParent(), pos + node.getLayoutY());
  //    }
  //  }
}
