package com.cs3733.teamd.Controller.IterationOne;

import com.cs3733.teamd.Controller.AbsController;
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
public class LoginController extends AbsController {

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

    //Search button
    @FXML
    public void onSearch(ActionEvent actionEvent) throws IOException {
              switchScreen(MMGpane, "/Views/Iteration1/MapMenu.fxml", "/Views/Iteration1/Login.fxml");
        /*Main.window.hide();

        Main.window.setScene(Main.MapMenuScene);
        Main.window.show();*/
        //Main.backRoot = Main.LoginScene;
        switchScreen(MMGpane, "/Views/Iteration1/MapMenu.fxml", "/Views/Iteration1/Login.fxml");

    }

    //Menu button
    @FXML
    public void onMenu(ActionEvent actionEvent) throws IOException{
        switchScreen(MMGpane, "/Views/Iteration1/Main.fxml", "/Views/Iteration1/Login.fxml");

    }

    //Back button
    @FXML
    public void onBack(ActionEvent actionEvent) throws  IOException{
        switchScreen(MMGpane, Main.backString, "/Views/Iteration1/Login.fxml");

    }

    //Submit button
    @FXML
    public void onSubmit(ActionEvent actionEvent) throws  IOException {
        username = UserNameField.getText();
        password = PasswordField.getText();
        System.out.print(username);
        System.out.print(password);

        switchScreen(MMGpane, "/Views/Iteration1/AdminMenu.fxml", "/Views/Iteration1/Login.fxml");
        /*Main.window.hide();
        Main.window.setScene(Main.AdminMenuScene);
        Main.window.show();
        Main.backRoot = Main.LoginScene;*/
        if(username.equals("dev")&&password.equals("dev")) {
            switchScreen(MMGpane, "/Views/Iteration1/AdminMenu.fxml", "/Views/Iteration1/Login.fxml");

        }
    }

    //Spanish translation
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
        }
        else if(Main.Langugage.equals("English") ){
            menu.setX(0);
        }
    }

}
