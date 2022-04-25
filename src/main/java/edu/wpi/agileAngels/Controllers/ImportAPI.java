package edu.wpi.agileAngels.Controllers;

import edu.wpi.teamW.*;

public class ImportAPI {

  public void LanguageInterp() throws ServiceException {
    edu.wpi.teamW.API.run(
        201,
        188,
        900,
        900,
        "/edu/wpi/agileAngels/views/stylesheets/style.css",
        "FDEPT00101",
        "FDEPT00201");
  }
}
