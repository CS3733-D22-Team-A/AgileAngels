package edu.wpi.agileAngels;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.HashMap;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Location {
  private String NodeID; //Unique for every location
  private HashMap<String, String> data; //Stores a location's values, except for nodeID

  //Sets initial location values.
  public Location(String nodeID, HashMap<String, String> dataIn){
    this.NodeID = nodeID;
    this.data = dataIn;
  }

  public String getNodeID(){
    return NodeID;
  }
  public String getXCoord(){
    return data.get("xCoord");
  }
  public String getYCoord(){
    return data.get("yCoord");
  }
  public String getFloor(){
    return data.get("Floor");
  }
  public String getNodeType(){
    return data.get("NodeType");
  }
  public String getLongName(){
    return data.get("LongName");
  }
  public String getShortName(){
    return data.get("ShortName");
  }
  public void setXCoord(String xCoordIn){
    data.put("xCoord", xCoordIn);
  }
  public void setYCoord(String yCoordIn){
    data.put("yCoord", yCoordIn);
  }
  public void setFloor(String FloorIn){
    data.put("Floor", FloorIn);
  }
  public void setNodeType(String NodeTypeIn){
    data.put("NodeType", NodeTypeIn);
  }
  public void setLongName(String LongNameIn){
    data.put("LongName", LongNameIn);
  }
  public void setShortName(String ShortNameIn){
    data.put("ShortName", ShortNameIn);
  }
}
