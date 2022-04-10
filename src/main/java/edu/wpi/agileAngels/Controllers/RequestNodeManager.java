package edu.wpi.agileAngels.Controllers;

import edu.wpi.agileAngels.Database.Location;
import edu.wpi.agileAngels.Database.LocationDAOImpl;
import edu.wpi.agileAngels.Database.Request;
import edu.wpi.agileAngels.Database.RequestDAOImpl;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class RequestNodeManager {

  private MapsController mapsController;
  private HashMap<String, Request> requestsHash = new HashMap<>();
  private RequestDAOImpl requestDAO = new RequestDAOImpl("", requestsHash, 0);
  private HashMap<String, RequestNode> nodes = new HashMap<>();
  private int[][] typeCounts = new int[5][5];
  private HashMap<String, Integer> floorsAndTypes = new HashMap<>();
  private LocationDAOImpl locationDAO = new LocationDAOImpl();
  HashMap<String, Location> locationsHash = new HashMap<>();

  public RequestNodeManager(MapsController mapsController) throws SQLException {
    this.mapsController = mapsController;

    this.locationsHash = locationDAO.getAllLocations();

    // initialize list of location floors and types
    floorsAndTypes.put("1", 0);
    floorsAndTypes.put("2", 1);
    floorsAndTypes.put("3", 2);
    floorsAndTypes.put("4", 3);
    floorsAndTypes.put("5", 4);
    floorsAndTypes.put("1", 0);
    floorsAndTypes.put("2", 1);
    floorsAndTypes.put("3", 2);
    floorsAndTypes.put("L1", 3);
    floorsAndTypes.put("L2", 4);

    // initialize counts for each type of location to zero
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        typeCounts[i][j] = 0;
      }
    }
  }

  void deleteNode(String nodeID) {
    nodes.remove(nodeID);
  }

  // gets all locations from the DB and creates nodes from them
  void createNodesFromDB() {
    requestsHash = requestDAO.getAllRequests();
    ArrayList<Request> requestsList = new ArrayList<Request>(requestsHash.values());
    for (Request request : requestsList) {
      typeCounts[floorsAndTypes.get(request.getRequestType())][
              floorsAndTypes.get(locationsHash.get(request.getLocation()))] +=
          1;
      // mapsController.displayNode(addNode(request));
    }
  }

  Integer getTypeCount(String type, String floor) {
    typeCounts[floorsAndTypes.get(type)][floorsAndTypes.get(floor)] += 1;
    return typeCounts[floorsAndTypes.get(type)][floorsAndTypes.get(floor)];
  }

  RequestNode addNode(Request request) {
    RequestNode requestNode = new RequestNode(request, this);
    nodes.put(requestNode.getName(), requestNode);
    return requestNode;
    // add the new location to the database
  }

  void editNode(LocationNode locationNode) {
    // edit the corresponding location in the backend

  }

  // gets called on button press and gets the node data
  void loadNode(RequestNode requestNode) {
    // mapsController.populateNodeData(requestNode);
  }
}
