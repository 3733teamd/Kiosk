package com.cs3733.teamd.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Created by Anh Dao on 4/11/2017.
 */
public class CreateUserScreenController extends AbsController {
    public Button BackButton;
    public Button CreateUserButton;
    public AnchorPane MMGpane;
    //Back button
    @FXML
    public void onBack(ActionEvent actionEvent) throws IOException {
        switchScreen(MMGpane, "/Views/EditMapScreen.fxml");

    }
    @FXML
    public void onCreateUser(ActionEvent actionEvent) throws IOException {
        //TODO: log out from previous user
        switchScreen(MMGpane, "/Views/LoginScreen.fxml");
    }
}
