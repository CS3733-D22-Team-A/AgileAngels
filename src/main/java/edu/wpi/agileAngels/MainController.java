package edu.wpi.agileAngels;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
      mapButton;
  @FXML
  private MenuItem equipmentMenu, equipmentMenu11, labMenu, sanitationMenu, mealMenu, giftMenu;

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
    } else if (event.getSource() == equipmentMenu11) {
      stage =
          (Stage)
              ((MenuItem) event.getTarget())
                  .getParentPopup()
                  .getOwnerWindow()
                  .getScene()
                  .getWindow();
      root = FXMLLoader.load(getClass().getResource("views/equipmentEdit-view.fxml"));
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

  @FXML
  private void goHome() throws IOException {

    Stage stage;
    Parent root;

    stage = (Stage) homeButton.getScene().getWindow();
    root = FXMLLoader.load(getClass().getResource("views/default-view.fxml"));

    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  void resetPage(String view) throws IOException {

    Stage stage;
    Parent root;

    stage = (Stage) homeButton.getScene().getWindow();
    root = FXMLLoader.load(getClass().getResource(view));

    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
}
