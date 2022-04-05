package edu.wpi.agileAngels;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MealController extends MainController {
  @FXML private MenuButton mealDropdown;
  @FXML private MenuItem chicken, steak, salad;
  @FXML private TextField roomInput, mealEmployeeText, mealStatus;

  @FXML private TextArea restrictions;

  @FXML private Label confirm;
  private RequestDAOImpl mealDAO;

  public void initialize(URL location, ResourceBundle resources) {
    HashMap<String, Request> mealData = new HashMap<String, Request>();
    mealDAO = new RequestDAOImpl("./Meal.csv", mealData, 0);
  }

  @FXML
  private void submitMeal() {
    if (mealDropdown.getText().isEmpty()
        || roomInput.getText().isEmpty()
        || mealDropdown.getText().isEmpty()) {
      confirm.setText("Please fill out all the required fields");
    } else {
      confirm.setText(
          "Your order will be delivered by "
              + mealEmployeeText.getText()
              + " to room "
              + roomInput.getText()
              + " for "
              + mealDropdown.getText()
              + ". Special Instructions: "
              + restrictions.getText());
      MealRequest request =
          new MealRequest(
              mealEmployeeText.getText(),
              roomInput.getText(),
              mealDropdown.getText(),
              mealStatus.getText(),
              //  "",
              restrictions.getText());
      mealDAO.addRequest(request);
    }
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
  private void clearPage() throws IOException, InterruptedException {
    loadPage("views/mealRequest-view.fxml", confirm);
  }
}
