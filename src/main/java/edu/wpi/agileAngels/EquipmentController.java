package edu.wpi.agileAngels;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class EquipmentController extends MainController {
  @FXML private MenuButton eqptDropdown;
  @FXML private MenuItem bed, recliner, xray, infusion;
  @FXML private TextField equipLocation, equipmentEmployeeText, equipmentStatus;
  @FXML private Label equipmentConfirmation;
  private Connection connection = DriverManager.getConnection("jdbc:derby:myDB;create=true");
  private MedDAOImpl medDAO;

  public EquipmentController() throws SQLException {
    medDAO = new MedDAOImpl(connection);
  }
  @FXML private TableView equipmentTable;
  @FXML
  private TableColumn nameColumn,
      availableColumn,
      typeColumn,
      locationColumn,
      employeeColumn,
      statusColumn,
      descriptionColumn;

  @FXML
  private void submitEquipment() throws SQLException {

    if (eqptDropdown.getText().isEmpty()
        || equipLocation.getText().isEmpty()
        || equipmentEmployeeText.getText().isEmpty()) {
      equipmentConfirmation.setText("Please fill out all the require fields");
    } else {
      equipmentConfirmation.setText(
          "Thank you, the "
              + eqptDropdown.getText()
              + " you requested will be delivered shortly to "
              + equipLocation.getText()
              + " by "
              + equipmentEmployeeText.getText()
              + ".");
      EquipmentRequest request =
          new EquipmentRequest(
              equipmentEmployeeText.getText(),
              equipLocation.getText(),
              eqptDropdown.getText(),
              equipmentStatus.getText());
    }
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
        "INSERT INTO MedicalEquipment(Name, available ,type, location, employee, status, description)VALUES(?,?,?,?,?,?,?)";
    PreparedStatement preparedStatement = connection.prepareStatement(addMed);
    preparedStatement.setString(1, medDevice.getName());
    preparedStatement.setString(2, medDevice.getAvailable());
    preparedStatement.setString(3, medDevice.getType());
    preparedStatement.setString(4, medDevice.getLocation());
    preparedStatement.setString(5, medDevice.getEmployee());
    preparedStatement.setString(6, medDevice.getStatus());
    preparedStatement.setString(7, medDevice.getDescription());
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

    resetPage("views/equipment-view.fxml");
  }
}
