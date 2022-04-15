/*-------------------------*/
/* DO NOT DELETE THIS TEST */
/*-------------------------*/

package edu.wpi.agileAngels;

import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.wpi.agileAngels.Controllers.LoginController;
import edu.wpi.agileAngels.Controllers.MethodTestClass;
import edu.wpi.agileAngels.Database.Employee;
import edu.wpi.agileAngels.Database.Location;
import edu.wpi.agileAngels.Database.Request;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

public class DefaultTest {

  LoginController dansLController = new LoginController();

  public DefaultTest() throws SQLException {}

  @Test
  public void test() {}

  @Test
  public void initialsMakerDanielTest() {
    String danInitials = "DO";
    assertTrue(danInitials.equals(dansLController.initialsMaker("Daniel Onyema")));
  }

  // Testing
  @Test
  public void initialsMakerJustinTest() {
    String justinInitials = "JW";
    assertTrue(
        justinInitials.equals(dansLController.initialsMaker("Justin Paul Santiago - Wonoski")));
  }

  @Test
  public void initialsMakerEmptyTest() {
    String notApplicable = "N/A";
    assertTrue(notApplicable.equals(dansLController.initialsMaker("")));
  }

  @Test
  public void filterReqEmployeeHarmoniTest() throws SQLException {
    MethodTestClass dansMTC = new MethodTestClass(0);

    ObservableList<Request> dansList = FXCollections.observableArrayList();
    Employee harmoni = new Employee("Harmoni", "needsToPlayPokemon");
    Employee joe = new Employee("Joe", "Sheeeesh");
    Employee aaron = new Employee("Aaron", "He plays League?");
    Location unityHall =
        new Location(
            "balls", 1, 1, "your mom", "backrooms", "help", "guys im coding", "cook-hungy");

    Request hReq1 = new Request("hReq1", harmoni, unityHall, "ooh", "eee", "ooh", "aah", "ahh");
    Request hReq2 =
        new Request("hReq1", harmoni, unityHall, "BALLS", "JAKOB", "TOUCHES", "HIS", "BIKE");
    Request notHReq =
        new Request("hReq1", joe, unityHall, "BALLS", "JAKOB", "TOUCHES", "HIS", "BIKE");
    Request notHReq2 =
        new Request("hReq1", aaron, unityHall, "BALLS", "JAKOB", "TOUCHES", "HIS", "BIKE");
    Request notHReq3 =
        new Request("hReq1", joe, unityHall, "BALLS", "JAKOB", "TOUCHES", "HIS", "BIKE");
    Request notHReq4 =
        new Request("hReq1", joe, unityHall, "BALLS", "JAKOB", "TOUCHES", "HIS", "BIKE");

    dansList.add(hReq1);
    dansList.add(hReq2);
    dansList.add(notHReq);
    dansList.add(notHReq2);
    dansList.add(notHReq3);
    dansList.add(notHReq4);

    dansMTC.filterReqEmployeeNoMedData(harmoni, dansList);

    assertTrue(dansList.contains(hReq1) && dansList.contains(hReq2));
  }
}
