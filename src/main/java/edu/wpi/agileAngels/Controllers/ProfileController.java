package edu.wpi.agileAngels.Controllers;

import edu.wpi.agileAngels.Database.Employee;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

public class ProfileController implements Initializable {

  AppController appController = AppController.getInstance();
  @FXML Label userInitials, userName, userDepartment, userTitle, userPermission, userSupervisees;
  @FXML MenuButton colorType;

  String supervisees = "";

  public void initialize(URL location, ResourceBundle resources) {
    userInitials.setText(appController.getCurrentUser().initialsMaker());
    userName.setText(appController.getCurrentUser().getName());
    userDepartment.setText(appController.getCurrentUser().getDepartment());

    try {
      userTitle.setText((appController.getCurrentUser().getSupervisor().getName()));
      for (Employee e : appController.getCurrentUser().getSupervisees()) {
        supervisees += e.getName() + ", ";
      }

      userSupervisees.setText(supervisees);
    } catch (NullPointerException e) {
      userTitle.setText("Unsupervised");
    }
    userPermission.setText(String.valueOf(appController.getCurrentUser().getPermissionLevel()));
  }

  @FXML
  void orgChart() {
    appController.loadPage("/edu/wpi/agileAngels/views/orgChart-view.fxml");
  }

  public void updateColor() {
    appController.color = colorType.getText().toLowerCase(Locale.ROOT);
    appController.updateMenuColor();
    appController.loadPage("/edu/wpi/agileAngels/views/profile-view.fxml");
  }

  @FXML
  public void typeMenu(ActionEvent event) {
    MenuItem button = (MenuItem) event.getSource();
    colorType.setText(button.getText());
    updateColor();
  }
}
