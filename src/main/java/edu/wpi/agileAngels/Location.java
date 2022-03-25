package edu.wpi.agileAngels;

import java.sql.*;
import java.util.HashMap;

public class Location {
  private String NodeID; // Unique for every location
  private HashMap<String, String> data; // Stores a location's values, except for nodeID

  // Sets initial location values.
  public Location(String nodeID, HashMap<String, String> dataIn) {
    this.NodeID = nodeID;
    this.data = dataIn;
  }

  public String getNodeID() {
    return NodeID;
  }

  public String getXCoord() {
    return data.get("xcoord");
  }

  public String getYCoord() {
    return data.get("ycoord");
  }

  public String getFloor() {
    return data.get("floor");
  }

  public String getBuilding() {
    return data.get("building");
  }

  public String getNodeType() {
    return data.get("nodeType");
  }

  public String getLongName() {
    return data.get("longName");
  }

  public String getShortName() {
    return data.get("shortName");
  }

  public void setXCoord(String xCoordIn) {
    data.put("xcoord", xCoordIn);
  }

  public void setYCoord(String yCoordIn) {
    data.put("ycoord", yCoordIn);
  }

  public void setFloor(String FloorIn) {
    data.put("floor", FloorIn);
  }

  public void setNodeType(String NodeTypeIn) {
    data.put("nodeType", NodeTypeIn);
  }

  public void setLongName(String LongNameIn) {
    data.put("longName", LongNameIn);
  }

  public void setShortName(String ShortNameIn) {
    data.put("shortName", ShortNameIn);
  }
}
