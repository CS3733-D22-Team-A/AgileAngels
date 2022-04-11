package edu.wpi.agileAngels.Controllers;

import com.jfoenix.controls.JFXToggleButton;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

// brings you to pages
public class ServiceRequestController extends MainController implements Initializable {

  @FXML Button equipmentRequest, labRequest, sanRequest, mealRequest, giftRequest, testButton;
  @FXML private JFXToggleButton toggleButton;

  // These are/will be the hidden labels for the toggleable switch.
  @FXML private Label harmoniLabel, jakobLabel, justinLabel, danielLabel, basharLabel, jakobLabel2;

  /**
   * showCreators() will make invisible labels displaying people's names visible again. And turn
   * them off. "I don't know why, I don't want to know why" - TF2 source code. btw don't touch this,
   * or I'll throw hands. ~<3
   */
  public void showCreators(ActionEvent event) {
    ArrayList<Label> creatorLabels =
        new ArrayList<>(
            Arrays.asList(
                harmoniLabel, jakobLabel, justinLabel, danielLabel, basharLabel, jakobLabel2));
    if (toggleButton.isSelected()) {
      // Will turn off names.
      creatorLabels.forEach((coder) -> coder.setVisible(true));
    } else {
      // Will turn on the names.
      creatorLabels.forEach((coder) -> coder.setVisible(false));
    }
  }

  /**
   * This will bring you to the various request pages when a button is clicked.
   *
   * @param event
   * @throws IOException
   */
  public void requestButton(ActionEvent event) throws IOException {
    if (event.getSource() == equipmentRequest) {
      loadPage("../views/equipment-view.fxml", equipmentRequest);
    }
    if (event.getSource() == labRequest) {
      loadPage("../views/lab-view.fxml", equipmentRequest);
    }
    if (event.getSource() == sanRequest) {
      loadPage("../views/sanitation-view.fxml", equipmentRequest);
    }
    if (event.getSource() == mealRequest) {
      loadPage("../views/mealRequest-view.fxml", equipmentRequest);
    }
    if (event.getSource() == giftRequest) {
      loadPage("../views/gifts-view.fxml", equipmentRequest);
    }
    if (event.getSource() == testButton) {
      loadPage("../views/test-view.fxml", equipmentRequest);
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {}
}
