package edu.wpi.agileAngels;

import java.util.Map;

// test DAO methods
public class DAOPatternDemo {
  public void main(String[] args) {
    LocationDAOImpl locationDao = new LocationDAOImpl(null);
    {
      // print all students
      for (Map.Entry<String, Location> set : locationDao.getAllLocations().entrySet()) {
        System.out.println(
            "Location: [NodeID: "
                + set.getKey()
                + " long name"
                + set.getValue().getLongName()
                + " ]");
      }

      // add location
      String place = "?";
      Location a = new Location("0", place, place, place, place, place, place, place);
      locationDao.addLocation(a);
      // update location
      Location l = locationDao.getAllLocations().get("0");
      String name = "main campus";
      locationDao.updateLocationLongName(l, name);

      // get the location
      Location b = locationDao.getAllLocations().get("0");
      System.out.println(
          "Location: [NodeID: " + b.getNodeID() + " long name" + b.getLongName() + " ]");
    }
  }
}
