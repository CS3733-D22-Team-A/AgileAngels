package edu.wpi.agileAngels;

import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class Acontroller {

  @FXML
  private Button openScene,
      equipmentButton,
      labButton,
      sanitationButton,
      giftButton,
      foodButton,
      homeButton;
  @FXML private MenuItem equipmentMenu, labMenu, sanitationMenu, mealMenu, giftMenu;

  // Switches to a new scene depending on which button is pressed
  @FXML
  private void openScene(ActionEvent event) throws IOException {
    Stage stage;
    Parent root;
    // If the equipment request button on the default scene is pressed,
    // switch to the equipment scene
    if (event.getSource() == equipmentButton || event.getSource() == equipmentMenu) {
      stage = (Stage) equipmentButton.getScene().getWindow();
      root = FXMLLoader.load(getClass().getResource("views/equipment-view.fxml"));
    }
    // If the lab request button on the default scene is pressed,
    // switch to the lab scene
    else if (event.getSource() == labButton || event.getSource() == labMenu) {
      stage = (Stage) equipmentButton.getScene().getWindow();
      root = FXMLLoader.load(getClass().getResource("views/lab-view.fxml"));
    }
    // If the meal request button on the default scene is pressed,
    // switch to the meal scene
    else if (event.getSource() == foodButton || event.getSource() == mealMenu) {
      stage = (Stage) foodButton.getScene().getWindow();
      root = FXMLLoader.load(getClass().getResource("views/mealRequest-view.fxml"));
    }
    // If the sanitation request button on the default scene is pressed,
    // switch to the sanitation scene
    else if (event.getSource() == sanitationButton || event.getSource() == sanitationMenu) {
      stage = (Stage) sanitationButton.getScene().getWindow();
      root = FXMLLoader.load(getClass().getResource("views/sanitation-view.fxml"));
    }
    // If the gift request button on the default scene is pressed,
    // switch to the gift scene
    else if (event.getSource() == giftButton || event.getSource() == giftMenu) {
      stage = (Stage) giftButton.getScene().getWindow();
      root = FXMLLoader.load(getClass().getResource("views/gifts-view.fxml"));
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
  private void closeApp() {
    Platform.exit();
  }
}
