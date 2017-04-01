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
public class EditServiceController {

    public Button largerTextButton;
    public Button SearchButton;
    public Button LoginButton;
    public Button BackButton;
    public Button MenuButton;
    public Button submitRemoveService;
    public Button submitModifyService;
    public Button submitAddService;

    public TextField addService;

    public ChoiceBox addRoomSelect;
    public ChoiceBox modifyServiceSelect;
    public ChoiceBox modifyRoomSelect;
    public ChoiceBox removeServiceSelect;

    @FXML
    public void onSearch(ActionEvent actionEvent) throws IOException {
        Main.window.hide();
        Main.window.setScene(Main.MapMenuScene);
        Main.window.show();
        Main.backRoot = Main.EditServiceScene;
    }

    @FXML
    public void onLogin(ActionEvent actionEvent) throws IOException{
        Main.window.hide();
        Main.window.setScene(Main.LoginScene);
        Main.window.show();
        Main.backRoot = Main.EditServiceScene;
    }
    @FXML
    public void onBack(ActionEvent actionEvent) throws  IOException{
        Main.window.hide();
        Main.window.setScene(Main.backRoot);
        Main.window.show();
        Main.backRoot = Main.EditServiceScene;
    }


}
