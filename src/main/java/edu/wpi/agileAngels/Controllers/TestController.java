package edu.wpi.agileAngels.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

// test button on the front end
public class TestController {

  @FXML Button button;

  AppController appController = AppController.getInstance();

  public void doThing(ActionEvent event) {}
}
