package com.cs3733.teamd.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    //Back button
    @FXML
    public void onBack(ActionEvent actionEvent) throws  IOException{
        switchScreen(MMGpane, "/Views/UserScreen.fxml");
    }
    //Submit button
    @FXML
    public void onSubmit(ActionEvent actionEvent) throws IOException {
//        username = UserNameField.getText();
//        password = PasswordField.getText();
//        System.out.print(username);
//        System.out.print(password);

        switchScreen(MMGpane, "/Views/EditMapScreen.fxml");
    }
}
