package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Main;
import com.cs3733.teamd.Model.ApplicationConfiguration;
import com.cs3733.teamd.Controller.MementoController;

import com.cs3733.teamd.Model.CareTaker;
import com.cs3733.teamd.Model.Originator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.Serializable;
import java.lang.Comparable;

import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Created by Allyk on 4/4/2017.
 */
public abstract class AbsController {


    protected Boolean init=true;


    public void switchScreen(AnchorPane gp, String ViewPath) throws IOException{
        //for memento
        MementoController.addCareTaker(ViewPath);

        //
        Stage stage = (Stage) gp.getScene().getWindow();
        Parent pane;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ViewPath), Main.bundle);
        pane = loader.load();

        stage.getScene().setRoot(pane);
    }


    public void popupScreen(AnchorPane popupGP, String popupViewPath, String title) throws IOException{

        Stage popupStage= new Stage();

        Parent root;

        FXMLLoader loader = new FXMLLoader(getClass().getResource(popupViewPath), Main.bundle);

        root=loader.load();

        popupStage.setScene(new Scene(root));

        popupStage.initModality(Modality.APPLICATION_MODAL);

        popupStage.setTitle(title);

        popupStage.show(); // should be show and wait

    }


    void switchLanguage(String lan) {

        ApplicationConfiguration appConfig = ApplicationConfiguration.getInstance();
        //TODO : CHANGE INTO SWITCH STATEMENT FOR MULTIPLE LANGUAGES
        if(lan.equals("English")){
            appConfig.setCurrentLangauge(ApplicationConfiguration.Language.ENGLISH);
            Main.bundle = ResourceBundle.getBundle("MyLabels", appConfig.getEnglishLocale());

        }
        else if(lan.equals("Español")){

            appConfig.setCurrentLangauge(ApplicationConfiguration.Language.SPANISH);
            Main.bundle = ResourceBundle.getBundle("MyLabels", appConfig.getSpanishLocale());

        }
        else if(lan.equalsIgnoreCase("Français")){
            appConfig.setCurrentLangauge(ApplicationConfiguration.Language.FRENCH);
            Main.bundle = ResourceBundle.getBundle("MyLabels", appConfig.getFrenchLocale());
        }
        else if(lan.equalsIgnoreCase("Chinese")){
            appConfig.setCurrentLangauge(ApplicationConfiguration.Language.CHINESE);
            Main.bundle = ResourceBundle.getBundle("MyLabels", appConfig.getChineseLocale());
        }
        else if(lan.equalsIgnoreCase("Portuguese")){
            appConfig.setCurrentLangauge(ApplicationConfiguration.Language.PORTUGUESE);
            Main.bundle = ResourceBundle.getBundle("MyLabels", appConfig.getProtugeseLocale());
        }
        else{

        }
       /* if(appConfig.getCurrentLanguage() == ApplicationConfiguration.Language.ENGLISH) {
            appConfig.setCurrentLangauge(ApplicationConfiguration.Language.SPANISH);
            Main.bundle = ResourceBundle.getBundle("MyLabels", appConfig.getSpanishLocale());
        } else if(appConfig.getCurrentLanguage() == ApplicationConfiguration.Language.SPANISH){
            appConfig.setCurrentLangauge(ApplicationConfiguration.Language.ENGLISH);
            Main.bundle = ResourceBundle.getBundle("MyLabels", appConfig.getEnglishLocale());
        } else {
            System.out.println("ERROR: Undetected Langauge");
        }*/
    }

}
