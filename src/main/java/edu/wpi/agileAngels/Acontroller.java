package edu.wpi.agileAngels;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class Acontroller {

  @FXML private Button equipmentButton, labButton, sanitationButton, giftButton, homeButton;
  @FXML private ChoiceBox<String> labCHOICE;

  ObservableList<String> labList =
      FXCollections.observableArrayList("Blood Test", "Urine Test", "Tumor Marker");

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
    // If the sanitation request button on the default scene is pressed,
    // switch to the sanitation scene
    else if (event.getSource() == sanitationButton) {
      stage = (Stage) sanitationButton.getScene().getWindow();
      root = FXMLLoader.load(getClass().getResource("views/sanitation-view.fxml"));
    }
    // If the home button is pressed, switch to the default scene
    else {
      stage = (Stage) homeButton.getScene().getWindow();
      root = FXMLLoader.load(getClass().getResource("views/default-view.fxml"));
    }

    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    System.out.println(labCHOICE);
  }

  @FXML
  public void initialize() {
    // labBox.setValue("Blood Test");
  }

  public void getTest(javafx.scene.input.MouseEvent mouseEvent) {}

  /*
   @FXML
   public void initialize() {
     System.out.println(labBox);
     System.out.println(labBox.getItems());
     labBox.getItems().addAll("Blood Test", "Urine Test", "Tumor Marker");
   }

  */
}
