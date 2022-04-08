package edu.wpi.agileAngels.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class DashboardController extends MainController implements Initializable {

  @FXML Button dash1, dash2, dash3, dash4, dash5, dashL1, dashL2;
  @FXML Pane stackDash5, stackDash4, stackDash3, stackDash2, stackDash1, stackDashL1, stackDashL2;

  ArrayList<Pane> panes = new ArrayList<>();

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

  public void swapFloorDash(MouseEvent event) {

    panes.add(stackDash1);
    panes.add(stackDash2);
    panes.add(stackDash3);
    panes.add(stackDash4);
    panes.add(stackDash5);
    panes.add(stackDashL1);
    panes.add(stackDashL2);

    if (event.getSource() == dash1) {
      for (Pane pane : panes) {
        if (pane == stackDash1) {
          pane.setVisible(true);
        } else {
          pane.setVisible(false);
        }
      }
    }

    if (event.getSource() == dash2) {
      for (Pane pane : panes) {
        if (pane == stackDash2) {
          pane.setVisible(true);
        } else {
          pane.setVisible(false);
        }
      }
    }

    if (event.getSource() == dash3) {
      for (Pane pane : panes) {
        if (pane == stackDash3) {
          pane.setVisible(true);
        } else {
          pane.setVisible(false);
        }
      }
    }

    if (event.getSource() == dash4) {
      for (Pane pane : panes) {
        if (pane == stackDash4) {
          pane.setVisible(true);
        } else {
          pane.setVisible(false);
        }
      }
    }

    if (event.getSource() == dash5) {
      for (Pane pane : panes) {
        if (pane == stackDash5) {
          pane.setVisible(true);
        } else {
          pane.setVisible(false);
        }
      }
    }

    if (event.getSource() == dashL1) {
      for (Pane pane : panes) {
        if (pane == stackDashL1) {
          pane.setVisible(true);
        } else {
          pane.setVisible(false);
        }
      }
    }

    if (event.getSource() == dashL2) {
      for (Pane pane : panes) {
        if (pane == stackDashL2) {
          pane.setVisible(true);
        } else {
          pane.setVisible(false);
        }
      }
    }
  }

  public void unhover(MouseEvent event) {
    stackDash1.setVisible(false);
    stackDash2.setVisible(false);
    stackDash3.setVisible(false);
    stackDash4.setVisible(false);
    stackDash5.setVisible(false);
    stackDashL1.setVisible(false);
    stackDashL2.setVisible(false);
  }

  @FXML
  public void loadFloorMap(ActionEvent event) throws IOException {

    if (event.getSource() == dash1) {
      loadPage("../views/map-view.fxml", dash1);
    }

    if (event.getSource() == dash2) {
      loadPage("../views/map-view.fxml", dash2);
    }

    if (event.getSource() == dash3) {
      loadPage("../views/map-view.fxml", dash3);
    }

    if (event.getSource() == dash4) {
      loadPage("../views/map-view.fxml", dash4);
    }

    if (event.getSource() == dash5) {
      loadPage("../views/map-view.fxml", dash5);
    }

    if (event.getSource() == dashL1) {
      loadPage("../views/map-view.fxml", dashL1);
    }

    if (event.getSource() == dashL2) {
      loadPage("../views/map-view.fxml", dashL2);
    }
  }
}
