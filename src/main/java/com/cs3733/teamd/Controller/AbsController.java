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


    void switchLanguage() {
        ApplicationConfiguration appConfig = ApplicationConfiguration.getInstance();
        //TODO : CHANGE INTO SWITCH STATEMENT FOR MULTIPLE LANGUAGES
        if(appConfig.getCurrentLanguage() == ApplicationConfiguration.Language.ENGLISH) {
            appConfig.setCurrentLangauge(ApplicationConfiguration.Language.SPANISH);
            Main.bundle = ResourceBundle.getBundle("MyLabels", appConfig.getSpanishLocale());
        } else if(appConfig.getCurrentLanguage() == ApplicationConfiguration.Language.SPANISH){
            appConfig.setCurrentLangauge(ApplicationConfiguration.Language.ENGLISH);
            Main.bundle = ResourceBundle.getBundle("MyLabels", appConfig.getEnglishLocale());
        } else {
            System.out.println("ERROR: Undetected Langauge");
        }
    }

}
