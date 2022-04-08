/*-------------------------*/
/* DO NOT DELETE THIS TEST */
/*-------------------------*/

package edu.wpi.agileAngels;

import edu.wpi.agileAngels.Database.MedicalEquip;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

public class DefaultTest {

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
}
