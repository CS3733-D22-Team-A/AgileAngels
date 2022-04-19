package edu.wpi.agileAngels.Controllers;

import edu.wpi.agileAngels.Database.DBconnection;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class MenuController implements Initializable {

  private @FXML Button back,
      close,
      serviceRequest,
      labRequest,
      map,
      dashboard,
      homeImage,
      userButton,
      emergency,
      test,
      pageTitle;

  private @FXML Pane menuPane;
  private @FXML AnchorPane anchor;

  AppController appController = AppController.getInstance();

  public MenuController() {
    appController.setCurrentMenuController(this);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    anchor.setPickOnBounds(false);
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
  private void menuItem(ActionEvent event) {
    if (event.getSource() == serviceRequest) {
      appController.loadPage("/edu/wpi/agileAngels/views/serviceRequest-view.fxml");
    } else if (event.getSource() == emergency) {
      appController.loadPage("/edu/wpi/agileAngels/views/emergency-view.fxml");
    } else if (event.getSource() == dashboard) {
      appController.loadPage("/edu/wpi/agileAngels/views/NEWdashboard.fxml");
    }
  }

  public void changeTitle(String page) {
    pageTitle.setText(page);
  }

  public void hideButtons() {
    back.setVisible(false);
    close.setVisible(false);
  }

  public void profile(ActionEvent event) {}

  public void goHome(ActionEvent event) {
    appController.loadPage("/edu/wpi/agileAngels/views/home-view.fxml");
  }
}
