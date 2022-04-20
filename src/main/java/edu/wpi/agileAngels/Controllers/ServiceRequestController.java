package edu.wpi.agileAngels.Controllers;

import com.jfoenix.controls.JFXToggleButton;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URL;
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
      saniRequest,
      mealRequest,
      giftRequest,
      laundryRequest,
      maintenanceRequest,
      morgueRequest,
      patientTransportRequest;
  @FXML private JFXToggleButton toggleButton, clientToggle;

  // These are/will be the hidden labels for the toggleable switch.
  @FXML
  private Label erText,
      lrText,
      mrText,
      srText,
      mealText,
      morText,
      mgbText,
      grText,
      launText,
      ptText;

  AppController appController = AppController.getInstance();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    appController.addPropertyChangeListener(this);
    updateToggle();
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
    if (toggleButton.isSelected()) {
      // Will turn off names.
      erText.setText("Equipment Request: Harmoni");
      lrText.setText("Lab Request: Justin");
      mrText.setText("Maintenance Request: Talia");
      srText.setText("Sanitation Request: Daniel");
      mealText.setText("Meal Request: Jakob");
      morText.setText("Morgue Request: Aaron");
      mgbText.setText("Mass General Babes: Aadhya");
      grText.setText("Gift Request: Bashar");
      launText.setText("Laundry Request: Bashar");
      ptText.setText("Patient Transport: Ali");
    } else {
      // Will turn on the names.
      erText.setText("Equipment Request");
      lrText.setText("Lab Request");
      mrText.setText("Maintenance Request");
      srText.setText("Sanitation Request");
      mealText.setText("Meal Request");
      morText.setText("Morgue Request");
      mgbText.setText("Mass General Babes");
      grText.setText("Gift Request");
      launText.setText("Laundry Request");
      ptText.setText("Patient Transport");
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
    } else if (event.getSource() == saniRequest) {
      appController.loadPage("/edu/wpi/agileAngels/views/sanitation-view.fxml");
    } else if (event.getSource() == mealRequest) {
      appController.loadPage("/edu/wpi/agileAngels/views/mealRequest-view.fxml");
    } else if (event.getSource() == giftRequest) {
      appController.loadPage("/edu/wpi/agileAngels/views/gifts-view.fxml");
    } else if (event.getSource() == maintenanceRequest) {
      appController.loadPage("/edu/wpi/agileAngels/views/maintenance-view.fxml");
    } else if (event.getSource() == laundryRequest) {
      // todo this was the mass babes line I edited if your wondeiring or need to change it
      appController.loadPage("/edu/wpi/agileAngels/views/laundryRequest-view.fxml");
    } else if (event.getSource() == morgueRequest) {
      appController.loadPage("/edu/wpi/agileAngels/views/morgue-view.fxml");
    } else if (event.getSource() == patientTransportRequest) {
      appController.loadPage("/edu/wpi/agileAngels/views/patientTransport-view.fxml");
    }

    /*else if (event.getSource() == testButton) {
      appController.loadPage("/edu/wpi/agileAngels/views/test-view.fxml");
    }*/
  }

  /**
   * uses the toggleStatus to update the toggle from client to embedded.
   *
   * @param event
   */
  @FXML
  private void toggleStatus(ActionEvent event) {
    if (clientToggle.isSelected()) {
      appController.setEmbeddedON(true);
      // System.out.println("oop gurl and i skr skr skr");
    } else {
      appController.setEmbeddedON(false);
      // System.out.println("is false now gurl");
    }
  }

  /** This will check when initalizing what status the toggle was on. */
  private void updateToggle() {
    if (appController.isEmbeddedON()) {
      clientToggle.setSelected(appController.isEmbeddedON());
      // System.out.println("Justin's here");
      System.out.println(appController.isEmbeddedON());
    }
  }
}
