package edu.wpi.agileAngels.Controllers;

import edu.wpi.agileAngels.Database.Employee;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class MedAidController implements Initializable {
  public Pane treatment1, treatment2, treatment3;
  public TextField Condition;
  public Label treatmentLabel1, treatmentLabel2, treatmentLabel3;
  public TableView MedAidTable;
  public TableColumn availableColumn, employeeColumn;

  AppController appController = AppController.getInstance();
  private static ObservableList<MedicalStaff> empList = FXCollections.observableArrayList();
  private static ObservableList<MedicalStaff> empty = FXCollections.observableArrayList();

  private void MedAidViewController() {}

  LinkedList<Employee> employees = new LinkedList<Employee>();
  LinkedList<String> treatments = new LinkedList<String>();
  HashMap<String, Condition> data = new HashMap<String, Condition>();
  Condition condition = new Condition(employees, treatments);

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    treatment1.setVisible(false);
    treatment2.setVisible(false);
    treatment3.setVisible(false);
    employeeColumn.setCellValueFactory(new PropertyValueFactory<MedicalStaff, String>("Employee"));
    availableColumn.setCellValueFactory(
        new PropertyValueFactory<MedicalStaff, String>("Available"));

    Employee emp1 = new Employee("Employee1", "123", "3");
    Employee emp2 = new Employee("Employee2", "123", "3");
    Employee emp3 = new Employee("Employee3", "123", "3");
    Employee emp4 = new Employee("Employee4", "123", "3");
    Condition con1 = new Condition(new LinkedList<>(), new LinkedList<>());
    Condition con2 = new Condition(new LinkedList<>(), new LinkedList<>());
    Condition con3 = new Condition(new LinkedList<>(), new LinkedList<>());
    Condition con4 = new Condition(new LinkedList<>(), new LinkedList<>());
    data.put("Mono", con1);
    con1.treatments.add("Sleep");
    con1.treatments.add("Rest");
    con1.treatments.add("Pain Killers");
    con1.employees.add(emp1);
    data.put("Cancer", con2);
    con2.treatments.add("Chemo");
    con2.treatments.add("Radiation");
    con2.employees.add(emp1);
    con2.employees.add(emp2);
    data.put("Heart Attack", con3);
    con3.treatments.add("Medication");
    con3.treatments.add("Be healthy");
    data.put("Depression", con4);
    con4.treatments.add("be happier");
    //  con1.treatments.add("Depression");
  }

  public void submit(ActionEvent actionEvent) {

    String answer = Condition.getText();
    Condition con = data.get(answer);
    int size = con.treatments.size();
    System.out.println(size);
    clearAlso();
    if (size == 0) {
    } else if (size == 1) {
      treatment1.setVisible(true);
      treatmentLabel1.setText(con.treatments.get(0));
    } else if (size == 2) {
      treatment1.setVisible(true);
      treatmentLabel1.setText(con.treatments.get(0));
      treatment2.setVisible(true);
      treatmentLabel2.setText(con.treatments.get(1));
    } else {
      treatment1.setVisible(true);
      treatmentLabel1.setText(con.treatments.get(0));
      treatment2.setVisible(true);
      treatmentLabel2.setText(con.treatments.get(1));
      treatment3.setVisible(true);
      treatmentLabel3.setText(con.treatments.get(2));
    }
    populateTable(con);
  }

  public void clear(ActionEvent actionEvent) {
    clearAlso();
  }

  public void clearAlso() {
    treatment1.setVisible(false);
    treatment2.setVisible(false);
    treatment3.setVisible(false);
    empList.clear();
  }

  private void populateTable(Condition condition) {

    // LinkedList<Employee> list = condition.employees;
    for (int i = 0; i < condition.employees.size(); i++) {
      MedicalStaff a = new MedicalStaff(condition.employees.get(i).getName(), "N/A");
      empList.add(a);

      MedAidTable.setItems(empList);
      // MedAidTable.getColumns().addAll(employeeColumn, availableColumn);
      System.out.println(condition.employees.get(i).getName());
    }

    // MedAidTable.setItems(empList);
  }
}
