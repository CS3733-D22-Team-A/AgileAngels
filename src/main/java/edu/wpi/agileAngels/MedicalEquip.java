package edu.wpi.agileAngels;
// individual physical equipment object
// change this late or add more attr fields
// TODO add field for clean / dirty
public class MedicalEquip {
  private final String ID;
  private String type;
  private boolean clean;
  private String location;

  public MedicalEquip(String id, String typeIn, boolean cleanIn, String locationIn) {
    this.ID = id;
    this.type = typeIn;
    this.clean = cleanIn;
    this.location = locationIn;
  }

  public String getID() {
    return ID;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public boolean isClean() {
    return clean;
  }

  public void setClean(boolean clean) {
    this.clean = clean;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }
}
