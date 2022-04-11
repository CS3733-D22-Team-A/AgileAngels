package edu.wpi.agileAngels.Controllers;

import edu.wpi.agileAngels.Database.LocationDAOImpl;
import edu.wpi.agileAngels.Database.Request;
import edu.wpi.agileAngels.Database.RequestDAOImpl;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class GiftsController extends MainController implements Initializable {
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
  @FXML Button addButton, editButton, deleteButton;
  @FXML private Label giftConfirm;
  private RequestDAOImpl giftDAO;

  private EmployeeManager empDAO = EmployeeManager.getInstance();
  private LocationDAOImpl locDAO = LocationDAOImpl.getInstance();
  // TODO make gift table in the UI
  @FXML private TableView giftTable;
  private static ObservableList<Request> giftData =
      FXCollections.observableArrayList(); // list of requests

  public GiftsController() throws SQLException {}

  /*
      @Override
      public void initialize(URL location, ResourceBundle resources) {

        HashMap<String, Request> data = new HashMap<>();
        GiftRequest giftRequest = new GiftRequest("?", "?", "?", "?", "?", "?", "?", "?");
        data.put("0", giftRequest);
        try {
          giftDAO = new RequestDAOImpl("./MedData.csv", data, 0);
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }

      @FXML
    //   Submits fields to a Java gifts Request Object
      private void submitGift() {
        String dropDown = dropdownButtonText.getText();
        String sender = giftSender.getText();
        String recipient = giftRecipient.getText();
        String employee = giftEmployeeText.getText();
        String location = giftLocation.getText();
        String message = giftMessage.getText();
        String delete = deleteName.getText();
        String edit = editRequest.getText();
        String status = giftStatus.getText();

        // if the fields are empty or to delete input is not empty
        if (!delete.isEmpty()) {
          deleteGiftRequest(delete);
          // editing a request
        } else if (!edit.isEmpty()) {
          editGiftRequest(edit, dropDown, "location", employee, status, sender, recipient);
        } else {
          addGiftRequest("available", dropDown, location, employee, status, message, sender, recipient);
        }
      }
  */
  @FXML
  private void deleteGiftRequest(ActionEvent event) {
    /* //USE THIS FOR DELETE! Make sure you set deleteString somewhere outside!
    String deleteString
    if (!deleteString.isEmpty()) {
      System.out.println("DELETE REQUEST");
      for (int i = 0; i < giftData.size(); i++) {
        Request object = giftData.get(i);
        if (0 == deleteString.compareTo(object.getName())) {
          giftData.remove(i);
          giftDAO.deleteRequest(object);
        }
      }
      // giftTable.setItems(giftData);
    }
    */
  }

  @FXML
  private void editGiftRequest(ActionEvent action) { // Use this
    System.out.println("EDIT REQUEST");
    /*
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
        found.setType(dropDownString);
        giftDAO.updateType(found, dropDownString);
      }
      if (!locationString.isEmpty()) {
        found.setLocation(locationString);
        giftDAO.updateLocation(found, locationString);
      }
      if (!employeeString.isEmpty()) {
        found.setEmployee(employeeString);
        giftDAO.updateEmployeeName(found, employeeString);
      }
      if (!statusString.isEmpty()) {
        found.setStatus(statusString);
        giftDAO.updateStatus(found, statusString);
      }
      // TODO edit sender and recipient
      giftData.set(num, found);

      // giftTable.setItems(giftData);
    }

     */
  }

  /*

    private void addGiftRequest(
        String name,
        String employee,
        String location,
        String type,
        String status,
        String description,
        String sender,
        String recipient) {
      if (sender.isEmpty()
          || employee.isEmpty()
          || type.isEmpty()
          || location.isEmpty()
          || recipient.isEmpty()) { // TODO do we need a gift recipient???
        giftConfirm.setText("Please fill out all of the required fields");
      } else {
        giftConfirm.setText(
            "Thank you, "
                + sender
                + ", "
                + employee
                + " will deliver "
                + type
                + " to "
                + recipient
                + " soon. ");
        GiftRequest request =
            new GiftRequest("", employee, location, type, status, description, sender, recipient);

        giftDAO.addRequest(request);
      }
    }

    @FXML
    private void clearPage() throws IOException, InterruptedException {
      loadPage("views/gifts-view.fxml", giftConfirm);
    }
  }


  */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    HashMap<String, Request> giftData = new HashMap<>();
    /**
     * try { giftDAO = new RequestDAOImpl("./GIFT.CSV", giftData, 0); } catch (SQLException e) {
     * e.printStackTrace(); }*
     */
  }

  @FXML
  /** Submits fields to a Java gifts Request Object */
  private void submitGift() {
    String dropDown = dropdownButtonText.getText();
    String sender = giftSender.getText();
    String recipient = giftRecipient.getText();
    String employee = giftEmployeeText.getText();
    // String location = giftLocation.getText();
    String message = giftMessage.getText();
    // String delete = deleteName.getText();
    // String edit = editRequest.getText();
    String status = giftStatus.getText();
    // attributes arent all filled
    if (giftSender.getText().isEmpty()
        || employee.isEmpty()
        || dropDown.isEmpty()
        || recipient.isEmpty()) {
      giftConfirm.setText("Please fill out all of the required fields");
    } else {
      giftConfirm.setText(
          "Thank you, "
              + giftSender.getText()
              + ", "
              + giftEmployeeText.getText()
              + " will deliver "
              + dropdownButtonText.getText()
              + " to "
              + giftRecipient.getText()
              + " soon. ");
      /**
       * GiftRequest request = new GiftRequest( "", giftEmployeeText.getText(),
       * giftRecipient.getText(), dropdownButtonText.getText(), giftStatus.getText(),
       * giftMessage.getText(), giftSender.getText());
       *
       * <p>giftDAO.addRequest(request);*
       */
    }
    addGiftRequest(dropDown, sender, recipient, employee, "location", message, status);
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
    Request request =
        new Request(
            "",
            empDAO.getEmployee(giftEmployeeText.getText()),
            locDAO.getLocation(giftRecipient.getText()),
            dropdownButtonText.getText(),
            giftStatus.getText(),
            giftMessage.getText(),
            giftSender.getText(),
            giftRecipient.getText());

    giftDAO.addRequest(request);
  }
}
