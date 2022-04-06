package edu.wpi.agileAngels;

public class GiftRequest extends Request {
  private String sender;

  public GiftRequest(
      String name,
      String employeeName,
      String location,
      String type,
      String status,
      String description,
      String sender) {
    super(name, employeeName, location, type, status, description);
    this.sender = sender; // new attr
    // message = description
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
  /*
  public void setMessage(String message) {
    this.description = message;
  }
  */

}
