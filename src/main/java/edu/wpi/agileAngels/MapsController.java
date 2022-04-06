package edu.wpi.agileAngels;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class MapsController extends MainController {

  @FXML
  private ImageView floorOneMap, floorTwoMap, floorThreeMap, lowerLevelOneMap, lowerLevelTwoMap;

  @FXML private Button floorOne, floorTwo, floorThree, lowerLevelOne, lowerLevelTwo,
  updateButton,addButton,removeButton,clearButton;

  @FXML
  private TextField nodeIDField,nameField,xCoordField,yCoordField,nodeTypeField;

  private void changeFloor(){ //takes in a int or a string once it's implemented.

  }

  private void addNode(String nodeID,String name,double xCoord,double yCoord,String nodeType){
    //adds node to page.
  }

  private void editNode(String nodeID, String name,double xCoord, double yCoord, String nodeType){

  }

  private void removeNode(String nodeID){
   // Node.remove(NodeID) mega brain.
  }

  private void clearFields(){
    //I have no clue how to write this without fields yet.
  }

  private void switchMode(){
    //Can't do anything till pages set up.
  }

  private void displayNode(Node){
    //At the time of coding there's no node class so this will cause errors.
  }

}
