package edu.wpi.agileAngels.Controllers;

import edu.wpi.agileAngels.Database.DBconnection;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

// test button on the front end
public class TestController implements Initializable {

  // Remove these to use test controler, this dosn't work here for some reason
  // :shrug:
  // @FXML public TextField searchbar;

  // @FXML public AnchorPane main;
  //

  @FXML
  public void switchConnection(ActionEvent event) {
    DBconnection.switchConnection();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // String[] words = {"Test", "This", "Text"};
    // TextFields.bindAutoCompletion(searchbar, words);
  }
}
