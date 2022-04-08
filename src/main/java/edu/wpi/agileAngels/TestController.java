package edu.wpi.agileAngels;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

// test button on the front end
public class TestController extends MainController {

  Button button = new Button();
  @FXML AnchorPane anchor;
  @FXML Button testButton;

  public void newCircle(ActionEvent event) {
    button.setText("hello");
    button.setLayoutX(100);
    button.setLayoutY(100);
    button.setOnAction(
        (ActionEvent event2) -> {
          changeColor(button);
        });
    anchor.getChildren().add(button);
  }

  private void changeColor(Button button) {
    System.out.println(button.getLayoutX());
    testButton.setTextFill(Color.rgb(255, 0, 0));
  }
}
