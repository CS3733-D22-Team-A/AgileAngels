package edu.wpi.agileAngels;

import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

public class Node {
    //private MapsController maps = new MapsController();
    private Location location;

    public Node(Location location, MapsController maps) {
        this.location = location;
        // this.maps = maps;
    }


    public Circle getDot() {
        Circle circle = new Circle();
        circle.setOnMousePressed(
                (MouseEvent event) -> {
                    if (event.isSecondaryButtonDown()) {
                        delete();
                    }
                });
        return circle;
    }

    public void delete(){
        // MapsController.deleteLocation();

    }

}