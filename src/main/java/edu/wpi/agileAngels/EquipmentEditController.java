package edu.wpi.agileAngels;

import java.io.IOException;
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
import javafx.scene.control.cell.PropertyValueFactory;

public class EquipmentEditController extends MainController implements Initializable {
  @FXML private TextField nodeID, newStatus;
  @FXML private Label equipmentConfirmation;
  private Connection connection = DriverManager.getConnection("jdbc:derby:myDB;create=true");
  private MedDAOImpl medDAO;

  private ObservableList<MedDevice> medData = FXCollections.observableArrayList();

  @FXML private TableView equipmentTable;
  @FXML
  private TableColumn nameColumn,
      availableColumn,
      typeColumn,
      locationColumn,
      employeeColumn,
      statusColumn,
      descriptionColumn;

  public EquipmentEditController() throws SQLException {}

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

    if (nodeID.getText().isEmpty() || newStatus.getText().isEmpty()) {
      equipmentConfirmation.setText("Please fill out all the require fields");
    } else {
      equipmentConfirmation.setText(
          "The status of the request with ID "
              + nodeID.getText()
              + " has been updated to "
              + newStatus.getText()
              + ". ");

      HashMap<String, MedDevice> data = medDAO.getAllMedicalEquipmentRequests();
      medDAO.updateStatus(data.get(nodeID.getText()), newStatus.getText());
      PreparedStatement statement =
          connection.prepareStatement("UPDATE MEDICALEQUIPMENT SET Status= ? WHERE name = ?");
      statement.setString(1, newStatus.getText());
      statement.setString(2, nodeID.getText());
      statement.execute();

      equipmentTable.setItems(medData);
    }
  }

  @FXML
  private void clearPage() throws IOException {

    resetPage("views/equipment-view.fxml");
  }
}
