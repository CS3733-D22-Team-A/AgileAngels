package edu.wpi.agileAngels.Database;

import edu.wpi.agileAngels.Adb;
import edu.wpi.agileAngels.Controllers.EmployeeManager;
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

// Implementation of RequestDAO
public class RequestDAOImpl implements RequestDAO {
  private String CSV_FILE_PATH;
  private HashMap<String, Request> reqData = new HashMap();
  private int count;
  private String DAOtype;

  private static EmployeeManager empManager = null;
  private LocationDAOImpl locDAO = LocationDAOImpl.getInstance();
  private static RequestDAOImpl MedrequestDAO = null;
  private static RequestDAOImpl LabrequestDAO = null;
  private static RequestDAOImpl SanrequestDAO = null;
  private static RequestDAOImpl MealDAO = null;
  private static RequestDAOImpl GiftDAO = null;
  private static RequestDAOImpl MaintenanceDAO = null;

  public RequestDAOImpl(HashMap<String, Request> reqData, int count, String type)
      throws SQLException {
    this.CSV_FILE_PATH = "./Requests.csv";
    this.reqData = reqData;
    this.count = count;
    this.DAOtype = type;
  }

  public static RequestDAOImpl getInstance(String type) throws SQLException {
    HashMap<String, Request> data = new HashMap();
    if (0 == type.compareTo("MedRequest")) {
      if (MedrequestDAO == null) {
        MedrequestDAO = new RequestDAOImpl(data, 1, "MedRequest");
      }
      return MedrequestDAO;
    } else if (GiftDAO == null && 0 == type.compareTo("GiftRequest")) {
      data = new HashMap();
      GiftDAO = new RequestDAOImpl(data, 1, "GiftRequest");
      return GiftDAO;
    } else if (0 == type.compareTo("LabRequest")) {
      if (LabrequestDAO == null) {
        LabrequestDAO = new RequestDAOImpl(data, 1, "LabRequest");
      }
      return LabrequestDAO;
    } else if (0 == type.compareTo("ServiceRequest")) {
      if (SanrequestDAO == null) {
        SanrequestDAO = new RequestDAOImpl(data, 1, "SanRequest");
      }
      return SanrequestDAO;
    } else if (0 == type.compareTo("MealRequest")) {
      if (MealDAO == null) {
        MealDAO = new RequestDAOImpl(data, 1, "MealRequest");
      }
      return MealDAO;
    }else if (0 == type.compareTo("MaintenanceRequest")) {
      if (MaintenanceDAO == null) {
        MaintenanceDAO = new RequestDAOImpl(data, 1, "MaintenanceRequest");
      }
      return MaintenanceDAO;
    }
    return null;
  }

  public HashMap<String, Request> getAllRequests() {
    return this.reqData;
  }

  public void updateEmployeeName(Request request, String newName) {
    empManager.getAllEmployees();
    request.setEmployee(empManager.getEmployee(newName));
    Adb.updateRequest(request, "EmployeeName", newName);
  }

  public void updateRequestType(Request request, int requestType) {
    request.setRequestType(requestType);
  }

  public void updateType(Request request, String newType) {
    request.setType(newType);
    Adb.updateRequest(request, "Type", newType);
  }

  public void updateLocation(Request request, Location newLocation) {
    request.setLocation(newLocation);
    Adb.updateRequest(request);
    // Adb.updateRequest(request, "Location", newLocation);
  }

  public void updateDescription(Request request, String description) {
    request.setDescription(description);
    // Adb.updateRequest(request);
    Adb.updateRequest(request, "Description", description);
  }

  public void updateStatus(Request request, String newStatus) {
    request.setStatus(newStatus);
    Adb.updateRequest(request, "Status", newStatus);
  }

  public void deleteRequest(Request request) {
    this.reqData.remove(request.getName());
    String name = request.getName();
    Adb.removeRequest(name);
  }

