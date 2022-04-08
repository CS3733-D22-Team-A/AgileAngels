package edu.wpi.agileAngels;

import java.util.ArrayList;
import java.util.HashMap;

public class EmployeeManager implements EmployeeDAO{

    private HashMap<Integer, Employee> employeeHashMap;

    // Create employee object.
    public HashMap getAllEmployees(){
        return this.employeeHashMap;
    }

    // Get Employee from hash.
    public Employee getEmployee(int userID){
        return employeeHashMap.get(userID);
    }

    public void removeEmployee(int userID){
        employeeHashMap.remove(userID);
    }

    public void addEmployee(String name, String username, String password, int userID){
        ArrayList<Request> newERequest = new ArrayList<Request>();
        Employee newEmployee = new Employee(name, username, password,newERequest);
        employeeHashMap.put(userID, newEmployee);
    }

    public void updateEmployeeName(int userID, String newName){
        employeeHashMap.get(userID).setName(newName);
    }

    public void updateEmployeeUsername(int userID, String newUN){
        employeeHashMap.get(userID).setUsername(newUN);
    }

    public void updateEmployeePassword(int userID, String newPW){
        employeeHashMap.get(userID).setPassword(newPW);
    }

    public ArrayList<Request> getRequests(int userID){
        return employeeHashMap.get(userID).getRequests();
    }

    public void addRequest(int userID, Request newReq){
        employeeHashMap.get(userID).addRequest(newReq);
    }

    public void removeRequest(int userID, Request oldReq){
        employeeHashMap.get(userID).removeRequest(oldReq);
    }


}
