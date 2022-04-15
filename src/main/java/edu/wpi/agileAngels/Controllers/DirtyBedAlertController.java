package edu.wpi.agileAngels.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DirtyBedAlertController extends AlertController implements Initializable {

    @FXML Label AlertMessage;
    private String floor;
    private AppController appController = AppController.getInstance();

    public DirtyBedAlertController() throws SQLException {
        floor = appController.getCurrentFloor();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AlertMessage.setText("There are currently "
                + Integer.toString(appController.getDirtyBeds(this.floor))
                + " dirty beds on floor "
                + floor
                + ". Please make service requests to have them cleaned.");
    }

    @FXML
    public void makeRequests() {
        // TODO
    }
}
