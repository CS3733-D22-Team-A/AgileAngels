package edu.wpi.agileAngels;

import java.net.URL;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

// similar to equip controller
public class LabController extends MainController implements Initializable {

  @FXML private TextField labTestLocation, labEmployeeText, labStatus, labDelete, labEdit;

  private RequestDAOImpl LabDAO = RequestDAOImpl.getInstance("LabRequest");
  private static ObservableList<Request> labData = FXCollections.observableArrayList();
  @FXML private TableView labTable;
  @FXML
  private TableColumn nameColumn,
      availableColumn,
      typeColumn,
      locationColumn,
      employeeColumn,
      statusColumn,
      descriptionColumn;
  @FXML
  private Label labTestConfirmation,
      dropdownButtonText,
      bloodLabel,
      urineLabel,
      tumorLabel,
      covidLabel;

  public LabController() throws SQLException {}

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    availableColumn.setCellValueFactory(new PropertyValueFactory<>("available"));
    typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
    locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
    employeeColumn.setCellValueFactory(new PropertyValueFactory<>("employee"));
    statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    if (labData.isEmpty()) {
      System.out.println("THE TABLE IS CURRENTLY EMPTY I WILL POPuLATE");
      LabDAO.csvRead();
      Iterator var3 = LabDAO.getAllRequests().entrySet().iterator();

      while (var3.hasNext()) {
        Map.Entry<String, Request> entry = (Map.Entry) var3.next();
        Request object = (Request) entry.getValue();
        labData.add(object);
      }
    }

    labTable.setItems(labData);
  }

  @FXML
  private void submitLabTest() {
    if (dropdownButtonText.getText().isEmpty()
        || labTestLocation.getText().isEmpty()
        || labEmployeeText.getText().isEmpty()
        || (!labDelete.getText().isEmpty())) {

      if (!labDelete.getText().isEmpty()) {
        for (int i = 0; i < labData.size(); i++) {
          Request object = labData.get(i);
          if (0 == labDelete.getText().compareTo(object.getName())) {

            labData.remove(i);
            LabDAO.deleteRequest(object);
          }
        }
        labTable.setItems(labData);
      }
      labTestConfirmation.setText("Please fill out all the required fields");
    } else if ((!dropdownButtonText.getText().isEmpty()
            || !labTestLocation.getText().isEmpty()
            || !labEmployeeText.getText().isEmpty())
        && (!labEdit.getText().isEmpty())) {

      Request found = null;
      int num = 0;
      for (int i = 0; i < labData.size(); i++) {
        Request device = labData.get(i);
        if (0 == labEdit.getText().compareTo(device.getName())) {
          found = device;
          num = i;
        }
      }
      if (found != null) {
        if (!dropdownButtonText.getText().isEmpty()) {
          String type = dropdownButtonText.getText();
          found.setType(type);
          LabDAO.updateType(found, type);
        }
        if (!labTestLocation.getText().isEmpty()) {
          String location = labTestLocation.getText();
          found.setLocation(location);
          LabDAO.updateLocation(found, location);
        }
        if (!labEmployeeText.getText().isEmpty()) {
          String employee = labEmployeeText.getText();
          found.setEmployee(employee);
          LabDAO.updateEmployeeName(found, employee);
        }
        labData.set(num, found);

        labTable.setItems(labData);
      }

    } else {
      labTestConfirmation.setText(
          "Thank you! Your "
              + dropdownButtonText.getText()
              + " you requested will be delivered shortly to "
              + labTestLocation.getText()
              + " by "
              + labEmployeeText.getText()
              + ".");
      Request request =
          new Request(
              "",
              "available",
              labEmployeeText.getText(),
              labTestLocation.getText(),
              dropdownButtonText.getText(),
              labStatus.getText(),
              "",
              "");

      LabDAO.addRequest(request);

      labData.add(request);

      labTable.setItems(labData);
    }
  }
}
