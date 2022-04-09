package edu.wpi.agileAngels.Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class EmployeeTable implements TableI {

    @Override
    public boolean add(Object obj) {
        try {
            if(!(obj instanceof Employee)){
                return false;
            }
            Employee emp = (Employee) obj;
            String add =
                    "INSERT INTO Employees(name, requests)VALUES(?,?)";
            PreparedStatement preparedStatement = DBconnection.getConnection().prepareStatement(add);
            preparedStatement.setString(1, emp.getName());
            preparedStatement.setString(2, emp.getRequests());
            preparedStatement.execute();
            return true;
        }catch(SQLException sqlException){
            return false;
        }
    }

    @Override
    public boolean delete(String str){
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

            @Override
            public boolean update (Object obj){
            try {
                if (!(obj instanceof Employee)) {
                    return false;
                }
                Employee emp = (Employees) obj;
                PreparedStatement preparedStatement =
                        DBconnection.getConnection().prepareStatement("UPDATE RequestTable SET requests = ? WHERE name = ?");
                preparedStatement.setString(1, emp.getRequests());
                preparedStatement.setString(2, emp.getName());
                preparedStatement.execute();
                return true;
            } catch (SQLException sqlException) {
                return false;
            }
        }

            @Override
            public boolean createTable () {
            try {
                Statement query = DBconnection.getConnection().createStatement();
                String queryEmployees = "CREATE TABLE Employees( "
                        + "name VARCHAR(50),"
                        + "requests VARCHAR(50))";
                query.execute(queryEmployees);
                return true;
                } catch (SQLException sqlException) {
                return false;
                }
            }


            @Override
            public boolean dropTable () {
            try {
                Statement droptable = DBconnection.getConnection().createStatement();
                String dropLoc = "DROP TABLE Employees";
                droptable.execute(dropLoc);
                return true;
                } catch (SQLException sqlException) {
                return false;
                }
            }
    }
