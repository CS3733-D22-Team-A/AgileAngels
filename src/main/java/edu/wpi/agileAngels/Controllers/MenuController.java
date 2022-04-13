package edu.wpi.agileAngels.Controllers;

import edu.wpi.agileAngels.Database.DBconnection;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class MenuController implements Initializable {

  @FXML Button back, close, equipRequest, viewRequest, map, homeImage, userButton, emergency;
  @FXML Pane menuPane;

  AppController appController = AppController.getInstance();

  public MenuController() {
    appController.setCurrentMenuController(this);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    close.setViewOrder(-1000);
    back.setViewOrder(-1001);
    menuPane.setViewOrder(-1002);
    homeImage.setViewOrder(-1003);
    userButton.setViewOrder(-1004);
  }

  @FXML
  private void closeApp() {
    DBconnection.shutdown();
    Platform.exit();
  }

  @FXML
  public void back() {
    appController.pageHistory.pop();
    appController.loadPage(appController.pageHistory.peek());
  }

  @FXML
  private void menuItem(ActionEvent event) throws IOException {
    if (event.getSource() == equipRequest) {
      appController.loadPage("/edu/wpi/agileAngels/views/equipment-view.fxml");
    }
    else if (event.getSource() == viewRequest) {
      appController.loadPage("/edu/wpi/agileAngels/views/equipmentEdit-view.fxml");
    } else if (event.getSource() == map) {
      appController.loadPage("/edu/wpi/agileAngels/views/map-view.fxml");
    }else if (event.getSource() == emergency) {
      appController.loadPage("/edu/wpi/agileAngels/views/emergency-view.fxml");
    }

  }

  public void profile(ActionEvent event) {}

  public void goHome(ActionEvent event) {
    appController.loadPage("/edu/wpi/agileAngels/views/home-view.fxml");
  }
}
