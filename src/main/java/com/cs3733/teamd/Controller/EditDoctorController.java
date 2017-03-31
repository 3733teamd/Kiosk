package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * Created by Allyk on 3/26/2017.
 */
public class EditDoctorController {

    public Button largerTextButton;
    public Button SearchButton;
    public Button LoginButton;
    public Button BackButton;
    public Button MenuButton;

    public Button submitRemoveDoctor;
    public Button submitModifyDoctor;
    public Button submitAddDoctor;

    public TextField addDoctorLabel;

    public ChoiceBox addRoomSelect;
    public ChoiceBox modifyDoctorSelect;
    public ChoiceBox modifyRoomSelect;
    public ChoiceBox removeDoctorSelect;


    @FXML
    public void onSearch(ActionEvent actionEvent) throws IOException {
        Main.window.hide();
        Main.window.setScene(Main.MapMenuScene);
        Main.window.show();
        Main.backRoot = Main.EditDoctorScene;
    }

    @FXML
    public void onLogin(ActionEvent actionEvent) throws IOException{
        Main.window.hide();
        Main.window.setScene(Main.LoginScene);
        Main.window.show();
        Main.backRoot = Main.EditDoctorScene;
    }

}
