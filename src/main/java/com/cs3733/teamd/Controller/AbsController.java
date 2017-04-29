package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Main;
import com.cs3733.teamd.Model.ApplicationConfiguration;
import com.cs3733.teamd.Controller.MementoController;

import com.cs3733.teamd.Model.CareTaker;
import com.cs3733.teamd.Model.Entities.Directory;
import com.cs3733.teamd.Model.Entities.DirectoryInterface;
import com.cs3733.teamd.Model.Originator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
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


    public void switchScreen(Pane gp, String ViewPath) throws IOException{
        //for memento
        MementoController.addCareTaker(ViewPath);
        Directory.getInstance().removeObservers();
        //
        Stage stage = (Stage) gp.getScene().getWindow();
        Parent pane;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ViewPath), Main.bundle);
        pane = loader.load();

        stage.getScene().setRoot(pane);
    }


    public void popupScreen(Pane popupGP, String popupViewPath, String title) throws IOException{

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
        else if(lan.equals("Español") ||lan.equals("\u0045\u0073\u0070\u0061\u00f1\u006f\u006c")){

            appConfig.setCurrentLangauge(ApplicationConfiguration.Language.SPANISH);
            Main.bundle = ResourceBundle.getBundle("MyLabels", appConfig.getSpanishLocale());

        }
        else if(lan.equalsIgnoreCase("Français")||lan.equalsIgnoreCase("\u0046\u0072\u0061\u006e\u00e7\u0061\u0069\u0073")){
            appConfig.setCurrentLangauge(ApplicationConfiguration.Language.FRENCH);
            Main.bundle = ResourceBundle.getBundle("MyLabels", appConfig.getFrenchLocale());
        }
        else if(lan.equalsIgnoreCase("\u4e2d\u6587")){
            appConfig.setCurrentLangauge(ApplicationConfiguration.Language.CHINESE);
            Main.bundle = ResourceBundle.getBundle("MyLabels", appConfig.getChineseLocale());
        }
        else if(lan.equalsIgnoreCase("Português")||lan.equals("\u0050\u006f\u0072\u0074\u0075\u0067\u0075\u00ea\u0073")){
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
