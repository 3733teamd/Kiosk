package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Main;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;


/**
 * Created by Allyk on 3/26/2017.
 */
public class MainController  {
    public Button largerTextButton;
    public Button SearchButton;
    public Button LoginButton;
    public Button BackButton;
    public Button MenuButton;

    public Text menu;
    public Text hospital;
    public Text kiosk;
    public Text instruct;
    public Text tapS;
    public Text tapL;
    public Text tapM;
    public Text tapB;


    private Boolean languageEnglish =true;


    @FXML
    public void onSpanish(ActionEvent actionEvent){
       languageEnglish = !languageEnglish;
       if(languageEnglish ==false) {
           Main.Langugage = "Spanish";
           Main.bundle = ResourceBundle.getBundle("MyLabels", new Locale("es", "SP"));
       }
       else{
           Main.Langugage = "English";
           Main.bundle = ResourceBundle.getBundle("MyLabels", new Locale("en", "US"));
       }

        Main.window.hide();
        Main.window.setScene(Main.MainScene);
        Main.window.show();

        setText();
    }

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

    public void setText(){
        SearchButton.setText(Main.bundle.getString("search"));
        LoginButton.setText(Main.bundle.getString("login"));
        MenuButton.setText(Main.bundle.getString("menu"));
        BackButton.setText(Main.bundle.getString("back"));
        menu.setText(Main.bundle.getString("mainmenu"));
        hospital.setText(Main.bundle.getString("hospital"));
        kiosk.setText(Main.bundle.getString("kiosk"));
        instruct.setText(Main.bundle.getString("instruct"));
        tapS.setText(Main.bundle.getString("tapS"));
        tapL.setText(Main.bundle.getString("tapL"));
        tapM.setText(Main.bundle.getString("tapM"));
        tapB.setText(Main.bundle.getString("tapB"));

    }


}
