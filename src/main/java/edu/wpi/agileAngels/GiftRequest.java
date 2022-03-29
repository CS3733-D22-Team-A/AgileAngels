package edu.wpi.agileAngels;

public class GiftRequest extends Request {

  private String sender, message;

  public GiftRequest(
      String employeeName, String location, String type, String sender, String message) {
    super(employeeName, location, type);
    this.message = message;
    this.sender = sender;
  }

  public String getSender() {
    return sender;
  }

  public String getMessage() {
    return message;
  }

  public void setSender(String sender) {
    this.sender = sender;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
