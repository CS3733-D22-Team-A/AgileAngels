package edu.wpi.agileAngels;

import edu.wpi.agileAngels.Controllers.*;
import edu.wpi.agileAngels.Database.*;
import java.util.ArrayList;

public class DatabaseTests {

  private static Adb database;

  public static void main(String[] args) {
    database = new Adb();
    database.initialize();
    testMedicalEquipmentTable();
    testEmployeesTable();
    testLocationsTable();
    testServiceRequestsTable();
    DBconnection.shutdown();
  }

  public static void testMedicalEquipmentTable() {

    MedicalEquip mE = new MedicalEquip("R1", "X-Ray Machine", true, "Tower");
    MedicalEquip mE2 = new MedicalEquip("R2", "X-Ray Machine", false, "Hall");
    MedicalEquip mE3 = new MedicalEquip("R3", "Bed", true, "Hall");
    MedicalEquip mE4 = new MedicalEquip("R4", "Bed", false, "Tower");
    MedicalEquip mE5 = new MedicalEquip("R5", "Recliner", false, "Tower");

    // Add
    Adb.addMedicalEquipment(mE);
    Adb.addMedicalEquipment(mE2);
    Adb.addMedicalEquipment(mE3);
    Adb.addMedicalEquipment(mE4);
    Adb.addMedicalEquipment(mE5);

    // Remove
    Adb.removeMedicalEquipment(mE2.getID());
    Adb.removeMedicalEquipment(mE2.getID());

    // Update
    mE3.setClean(false);
    Adb.updateMedicalEquipment(mE3);
    mE4.setLocation("Cafeteria");
    Adb.updateMedicalEquipment(mE4);
  }

  public static void testServiceRequestsTable() {
    Request r1 =
        new Request("R1", "Martha", "Hall", "MED", "Complete", "Descr.", "Available", "N/A");
    Request r2 = new Request("R2", "Gary", "Tower", "GIFT", "In Progress", "Descr.", "N/A", "N/A");
    Request r3 =
        new Request("R3", "Lou", "Hall", "MED", "Not Started", "Descr.", " Not Available", "N/A");
    Request r4 = new Request("R4", "John", "Cafeteria", "SAN", "Complete", "Descr.", "N/A", "N/A");

    // Add
    Adb.addRequest(r1);
    Adb.addRequest(r2);
    Adb.addRequest(r3);
    Adb.addRequest(r4);

    // Remove
    Adb.removeRequest(r1.getName());
    Adb.removeRequest(r1.getName());

    // Update
    r4.setLocation("Tower 2");
    Adb.updateRequest(r4);
  }

  public static void testLocationsTable() {
    Location loc1 = new Location("TOW101", 90.8, 70.8, "Floor 3", "Tower", "??", "Hallway", "HALL");
    Location loc2 =
        new Location("TOW102", 95.7, 70.8, "Floor 3", "Tower", "??", "Room 34", "ROOM34");
    Location loc3 =
        new Location("TOW103", 100.8, 70.8, "Floor 3", "Tower", "??", "Room 35", "ROOM35");

    // Add
    Adb.addLocation(loc1);
    Adb.addLocation(loc2);
    Adb.addLocation(loc3);

    // Remove
    Adb.removeLocation(loc2.getNodeID());
    Adb.removeLocation("Room 36");

    // Update
    loc3.setYCoord(80.2);
    Adb.updateLocation(loc3);
  }

  public static void testEmployeesTable() {
    ArrayList<Request> reqs = new ArrayList<Request>();
    reqs.add(new Request("R1", "Martha", "Hall", "MED", "Complete", "Descr.", "Available", "N/A"));
    Employee Emily = new Employee("Emily", "emily123", new ArrayList<Request>());
    Employee Martha = new Employee("Martha", "jjjjjtype", reqs);
    reqs.remove(0);
    reqs.add(
        new Request("R3", "Lou", "Hall", "MED", "Not Started", "Descr.", " Not Available", "N/A"));
    reqs.add(
        new Request("R6", "Lou", "Hall", "MED", "Not Started", "Descr.", " Not Available", "N/A"));
    Employee Lou = new Employee("Lou", "kellyanne", reqs);

    // Add
    Adb.addEmployee(Emily);
    Adb.addEmployee(Martha);
    Adb.addEmployee(Lou);

    // Remove
    Adb.removeEmployee(Martha.getName());

    // Update
    Emily.addRequest(new Request("R1", "", "", "", "", "", "", ""));
    Adb.updateEmployee(Emily);
  }
}