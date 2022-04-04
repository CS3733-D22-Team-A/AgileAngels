package edu.wpi.agileAngels;

import java.awt.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class EquipmentController extends MainController implements Initializable {
  @FXML private TextField equipLocation, equipmentEmployeeText, equipmentStatus;
  @FXML private Label equipmentConfirmation, dropdownButtonText;
  @FXML private TableView equipmentTable;
  @FXML Button clear;

  private Connection connection = DriverManager.getConnection("jdbc:derby:myDB;create=true");
  private MedDAOImpl medDAO;

  private ObservableList<MedDevice> medData = FXCollections.observableArrayList();

  @FXML
  private TableColumn nameColumn,
      availableColumn,
      typeColumn,
      locationColumn,
      employeeColumn,
      statusColumn,
      descriptionColumn;

  public EquipmentController() throws SQLException {}

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    medDAO = new MedDAOImpl(connection);

    // Implement DAO here.

    HashMap<String, MedDevice> data = medDAO.getAllMedicalEquipmentRequests();
    for (Map.Entry<String, MedDevice> entry : data.entrySet()) {
      MedDevice object = entry.getValue();
      medData.add(object);
    }

    // no need to add them to the table since the FXMLLoader is ready doing that
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    availableColumn.setCellValueFactory(new PropertyValueFactory<>("available"));
    typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
    locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
    employeeColumn.setCellValueFactory(new PropertyValueFactory<>("employee"));
    statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    equipmentTable.setItems(medData);
  }

  @FXML
  private void submitEquipment() throws SQLException {

    if (dropdownButtonText.getText().isEmpty()
        || equipLocation.getText().isEmpty()
        || equipmentEmployeeText.getText().isEmpty()) {
      equipmentConfirmation.setText("Please fill out all the require fields");
    } else {
      equipmentConfirmation.setText(
          "Thank you, the "
              + dropdownButtonText.getText()
              + " you requested will be delivered shortly to "
              + equipLocation.getText()
              + " by "
              + equipmentEmployeeText.getText()
              + ".");

      String placeholder = "?";
      MedDevice medDevice =
          new MedDevice(
              dropdownButtonText.getText(),
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
      medData.add(medDevice);
      equipmentTable.setItems(medData);
    }
  }

  //  @FXML
  //  private void clearPage() throws IOException {
  //    System.out.println("test");
  //    loadPage(pageHistory.peek(), clear);
  //  }
}
