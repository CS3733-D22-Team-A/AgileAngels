package edu.wpi.agileAngels;

import java.awt.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
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
import javafx.scene.layout.Pane;

public class EquipmentController extends MainController implements Initializable {
  // @FXML private MenuButton eqptDropdown;
  // @FXML private MenuItem bed, recliner, xray, infusion;
  @FXML private Button equipDropdown, bed, recliner, xray, infusion, equipDropdownButton;
  @FXML private TextField equipLocation, equipmentEmployeeText, equipmentStatus, deleteName;
  @FXML private Label equipmentConfirmation, dropdownButtonText;
  @FXML private TableView equipmentTable;
  private Connection connection;
  @FXML Button clear;

  @FXML Pane drop, drop2;

  private MedDAOImpl medDAO;
  private RequestDAOImpl MedrequestImpl;

  private ObservableList<Request> medData = FXCollections.observableArrayList();

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

    connection = DBconnection.getConnection();

    // Implement DAO here.

    // HashMap<String, MedDevice> data = medDAO.getAllMedicalEquipmentRequests();
    HashMap<String, Request> data = new HashMap<>();
    MedDevice medDevice = new MedDevice("?", "?", "?", "?", "?", "?", "?");
    data.put("0", medDevice);
    MedrequestImpl = new RequestDAOImpl("./MedData.csv", data, 0);

    /**
     * for (Map.Entry<String, Request> entry : data.entrySet()) { Request object = entry.getValue();
     * medData.add(object); }*
     */
    medData.add(medDevice);

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
        || equipmentEmployeeText.getText().isEmpty()
        || !deleteName.getText().isEmpty()) {
      if (!deleteName.getText().isEmpty()) {
        for (int i = 0; i < medData.size(); i++) {
          Request object = medData.get(i);
          if (0 == deleteName.getText().compareTo(object.getName())) {

            medData.remove(i);
            MedrequestImpl.deleteRequest(object);
          }
        }
        equipmentTable.setItems(medData);
      }

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
              placeholder,
              placeholder,
              dropdownButtonText.getText(),
              equipLocation.getText(),
              equipmentEmployeeText.getText(),
              equipmentStatus.getText(),
              placeholder);
      /*medDAO.addMed(medDevice);
      medData.add(medDevice);*/
      MedrequestImpl.addRequest(medDevice);

      medData.add(medDevice);

      equipmentTable.setItems(medData);
    }
  }
}
