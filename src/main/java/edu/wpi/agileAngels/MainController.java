package edu.wpi.agileAngels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class MainController {

  @FXML Button back, close, clear, equipRequest, viewRequest, map;
  @FXML Button userButton, userIDIcon, dropButton, op1, op2, op3, op4;
  @FXML Label dropdownButtonText, op1Label, op2Label, op3Label, op4Label;

  @FXML AnchorPane anchor;

  @FXML Pane menuPane, dropButtonPane;

  public static Stack<String> pageHistory = new Stack<>();
  private static String username;
  private static String userID;
  public static Boolean loggedIn = false;

  @FXML
  private void closeApp() {
    DBconnection.shutdown();
    Platform.exit();
  }

  ArrayList<Circle> newList = new ArrayList<Circle>();

  public void loadPage(String view, Control item) throws IOException {

    if (item == back) {
    } else if (pageHistory.empty()) {
      pageHistory.push("views/home-view.fxml");
      pageHistory.push(view);
    } else if (view != pageHistory.peek()) {
      pageHistory.push(view);
    }

    System.out.println(pageHistory);
    Stage stage;
    Parent root;
    stage = (Stage) item.getScene().getWindow();
    root = FXMLLoader.load(getClass().getResource(view));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.setResizable(true);
    stage.show();
  }

  public void loadMapPage(String view, Control item, Group group) throws IOException {

    System.out.println(pageHistory);
    Stage stage;
    Parent root;
    stage = (Stage) item.getScene().getWindow();
    root = FXMLLoader.load(getClass().getResource(view));
    group.getChildren().set(0, root);
    Scene scene = new Scene(group);
    stage.setScene(scene);
    stage.setResizable(true);
    stage.show();
  }

  @FXML
  public void back() throws IOException {
    pageHistory.pop();
    loadPage(pageHistory.peek(), back);
  }

  @FXML
  private void clearPage() throws IOException {
    System.out.println("test");
    loadPage(pageHistory.peek(), clear);
  }

  public void setUsername(String user) {
    username = user;
    userID = String.valueOf(user.toUpperCase().charAt(0));
  }

  public String getUsername() {
    return username;
  }

  public String getUserID() {
    return userID;
  }

  @FXML
  private void menuItem(ActionEvent event) throws IOException {
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
  private void profile() throws IOException {
    loadPage("views/login.fxml", close);
  }

  @FXML
  private void goHome(ActionEvent event) throws IOException {
    loadPage("views/home-view.fxml", close);
  }

  public void enterDropdown() {
    menuPane.setViewOrder(-1);
    dropButtonPane.setViewOrder(-1);
    dropButton.setVisible(false);
    menuPane.setVisible(true);
  }

  public void closeMenu() {

    try {
      menuPane.setVisible(false);
      dropButton.setVisible(true);
    } catch (NullPointerException e) {
    }
  }

  @FXML
  private void menuItemSelected(ActionEvent event) {
    dropdownButtonText.setTextFill(Color.rgb(0, 0, 0));
    if (event.getSource() == op1) {
      dropdownButtonText.setText(op1Label.getText());
    }
    if (event.getSource() == op2) {
      dropdownButtonText.setText(op2Label.getText());
    }
    if (event.getSource() == op3) {
      dropdownButtonText.setText(op3Label.getText());
    }
    if (event.getSource() == op4) {
      dropdownButtonText.setText(op4Label.getText());
    }
    closeMenu();
  }
}
