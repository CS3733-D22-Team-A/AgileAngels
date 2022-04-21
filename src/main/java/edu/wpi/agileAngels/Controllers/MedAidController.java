package edu.wpi.agileAngels.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

public class MedAidController implements Initializable {
  public Pane treatment1, treatment2, treatment3;
  AppController appController = AppController.getInstance();

  private void MedAidViewController() {}

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    // appController.addPropertyChangeListener(this);
    treatment1.setVisible(false);
    treatment2.setVisible(false);
    treatment3.setVisible(false);
  }

  public void submit(ActionEvent actionEvent) {
    treatment1.setVisible(true);
    treatment2.setVisible(true);
    treatment3.setVisible(true);
  }

  public void clear(ActionEvent actionEvent) {
    treatment1.setVisible(false);
    treatment2.setVisible(false);
    treatment3.setVisible(false);
  }
}
