package edu.wpi.agileAngels;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class GiftsController extends MainController {
  @FXML private TextField giftSender, giftRecipient, giftMessage, giftEmployeeText;

  @FXML private MenuButton giftType;

  @FXML private MenuItem balloons, flowers;

  @FXML private Label giftConfirm;

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

  @FXML
  private void clearPage() throws IOException {

    Stage stage;
    Parent root;

    stage = (Stage) homeButton.getScene().getWindow();
    root = FXMLLoader.load(getClass().getResource("views/gifts-view.fxml"));

    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
}
