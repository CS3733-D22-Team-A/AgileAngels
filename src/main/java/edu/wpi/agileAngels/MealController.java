package edu.wpi.agileAngels;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class MealController extends MainController {
  @FXML private MenuButton mealDropdown;
  @FXML private MenuItem chicken, steak, salad;
  @FXML private TextField roomInput;

  @FXML private TextArea restrictions;

  @FXML private Label confirm;

  @FXML
  private void submitMeal() {
    confirm.setText(
        "Order confirmed to room "
            + roomInput.getText()
            + " for "
            + mealDropdown.getText()
            + ". Special Instructions: "
            + restrictions.getText());
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
  private void clearPage() throws IOException {

    Stage stage;
    Parent root;

    stage = (Stage) homeButton.getScene().getWindow();
    root = FXMLLoader.load(getClass().getResource("views/mealRequest-view.fxml"));

    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
}
