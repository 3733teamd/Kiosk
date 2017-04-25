package com.cs3733.teamd.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Created by Stephen on 4/24/2017.
 */
public class AdminSelectionScreenController extends AbsController {

    @FXML
    private AnchorPane MMGpane;

    @FXML
    public void initialize() {

    }

    @FXML
    void onEditMap(ActionEvent event) {
        try {
            super.switchScreen(MMGpane, "/Views/EditMapScreen.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onEnhancedMap(ActionEvent event) {
        try {
            super.switchScreen(MMGpane, "/Views/UserScreen.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
