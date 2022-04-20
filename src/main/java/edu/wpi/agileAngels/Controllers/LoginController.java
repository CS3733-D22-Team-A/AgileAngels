package edu.wpi.agileAngels.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class LoginController implements Initializable {
  @FXML private TextField username;
  @FXML private Label invalid;
  @FXML private Button login;
  @FXML private PasswordField passwordBox;

  AppController appController = AppController.getInstance();

  private EmployeeManager employeeManager = EmployeeManager.getInstance();

  public LoginController() throws SQLException {}

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    employeeManager.addEmployee("Admin", "Admin");
    employeeManager.addEmployee("Nurse", "Nurse");
    employeeManager.addEmployee("Justin", "Password");
    employeeManager.addEmployee("Staff", "Staff");
    employeeManager.addEmployee("", "");
  }

  /**
   * Is the login textfields that take in a username and password and checks to see if it matches
   * with anything from the database / hashmap.
   *
   * @throws IOException
   */
  @FXML
  private void login() throws IOException {

    if (employeeManager.getName(username.getText())
        && passwordBox.getText().equals(employeeManager.getPassword(username.getText()))) {
      appController.loadPage("/edu/wpi/agileAngels/views/NEWdashboard.fxml");
    } else {
      invalid.setTextFill(Color.rgb(220, 80, 80));
      invalid.setText("Invalid username or password.");
      passwordBox.clear();
    }
  }

  /**
   * Duplicate enter code, but will work fine for now but won't be in diagram.
   *
   * @param event
   * @throws IOException
   */
  @FXML
  private void loginWithEnter(KeyEvent event) throws IOException {
    if (event.getCode().equals(KeyCode.ENTER)) {
      login();
    }
  }

  /**
   * Creates the initial(s) of the given string. If only 1 name is given, 1 intial will return. If
   * 2+ names, 2 initials. Has no defense against illegal characters...
   *
   * @return The initial(s) of the given string
   */
  public String initialsMaker(String name) {
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

  public void closeApp(ActionEvent event) {
    appController.closeApp();
  }

  public void clearPage(ActionEvent event) throws IOException {
    appController.clearPage();
  }
}

  /*
  Maybe make a way to turn password text into asterisks as it's being typed, and a way to turn it back when "show pw" is clicked.
   */
