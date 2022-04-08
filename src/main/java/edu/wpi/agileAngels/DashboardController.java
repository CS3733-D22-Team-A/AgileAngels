package edu.wpi.agileAngels;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class DashboardController extends MainController implements Initializable {

  @FXML public Button dash1, dash2, dash3, dash4, dash5, dashL1, dashL2;
  @FXML
  public Pane stackDash5, stackDash4, stackDash3, stackDash2, stackDash1, stackDashL1, stackDashL2;



  public void swapFloorDash(ActionEvent event) {

    if (event.getSource() == dash1) {
      stackDash1.setVisible(true);
    }

    if (event.getSource() == dash2) {
      stackDash2.setVisible(true);
    }

    if (event.getSource() == dash3) {
      stackDash3.setVisible(true);
    }

    if (event.getSource() == dash4) {
      stackDash4.setVisible(true);
    }

    if (event.getSource() == dash5) {
      stackDash5.setVisible(true);
    }

    if (event.getSource() == dashL1) {
      stackDashL1.setVisible(true);
    }

    if (event.getSource() == dashL2) {
      stackDashL2.setVisible(true);
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    stackDash1.setVisible(false);
    stackDash2.setVisible(false);
    stackDash3.setVisible(false);
    stackDash4.setVisible(false);
    stackDash5.setVisible(false);
    stackDashL1.setVisible(false);
    stackDashL2.setVisible(false);
  }
}
