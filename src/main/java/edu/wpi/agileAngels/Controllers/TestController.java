package edu.wpi.agileAngels.Controllers;

import edu.wpi.agileAngels.Database.DBconnection;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.textfield.TextFields;

// test button on the front end
public class TestController extends MainController implements Initializable {

  @FXML private TextField input;

  @FXML private AnchorPane main;

  @FXML
  public void switchConnection(ActionEvent event) {
    DBconnection.switchConnection();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    String[] words = {"Test", "This", "Text"};
    TextFields.bindAutoCompletion(input, words);
  }
}
