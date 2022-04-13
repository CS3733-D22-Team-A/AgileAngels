package edu.wpi.agileAngels.Controllers;

import edu.wpi.agileAngels.Database.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class GiftsController implements Initializable {
  @FXML
  private TextField giftSender,
      giftRecipient,
      giftMessage,
      giftEmployeeText,
      giftLocation,
      giftStatus,
      deleteName,
      editRequest;
  @FXML
  private TableColumn senderColumn,
      recipientColumn,
      employeeColumn,
      locationColumn,
      statusColumn,
      typeColumn,
      nameColumn,
      messageColumn;
  @FXML MenuButton giftType;
  @FXML MenuItem baloons, flowers, card;

  @FXML Button addButton, editButton, deleteButton;
  @FXML private Label giftConfirm;
  private RequestDAOImpl GiftrequestImpl =
      RequestDAOImpl.getInstance("GiftRequest"); // instance of RequestDAOImpl to access functions

  private LocationDAOImpl locDAO = LocationDAOImpl.getInstance();
  private HashMap<String, Location> locationsHash = locDAO.getAllLocations();

  private EmployeeManager employeeDAO = EmployeeManager.getInstance();
  private HashMap<String, Employee> employeesHash = employeeDAO.getAllEmployees();



  @FXML private TableView giftTable;
  private AppController appController = AppController.getInstance();

  private static ObservableList<Request> giftData =
      FXCollections.observableArrayList(); // list of requests

  public GiftsController() throws SQLException {}

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    senderColumn.setCellValueFactory(new PropertyValueFactory<>("attribute1"));
    recipientColumn.setCellValueFactory(new PropertyValueFactory<>("attribute2"));
    employeeColumn.setCellValueFactory(new PropertyValueFactory<>("employee"));
    locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
    statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    messageColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

    if (giftData.isEmpty()) {
      System.out.println("THE TABLE IS CURRENTLY EMPTY I WILL POPuLATE");
      GiftrequestImpl.csvRead();
      Iterator var3 = GiftrequestImpl.getAllRequests().entrySet().iterator();

      for (Map.Entry<String, Request> entry : GiftrequestImpl.getAllRequests().entrySet()) {
        Request req = entry.getValue();
        giftData.add(req);
      }
    }

    giftTable.setItems(giftData);
  }


  @FXML
  /** Submits fields to a Java gifts Request Object */
  private void submitGift() {
    String dropDown = giftType.getText();
    String sender = giftSender.getText();
    String recipient = giftRecipient.getText();
    String employee = giftEmployeeText.getText();
    String location = giftLocation.getText();
    String message = giftMessage.getText();
    String delete = deleteName.getText();
    String edit = editRequest.getText();
    String status = giftStatus.getText();
    // attributes arent all filled
    if (!delete.isEmpty()) {
      deleteGiftRequest(delete);
      // editing a request
    } else if (!edit.isEmpty()) {
      editGiftRequest(edit, dropDown, sender, recipient, employee, location, message, status);
    } else if (giftSender.getText().isEmpty()
        || employee.isEmpty()
        || dropDown.isEmpty()
        || recipient.isEmpty()) {
      giftConfirm.setText("Please fill out all of the required fields");
    } else {
      addGiftRequest(dropDown, sender, recipient, employee, location, message, status);
      giftConfirm.setText(
          "Thank you, "
              + giftSender.getText()
              + ", "
              + giftEmployeeText.getText()
              + " will deliver "
              + giftType.getText()
              + " to "
              + giftRecipient.getText()
              + " soon. ");
    }
  }

  private void addGiftRequest(
      String dropDown,
      String sender,
      String recipient,
      String employee,
      String location,
      String message,
      String status) {

    giftConfirm.setText(
        "Thank you, "
            + sender
            + ", "
            + employee
            + " will deliver "
            + dropDown
            + " to "
            + recipient
            // + " to "
            // + location
            + " soon. ");

    String placeholder = "?";
    Request gift =
        new Request(placeholder, employeesHash.get(employee), locationsHash.get(location), dropDown, status, message, sender, recipient);
    // todo is this right?
    GiftrequestImpl.addRequest(gift); // add to hashmap
    giftData.add(gift); // add to the UI
    giftTable.setItems(giftData);
  }

  @FXML
  private void deleteGiftRequest(String deleteString) {
    if (!deleteString.isEmpty()) {
      System.out.println("DELETE REQUEST");
      for (int i = 0; i < giftData.size(); i++) {
        Request object = giftData.get(i);
        if (0 == deleteString.compareTo(object.getName())) {
          giftData.remove(i);
          GiftrequestImpl.deleteRequest(object);
        }
      }
      giftTable.setItems(giftData);
    }
  }

  @FXML
  private void editGiftRequest(
      String editString,
      String dropDownString,
      String senderString,
      String recipientString,
      String employeeString,
      String locationString,
      String messageString,
      String statusString) {
    System.out.println("EDIT REQUEST");
    Request found = null;
    int num = 0;
    for (int i = 0; i < giftData.size(); i++) {
      Request device = giftData.get(i);
      if (0 == editRequest.getText().compareTo(device.getName())) {
        found = device;
        num = i;
      }
    }
    if (found != null) {
      if (!dropDownString.isEmpty()) {
        // String type = dropdownButtonText.getText();
        found.setType(dropDownString);
        GiftrequestImpl.updateType(found, dropDownString);
      }
      if (!locationString.isEmpty()) {
        // String location = equipLocation.getText();
        found.setLocation(locationsHash.get(locationString));
        GiftrequestImpl.updateLocation(found, locationsHash.get(locationString));
      }
      if (!employeeString.isEmpty()) {
        // String employee = emp.getText();
        found.setEmployee(employeesHash.get(employeeString));
        GiftrequestImpl.updateEmployeeName(found, employeeString);
      }
      if (!statusString.isEmpty()) {
        // String employee = emp.getText();
        found.setStatus(statusString);
        GiftrequestImpl.updateStatus(found, statusString);
      }
      if (!senderString.isEmpty()) {
        // String sender = emp.getText();
        found.setAttribute1(senderString);
        GiftrequestImpl.updateStatus(found, senderString);
      }
      if (!recipientString.isEmpty()) {
        // String recipent = emp.getText();
        found.setAttribute2(recipientString);
        GiftrequestImpl.updateStatus(found, recipientString);
      }
      if (!messageString.isEmpty()) {
        // String description = emp.getText();
        found.setDescription(messageString);
        GiftrequestImpl.updateStatus(found, messageString);
      }
      giftData.set(num, found);

      giftTable.setItems(giftData);
    }
  }

  public void clearPage(ActionEvent event) {
    appController.clearPage();
  }

  public void giftType(ActionEvent event) {
    MenuItem button = (MenuItem) event.getSource();
    giftType.setText(button.getText());
    }

}
