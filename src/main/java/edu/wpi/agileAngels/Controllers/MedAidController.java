package edu.wpi.agileAngels.Controllers;

import edu.wpi.agileAngels.Database.Employee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class MedAidController implements Initializable {
    public Pane treatment1, treatment2, treatment3;
    public TextField Condition;
    public Label treatmentLabel1, treatmentLabel2, treatmentLabel3;
    public TableView MedAidTable;
    public TableColumn availableColumn, employeeColumn;
    public ImageView icon1, icon2, icon3;

    AppController appController = AppController.getInstance();
    private static ObservableList<MedicalStaff> empList = FXCollections.observableArrayList();

    private void MedAidViewController() {
    }

    LinkedList<Employee> employees = new LinkedList<Employee>();
    LinkedList<String> treatments = new LinkedList<String>();
    HashMap<String, Condition> data = new HashMap<String, Condition>();
    // Condition condition = new Condition(employees, treatments);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scan(data);
        treatment1.setVisible(false);
        treatment2.setVisible(false);
        treatment3.setVisible(false);
        employeeColumn.setCellValueFactory(new PropertyValueFactory<MedicalStaff, String>("Employee"));
        availableColumn.setCellValueFactory(
                new PropertyValueFactory<MedicalStaff, String>("Available"));
    }

    public void submit(ActionEvent actionEvent) {

        String answer = Condition.getText();
        Condition con = data.get(answer);
        int size = con.treatments.size();
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (con.treatments.get(i).equals("N/A")) count++;
        }
        size = size - count;
        System.out.println(size);
        clearAlso();
        if (size == 0) {
        } else if (size == 1) {
            displayIcon(con.treatments.get(0), 1);
            treatment1.setVisible(true);
            treatmentLabel1.setText(con.treatments.get(0));
        } else if (size == 2) {
            displayIcon(con.treatments.get(0), 1);
            displayIcon(con.treatments.get(1), 2);
            treatment1.setVisible(true);
            treatmentLabel1.setText(con.treatments.get(0));
            treatment2.setVisible(true);
            treatmentLabel2.setText(con.treatments.get(1));
        } else {
            displayIcon(con.treatments.get(0), 1);

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

    private void scan(HashMap<String, Condition> data) {
        String line = "";
        String splitBy = ",";

        try {
            BufferedReader br = new BufferedReader(new FileReader("MedAid.csv"));
            boolean OnHeader = false;
            line.split(splitBy);

            while ((line = br.readLine()) != null) {
                if (OnHeader) {
                    String[] values = line.split(splitBy);
                    System.out.println("NUM ENTRIES " + values.length);
                    data.put(values[0], new Condition(new LinkedList<>(), new LinkedList<>()));
                    data.get(values[0].toString()).treatments.add(values[1]);
                    data.get(values[0].toString()).treatments.add(values[2]);
                    data.get(values[0].toString()).treatments.add(values[3]);
                    // System.out.println(data.get(values[0]).treatments.get(1));
                } else {
                    OnHeader = true;
                }
            }
        } catch (IOException var7) {
            var7.printStackTrace();
        }
    }

    private void displayIcon(String treatment, int iconNum) {

        System.out.println(treatment);


        //pills
        Image im1 = new Image("https://www.pinclipart.com/picdir/middle/523-5239855_pill-clipart-svg-pills-icon-png-transparent-png.png");
        //bed
        Image im2 = new Image("http://simpleicon.com/wp-content/uploads/rest.png");
        //Hydration
        Image im3 = new Image("https://png.pngtree.com/png-vector/20190328/ourlarge/pngtree-vector-water-bottle-icon-png-image_872119.jpg");
        //Surgery
        Image im4 = new Image("https://cdn-icons-png.flaticon.com/512/3030/3030898.png");
        //Therapy
        Image im5 = new Image("https://icon-library.com/images/therapy-icon/therapy-icon-6.jpg");
        if (treatment.equals("Medication") || treatment.equals("Antibiotics")) {
            if (iconNum == 1) icon1.setImage(im1);
            else if (iconNum == 2) icon2.setImage(im1);
            else icon3.setImage(im1);
        }
        if (treatment.equals("Rest")) {
            if (iconNum == 1) icon1.setImage(im2);
            else if (iconNum == 2) icon2.setImage(im2);
            else icon3.setImage(im2);
        }
        if (treatment.equals("Drink Water") || treatment.equals("Hydration")) {
            if (iconNum == 1) icon1.setImage(im3);
            else if (iconNum == 2) icon2.setImage(im3);
            else icon3.setImage(im3);
        }
        if (treatment.equals("Surgery")) {
            if (iconNum == 1) icon1.setImage(im4);
            else if (iconNum == 2) icon2.setImage(im4);
            else icon3.setImage(im4);
        }
        else {
            if (iconNum == 1) icon1.setImage(im5);
            else if (iconNum == 2) icon2.setImage(im5);
            else icon3.setImage(im5);
        }
    }
}
