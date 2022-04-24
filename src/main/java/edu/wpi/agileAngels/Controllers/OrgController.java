package edu.wpi.agileAngels.Controllers;

import edu.wpi.agileAngels.Database.Employee;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class OrgController implements Initializable {

  AppController appController = AppController.getInstance();
  EmployeeManager employeeManager = EmployeeManager.getInstance();

  @FXML HBox boss, coworkers, kiddos;

  public void initialize(URL location, ResourceBundle resources) {
    createChart(appController.getCurrentUser());
  }

  void createChart(Employee employee) {
    boss.getChildren().clear();
    coworkers.getChildren().clear();
    kiddos.getChildren().clear();

    Button bossButton = new Button(employee.getSupervisor().getName());
    bossButton.setPrefSize(100, 100);
    bossButton.setOnAction(
        event -> {
          if (employee.getSupervisor().getSupervisor() == null) {
            createChart(employee);
          } else {
            createChart(employee.getSupervisor());
          }
        });
    boss.getChildren().add(bossButton);

    for (Employee e : employee.getSupervisor().getSupervisees()) {
      Button person = new Button(e.getName());
      person.setPrefSize(100, 100);
      if (e.equals(employee)) {
        person.setStyle("-fx-background-color: #49A3AE");
      }
      person.setOnAction(
          event -> {
            createChart(employeeManager.getEmployee(person.getText()));
            System.out.println(person.getText());
          });
      coworkers.getChildren().add(person);
    }

    for (Employee e : employee.getSupervisees()) {
      Button kiddo = new Button(e.getName());
      kiddo.setPrefSize(100, 100);
      kiddo.setOnAction(
          event -> {
            createChart(employeeManager.getEmployee(kiddo.getText()));
          });
      kiddos.getChildren().add(kiddo);
    }
  }
}
