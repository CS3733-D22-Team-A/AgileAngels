package edu.wpi.agileAngels;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class MedDAOImpl implements MedDAO {
  // TODO: delete class, move parser
  private final String CSV_FILE_PATH = "./MedData.csv";
  private HashMap<String, MedDevice> medData = new HashMap<>();

  public MedDAOImpl() throws SQLException {
    Connection connection = DBconnection.getConnection();
    try (Reader reader = Files.newBufferedReader(Paths.get(CSV_FILE_PATH));
        CSVParser csvParser =
            new CSVParser(
                reader,
                CSVFormat.DEFAULT
                    .withHeader(
                        "Name",
                        "Available",
                        "Type",
                        "Location",
                        "building",
                        "Employee",
                        "Status",
                        "Description")
                    .withIgnoreHeaderCase()
                    .withTrim())) {

      for (CSVRecord csvRecord : csvParser) { // each row has a dictionary

        String name = csvRecord.get(0);
        if (!name.equals("name")) {
          Statement statement =
              connection.prepareStatement(
                  "INSERT INTO MedicalEquipment(name,available ,type, location, employee, status, description)values(?,?,?,?,?,?,?)");

          // Accessing values by the names assigned to each column

          for (int i = 1; i < 8; i++) { // each item in the row
            ((PreparedStatement) statement)
                .setString(
                    i, csvRecord.get(i - 1)); // to access the first value for table it starts at 1
          }
          MedDevice medDevice =
              new MedDevice(
                  csvRecord.get(0),
                  csvRecord.get(1),
                  csvRecord.get(2),
                  csvRecord.get(3),
                  csvRecord.get(4),
                  csvRecord.get(5),
                  csvRecord.get(6));
          medData.put(csvRecord.get(0), medDevice);

          ((PreparedStatement) statement).execute();
        }
      }
    } catch (SQLException | IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public HashMap<String, MedDevice> getAllMedicalEquipmentRequests() {
    return medData;
  }

  @Override
  // database needs unique names to recognize medical requests
  public void updateName(MedDevice medDevice, String newName) {
    medDevice.setName(newName);
    System.out.println(
        "Medical Equipment: Name " + medDevice.getName() + ", updated in the database");
  }

  @Override
  public void updateAvailability(MedDevice medDevice, String newAvailability) {
    medDevice.setAvailable(newAvailability);
    System.out.println(
        "Medical Equipment: Name " + medDevice.getName() + ", updated in the database");
  }

  @Override
  public void updateType(MedDevice medDevice, String newType) {
    medDevice.setType(newType);
    System.out.println(
        "Medical Equipment: Name " + medDevice.getName() + ", updated in the database");
  }

  @Override
  public void updateLocation(MedDevice medDevice, String newLocation) {
    medDevice.setLocation(newLocation);
    System.out.println(
        "Medical Equipment: Name " + medDevice.getName() + ", updated in the database");
  }

  @Override
  public void updateEmployee(MedDevice medDevice, String newEmployee) {
    medDevice.setEmployee(newEmployee);
    System.out.println(
        "Medical Equipment: Name " + medDevice.getName() + ", updated in the database");
  }

  @Override
  public void updateStatus(MedDevice medDevice, String newStatus) {
    medDevice.setStatus(newStatus);
    System.out.println(
        "Medical Equipment: Name " + medDevice.getName() + ", updated in the database");
  }

  @Override
  public void deleteMed(MedDevice medDevice) {
    medData.remove(medDevice.getName());
    System.out.println(
        "Medical Equipment: Name " + medDevice.getName() + ", deleted in the database");
  }

  @Override
  public void addMed(MedDevice medDevice) {
    medData.put(medDevice.getName(), medDevice);
    // (Adb instance).addMedicalEquipmentRequest(medDevice);
    System.out.println(
        "Medical Equipment: Name " + medDevice.getName() + ", added in the database");
  }
}
