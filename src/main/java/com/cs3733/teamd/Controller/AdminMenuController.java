package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

/**
 * Created by Allyk on 3/26/2017.
 */
public class AdminMenuController {

    public Button largerTextButton;
    public Button SearchButton;
    public Button LoginButton;
    public Button BackButton;
    public Button MenuButton;

    public Button EditServiceDirectory;
    public Button EditMap;
    public Button EditDoctorDirectory;

    @FXML
    public void onSearch(ActionEvent actionEvent) throws IOException {
        Main.window.hide();
        Main.window.setScene(Main.MapMenuScene);
        Main.window.show();
        Main.backRoot = Main.AdminMenuScene;
    }

    @FXML
    public void onLogin(ActionEvent actionEvent) throws IOException{
        Main.window.hide();
        Main.window.setScene(Main.LoginScene);
        Main.window.show();
        Main.backRoot = Main.AdminMenuScene;
    }

    @FXML
    public void onBack(ActionEvent actionEvent) throws  IOException{
        Main.window.hide();
        Main.window.setScene(Main.backRoot);
        Main.window.show();
        Main.backRoot = Main.AdminMenuScene;
    }

    @FXML
    public void onEditServiceDirectory(ActionEvent actionEvent) throws IOException {
        Main.window.hide();
        Main.window.setScene(Main.EditServiceScene);
        Main.window.show();
        Main.backRoot = Main.AdminMenuScene;
    }

    @FXML
    public void onEditMap(ActionEvent actionEvent) throws IOException {
        Main.window.hide();
        Main.window.setScene(Main.EditMapScene);
        Main.window.show();
        Main.backRoot = Main.AdminMenuScene;
    }
    @FXML
    public void onEditDoctorDirectory(ActionEvent actionEvent) throws IOException {
        Main.window.hide();
        Main.window.setScene(Main.EditDoctorScene);
        Main.window.show();
        Main.backRoot = Main.AdminMenuScene;
    }

    @FXML
    public void onMenu(ActionEvent actionEvent) throws IOException{
        Main.window.hide();
        Main.window.setScene(Main.MainScene);
        Main.window.show();
        Main.backRoot = Main.LoginScene;
    }

}
