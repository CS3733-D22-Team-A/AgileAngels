package edu.wpi.agileAngels;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javax.swing.*;

public class LoginController extends MainController {
  @FXML private TextField username, passwordBox;
  @FXML private Label invalid;
  @FXML private Button login;

  @FXML
  private void login() throws IOException {
    if (username.getText().equals(passwordBox.getText()) && !username.getText().isEmpty()) {
      loggedIn = true;
      setUsername(username.getText());
      loadPage("views/home-view.fxml", login);
    } else {
      invalid.setTextFill(Color.rgb(220, 80, 80));
      invalid.setText("Invalid username or password:\nPlease try again");
    }
  }

  /**
   * Creates the initial(s) of the given string. If only 1 name is given, 1 intial will return. If
   * 2+ names, 2 initials. Has no defense against illegal characters...
   *
   * @return The initial(s) of the given string
   */
  public String intialsMaker(String name) {
    String initials;

    // Is this name empty? Initials ain't applicable...
    if (name.isEmpty()) {
      initials = "N/A";
    }

    // Not empty? Not illegal? Run the actual method.
    else {

      // If the name has a space, 2+ names were given and need to be broken up.
      char firstInitial = name.charAt(0);
      if (name.contains(" ")) {
        int lastSpaceIndex = name.lastIndexOf(" ");
        char secondInitial = name.charAt(lastSpaceIndex + 1);

        initials = "" + firstInitial + secondInitial;
      }

      // Else, 1 name was given, throw the first character.
      else {
        initials = "" + firstInitial;
      }
    }
    return initials;
  }

  /*
  Maybe make a way to turn password text into asterisks as it's being typed, and a way to turn it back when "show pw" is clicked.
   */
}
