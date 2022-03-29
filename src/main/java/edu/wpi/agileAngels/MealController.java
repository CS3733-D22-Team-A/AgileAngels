package edu.wpi.agileAngels;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
}
