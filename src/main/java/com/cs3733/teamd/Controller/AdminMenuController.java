package com.cs3733.teamd.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * Created by Ryan on 4/27/2017.
 */
public class AdminMenuController extends AbsController{

    public Button toEditMap;
    public Button toEditTag;
    public Button toEditProf;
    public Button toEnhancedMap;
    public Button toAddUser;
    public Button toBugView;
    public Pane MMGpane;


    @FXML
    public void initialize() {

    }

    public void goToEditMap(ActionEvent actionEvent) {
        try {
            super.switchScreen(MMGpane, "/Views/EditMapScreen.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToEditTag(ActionEvent actionEvent) {
        try {
            super.switchScreen(MMGpane, "/Views/EditTagScreen.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToEditProf(ActionEvent actionEvent) {
        try {
            super.switchScreen(MMGpane, "/Views/EditProfScreen.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToEnhancedMap(ActionEvent actionEvent) {
        try {
            super.switchScreen(MMGpane, "/Views/UserScreen.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToAddUser(ActionEvent actionEvent) {
        try {
            super.switchScreen(MMGpane, "/Views/CreateUserScreen.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToBugView(ActionEvent actionEvent) {
        try {
            super.switchScreen(MMGpane, "/Views/ViewBugScreen.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logout(ActionEvent actionEvent) {
        try {
            super.switchScreen(MMGpane, "/Views/UserScreen.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}