package edu.wpi.agileAngels;
//test DAO methods
public class DAOPatternDemo {
    public static main(String[] args) {
        LocationDAO locationDao = new LocationDAO();
        {
            //print all students
            for (Location l : locationDao.getLocations()) {
                System.out.println("Location: [NodeID: " +
                        l.getNodeID() + " long name" + l.getLongName() + " ]");
            }
            //update location
            Location l = locationDao.getAllLocations().get(0);
            l.setLongName("main campus");
            locationDao.updateLocationLongName(l);

            //get the location
            locationDao.getLocation("0");
            System.out.println("Location: [NodeID: " +
                    l.getNodeID() + " long name" + l.getLongName() + " ]");

        }
    }
}