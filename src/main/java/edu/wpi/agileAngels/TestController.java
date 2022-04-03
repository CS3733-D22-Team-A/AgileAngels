package edu.wpi.agileAngels;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class TestController extends MainController {
  @FXML Pane drop;
  @FXML Button drop1;
  @FXML Label dropText;
  @FXML Button op1, op2, op3, op4;

  public void dropDownTest() throws IOException {
    drop.setVisible(true);
  }

  public void closeMenu(MouseEvent mouseEvent) {
    drop.setVisible(false);
  }

  public void menuItemSelected(ActionEvent event) {
    dropText.setTextFill(Color.rgb(0, 0, 0));
    if (event.getSource() == op1) {
      dropText.setText("Option 1");
    } else if (event.getSource() == op2) {
      dropText.setText("Option 2");
    } else if (event.getSource() == op3) {
      dropText.setText("Option 3");
    } else if (event.getSource() == op4) {
      dropText.setText("Option 4");
    }
    drop.setVisible(false);
  }
}
