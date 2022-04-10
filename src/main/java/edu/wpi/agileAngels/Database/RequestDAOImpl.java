package edu.wpi.agileAngels.Database;

import edu.wpi.agileAngels.Adb;
import java.io.*;
import java.sql.SQLException;
import java.util.HashMap;

// Implementation of RequestDAO
public class RequestDAOImpl implements RequestDAO {
  private String CSV_FILE_PATH;
  private HashMap<String, Request> reqData = new HashMap();
  private int count;
  private String DAOtype;

  private static RequestDAOImpl requestDAO;

  public RequestDAOImpl(
      String CSV_FILE_PATH, HashMap<String, Request> reqData, int count, String type)
      throws SQLException {
    this.CSV_FILE_PATH = CSV_FILE_PATH;
    this.reqData = reqData;
    this.count = count;
    this.DAOtype = type;
  }

  public static RequestDAOImpl getInstance(String type) throws SQLException {
    HashMap data;
    if (requestDAO == null && 0 == type.compareTo("MedRequest")) {
      data = new HashMap();
      requestDAO = new RequestDAOImpl("./MedData.csv", data, 1, "MedRequest");
      return requestDAO;
    } else {
      return requestDAO;
    }
  }

  public HashMap<String, Request> getAllRequests() {
    return this.reqData;
  }

  public void updateEmployeeName(Request request, String newName) {
    request.setEmployee(newName);
    Adb.updateRequest(request, "EmployeeName", newName);
  }

  public void updateRequestType(Request request, int requestType) {
    request.setRequestType(requestType);
  }

  public void updateType(Request request, String newType) {
    request.setType(newType);
    Adb.updateRequest(request, "Type", newType);
  }

  public void updateLocation(Request request, String newLocation) {
    request.setLocation(newLocation);
    Adb.updateRequest(request, "Location", newLocation);
  }

  public void updateDescription(Request request, String description) {
    request.setDescription(description);
    Adb.updateRequest(request, "Description", description);
  }

  public void updateStatus(Request request, String newStatus) {
    request.setStatus(newStatus);
    Adb.updateRequest(request, "Status", newStatus);
  }

  public void deleteRequest(Request request) {
    this.reqData.remove(request.getDescription());
    String name = request.getName();
    Adb.removeRequest(name);
  }

  public void addRequest(Request request) {
    ++this.count;
    String letter;
    if (0 == DAOtype.compareTo("MedRequest")) {
      letter = "Med";
    } else {
      letter = "Lab";
    }

    letter = letter + Integer.toString(this.count);
    request.setName(letter);
    this.reqData.put(letter, request);
    Adb.addRequest(request);
  }

  public void csvRead() {
    String line = "";
    String splitBy = ",";

    try {
      BufferedReader br = new BufferedReader(new FileReader(this.CSV_FILE_PATH));
      boolean OnHeader = false;
      line.split(splitBy);

      while ((line = br.readLine()) != null) {
        if (OnHeader) {
          String[] values = line.split(splitBy);
          this.typeofDAO(values);
          System.out.println(values[0]);
        } else {
          OnHeader = true;
        }
      }
    } catch (IOException var7) {
      var7.printStackTrace();
    } catch (SQLException var8) {
      var8.printStackTrace();
    }
  }

  private void typeofDAO(String[] values) throws SQLException {
    ++this.count;
    Request request =
        new Request(
            values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7]);
    this.reqData.put(values[0], request);
    Adb.addRequest(request);
  }
}
