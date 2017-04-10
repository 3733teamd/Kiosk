package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Main;
import com.cs3733.teamd.Model.Directory;
import com.cs3733.teamd.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Created by Anh Dao on 4/6/2017.
 */
public class LoginScreenController extends AbsController {
    public TextField username;
    public PasswordField password;
    public Button LoginButton;
    public Button BackButton;
    public AnchorPane MMGpane;
    public Label errorIndicator;
    public Text userText;
    public Text pwText;
    @FXML
    public void initialize(){
        setText();
    }

    //Back button
    @FXML
    public void onBack(ActionEvent actionEvent) throws  IOException{
        switchScreen(MMGpane, "/Views/UserScreen.fxml");
    }
    //Login button
    @FXML
    public void onLogin(ActionEvent actionEvent) throws IOException {
       String user = username.getText();
       String pass = password.getText();

       Directory dir = Directory.getInstance();

       User u = dir.loginUser(user,pass);

       if (u != null){
           System.out.print("Successful log in as admin.\n");
           switchScreen(MMGpane, "/Views/EditMapScreen.fxml");
       }
       else{
            errorIndicator.setText("Invalid login credentials!");

       }

    }
    //Spanish translation
    @FXML
    public void setText() {
       // SearchButton.setText(Main.bundle.getString("search"));
        LoginButton.setText(Main.bundle.getString("login"));
     //   MenuButton.setText(Main.bundle.getString("menu"));
        BackButton.setText(Main.bundle.getString("back"));
       // menu.setText(Main.bundle.getString("login"));
        userText.setText(Main.bundle.getString("username"));
        pwText.setText(Main.bundle.getString("password"));

    }

}
