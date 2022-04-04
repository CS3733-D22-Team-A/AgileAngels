package edu.wpi.agileAngels;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

public class DemoRequestDAO {
  private static Connection connection;
  private static LocationDAO locationDao;

  public static void main(String[] args) throws SQLException {

    connection = DBconnection.getConnection();
    RequestDAO requestDAO = new RequestDAOImpl();
    System.out.println("Start Test");
    HashMap<String, Request> data = requestDAO.getAllRequests();
    Request req1 = new MealRequest("Dan", "WPI", "Dinner", "In Progrss", "none");
    Request req2 =
        new GiftRequest("Kermit", "Stage", "Frog", "Complete", "rainbow connection", "Ms. Piggy");
    Request req3 = new EquipmentRequest("Legolas", "Isengard", "Hobbits", "Fabulous", "?");
    Request req4 = new LabRequest("Ali", "WPI", "Blood", "In Progress", "WPI sucks your life out");
    Request req5 = new SanitationRequest("yourMom", "Home", "Mom", "Eh", "...");
    Request req6 = new ServiceRequest("a", "b", "c", "d", "e");
    req3.setDescription("changed");
    req6.setEmployee("changed");
    req2.setStatus(
        "changed"); // TODO setter and getter for sender and be able to change the extra fields!
    req1.setType("changed");
    req4.setLocation("changed");

    System.out.println("request type: " + req3.getRequestType() + " " + req3.getDescription()); // 0
    System.out.println("request type: " + req6.getRequestType() + " " + req6.getEmployee()); // 1
    System.out.println("request type: " + req2.getRequestType() + " " + req2.getStatus()); // 2
    System.out.println("request type: " + req1.getRequestType() + " " + req1.getType()); // 3
    System.out.println("request type: " + req4.getRequestType() + " " + req4.getLocation()); // 4
    System.out.println("request type: " + req5.getRequestType()); // 5
  }
}
