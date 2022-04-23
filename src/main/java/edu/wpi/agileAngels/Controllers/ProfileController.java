package edu.wpi.agileAngels.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class ProfileController implements Initializable {

  AppController appController = AppController.getInstance();
  @FXML Label userInitials, userName, userDepartment, userTitle, userPermission;

  public void initialize(URL location, ResourceBundle resources) {
    userInitials.setText(appController.getCurrentUser().initialsMaker());
    userName.setText(appController.getCurrentUser().getName());
  }
}
