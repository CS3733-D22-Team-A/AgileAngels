package edu.wpi.agileAngels.Controllers;

import edu.wpi.agileAngels.Database.DBconnection;
import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MenuController {

  @FXML HBox menuButtons;
  @FXML Button back, close, clear, equipRequest, viewRequest, map;

  public MenuController() {
    close.setViewOrder(-1000);
    back.setViewOrder(-1000);
    menuButtons.setViewOrder(-1000);
  }

  @FXML
  private void closeApp() {
    DBconnection.shutdown();
    Platform.exit();
  }

  // TODO: why is there 2 load pages
  public void loadPage(String view, Control item) throws IOException {

    //    if (item == back) {
    //    } else if (pageHistory.empty()) {
    //      pageHistory.push("../views/home-view.fxml");
    //      pageHistory.push(view);
    //    } else if (view != pageHistory.peek()) {
    //      pageHistory.push(view);
    //    }

    Stage stage;
    Parent root;
    stage = (Stage) close.getScene().getWindow();
    root = FXMLLoader.load(getClass().getResource(view));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.setResizable(true);
    stage.show();
  }

  @FXML
  public void back() throws IOException {
    //    pageHistory.pop();
    //    loadPage(pageHistory.peek(), back);
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
      loadPage("../views/equipment-view.fxml", close);
    }
    if (event.getSource() == viewRequest) {
      loadPage("../views/equipmentEdit-view.fxml", close);
    }
    if (event.getSource() == map) {
      loadPage("../views/map-view.fxml", close);
    }
  }

  public void goHome(ActionEvent event) {}

  public void profile(ActionEvent event) {}
}
