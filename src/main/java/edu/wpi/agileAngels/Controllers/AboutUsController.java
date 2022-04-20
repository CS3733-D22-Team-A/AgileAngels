package edu.wpi.agileAngels.Controllers;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class AboutUsController implements Initializable, PropertyChangeListener {

  @FXML Button basharButton;

  AppController appController = AppController.getInstance();

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    appController.addPropertyChangeListener(this);
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    String changeType = evt.getPropertyName();
    int newValue = (int) evt.getNewValue();
  }
}
