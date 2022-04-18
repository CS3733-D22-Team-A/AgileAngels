package edu.wpi.agileAngels.Controllers;

import edu.wpi.agileAngels.Database.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class BedAlertController {

  @FXML Button requestButton;
  private AppController appController = AppController.getInstance();
  private MedEquipImpl equipDAO = MedEquipImpl.getInstance();
  private HashMap<String, MedicalEquip> equipHash = equipDAO.getAllMedicalEquipment();
  private ArrayList<MedicalEquip> equipList = new ArrayList<>(equipHash.values());
  private RequestDAOImpl requestDAO = RequestDAOImpl.getInstance("MedRequest");
  private LocationDAOImpl locationDAO = LocationDAOImpl.getInstance();
  private HashMap<String, Location> locationHash = locationDAO.getAllLocations();
  private EmployeeManager employeeDAO = EmployeeManager.getInstance();
  private HashMap<String, Location> employeeHash = employeeDAO.getAllEmployees();

  public BedAlertController() throws SQLException {
  }

  @FXML
  public void makeRequests() {
    String floor = appController.getBedFloor();
    ArrayList<MedicalEquip> dirtyBeds = new ArrayList<>();

    // get all dirty beds
    for (MedicalEquip medEquip : equipList) {
      if (!medEquip.isClean()
              && medEquip.getType().equals("Bed")
              && medEquip.getLocation().getFloor().equals(floor)) {
        dirtyBeds.add(medEquip);
      }
    }

    // create requests to clean all dirty beds
    for (MedicalEquip medEquip : dirtyBeds) {
      Request medDevice =
              new Request(
                      "MedClean",
                      employeeDAO.getEmployee("James"),
                      medEquip.getLocation(),
                      "Bed",
                      "notStarted",
                      "describe",
                      "something",
                      "Clean",
                      medEquip);
      requestDAO.addRequest(medDevice);

      // set status of bed
      medEquip.setStatus("inUse");
    }

    // close the alert window
    closeWindow();
  }

  public void closeWindow() {
    Stage stage = (Stage) requestButton.getScene().getWindow();
    stage.close();
  }
}
