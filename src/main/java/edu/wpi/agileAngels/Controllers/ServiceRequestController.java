package edu.wpi.agileAngels.Controllers;

import com.jfoenix.controls.JFXToggleButton;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
public class ServiceRequestController implements Initializable, PropertyChangeListener {

  @FXML
  Button equipmentRequest,
      labRequest,
      sanRequest,
      mealRequest,
      giftRequest,
      mbgRequest,
      maintenanceRequest,
      morgueRequest,
      patientTransportRequest;
  @FXML private JFXToggleButton toggleButton;

  // These are/will be the hidden labels for the toggleable switch.
  @FXML private Label harmoniLabel, jakobLabel, justinLabel, danielLabel, basharLabel, jakobLabel2;

  AppController appController = AppController.getInstance();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    appController.addPropertyChangeListener(this);
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    String changeType = evt.getPropertyName();
    int newValue = (int) evt.getNewValue();
    appController.displayAlert();
  }

  /**
   * showCreators() will make invisible labels displaying people's names visible again. And turn
   * them off. "I don't know why, I don't want to know why" - TF2 source code. btw don't touch this,
   * or I'll throw hands. ~<3
   */
  public void showCreators(ActionEvent event) {
    ArrayList<Label> creatorLabels =
        new ArrayList<>(
            Arrays.asList(
                harmoniLabel, justinLabel, danielLabel, basharLabel
                /** ,jakobLabel,jakobLabel2 */
                ));
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
  public void requestButton(ActionEvent event) {
    if (event.getSource() == equipmentRequest) {
      appController.loadPage("/edu/wpi/agileAngels/views/equipment-view.fxml");
    } else if (event.getSource() == labRequest) {
      appController.loadPage("/edu/wpi/agileAngels/views/lab-view.fxml");
    } else if (event.getSource() == sanRequest) {
      appController.loadPage("/edu/wpi/agileAngels/views/sanitation-view.fxml");
    } else if (event.getSource() == mealRequest) {
      appController.loadPage("/edu/wpi/agileAngels/views/mealRequest-view.fxml");
    } else if (event.getSource() == giftRequest) {
      appController.loadPage("/edu/wpi/agileAngels/views/gifts-view.fxml");
    } else if (event.getSource() == maintenanceRequest) {
      appController.loadPage("/edu/wpi/agileAngels/views/maintenance-view.fxml");
    } else if (event.getSource() == mbgRequest) {
      appController.loadPage("/edu/wpi/agileAngels/views/mbg-view.fxml");
    } else if (event.getSource() == morgueRequest) {
      appController.loadPage("/edu/wpi/agileAngels/views/morgue-view.fxml");
    } else if (event.getSource() == patientTransportRequest) {
      appController.loadPage("/edu/wpi/agileAngels/views/patientTransport-view.fxml");
    }

    /*else if (event.getSource() == testButton) {
      appController.loadPage("/edu/wpi/agileAngels/views/test-view.fxml");
    }*/
  }
}
