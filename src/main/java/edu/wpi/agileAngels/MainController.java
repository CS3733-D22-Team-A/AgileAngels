package edu.wpi.agileAngels;

import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainController {

  @FXML private Button back, close, equipRequest, viewRequest, map;
  @FXML private MenuItem equipmentMenu, labMenu, sanitationMenu, mealMenu, giftMenu;

  @FXML AnchorPane anchor;

  // public Stack<String> pageHistory = new Stack<>();

  @FXML
  private void closeApp() {
    Platform.exit();
  }

  void loadPage(String view, Control item) throws IOException {

    // pageHistory.push("views/maps.fxml");
    System.out.println("test");
    Stage stage;
    Parent root;
    stage = (Stage) item.getScene().getWindow();
    root = FXMLLoader.load(getClass().getResource(view));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.setResizable(false);
    stage.show();
  }

  @FXML
  private void back(ActionEvent event) throws IOException {

    loadPage("views/home-view.fxml", back);
  }

  @FXML
  private void menuItem(ActionEvent event) throws IOException, InterruptedException {
    if (event.getSource() == equipRequest) {
      loadPage("views/equipment-view.fxml", close);
    }
    if (event.getSource() == viewRequest) {
      loadPage("views/home-view.fxml", close);
    }
    if (event.getSource() == map) {
      loadPage("views/maps.fxml", close);
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

  static double pos = 0;

  public double getPositionX(Node node) {
    if (node.getParent() == anchor) {
      double position = pos + node.getLayoutX();
      pos = 0;
      return position;
    } else {
      pos += node.getLayoutX();
      return getPositionX(node.getParent());
    }
  }

  public double getPositionY(Node node) {
    if (node.getParent() == anchor) {
      double position = pos + node.getLayoutY();
      pos = 0;
      return position;
    } else {
      pos += node.getLayoutY();
      return getPositionY(node.getParent());
    }
  }
}
