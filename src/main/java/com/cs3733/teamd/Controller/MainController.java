package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Main;
import javafx.scene.control.Button;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;

import java.io.IOException;


/**
 * Created by Allyk on 3/26/2017.
 */
public class MainController {
    public Button largerTextButton;
    public Button SearchButton;
    public Button LoginButton;
    public Button BackButton;
    public Button MenuButton;

    @FXML
    public void onSearch(ActionEvent actionEvent) throws IOException{
        Main.window.hide();
        Main.window.setScene(Main.MapMenuScene);
        Main.window.show();
        Main.backRoot = Main.MainScene;
    }

    @FXML
    public void onLogin(ActionEvent actionEvent) throws IOException{
        Main.window.hide();
        Main.window.setScene(Main.LoginScene);
        Main.window.show();
        Main.backRoot = Main.MainScene;
    }

    @FXML
    public void onBack(ActionEvent actionEvent) throws  IOException{
        Main.window.hide();
        Main.window.setScene(Main.backRoot);
        Main.window.show();
        Main.backRoot = Main.MainScene;
    }

}
