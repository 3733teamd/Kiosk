package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Main;
import javafx.scene.control.Button;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ResourceBundle;


/**
 * Created by Allyk on 3/26/2017.
 */
public class MainController extends AbsController {
    public Button largerTextButton;
    public Button SearchButton;
    public Button LoginButton;
    public Button BackButton;
    public Button MenuButton;
    public Button SpanishButton;

    public Text menu;
    public Text hospital;
    public Text kiosk;
    public Text instruct;
    public Text tapS;
    public Text tapL;
    public Text tapM;
    public Text tapB;

    public AnchorPane MMGpane;

    private Boolean languageEnglish =true;

    @FXML
    public void initialize(){

        //backString = "/Views/Main.fxml";
        setText();
    }

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
        switchScreen(MMGpane, "/Views/MapMenu.fxml", "/Views/Main.fxml");
    }

    //Login button
    @FXML
    public void onLogin(ActionEvent actionEvent) throws IOException{
        switchScreen(MMGpane, "/Views/Login.fxml", "/Views/Main.fxml");
    }

    //Back button
    @FXML
    public void onBack(ActionEvent actionEvent) throws  IOException{
        switchScreen(MMGpane, Main.backString, "/Views/Main.fxml");
    }

    //Spanish translation
    public void setText(){
        SpanishButton.setText(Main.bundle.getString("spanish"));
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
        if(Main.Langugage.equals("Spanish") ){
            menu.setX(-50);
        }
        else if(Main.Langugage.equals("English") ){
            menu.setX(0);
        }

    }


}
