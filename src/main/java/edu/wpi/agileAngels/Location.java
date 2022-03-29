package edu.wpi.agileAngels;

public class Location {
  private String NodeID; // Unique for every location
  private String xCoord;
  private String yCoord;
  private String floor;
  private String building;
  private String nodeType;
  private String longName;
  private String shortName;

  // Sets initial location values. Constructor
  public Location(
      String nodeID,
      String xCoord,
      String yCoord,
      String floor,
      String building,
      String nodeType,
      String longName,
      String shortName) {
    this.NodeID = nodeID;
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.floor = floor;
    this.building = building;
    this.nodeType = nodeType;
    this.longName = longName;
    this.shortName = shortName;
  }

  public String getNodeID() {

    return NodeID;
  }

  public String getXCoord() {
    return xCoord;
  }

  public String getYCoord() {
    return yCoord;
  }

  public String getFloor() {
    return floor;
  }

  public String getBuilding() {
    return building;
  }

  public String getNodeType() {
    return nodeType;
  }

  public String getLongName() {
    return longName;
  }

  public String getShortName() {
    return shortName;
  }

  public void setXCoord(String xCoordIn) {
    this.xCoord = xCoordIn;
  }

  public void setYCoord(String yCoordIn) {
    this.yCoord = yCoordIn;
  }

  public void setFloor(String FloorIn) {
    this.floor = FloorIn;
  }

  public void setBuilding(String BuildingIn) {
    this.building = BuildingIn;
  }

  public void setNodeType(String NodeTypeIn) {
    this.nodeType = NodeTypeIn;
  }

  public void setLongName(String LongNameIn) {
    this.longName = LongNameIn;
  }

  public void setShortName(String ShortNameIn) {
    this.shortName = ShortNameIn;
  }
}
