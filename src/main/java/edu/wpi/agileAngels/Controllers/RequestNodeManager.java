package edu.wpi.agileAngels.Controllers;

import edu.wpi.agileAngels.Database.Request;
import edu.wpi.agileAngels.Database.RequestDAOImpl;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class RequestNodeManager {

  private MapsController mapsController;
  private RequestDAOImpl requestDAO = RequestDAOImpl.getInstance("MedRequest");
  private HashMap<String, RequestNode> nodes = new HashMap<>();

  public RequestNodeManager(MapsController mapsController) throws SQLException {
    this.mapsController = mapsController;
  }

  // gets all locations from the DB and creates nodes from them
  void createNodesFromDB() throws SQLException {
    requestDAO.csvRead();
    ArrayList<Request> requestsList = new ArrayList<>(requestDAO.getAllRequests().values());
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
}
