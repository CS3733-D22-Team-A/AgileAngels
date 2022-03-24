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
  // Makes a Location object and reads through CSV FILE and makes table
  private String NodeID;
  private HashMap<String, String> data;

  public Location(String nodeID, HashMap<String, String> dataIn){
    this.NodeID = nodeID;
    this.data = dataIn;
  }

  public String getNodeID(){}
  public String getXCoord(){}
  public String getYCoord(){}
  public String getFloor(){}
  public String getNodeType(){}
  public String getLongName(){}
  public String getShortName(){}
  public String setNodeID(){}
  public String setXCoord(){}
  public String setYCoord(){}
  public String setFloor(){}
  public String setNodeType(){}
  public String setLongName(){}
  public String setShortName(){}
}
