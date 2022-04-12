package edu.wpi.agileAngels.Controllers;

import edu.wpi.agileAngels.Database.DBconnection;
import java.io.IOException;
import java.util.Stack;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppController {

  public static Stack<String> pageHistory = new Stack<>();

  private static AppController appController = null;
  private MenuController menuController;

  public AppController() {}

  public static AppController getInstance() {
    if (appController == null) {
      appController = new AppController();
    }
    return appController;
  }

  Stage primaryStage;

  public void init(Stage primaryStage) {
    this.primaryStage = primaryStage;
    loadPage("../views/login.fxml");
  }

  public void closeApp() {
    DBconnection.shutdown();
    Platform.exit();
  }

  public void loadPage(String view) {

    if (pageHistory.isEmpty()) {
      pageHistory.push(view);
    } else if (view != pageHistory.peek()) {
      pageHistory.push(view);
    }

    FXMLLoader loader = new FXMLLoader(getClass().getResource(view));
    Parent root = null;
    try {
      root = loader.load();
      Scene defaultScene = new Scene(root);

      defaultScene
          .getStylesheets()
          .add(
              "https://fonts.googleapis.com/css2?family=Roboto:wght@100;300;400;500;700;900&display=swap");

      primaryStage.setScene(defaultScene);
      primaryStage.setResizable(true);
      primaryStage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void back() {
    pageHistory.pop();
    loadPage(pageHistory.peek());
  }

  public void clearPage() {
    loadPage(pageHistory.peek());
  }

  private void profile() throws IOException {
    loadPage("../views/login.fxml");
  }

  private void goHome(ActionEvent event) {
    loadPage("../views/home-view.fxml");
  }

  public void setCurrentMenuController(MenuController menuController) {
    this.menuController = menuController;
  }

  public MenuController getMenuController() {
    return menuController;
  }
}
