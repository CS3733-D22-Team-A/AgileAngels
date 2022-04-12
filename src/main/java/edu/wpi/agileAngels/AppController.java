package edu.wpi.agileAngels;

import edu.wpi.agileAngels.Database.DBconnection;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Stack;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppController {

  public static Stack<String> pageHistory = new Stack<>();

  private static AppController appController = null;

  public AppController() {}

  public static AppController getInstance() throws SQLException {
    if (appController == null) {
      appController = new AppController();
    }
    return appController;
  }

  Stage primaryStage;

  public void setPrimaryStage(Stage primaryStage) {
    this.primaryStage = primaryStage;
  }

  @FXML
  private void closeApp() {
    DBconnection.shutdown();
    Platform.exit();
  }

  public void loadPage(String view) throws IOException {

    if (pageHistory.isEmpty()) {
      pageHistory.push(view);
    } else if (view != pageHistory.peek()) {
      pageHistory.push(view);
    }

    FXMLLoader loader = new FXMLLoader(getClass().getResource(view));
    Parent root = loader.load();
    Scene defaultScene = new Scene(root);

    defaultScene
        .getStylesheets()
        .add(
            "https://fonts.googleapis.com/css2?family=Roboto:wght@100;300;400;500;700;900&display=swap");

    primaryStage.setScene(defaultScene);
    primaryStage.setResizable(false);
    primaryStage.show();
  }

  @FXML
  public void back() throws IOException {
    pageHistory.pop();
    loadPage(pageHistory.peek());
  }

  @FXML
  public void clearPage() throws IOException {
    loadPage(pageHistory.peek());
  }

  @FXML
  private void profile() throws IOException {
    loadPage("../views/login.fxml");
  }

  @FXML
  private void goHome(ActionEvent event) throws IOException {
    loadPage("../views/home-view.fxml");
  }
}
