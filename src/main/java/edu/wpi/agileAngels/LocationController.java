package edu.wpi.agileAngels;

import javafx.beans.property.SimpleListProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class LocationController extends MainController implements Initializable {
    @FXML
    private TableColumn nodeIDColumn,xCoordColumn,yCoordColumn,floorColumn,
    buildingColumn,nodeTypeColumn,longNameColumn,shortNameColumn;

    @FXML
    private TableView locationTable;

    SimpleListProperty location;

    public void submitData() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    super.initalize();
        //Implement DAO here.
        LocationDAOImpl = locationDAO;
        try {
            employees = dao.findAll();

            // set data for the table created by the FXMLLoader
            table.setItems(employees);

            // no need to add them to the table since the FXMLLoader is ready doing that
            firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        } catch (SQLException e) {
            // TODO Mettre une popup erreur base de données
            e.printStackTrace();
        }

    }
    }
}
