package edu.wpi.agileAngels.Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ServiceRequestTable implements TableI {

  /**
   * Adds a new Request to the service request table.
   *
   * @param obj new Request
   * @return True if successful, false otherwise.
   */
  @Override
  public boolean add(Object obj) {
    try {
      if (!(obj instanceof Request)) {
        return false;
      }
      Request request = (Request) obj;
      String add =
          "INSERT INTO ServiceRequests(Name, EmployeeName, Location, Type, Status, Description, Attribute1, Attribute2) VALUES(?,?,?,?,?,?,?,?)";
      PreparedStatement preparedStatement = DBconnection.getConnection().prepareStatement(add);
      preparedStatement.setString(1, request.getName());
      preparedStatement.setString(2, request.getEmployee());
      preparedStatement.setString(3, request.getLocation());
      preparedStatement.setString(4, request.getType());
      preparedStatement.setString(5, request.getStatus());
      preparedStatement.setString(6, request.getDescription());
      preparedStatement.setString(7, request.getAttribute1());
      preparedStatement.setString(8, request.getAttribute2());
      preparedStatement.execute();
      return true;
    } catch (SQLException sqlException) {
      return false;
    }
  }

  /**
   * Deletes a service request by name.
   *
   * @param str Request name
   * @return True if successful, false otherwise.
   */
  @Override
  public boolean delete(String str) {
    try {
      String delete = "DELETE FROM ServiceRequests WHERE Name = ?";
      PreparedStatement preparedStatement = DBconnection.getConnection().prepareStatement(delete);
      preparedStatement.setString(1, str);
      preparedStatement.execute();
      return true;
    } catch (SQLException sqlException) {
      return false;
    }
  }

  /**
   * Updates Request specified by the name.
   *
   * @param obj updated Request
   * @return True if successful, false otherwise.
   */
  @Override
  public boolean update(Object obj) {
    try {
      if (!(obj instanceof Request)) {
        return false;
      }
      Request request = (Request) obj;
      String update =
          "UPDATE ServiceRequests SET EmployeeName = ?, Location = ?, Type = ?, Status = ?, Description = ?, Attribute1 = ?, Attribute2 = ? WHERE Name = ?";
      PreparedStatement preparedStatement = DBconnection.getConnection().prepareStatement(update);
      preparedStatement.setString(1, request.getEmployee());
      preparedStatement.setString(2, request.getLocation());
      preparedStatement.setString(3, request.getType());
      preparedStatement.setString(4, request.getStatus());
      preparedStatement.setString(5, request.getDescription());
      preparedStatement.setString(6, request.getAttribute1());
      preparedStatement.setString(7, request.getAttribute2());
      preparedStatement.setString(8, request.getName());
      preparedStatement.execute();
      return true;
    } catch (SQLException sqlException) {
      return false;
    }
  }

  /**
   * Creates a new service request table.
   *
   * @return True if successful, false otherwise.
   */
  @Override
  public boolean createTable() {
    try {
      Statement query = DBconnection.getConnection().createStatement();
      String queryServiceRequests =
          "CREATE TABLE ServiceRequests("
              + "Name VARCHAR(50),"
              + "EmployeeName VARCHAR(50),"
              + "Location VARCHAR(50),"
              + "Type VARCHAR(50),"
              + "Status VARCHAR(50),"
              + "Description VARCHAR(50),"
              + "Attribute1 VARCHAR(50),"
              + "Attribute2 VARCHAR(50),"
              + "PRIMARY KEY (Name))";
      query.execute(queryServiceRequests);
      return true;
    } catch (SQLException sqlException) {
      return false;
    }
  }

  /**
   * Drops the service request table.
   *
   * @return True if successful, false otherwise.
   */
  @Override
  public boolean dropTable() {
    try {
      Statement dropTable = DBconnection.getConnection().createStatement();
      String queryDropMed = "DROP TABLE ServiceRequests";
      dropTable.execute(queryDropMed);
      return true;
    } catch (SQLException sqlException) {
      return false;
    }
  }
}
