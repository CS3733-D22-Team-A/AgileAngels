package edu.wpi.agileAngels;

import java.awt.*;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class EquipmentController extends MainController implements Initializable{
  //@FXML private MenuButton eqptDropdown;
  //@FXML private MenuItem bed, recliner, xray, infusion;
  @FXML private Button equipDropdown, bed, recliner, xray, infusion, equipDropdownButton;
  @FXML private TextField equipLocation, equipmentEmployeeText, equipmentStatus;
  @FXML private Label equipmentConfirmation, dropText;
  @FXML private TableView equipmentTable;

  @FXML Pane drop;
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

    if (dropText.getText().isEmpty()
        || equipLocation.getText().isEmpty()
        || equipmentEmployeeText.getText().isEmpty()) {
      equipmentConfirmation.setText("Please fill out all the require fields");
    } else {
      equipmentConfirmation.setText(
          "Thank you, the "
              + dropText.getText()
              + " you requested will be delivered shortly to "
              + equipLocation.getText()
              + " by "
              + equipmentEmployeeText.getText()
              + ".");

      String placeholder = "?";
      MedDevice medDevice =
          new MedDevice(
              dropText.getText(),
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

  public void eqpDrop() throws IOException {
    //    drop.setLayoutX(equipDropdownButton.getParent().getParent().getLayoutX());
    //    drop.setLayoutY(equipDropdownButton.getParent().getParent().getLayoutY());

    // drop.setLayoutX(getPositionX(equipDropdownButton));
    // drop.setLayoutY(getPositionY(equipDropdownButton));
    drop.setViewOrder(0);
    drop.setVisible(true);
  }

  public void closeMenu(MouseEvent mouseEvent) {
    drop.setVisible(false);
  }

  public void menuItemSelected(ActionEvent event) {
    dropText.setTextFill(Color.rgb(0, 0, 0));
    if (event.getSource() == bed) {
      dropText.setText("Bed");
    } else if (event.getSource() == recliner) {
      dropText.setText("Recliner");
    } else if (event.getSource() == xray) {
      dropText.setText("XRay Machine");
    } else if (event.getSource() == infusion) {
      dropText.setText("Infusion Pump");
    }
    drop.setVisible(false);
  }

  @FXML
  private void clearPage() throws IOException {

    loadPage("views/equipment-view.fxml", equipmentStatus);
  }
}
