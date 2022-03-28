package edu.wpi.agileAngels;
import java.util.List;

//includes the four DOA methods
public interface LocationDOA
{
    pubilc List<Location> getAllLocations(); // error because of Location hides fields
    public Location getLocation(String NodeID);
    public void updateLocationType(Location location);
    public void updateLocationFloor(Location location);
    public void updateLocationLongName(Location location);
    public void updateLocationShortName(Location location);
    public void updateLocationXCoord(Location location);
    public void updateLocationYCoord(Location location);
    public void deleteLocation(Location location);

}
