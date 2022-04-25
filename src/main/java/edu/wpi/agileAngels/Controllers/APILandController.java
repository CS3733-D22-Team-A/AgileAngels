package edu.wpi.agileAngels.Controllers;

import edu.wpi.cs3733.D22.teamA.*;
import edu.wpi.teamW.ServiceException;
import java.sql.SQLException;
import javafx.event.ActionEvent;

public class APILandController {
  private ImportAPI importAPI = new ImportAPI();

  AppController appController = AppController.getInstance();

  public APILandController() throws SQLException {}

  public void mgbAction(ActionEvent event) {
    // edu.wpi.cs3733.D22.teamA.API.run()

    API.run(0, 0, 900, 900, "./MGBEmployees.csv");
  }

  public void loadCredits() {
    appController.loadPage("/edu/wpi/agileAngels/views/credits-view.fxml");
  }

  public void languageAction(ActionEvent actionEvent) throws ServiceException {
    importAPI.LanguageInterp();
  }
}
