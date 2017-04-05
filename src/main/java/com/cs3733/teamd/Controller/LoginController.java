package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;

/**
 * Created by Allyk on 3/26/2017.
 */
public class LoginController extends AbsController{

    public Button largerTextButton;

    public Button SearchButton;
    public Button LoginButton;
    public Button BackButton;
    public Button MenuButton;

    public Button loginSubmitButton;
    public TextField UserNameField;
    public TextField PasswordField;

    private String username;
    private String password;
    public Pane pane;
    public Text menu;
    public Text user;
    public Text pass;

    public AnchorPane MMGpane;

    @FXML   
    public void initialize(){
        setText();
    }

    @FXML
    public void onSearch(ActionEvent actionEvent) throws IOException {
        /*Main.window.hide();

        Main.window.setScene(Main.MapMenuScene);
        Main.window.show();*/
        //Main.backRoot = Main.LoginScene;

    }

    @FXML
    public void onMenu(ActionEvent actionEvent) throws IOException{
        /*Main.window.hide();
        Main.window.setScene(Main.MainScene);
        Main.window.show();
        Main.backRoot = Main.LoginScene;*/

        switchScreen(MMGpane, "/Views/Main.fxml", "/Views/Login.fxml");

    }

    @FXML
    public void onBack(ActionEvent actionEvent) throws  IOException{
        /*Main.window.hide();
        Main.window.setScene(Main.backRoot);
        Main.window.show();
        Main.backRoot = Main.LoginScene;*/
        switchScreen(MMGpane, Main.backString, "/Views/Login.fxml");

    }

    @FXML
    public void onSubmit(ActionEvent actionEvent) throws  IOException {
        username = UserNameField.getText();
        password = PasswordField.getText();
        System.out.print(username);
        System.out.print(password);

        /*Main.window.hide();
        Main.window.setScene(Main.AdminMenuScene);
        Main.window.show();
        Main.backRoot = Main.LoginScene;*/
        if(username.equals("dev")&&password.equals("dev")) {
            switchScreen(MMGpane, "/Views/AdminMenu.fxml", "/Views/Login.fxml");

        }
    }

    @FXML
    public void setText(){
        SearchButton.setText(Main.bundle.getString("search"));
        LoginButton.setText(Main.bundle.getString("login"));
        MenuButton.setText(Main.bundle.getString("menu"));
        BackButton.setText(Main.bundle.getString("back"));
        menu.setText(Main.bundle.getString("login"));
        user.setText(Main.bundle.getString("username"));
        pass.setText(Main.bundle.getString("password"));
        loginSubmitButton.setText(Main.bundle.getString("login"));
        if(Main.Langugage.equals("Spanish") ){
            menu.setX(-170);
            //loginSubmitButton.setLayoutX(555);
            //menu.setTranslateX(-175);
        }
        else if(Main.Langugage.equals("English") ){
            // menu.setTranslateX(-175);
            menu.setX(0);
            //loginSubmitButton.setLayoutX(600);
        }
    }

}