  public void addRequest(Request request) {
    ++this.count;
    String letter = "";
    if (0 == DAOtype.compareTo("MedRequest")) {
      letter = "Med";
    } else if (0 == DAOtype.compareTo("LabRequest")) {
      letter = "Lab";
    } else if (0 == DAOtype.compareTo("SanRequest")) {
      letter = "Sanitation";
    } else if (0 == DAOtype.compareTo("MealRequest")) {
      letter = "Meal";

    } else if (0 == DAOtype.compareTo("TransportRequest")) {
      letter = "Transport";
    } else if (0 == DAOtype.compareTo("GiftRequest")) {
      letter = "Gift";
    }else if (0 == DAOtype.compareTo("MaintenanceRequest")) {
      letter = "Main";
    }

    letter = letter + Integer.toString(this.count);
    request.setName(letter);
    this.reqData.put(letter, request);

    Adb.addRequest(request);
  }

  public void csvRead() {
    String line = "";
    String splitBy = ",";

    try {
      BufferedReader br = new BufferedReader(new FileReader(this.CSV_FILE_PATH));
      boolean OnHeader = false;
      line.split(splitBy);

      while ((line = br.readLine()) != null) {
        if (OnHeader) {
          String[] values = line.split(splitBy);
          typeofDAO(values);
        } else {
          OnHeader = true;
        }
      }
    } catch (IOException var7) {
      var7.printStackTrace();
    } catch (SQLException var8) {
      var8.printStackTrace();
    }
  }
  // UHHHH fix this
  private void typeofDAO(String[] values) throws SQLException {
    ++this.count;
    if (values[0].substring(0, 3).compareTo("Med") == 0 && DAOtype.compareTo("MedRequest") == 0) {
      makeRequest(values);
    }
    if (values[0].substring(0, 3).compareTo("Trans") == 0
        && DAOtype.compareTo("TransportRequest") == 0) {
      makeRequest(values);
    }
    if (values[0].substring(0, 4).compareTo("Meal") == 0) {}

    if (values[0].substring(0, 1).compareTo("L") == 0 && DAOtype.compareTo("LabRequest") == 0) {
      makeRequest(values);
    }

    if (values[0].substring(0, 1).compareTo("G") == 0) {}

    if (values[0].substring(0, 1).compareTo("S") == 0) {}
    return;
  }

  private void makeRequest(String[] values) throws SQLException {
    Request request =
        new Request(
            values[0],
            findEmployee(values[1]),
            findLocation(values[2]),
            values[3],
            values[4],
            values[5],
            values[6],
            values[7]);
    this.reqData.put(values[0], request);
    Adb.addRequest(request);
  }

  private Employee findEmployee(String value) throws SQLException {
    Employee employee;
    HashMap<String, Employee> employeeData = EmployeeManager.getInstance().getAllEmployees();

    employee = employeeData.get(value);
    return employee;
  }

  private Location findLocation(String value) {
    System.out.println("Location Value " + value);
    Location location;
    HashMap<String, Location> locationData = locDAO.getAllLocations();
    location = locationData.get(value);
    return location;
  }

  public void outputCSVFile() {
    String csvFilePath = "./RequestsOUT.csv";

    try {

      String sql = "SELECT * FROM ServiceRequests";

      Connection connection = DBconnection.getConnection();

      Statement statement = connection.createStatement();

      ResultSet result = statement.executeQuery(sql);

      BufferedWriter fileWriter = new BufferedWriter(new FileWriter(csvFilePath));

      // write header line containing column names
      fileWriter.write("name,employee,location,type,status,description, attribute1, attribute2");

      while (result.next()) {
        String name = result.getString("Name");
        String employee = result.getString("employeename");
        String location = result.getString("location");
        String type = result.getString("type");
        String status = result.getString("status");
        String description = result.getString("description");
        String attribute1 = result.getString("attribute1");
        String attribute2 = "NONE";
        String[] att =
            new String[] {
              name, employee, location, type, status, description, attribute1, attribute2
            };
        for (int i = 0; i < att.length; i++) {
          if (att[i].compareTo(" ") == 0) {
            att[i] = "None";
          }
        }
        String line =
            String.format(
                "%s,%s,%s, %s, %s, %s, %s,%s",
                att[0], att[1], att[2], att[3], att[4], att[5], att[6], att[7]);

        fileWriter.newLine();
        fileWriter.write(line);
      }

      statement.close();
      fileWriter.close();

    } catch (SQLException | IOException e) {
      System.out.println("Datababse error:");
      e.printStackTrace();
    }
  }
}
