package edu.wpi.agileAngels;

import java.util.HashMap;

// includes a list od Location objects and implements the Location DAO methods
// this will get the data from the DB?
public class LocationDAOImpl implements LocationDAO {
    // List is working as a database
    // List<Location> locations;
    HashMap<String, Location> locations = new HashMap<>();

    public LocationDAOImpl() { // error, maybe return void? doesn't in tutorial :(
        //  locations = new ArrayList<Location>();
       }

    // @Override
    // override is in the tutorial, maybe change method name to delete?
    // use the dictionaries here instead of this method (idea)
    public void deleteLocation(Location location) {
        locations.remove(location.getNodeID());
        System.out.println("Location: NodeID " + location.getNodeID() + ", deleted from the database");
    }

    // retrieve list of locations from the database

    public HashMap<String, String> getAllLocations() {
        // return locations;
        return null; // error we need to return a hashmap of locations
    }

    // @Override override in the tutorial, different method name?
    public Location getLocation(String NodeID) {
        return locations.get(NodeID);
    }

    // loc1 will have the one value that we want to take and replace in the other location
    // loc2 will have the location with the other values we DON'T want to replace

    public void updateLocationXCoord(Location location) {
        String NodeID = location.getNodeID();
        Location loc1 = location;
        Location loc2 = locations.get(NodeID);
        Location locNew =
                new Location(loc2.getNodeID(), loc1.getXCoord(), loc2.getYCoord(),
                        loc2.getFloor(), loc2.getBuilding(), loc2.getNodeType(),
                        loc2.getLongName(), loc2.getShortName());
        locations.replace(NodeID, locNew);
        System.out.println("Location: NodeID " + NodeID + ", updated in the database");
    }

    public void updateLocationYCoord(Location location) {
        String NodeID = location.getNodeID();
        Location loc1 = location;
        Location loc2 = locations.get(NodeID);
        Location locNew =
                new Location(loc2.getNodeID(), loc2.getXCoord(), loc1.getYCoord(),
                        loc2.getFloor(), loc2.getBuilding(), loc2.getNodeType(),
                        loc2.getLongName(), loc2.getShortName());
        locations.replace(NodeID, locNew);
        System.out.println("Location: NodeID " + NodeID + ", updated in the database");
    }

    public void updateLocationFloor(Location location) {
        String NodeID = location.getNodeID();
        Location loc1 = location;
        Location loc2 = locations.get(NodeID);
        Location locNew =
                new Location(loc2.getNodeID(), loc2.getXCoord(), loc2.getYCoord(),
                        loc1.getFloor(), loc2.getBuilding(), loc2.getNodeType(),
                        loc2.getLongName(), loc2.getShortName());
        locations.replace(NodeID, locNew);
        System.out.println("Location: NodeID " + NodeID + ", updated in the database");
    }

    public void updateLocationBuilding(Location location) {
        String NodeID = location.getNodeID();
        Location loc1 = location;
        Location loc2 = locations.get(NodeID);
        Location locNew =
                new Location(loc2.getNodeID(), loc2.getXCoord(), loc2.getYCoord(),
                        loc2.getFloor(), loc1.getBuilding(), loc2.getNodeType(),
                        loc2.getLongName(), loc2.getShortName());
        locations.replace(NodeID, locNew);
        System.out.println("Location: NodeID " + NodeID + ", updated in the database");
    }


    // @Override override in the tutoral, different method name? (for all updateLocation<field_name>
    // methods)
    public void updateLocationType(Location location) {
        String NodeID = location.getNodeID();
        Location loc1 = location;
        Location loc2 = locations.get(NodeID);
        Location locNew =
                new Location(loc2.getNodeID(), loc2.getXCoord(), loc2.getYCoord(),
                        loc2.getFloor(), loc2.getBuilding(), loc1.getNodeType(),
                        loc2.getLongName(), loc2.getShortName());
        locations.replace(NodeID, locNew);
        System.out.println("Location: NodeID " + NodeID + ", updated in the database");
    }


    public void updateLocationLongName(Location location) {
        String NodeID = location.getNodeID();
        Location loc1 = location;
        Location loc2 = locations.get(NodeID);
        Location locNew =
                new Location(loc2.getNodeID(), loc2.getXCoord(), loc2.getYCoord(),
                        loc2.getFloor(), loc2.getBuilding(), loc2.getNodeType(),
                        loc1.getLongName(), loc2.getShortName());
        locations.replace(NodeID, locNew);
        System.out.println("Location: NodeID " + NodeID + ", updated in the database");
    }

    public void updateLocationShortName(Location location) {
        String NodeID = location.getNodeID();
        Location loc1 = location;
        Location loc2 = locations.get(NodeID);
        Location locNew =
                new Location(loc2.getNodeID(), loc2.getXCoord(), loc2.getYCoord(),
                        loc2.getFloor(), loc2.getBuilding(), loc2.getNodeType(),
                        loc2.getLongName(), loc1.getShortName());
        locations.replace(NodeID, locNew);
        System.out.println("Location: NodeID " + NodeID + ", updated in the database");
    }

}
