package edu.wpi.agileAngels;

import java.util.ArrayList;
import java.util.List;

//includes a list od Location objects and implements the Location DOA methods
public class LocationDOAImpl implements LocationDAO {
    //List is working as a database
    List<Location> locations;

    public LocationDAOImpl() { //error, maybe return void? doesn't in tutorial :(
        locations = new ArrayList<Location>();
        Location loc1 = new Location(); //add values
        Location loc2 = new Location(); //add values
    }

    //@Override
    // override is in the tutorial, maybe change method name to delete?
    public void deleteLocation(Location location) {
        locations.remove(location.getNodeID());
        System.out.println("Location: NodeID " + location.getNodeID() + ", deleted from the database");
    }
    //retrieve list of locations from the database
   @Overrride
   public List<Location> getAllLocations(){
     return Locations;
    }
    // @Override override in the tutorial, different method name?
    public Location getLocation(String NodeID){
    return Locations.get(NodeID);
    }
    // @Override override in the tutoral, different method name? (for all updateLocation<field_name> methods)
    public void updateLocationType(Location location){
        locations.get(location.getNodeID()).setNodeType(location.getNodeType()); //error here with getter
        System.out.println("Location: NodeID " + location.getNodeID() + ", updated in the database");
    }ad
    public void updateLocationFloor(Location location){
        locations.get(location.getNodeID()).setFloor(location.getFloor()); //error here with getter
        System.out.println("Location: NodeID " + location.getNodeID() + ", updated in the database");
    }
    public void updateLocationLongName(Location location){
        locations.get(location.getNodeID()).setLongName(location.getLongName()); //error here with getter
        System.out.println("Location: NodeID " + location.getNodeID() + ", updated in the database");
    }
    public void updateLocationShortName(Location location){
        locations.get(location.getNodeID()).setShortName(location.getShortName()); //error here with getter
        System.out.println("Location: NodeID " + location.getNodeID() + ", updated in the database");
    }
    public void updateLocationXCoord(Location location){
        locations.get(location.getNodeID()).setXCoord(location.getXCoord()); //error here with getter
        System.out.println("Location: NodeID " + location.getNodeID() + ", updated in the database");
    }
    public void updateLocationYCoord(Location location){
        locations.get(location.getNodeID()).setYCoord(location.getYCoord()); //error here with getter
        System.out.println("Location: NodeID " + location.getNodeID() + ", updated in the database");
    }
}
