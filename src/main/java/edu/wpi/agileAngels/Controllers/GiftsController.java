package edu.wpi.agileAngels.Controllers;

import edu.wpi.agileAngels.Database.Request;
import edu.wpi.agileAngels.Database.RequestDAOImpl;
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
  private RequestDAOImpl GiftrequestImpl =
      RequestDAOImpl.getInstance("GiftRequest"); // instance of RequestDAOImpl to access functions
  @FXML private TableView giftTable;

  private static ObservableList<Request> giftData =
      FXCollections.observableArrayList(); // list of requests

  public GiftsController() throws SQLException {}

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    senderColumn.setCellValueFactory(new PropertyValueFactory<>("sender"));
    recipientColumn.setCellValueFactory(new PropertyValueFactory<>("recipent"));
    employeeColumn.setCellValueFactory(new PropertyValueFactory<>("employee"));
    locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
    statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    messageColumn.setCellValueFactory(new PropertyValueFactory<>("message"));

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
  /*
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
      String locationString,
      String employeeString,
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
        found.setLocation(locationString);
        GiftrequestImpl.updateLocation(found, locationString);
      }
      if (!employeeString.isEmpty()) {
        // String employee = emp.getText();
        found.setEmployee(employeeString);
        GiftrequestImpl.updateEmployeeName(found, employeeString);
      }
      if (!statusString.isEmpty()) {
        // String employee = emp.getText();
        found.setStatus(statusString);
        GiftrequestImpl.updateStatus(found, statusString);
      }
      giftData.set(num, found);

      giftTable.setItems(giftData);
    }
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

  // @Override
  // public void initialize(URL location, ResourceBundle resources) {
  //  HashMap<String, Request> giftData = new HashMap<>();
  /**
   * try { giftDAO = new RequestDAOImpl("./GIFT.CSV", giftData, 0); } catch (SQLException e) {
   * e.printStackTrace(); }*
   */
  // }

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

    String placeholder = "?";
    Request gift =
        new Request(placeholder, employee, location, dropDown, status, message, sender, recipient);
    // todo is this right?
    GiftrequestImpl.addRequest(gift); // add to hashmap
    giftData.add(gift); // add to the UI
    giftTable.setItems(giftData);
  }
}
