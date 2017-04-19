package com.cs3733.teamd.Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Created by benjamin on 4/18/2017.
 */
public class ViewBugController {

    public Button exitBugs;
    public AnchorPane MMGpane;

    public void leaveBugs(ActionEvent actionEvent) {
        Stage closeStage= (Stage) exitBugs.getScene().getWindow();
        closeStage.close();
    }
}
