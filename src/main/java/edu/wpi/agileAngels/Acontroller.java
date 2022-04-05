package edu.wpi.agileAngels;

import javafx.application.Platform;
import javafx.fxml.FXML;

public class Acontroller extends MainController {

  // Switches to a new scene depending on which button is pressed

  @FXML
  private void closeApp() {
    Platform.exit();
  }
}
