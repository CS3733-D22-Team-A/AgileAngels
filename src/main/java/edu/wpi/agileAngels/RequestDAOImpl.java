package edu.wpi.agileAngels;

import java.util.HashMap;

public class RequestDAOImpl implements RequestDAO {

  private final String CSV_FILE_PATH = "./MedData.csv";
  private HashMap<String, Request> reqData = new HashMap<>();

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

  public void deleteMed(Request request) {
    reqData.remove(request.getDescription()); // change to the key
  }

  public void addMed(Request request) {
    reqData.put(request.getDescription(), request); // change to the key
  }
}
