package edu.wpi.agileAngels.Database;

import edu.wpi.agileAngels.Adb;

import java.sql.*;
import java.util.HashMap;

public class MedicalEquipmentTable implements TableI {

  /**
   * Adds a MedicalEquip to the table
   *
   * @param obj MedicalEquip
   * @return True if successful, false if not
   */
  @Override
  public boolean add(Object obj) {
    try {
      if (!(obj instanceof MedicalEquip)) {
        return false;
      }
      MedicalEquip medE = (MedicalEquip) obj;
      String add = "INSERT INTO MedicalEquipment(ID,Type,Clean,Location,Status)VALUES(?,?,?,?,?)";
      PreparedStatement preparedStatement = DBconnection.getConnection().prepareStatement(add);
      preparedStatement.setString(1, medE.getID());
      preparedStatement.setString(2, medE.getType());
      if (medE.isClean()) {
        preparedStatement.setString(3, "Clean");
      } else {
        preparedStatement.setString(3, "Dirty");
      }
      preparedStatement.setString(4, medE.getLocation().getLongName());
      preparedStatement.setString(5, medE.getStatus());
      preparedStatement.execute();
      return true;
    } catch (SQLException e) {
      return false;
    }
  }

  /**
   * Deletes a MedicalEquip from the table
   *
   * @param str MedicalEquip ID
   * @return True if successful, false if not
   */
  @Override
  public boolean delete(String str) {
    try {
      String delete = "DELETE FROM MedicalEquipment WHERE ID = ?";
      PreparedStatement preparedStatement = DBconnection.getConnection().prepareStatement(delete);
      preparedStatement.setString(1, str);
      preparedStatement.execute();
      return true;
    } catch (SQLException e) {
      return false;
    }
  }

  /**
   * Updates the MedicalEquip entry by ID
   *
   * @param obj MedicalEquip
   * @return True if successful, false if not
   */
  @Override
  public boolean update(Object obj) {
    try {
      if (!(obj instanceof MedicalEquip)) {
        return false;
      }
      MedicalEquip medE = (MedicalEquip) obj;
      String update =
          "UPDATE MedicalEquipment SET Type = ?, Clean = ?, Location = ?, Status = ? WHERE ID = ?";
      PreparedStatement preparedStatement = DBconnection.getConnection().prepareStatement(update);
      preparedStatement.setString(1, medE.getType());
      if (medE.isClean()) {
        preparedStatement.setString(2, "Clean");
      } else {
        preparedStatement.setString(2, "Dirty");
      }
      preparedStatement.setString(3, medE.getLocation().getLongName());
      preparedStatement.setString(4, medE.getStatus());
      preparedStatement.setString(5, medE.getID());
      preparedStatement.execute();
      return true;
    } catch (SQLException e) {
      return false;
    }
  }

  /**
   * Creates a new MedicalEquipment table
   *
   * @return True if successful, false if not
   */
  @Override
  public boolean createTable() {
    try {
      Statement queryEquip = DBconnection.getConnection().createStatement();
      String queryEq =
          "CREATE TABLE MedicalEquipment ( "
              + "ID VARCHAR(50),"
              + "Type VARCHAR(50),"
              + "Clean VARCHAR(50),"
              + "Location VARCHAR(50),"
              + "Status VARCHAR(50),"
              + "PRIMARY KEY (ID))";

      queryEquip.execute(queryEq);
      return true;
    } catch (SQLException e) {
      return false;
    }
  }

  /**
   * Drops an existing MedicalEquipment table from the database
   *
   * @return True if successful, false if not
   */
  @Override
  public boolean dropTable() {
    try {
      Statement dropTable = DBconnection.getConnection().createStatement();
      String queryDropMed = "DROP TABLE MedicalEquipment";
      dropTable.execute(queryDropMed);
      return true;
    } catch (SQLException e) {
      return false;
    }
  }



  @Override
  public HashMap<String, Object> getData() throws SQLException {
    String sql = "SELECT * FROM ServiceRequests";
    HashMap<String, Employee> employeeHashmap = Adb.getEmployees();
    HashMap<String, Location> locationHashMap = Adb.getLocations();

    HashMap<String, Object> empty = new HashMap();

    Connection connection = DBconnection.getConnection();

    Statement statement = connection.createStatement();
    ResultSet result = statement.executeQuery(sql);


    while (result.next()) {
      String name = result.getString("Name");
      Employee employee = employeeHashmap.get(result.getString("employeename"));
      Location location = locationHashMap.get(result.getString("location"));
      String type = result.getString("type");
      String status = result.getString("status");
      String description = result.getString("description");
      String attribute1 = result.getString("attribute1");
      String attribute2 = result.getString("attribute2");

      Request request = new Request(name, employee, location, type, status, description, attribute1, attribute2);

      if (name.substring(0, 3).compareTo("Med") == 0) {
        Adb.addMedRequest(request);
      } else if (name.substring(0, 4).compareTo("Meal") ==0) {
        Adb.addMealRequest(request);
      } else if (name.substring(0, 4).compareTo("L") == 0) {
        Adb.addLabRequest(request);
      } else if (name.substring(0, 4).compareTo("Main") == 0) {
        Adb.addmainRequest(request);
      } else if (name.substring(0, 4).compareTo("Tran") == 0) {
        {
          Adb.addTransportRequest(request);
        }
      } else if (name.substring(0, 3).compareTo("Mor") == 0) {
        Adb.addMorgueRequest(request);

      }

    }

    return empty;


  }
}
