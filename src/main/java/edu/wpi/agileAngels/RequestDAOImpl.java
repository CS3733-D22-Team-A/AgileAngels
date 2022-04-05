package edu.wpi.agileAngels;

import java.util.HashMap;

public class RequestDAOImpl {

  private String CSV_FILE_PATH = "./MedData.csv";
  private HashMap<String, Request> reqData = new HashMap<>();
  private int count;

  public RequestDAOImpl(String CSV_FILE_PATH, HashMap<String, Request> reqData, int count){
      this.CSV_FILE_PATH = CSV_FILE_PATH;
      this.reqData = reqData;
      this.count = count;
  }

  public HashMap<String, Request> getAllRequests() {
    return reqData;
  }

  public void updateEmployeeName(Request request, String newName) {
    request.setEmployee(newName);
  }

  public void updateRequestType(Request request, int requestType) {
      request.setRequestType(requestType);
  }

  public void updateType(Request request, String newType) {
      request.setType(newType);
  }

  public void updateLocation(Request request, String newLocation) {
      request.setLocation(newLocation);
  }

  public void updateDescription(Request request, String description) {
      request.setDescription(description);
  }

  public void updateStatus(Request request, String newStatus) {
      request.setStatus(newStatus);
  }

  public void deleteRequest(Request request) {
      reqData.remove(request.getDescription()); //change to the key
  }

  public void addRequest(Request request) {
      reqData.put(request.getDescription(), request);
      count = count +1;
      //change to the key
  }
}
