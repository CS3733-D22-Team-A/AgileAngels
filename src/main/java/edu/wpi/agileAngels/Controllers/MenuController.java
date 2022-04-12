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

  @FXML Button back, close, equipRequest, viewRequest, map;
  @FXML Pane menuPane;

  AppController appController = AppController.getInstance();

  public MenuController() {
    appController.setCurrentMenuController(this);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    close.setViewOrder(-1000);
    back.setViewOrder(-1000);
    menuPane.setViewOrder(-1000);
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
  public void clearPage() throws IOException {
    //    loadPage(pageHistory.peek(), clear);
  }

  // three bars at the top of the page
  // TODO rework the menu bar
  @FXML
  private void menuItem(ActionEvent event) throws IOException {
    if (event.getSource() == equipRequest) {
      appController.loadPage("../views/equipment-view.fxml");
    }
    if (event.getSource() == viewRequest) {
      appController.loadPage("../views/equipmentEdit-view.fxml");
    }
    if (event.getSource() == map) {
      appController.loadPage("../views/map-view.fxml");
    }
  }

  public void goHome(ActionEvent event) {}

  public void profile(ActionEvent event) {}
}
