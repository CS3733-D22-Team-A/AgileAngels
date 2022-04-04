package edu.wpi.agileAngels;

public class GiftRequest extends Request {
  private String sender;

  public GiftRequest(
      String employeeName,
      String location,
      String type,
      String status,
      String description,
      String sender) {
    super(employeeName, location, type, status, description);
    this.sender = sender;
    setRequestType(2);
  }

  public String getSender() {
    return this.sender;
  }

  public String getMessage() {
    return getDescription();
  }

  public void setSender(String sender) {
    this.sender = sender;
  }

  public void setMessage(String message) {
    setDescription(message);
  }
}
