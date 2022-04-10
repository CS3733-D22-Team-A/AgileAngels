package edu.wpi.agileAngels.Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class MedicalEquipmentTable implements TableI {

  @Override
  public boolean add(Object obj) {
    try {
      if (!(obj instanceof MedicalEquip)) {
        return false;
      }
      MedicalEquip medE = (MedicalEquip) obj;
      String add = "INSERT INTO MedicalEquipment(ID,Type,Clean,Location)VALUES(?,?,?,?)";
      PreparedStatement preparedStatement = DBconnection.getConnection().prepareStatement(add);
      preparedStatement.setString(1, medE.getID());
      preparedStatement.setString(2, medE.getType());
      if (medE.isClean()) {
        preparedStatement.setString(3, "Clean");
      } else {
        preparedStatement.setString(3, "Dirty");
      }
      preparedStatement.setString(4, medE.getLocation());
      preparedStatement.execute();
      return true;
    } catch (SQLException e) {
      return false;
    }
  }

  @Override
  public boolean delete(String str) {
    try {
      String delete = "DELETE FROM MedicalEquipment WHERE ID = ?";
      PreparedStatement preparedStatement = DBconnection.getConnection().prepareStatement(delete);
      preparedStatement.setString(1, str);
      return true;
    } catch (SQLException e) {
      return false;
    }
  }

  @Override
  public boolean update(Object obj) {
    try {
      if (!(obj instanceof MedicalEquip)) {
        return false;
      }
      MedicalEquip medE = (MedicalEquip) obj;
      String update = "UPDATE MedicalEquipment SET Type = ?, Clean = ?, Location = ? WHERE = ?";
      PreparedStatement preparedStatement = DBconnection.getConnection().prepareStatement(update);
      preparedStatement.setString(1, medE.getType());
      preparedStatement.setString(2, "Clean");
      preparedStatement.setString(3, medE.getLocation());
      preparedStatement.setString(4, medE.getID());
      return true;
    } catch (SQLException e) {
      return false;
    }
  }

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
              + "PRIMARY KEY (ID))";
      queryEquip.execute(queryEq);
      return true;
    } catch (SQLException e) {
      return false;
    }
  }

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
