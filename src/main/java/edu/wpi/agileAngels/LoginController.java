package edu.wpi.agileAngels;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javax.swing.*;

// TODO needs to be implemented / written (LATER TO DO)
public class LoginController extends MainController {
  @FXML private TextField username, passwordBox;
  @FXML private Label invalid;
  @FXML private Button login;

  @FXML
  private void login() throws IOException {
    if (username.getText().equals(passwordBox.getText()) && !username.getText().isEmpty()) {
      loggedIn = true;
      setUsername(username.getText());
      pageHistory.pop();
      loadPage(pageHistory.peek(), login);
    } else {
      invalid.setTextFill(Color.rgb(220, 80, 80));
      invalid.setText("Invalid username or password:\nPlease try again");
    }
  }
}
