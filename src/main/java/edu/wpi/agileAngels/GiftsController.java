package edu.wpi.agileAngels;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class GiftsController extends MainController implements Initializable {
  @FXML private TextField giftSender, giftRecipient, giftMessage, giftEmployeeText, giftStatus;

  @FXML private MenuButton giftType;

  @FXML private MenuItem balloons, flowers;

  @FXML private Label giftConfirm;
private RequestDAOImpl giftDAO;
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    HashMap<String, Request> giftData = new HashMap<>();
    giftDAO = new RequestDAOImpl("./GIFT.CSV", giftData, 0);

  }

@FXML
  private void setGiftType(ActionEvent event) {
    if (event.getSource() == balloons) {
      giftType.setText("Balloons");
    }
    if (event.getSource() == flowers) {
      giftType.setText("Flowers");
    }
  }

  @FXML
  private void submitGift() {
    if (giftSender.getText().isEmpty()
        || giftEmployeeText.getText().isEmpty()
        || giftType.getText().isEmpty()
        || giftRecipient.getText().isEmpty()) {
      giftConfirm.setText("Please fill out all of the required fields");
    } else {
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
      GiftRequest request =
          new GiftRequest(
              giftEmployeeText.getText(),
              giftRecipient.getText(),
              giftType.getText(),
              giftStatus.getText(),
              "",
              // giftMessage.getText(),
              giftSender.getText());

      giftDAO.addRequest(request);
    }
  }

  @FXML
  private void clearPage() throws IOException, InterruptedException {
    loadPage("views/gifts-view.fxml", giftConfirm);
  }


}
