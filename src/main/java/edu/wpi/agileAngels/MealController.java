package edu.wpi.agileAngels;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class MealController extends MainController {
  @FXML private Button mealDropdown, pasta, steak, burger, pizza;
  @FXML private TextField roomInput, mealEmployeeText, mealStatus, restrictions;
  @FXML private Label dropdownButtonText, confirm, pastaLabel, stealLabel, burgerLabel, pizzaLabel;
  @FXML Pane drop, drop2;

  private RequestDAOImpl mealDAO;

  public void initialize(URL location, ResourceBundle resources) {
    HashMap<String, Request> mealData = new HashMap<String, Request>();
    mealDAO = new RequestDAOImpl("./Meal.csv", mealData, 0);
  }

  @FXML
  private void submitMeal() {
    if (dropdownButtonText.getText().isEmpty()
        || roomInput.getText().isEmpty()
        || dropdownButtonText.getText().isEmpty()) {
      confirm.setText("Please fill out all the required fields");
    } else {
      confirm.setText(
          "Your order will be delivered by "
              + mealEmployeeText.getText()
              + " to room "
              + roomInput.getText()
              + " for "
              + dropdownButtonText.getText()
              + ". Special Instructions: "
              + restrictions.getText());
      MealRequest request =
          new MealRequest(
              mealEmployeeText.getText(),
              roomInput.getText(),
              dropdownButtonText.getText(),
              mealStatus.getText(),
              restrictions.getText());
      mealDAO.addRequest(request);
    }
  }
}
