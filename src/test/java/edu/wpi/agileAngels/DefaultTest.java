/*-------------------------*/
/* DO NOT DELETE THIS TEST */
/*-------------------------*/

package edu.wpi.agileAngels;

import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.wpi.agileAngels.Controllers.LoginController;
import edu.wpi.agileAngels.Database.MedicalEquip;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

public class DefaultTest {

  LoginController dansLController = new LoginController();

  @Test
  public void test() {}

  @Test
  public void testMedEqpAddRemove() throws SQLException {
    String[] arr = new String[1];
    Adb a = new Adb();
    a.main(arr);
    MedicalEquip mE = new MedicalEquip("R2", "X-Ray Machine", true, "Hall");
    MedicalEquip mE2 = new MedicalEquip("R1", "X-Ray Machine", false, "Hall");
    MedicalEquip mE3 = new MedicalEquip("R3", "X-Ray Machine", true, "Hall");
    Adb.addMedicalEquipment(mE);
    Adb.addMedicalEquipment(mE2);
    Adb.addMedicalEquipment(mE3);
    Adb.removeMedicalEquipment(mE.getID());
  }

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
