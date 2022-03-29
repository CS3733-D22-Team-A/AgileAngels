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

  // Switches to a new scene depending on which button is pressed
  @FXML
  private void openScene(ActionEvent event) throws IOException {
    Stage stage;
    Parent root;
    // If the equipment request button on the default scene is pressed,
    // switch to the equipment scene
    if (event.getSource() == equipmentButton) {
      stage = (Stage) equipmentButton.getScene().getWindow();
      root = FXMLLoader.load(getClass().getResource("views/equipment-view.fxml"));
    }
    // If the lab request button on the default scene is pressed,
    // switch to the lab scene
    else if (event.getSource() == labButton) {
      stage = (Stage) equipmentButton.getScene().getWindow();
      root = FXMLLoader.load(getClass().getResource("views/lab-view.fxml"));
    }
    // If the meal request button on the default scene is pressed,
    // switch to the meal scene
    else if (event.getSource() == foodButton) {
      stage = (Stage) foodButton.getScene().getWindow();
      root = FXMLLoader.load(getClass().getResource("views/mealRequest-view.fxml"));
    }
    // If the sanitation request button on the default scene is pressed,
    // switch to the sanitation scene
    else if (event.getSource() == sanitationButton) {
      stage = (Stage) sanitationButton.getScene().getWindow();
      root = FXMLLoader.load(getClass().getResource("views/sanitation-view.fxml"));
    }
    // If the gift request button on the default scene is pressed,
    // switch to the gift scene
    else if (event.getSource() == giftButton) {
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

  @FXML
  private void submitMeal() {
    confirm.setText(
        "Your order will be delivered by "
            + mealEmployeeText.getText()
            + " to room "
            + roomInput.getText()
            + " for "
            + mealDropdown.getText()
            + ". Special Instructions: "
            + restrictions.getText());
  }

  @FXML
  private void submitSanitation() {
    sanitationConfirmation.setText(
        "Thank you, "
            + sanitationEmployeeText.getText()
            + " will be sent to "
            + sanLocation.getText()
            + " to sanitize "
            + sanIssue.getText()
            + ".");
  }

  @FXML
  private void submitGift() {
    giftConfirm.setText(
        "Thank you, "
            + giftSender.getText()
            + ", "
            + giftEmployeeText.getText()
            + " will deliver "
            + giftType.getText()
            + " to "
            + giftRecipient.getText()
            + " soon. ");
  }

  @FXML
  private void submitEquipment() {
    equipmentConfirmation.setText(
        "Thank you, the "
            + eqptDropdown.getText()
            + " you requested will be delivered shortly to "
            + equipLocation.getText()
            + " by "
            + equipmentEmployeeText.getText()
            + ".");
  }

  @FXML
  private void submitLabTest() {
    labTestConfirmation.setText(
        "Thank you! Your "
            + labDropdown.getText()
            + " you requested will be delivered shortly to "
            + labTestLocation.getText()
            + " by "
            + labEmployeeText.getText()
            + ".");
  }

  @FXML
  private void setMealType(ActionEvent event) throws IOException {
    if (event.getSource() == chicken) {
      mealDropdown.setText("Chicken");
    }
    if (event.getSource() == steak) {
      mealDropdown.setText("Steak");
    }
    if (event.getSource() == salad) {
      mealDropdown.setText("Salad");
    }
  }

  @FXML
  private void setEquipmentType(ActionEvent event) throws IOException {
    if (event.getSource() == bed) {
      eqptDropdown.setText("Bed");
    }
    if (event.getSource() == recliner) {
      eqptDropdown.setText("Recliner");
    }
    if (event.getSource() == xray) {
      eqptDropdown.setText("X-Ray Machine");
    }
    if (event.getSource() == infusion) {
      eqptDropdown.setText("X-Ray Machine");
    }
  }

  @FXML
  private void setLabType(ActionEvent event) throws IOException {
    if (event.getSource() == blood) {
      labDropdown.setText("Blood Test");
    }
    if (event.getSource() == urine) {
      labDropdown.setText("Urine Test");
    }
    if (event.getSource() == tumor) {
      labDropdown.setText("Tumor Markup");
    }
  }

  @FXML
  private void setGiftType(ActionEvent event) throws IOException {
    if (event.getSource() == balloons) {
      giftType.setText("Balloons");
    }
    if (event.getSource() == flowers) {
      giftType.setText("Flowers");
    }
  }
}
