package edu.wpi.agileAngels;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EquipmentController extends MainController {
  @FXML private MenuButton eqptDropdown;
  @FXML private MenuItem bed, recliner, xray, infusion;
  @FXML private TextField equipLocation, equipmentEmployeeText;
  @FXML private Label equipmentConfirmation;
  private Connection connection = DriverManager.getConnection("jdbc:derby:myDB;create=true");
  private MedDAOImpl medDAO;

  public EquipmentController() throws SQLException {
    medDAO = new MedDAOImpl(connection);
  }

  @FXML
  private void submitEquipment() throws SQLException {
    equipmentConfirmation.setText(
        "Thank you, the "
            + eqptDropdown.getText()
            + " you requested will be delivered shortly to "
            + equipLocation.getText()
            + " by "
            + equipmentEmployeeText.getText()
            + ".");

    // EquipmentRequest request = new EquipmentRequest(equipmentEmployeeText.getText(),
    // equipLocation.getText(), eqptDropdown.getText(), null, null, null, null);
    String placeholder = "?";
    MedDevice medDevice =
        new MedDevice(
            eqptDropdown.getText(),
            placeholder,
            placeholder,
            equipLocation.getText(),
            equipmentEmployeeText.getText(),
            placeholder,
            placeholder);
    medDAO.addMed(medDevice);
    String addMed =
        "INSERT INTO MedicalEquipment(Name, available ,type, location, employee, status, description)VALUES(?,'?','?','?','?','?','?')";
    PreparedStatement preparedStatement = connection.prepareStatement(addMed);
    preparedStatement.setString(1, medDevice.getName());
    preparedStatement.execute();
  }

  @FXML
  private void setEquipmentType(ActionEvent event) throws IOException {
    if (event.getSource() == bed) {
      eqptDropdown.setText("Bed");
    }
    if (event.getSource() == recliner) {
      eqptDropdown.setText("Recliner");
    }
    if (event.getSource() == xray) {
      eqptDropdown.setText("X-Ray Machine");
    }
    if (event.getSource() == infusion) {
      eqptDropdown.setText("Infusion");
    }
  }

  @FXML
  private void clearPage() throws IOException {

    Stage stage;
    Parent root;

    stage = (Stage) homeButton.getScene().getWindow();
    root = FXMLLoader.load(getClass().getResource("views/equipment-view.fxml"));

    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
}
