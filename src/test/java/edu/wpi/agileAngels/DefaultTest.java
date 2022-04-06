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
  public void setInitialsTest1() {
    String dansInitials = "DO";
    assertTrue(dansInitials.equals(dansLController.intialsMaker("Daniel Onyema")));
  }

  @Test
  public void setInitialsTest2() {
    String justinsInitials = "JW";
    assertTrue(
        justinsInitials.equals(dansLController.intialsMaker("Justin Paul Santiago - Wonoski")));
  }
}
