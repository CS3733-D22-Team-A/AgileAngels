package edu.wpi.agileAngels.Controllers;

import edu.wpi.agileAngels.Database.Employee;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class ProfileController implements Initializable {

  AppController appController = AppController.getInstance();
  @FXML Label userInitials, userName, userDepartment, userTitle, userPermission, userSupervisees;

  String supervisees;

  public void initialize(URL location, ResourceBundle resources) {
    userInitials.setText(appController.getCurrentUser().initialsMaker());
    userName.setText(appController.getCurrentUser().getName());
    userDepartment.setText(appController.getCurrentUser().getDepartment());

    try {
      userTitle.setText((appController.getCurrentUser().getSupervisor().getName()));
      for (Employee e : appController.getCurrentUser().getSupervisees()) {
        supervisees += e.getName() + ", ";
        System.out.println(e.getName());
      }
      userSupervisees.setText(supervisees);
    } catch (NullPointerException e) {
      userTitle.setText("Unsupervised");
    }
    userPermission.setText(String.valueOf(appController.getCurrentUser().getPermissionLevel()));
  }
}
