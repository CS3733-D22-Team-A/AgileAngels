package edu.wpi.agileAngels;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.scene.shape.Circle;

// test button on the front end
public class TestController extends MainController {

  private ArrayList<Circle> circles = new ArrayList<Circle>();

  public void newCircle(ActionEvent event) {
    circles.add(new Circle());

    circles.get(0).setRadius(100);
    circles.get(0).setId("circle");
    circles.get(0).setLayoutX(100);
    circles.get(0).setLayoutY(100);
    circles.get(0).setVisible(true);
  }
}
