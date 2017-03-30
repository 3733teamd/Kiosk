package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sample.Main;

import java.io.IOException;

/**
 * Created by Allyk on 3/26/2017.
 */
public class LoginController {

    public Button largerTextButton;

    public Button SearchButton;
    public Button LoginButton;
    public Button BackButton;
    public Button MenuButton;

    public Button loginSubmitButton;
    public TextField UserNameField;
    public TextField PasswordField;

    @FXML
    public void onSearch(ActionEvent actionEvent) throws IOException {
        Main.window.hide();
        Main.window.setScene(Main.MapMenuScene);
        Main.window.show();
        Main.backRoot = Main.LoginScene;
    }

    @FXML
    public void onMenu(ActionEvent actionEvent) throws IOException{
        Main.window.hide();
        Main.window.setScene(Main.MainScene);
        Main.window.show();
        Main.backRoot = Main.LoginScene;
    }



}
