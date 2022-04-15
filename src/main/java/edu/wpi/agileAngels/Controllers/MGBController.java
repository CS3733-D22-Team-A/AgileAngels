package edu.wpi.agileAngels.Controllers;

import javafx.fxml.Initializable;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;

public class MGBController implements Initializable, PropertyChangeListener {

    private AppController appController = AppController.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        appController.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String changeType = evt.getPropertyName();
        System.out.println(changeType);
        int newValue = (int) evt.getNewValue();
        System.out.println(newValue);
    }
}
