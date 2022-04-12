package edu.wpi.agileAngels.Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

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
              + "Status VARCHAR(50))";
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
}
