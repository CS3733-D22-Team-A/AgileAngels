package edu.wpi.agileAngels;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class GiftsController extends MainController {
  @FXML private TextField giftSender, giftRecipient, giftMessage, giftEmployeeText, giftStatus;

  @FXML private Label giftConfirm;

  @FXML
  private void submitGift() {
    if (giftSender.getText().isEmpty()
        || giftEmployeeText.getText().isEmpty()
        || dropdownButtonText.getText().isEmpty()
        || giftRecipient.getText().isEmpty()) {
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
      GiftRequest request =
          new GiftRequest(
              giftEmployeeText.getText(),
              giftRecipient.getText(),
              dropdownButtonText.getText(),
              giftStatus.getText(),
              giftMessage.getText(),
              giftSender.getText());
    }
  }
}
