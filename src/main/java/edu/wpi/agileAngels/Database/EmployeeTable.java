package edu.wpi.agileAngels.Database;

import edu.wpi.agileAngels.Adb;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class EmployeeTable implements TableI {
  /**
   * Adds a new Employee to the employee table.
   *
   * @param obj Employee
   * @return True if successful, false if not
   */
  @Override
  public boolean add(Object obj) {
    try {
      if (!(obj instanceof Employee)) {
        return false;
      }
      Employee emp = (Employee) obj;
      String add = "INSERT INTO Employees(name, password, floorOnDuty)VALUES(?,?,?)";
      PreparedStatement preparedStatement = DBconnection.getConnection().prepareStatement(add);
      preparedStatement.setString(1, emp.getName());
      preparedStatement.setString(2, emp.getPassword());
      preparedStatement.setString(3, emp.getFloorOnDuty());
      preparedStatement.execute();
      return true;
    } catch (SQLException sqlException) {
      return false;
    }
  }

  /**
   * Deletes an employee from the employee table
   *
   * @param str Employee name
   * @return True if successful, false if not
   */
  @Override
  public boolean delete(String str) {
    try {
      PreparedStatement preparedStatement =
          DBconnection.getConnection().prepareStatement("DELETE FROM Employees WHERE name = ?");
      preparedStatement.setString(1, str);
      preparedStatement.execute();
      return true;
    } catch (SQLException sqlException) {
      return false;
    }
  }

  /**
   * Updates an employee by name
   *
   * @param obj updated Employee
   * @return True if successful, false if not
   */
  @Override
  public boolean update(Object obj) {
    try {
      if (!(obj instanceof Employee)) {
        return false;
      }
      Employee emp = (Employee) obj;
      PreparedStatement preparedStatement =
          DBconnection.getConnection()
              .prepareStatement(
                  "UPDATE Employees SET password = ?, floorOnDuty = ? WHERE name = ?");
      preparedStatement.setString(1, emp.getPassword());
      preparedStatement.setString(2, emp.getFloorOnDuty());
      preparedStatement.setString(3, emp.getName());
      preparedStatement.execute();
      return true;
    } catch (SQLException sqlException) {
      return false;
    }
  }

  /**
   * Creates a new employee table
   *
   * @return True if successful, false if not
   */
  @Override
  public boolean createTable() {
    try {
      Statement query = DBconnection.getConnection().createStatement();
      String queryEmployees =
          "CREATE TABLE Employees( "
              + "Name VARCHAR(50),"
              + "Password VARCHAR(50),"
              + "FloorOnDuty VARCHAR(50),"
              + "PRIMARY KEY (Name))";
      query.execute(queryEmployees);
      return true;
    } catch (SQLException sqlException) {
      return false;
    }
  }

  /**
   * Drops the employee table
   *
   * @return True if successful, false if not
   */
  @Override
  public boolean dropTable() {
    try {
      Statement droptable = DBconnection.getConnection().createStatement();
      String dropLoc = "DROP TABLE Employees";
      droptable.execute(dropLoc);
      return true;
    } catch (SQLException sqlException) {
      return false;
    }
  }

  @Override
  public HashMap<String, Object> getData() throws SQLException {
    Adb.readCSVEmployees();
    return Adb.getEmployees();
  }
}
