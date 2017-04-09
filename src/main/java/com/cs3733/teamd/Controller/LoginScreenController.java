package com.cs3733.teamd.Controller;

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

import java.io.IOException;

/**
 * Created by Anh Dao on 4/6/2017.
 */
public class LoginScreenController extends AbsController {
    public TextField username;
    public PasswordField password;
    public Button SubmitButton;
    public Button BackButton;
    public AnchorPane MMGpane;
    public Label errorIndicator;



    //Back button
    @FXML
    public void onBack(ActionEvent actionEvent) throws  IOException{
        switchScreen(MMGpane, "/Views/UserScreen.fxml");
    }
    //Submit button
    @FXML
    public void onSubmit(ActionEvent actionEvent) throws IOException {
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
//        System.out.print(username);
//        System.out.print(password);


    }
}
