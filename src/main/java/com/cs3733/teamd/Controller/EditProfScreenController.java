package com.cs3733.teamd.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Created by Anh Dao on 4/6/2017.
 */
public class EditProfScreenController extends AbsController {
    public Button BackButton;
    public AnchorPane MMGpane;
    //Back button
    @FXML
    public void onBack(ActionEvent actionEvent) throws IOException {
        switchScreen(MMGpane, "/Views/EditMapScreen.fxml");
    }
}
