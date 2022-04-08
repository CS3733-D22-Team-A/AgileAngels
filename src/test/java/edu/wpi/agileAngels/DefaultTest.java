/*-------------------------*/
/* DO NOT DELETE THIS TEST */
/*-------------------------*/

package edu.wpi.agileAngels;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DefaultTest {

  LoginController dansLController = new LoginController();

  @Test
  public void test() {}

  @Test
  public void initialsMakerDanielTest() {
    String danInitials = "DO";
    assertTrue(danInitials.equals(dansLController.intialsMaker("Daniel Onyema")));
  }

  // Testing
  @Test
  public void initialsMakerJustinTest() {
    String justinInitials = "JW";
    assertTrue(
        justinInitials.equals(dansLController.intialsMaker("Justin Paul Santiago - Wonoski")));
  }

  @Test
  public void initialsMakerEmptyTest() {
    String notApplicable = "N/A";
    assertTrue(notApplicable.equals(dansLController.intialsMaker("")));
  }
}
