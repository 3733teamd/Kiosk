package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Created by Anh Dao on 4/6/2017.
 */
public class UserScreenController extends AbsController{
    public Button LoginButton;
    public Button SpanishButton;
    public Button SearchButton;

    public AnchorPane MMGpane;

    private Boolean languageEnglish =true;


    //Spanish button to change language to Spanish
    @FXML
    public void onSpanish(ActionEvent actionEvent){
        languageEnglish = !languageEnglish;
        if(languageEnglish ==false) {
            Main.Langugage = "Spanish";
            Main.bundle = ResourceBundle.getBundle("MyLabels", Main.spanish);
        }
        else{
            Main.Langugage = "English";
            Main.bundle = ResourceBundle.getBundle("MyLabels", Main.local);
        }

        Main.window.hide();
        Main.window.setScene(Main.MainScene);
        Main.window.show();

        setText();
    }

    //Search button
    @FXML
    public void onSearch(ActionEvent actionEvent) throws IOException{
        switchScreen(MMGpane, "/Views/UserScreen.fxml");
    }
    //Login button
    @FXML
    public void onLogin(ActionEvent actionEvent) throws IOException {
        switchScreen(MMGpane, "/Views/LoginScreen.fxml");
    }

    //Spanish translation
    public void setText(){

    }

}
