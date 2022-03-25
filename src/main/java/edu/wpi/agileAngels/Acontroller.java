package edu.wpi.agileAngels;

import java.io.IOException;
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
      homeButton,
      foodButton,
      giftButton,
      submitGift,
      submitSanitation;
  @FXML private MenuButton mealDropdown, eqptDropdown, giftType, labDropdown;
  @FXML private MenuItem blood, urine, tumor;
  @FXML
  private TextField roomInput,
      sanIssue,
      sanLocation,
      giftSender,
      giftRecipient,
      giftMessage,
      equipLocation,
      labTestLocation;

  @FXML private TextArea restrictions;
  @FXML
  private Label confirm,
      sanitationConfermation,
      equipmentConfirmation,
      giftConfirm,
      labTestConfirmation;;

  private String meal = "null";
  private String equipment = "";

  // Lab Request Menu Component
  private String labTest = "";

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
  private void setChicken() {
    mealDropdown.setText("Chicken");
    meal = "Chicken";
  }

  @FXML
  private void setSteak() {
    mealDropdown.setText("steak");
    meal = "Steak";
  }

  @FXML
  private void setSalad() {
    mealDropdown.setText("Salad");
    meal = "Salad";
  }

  @FXML
  private void placeOrder() {
    if (meal == "null") {
      confirm.setText("please select a meal");
    } else {
      confirm.setText(
          "Order confirmed to room "
              + roomInput.getText()
              + " for "
              + meal
              + "\n"
              + "Special Instructions: "
              + restrictions.getText());
    }
  }

  @FXML
  private void submitSanitation() {
    sanitationConfermation.setText(
        "thank you someone will be sent to "
            + sanLocation.getText()
            + " to sanitize "
            + sanIssue.getText());
  }

  @FXML
  private void submitGift() {
    giftConfirm.setText(
        "Thank you, "
            + giftSender.getText()
            + ",\n "
            + giftRecipient.getText()
            + " will receive "
            + "\n "
            + "their gift soon. ");
  }

  @FXML
  private void submitEquipment() {
    equipmentConfirmation.setText(
        "Thank you, the "
            + equipment
            + " you requested will be delivered shortly to "
            + equipLocation.getText()
            + ".");
  }

  @FXML
  private void setBed() {
    eqptDropdown.setText("Bed");
    equipment = "Bed";
  }

  @FXML
  private void setRecliner() {
    eqptDropdown.setText("Recliner");
    equipment = "Recliner";
  }

  @FXML
  private void setXray() {
    eqptDropdown.setText("X-Ray Machine");
    equipment = "X-Ray Machine";
  }

  @FXML
  private void setInfusion() {
    eqptDropdown.setText("Infusion Pump");
    equipment = "Infusion Pump";
  }

  @FXML
  private void setLabType(ActionEvent event) throws IOException {
    if (event.getSource() == blood) {
      labDropdown.setText("Blood");
    }
    if (event.getSource() == urine) {
      labDropdown.setText("Urine");
    }
    if (event.getSource() == tumor) {
      labDropdown.setText("Tumor");
    }
  }

  @FXML
  private void submitLabTest() {
    labTestConfirmation.setText(
        "Thank you! Your "
            + labTest
            + " you requested will be delivered shortly to "
            + labTestLocation.getText()
            + ".");
  }
}
