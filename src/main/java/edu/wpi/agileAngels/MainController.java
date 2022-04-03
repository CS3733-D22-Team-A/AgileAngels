package edu.wpi.agileAngels;

import java.io.IOException;
import java.util.Stack;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainController {

  @FXML private Button back, close, equipRequest, viewRequest, map;
  @FXML private MenuItem equipmentMenu, labMenu, sanitationMenu, mealMenu, giftMenu;

  @FXML AnchorPane anchor;

  public static Stack<String> pageHistory = new Stack<>();

  @FXML
  private void closeApp() {

    Platform.exit();
  }

  void loadPage(String view, Control item) throws IOException {

    if (item == back) {
    } else if (pageHistory.empty()) {
      pageHistory.push("views/home-view.fxml");
      pageHistory.push(view);
    } else if (view != pageHistory.peek()) {
      pageHistory.push(view);
    }

    Stage stage;
    Parent root;
    stage = (Stage) item.getScene().getWindow();
    root = FXMLLoader.load(getClass().getResource(view));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.setResizable(true);
    stage.show();
  }

  @FXML
  private void back(ActionEvent event) throws IOException {
    pageHistory.pop();
    loadPage(pageHistory.peek(), back);
  }

  @FXML
  private void menuItem(ActionEvent event) throws IOException, InterruptedException {
    if (event.getSource() == equipRequest) {
      loadPage("views/equipment-view.fxml", close);
    }
    if (event.getSource() == viewRequest) {
      loadPage("views/equipmentEdit-view.fxml", close);
    }
    if (event.getSource() == map) {
      loadPage("views/map-view.fxml", close);
    }
  }

  @FXML
  private void profile() throws IOException, InterruptedException {
    loadPage("views/home-view.fxml", close);
  }

  @FXML
  private void goHome(ActionEvent event) throws IOException, InterruptedException {
    loadPage("views/home-view.fxml", close);
  }
}
