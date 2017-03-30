package sample.Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;

import java.io.IOException;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Node;
import sample.Main;


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



}
