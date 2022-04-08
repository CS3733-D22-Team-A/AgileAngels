package edu.wpi.agileAngels;

import java.net.URL;
import java.sql.SQLException;
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

  public void initialize(URL location, ResourceBundle resources) throws SQLException {
    HashMap<String, Request> mealData = new HashMap<String, Request>();
    mealDAO = new RequestDAOImpl("./Meal.csv", mealData, 0);
  }

  @FXML
  private void submitMeal() {
    String dropDown = dropdownButtonText.getText();
    String loc = roomInput.getText();
    String emp = mealEmployeeText.getText();
    String status = mealStatus.getText();
    String restr = restrictions.getText();
    if (dropdownButtonText.getText().isEmpty()
        || roomInput.getText().isEmpty()
        || dropdownButtonText.getText().isEmpty()) {
      confirm.setText("Please fill out all the required fields");
    } else {
      confirm.setText(
          "Your order "
              + dropDown
              + " will be delivered by "
              + emp
              + " to room "
              + loc
              + ". Special Instructions: "
              + restr);
      Request request = new Request("", emp, loc, dropDown, status, restr, "", "");
      // mealDAO.addRequest(request);
    }
  }

  private void addMealRequest() {}
}
