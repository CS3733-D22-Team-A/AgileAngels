package edu.wpi.agileAngels;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Adb {

  private static final String CSV_FILE_PATH = "./TowerLocations.csv";

  //Makes a Location object and reads through CSV FILE
  public static class Location {
    public void read() throws IOException {
      try (Reader reader = Files.newBufferedReader(Paths.get(CSV_FILE_PATH));
          CSVParser csvParser =
              new CSVParser(
                  reader,
                  CSVFormat.DEFAULT
                      .withHeader(
                          "NodeID",
                          "xcoord",
                          "ycoord",
                          "floor",
                          "building",
                          "nodeType",
                          "longName",
                          "shortName")
                      .withIgnoreHeaderCase()
                      .withTrim()) ) {
        for (CSVRecord csvRecord : csvParser) {
          // Accessing values by the names assigned to each column
          String nodeID = csvRecord.get("NodeID");
          String xcoord = csvRecord.get("xcoord");
          String ycoord = csvRecord.get("ycoord");
          String floor = csvRecord.get("floor");
          String building = csvRecord.get("building");
          String nodeType = csvRecord.get("nodeType");
          String longName = csvRecord.get("longName");
          String shortName = csvRecord.get("shortName");

          System.out.println("Record No - " + csvRecord.getRecordNumber());
          System.out.println("---------------");
          System.out.println("NodeId : " + nodeID);
          System.out.println("floor : " + floor);
          System.out.println("nodeType : " + nodeType);
          System.out.println("longName : " + longName);
          System.out.println("---------------\n\n");
        }
      }
    }
  }

  public void main(String[] args) throws IOException {
    Location location = new Location();
    location.read();

  }
}
