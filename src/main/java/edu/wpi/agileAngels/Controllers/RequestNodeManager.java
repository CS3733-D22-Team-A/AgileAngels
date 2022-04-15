package edu.wpi.agileAngels.Controllers;

import edu.wpi.agileAngels.Database.Request;
import edu.wpi.agileAngels.Database.RequestDAOImpl;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.input.MouseEvent;

public class RequestNodeManager {

  private MapsController mapsController;
  private RequestDAOImpl medRequestDAO = RequestDAOImpl.getInstance("MedRequest");
  private RequestDAOImpl labRequestDAO = RequestDAOImpl.getInstance("LabRequest");
  private HashMap<String, RequestNode> nodes = new HashMap<>();

  public RequestNodeManager(MapsController mapsController) throws SQLException {
    this.mapsController = mapsController;
  }

  // gets all locations from the DB and creates nodes from them
  void createNodesFromDB() throws SQLException {
    ArrayList<Request> requestsList = new ArrayList<>(medRequestDAO.getAllRequests().values());
    requestsList.addAll(labRequestDAO.getAllRequests().values());
    for (Request request : requestsList) {
      mapsController.displayRequestNode(addNode(request));
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
}
