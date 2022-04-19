package edu.wpi.agileAngels.Controllers;

import edu.wpi.agileAngels.Database.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.input.MouseEvent;

public class RequestNodeManager {

  private MapsController mapsController;
  private RequestDAOImpl medRequestDAO = RequestDAOImpl.getInstance("MedRequest");
  private RequestDAOImpl labRequestDAO = RequestDAOImpl.getInstance("LabRequest");
  private EmployeeManager empDAO = EmployeeManager.getInstance();
  private HashMap<String, RequestNode> nodes = new HashMap<>();
  private LocationDAOImpl locationDAO = LocationDAOImpl.getInstance();
  HashMap<String, Location> locationsHash = locationDAO.getAllLocations();
  ArrayList<Location> locationsList = new ArrayList<Location>(locationsHash.values());

  public HashMap<String, Employee> employeeHash = empDAO.getAllEmployees();

  public RequestNodeManager(MapsController mapsController) throws SQLException {
    this.mapsController = mapsController;
  }

  public ArrayList<Location> getLocationsList() {
    return locationsList;
  }

  // gets all locations from the DB and creates nodes from them
  void createNodesFromDB() throws SQLException {
    ArrayList<Request> requestsList = new ArrayList<>(medRequestDAO.getAllRequests().values());
    requestsList.addAll(labRequestDAO.getAllRequests().values());
    for (Request request : requestsList) {
      mapsController.displayRequestNode(addNode(request));
    }
  }

  public void editRequestLocation(RequestNode request, Location newLocation) {

    if (request.getName().substring(0, 3).equals("Lab")) {
      request.setLocation(newLocation);
      labRequestDAO.updateLocation(request.getRequest(), newLocation);
    } else if (request.getName().substring(0, 3).equals("Med")) {
      request.setLocation(newLocation);
      medRequestDAO.updateLocation(request.getRequest(), newLocation);
    }
  }

  RequestNode addNode(Request request) throws SQLException {
    RequestNode requestNode = new RequestNode(request, this);
    nodes.put(requestNode.getName(), requestNode);
    return requestNode;
  }

  // gets called on button press and gets the node data
  void loadNode(RequestNode requestNode) {
    mapsController.populateRequestNodeData(requestNode);
  }

  public double getCroppedMapXOffset() {
    return mapsController.getCroppedMapXOffset();
  }

  public double getCroppedMapYOffset() {
    return mapsController.getCroppedMapYOffset();
  }

  public double getCroppedMapWidth() {
    return mapsController.getCroppedMapWidth();
  }

  public double getImagePaneWidth() {
    return mapsController.getImagePaneWidth();
  }

  public double getMapXCoordFromClick(MouseEvent click) {
    return mapsController.getMapXCoordFromClick(click);
  }

  public double getMapYCoordFromClick(MouseEvent click) {
    return mapsController.getMapYCoordFromClick(click);
  }

  public void setDraggedNodeCoords(MouseEvent mouseEvent) {
    mapsController.setCoordsOnMouseEvent(mouseEvent);
  }

  public void updateRequest(RequestNode request) {
    if (request.getName().substring(0, 3).equals("Lab")) {
      labRequestDAO.updateEmployeeName(request.getRequest(), request.getEmployee());
      labRequestDAO.updateStatus(request.getRequest(), request.getStatus());
    } else if (request.getName().substring(0, 3).equals("Med")) {
      medRequestDAO.updateEmployeeName(request.getRequest(), request.getEmployee());
      medRequestDAO.updateStatus(request.getRequest(), request.getStatus());
    }
  }
}
