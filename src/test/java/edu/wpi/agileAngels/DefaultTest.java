/*-------------------------*/
/* DO NOT DELETE THIS TEST */
/*-------------------------*/

package edu.wpi.agileAngels;

import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.wpi.agileAngels.Controllers.LoginController;
import java.sql.SQLException;
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
}
