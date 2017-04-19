package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Main;
import com.cs3733.teamd.Model.ApplicationConfiguration;
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

    public FXMLLoader switchScreen(AnchorPane gp, String ViewPath) throws IOException{

        AnchorPane pane;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ViewPath), Main.bundle);
        //Main.backString =currView;
        // System.out.println(Main.backString);

        pane = (AnchorPane) loader.load();
        gp.getChildren().setAll(pane);
        fitToParent(gp);

        return loader;
    }

//    public void switchScreen(AnchorPane gp, String ViewPath) throws IOException{
//        Stage stage = (Stage) gp.getScene().getWindow();
//        AnchorPane pane ;
//        FXMLLoader loader = new FXMLLoader(getClass().getResource(ViewPath), Main.bundle);
//
//        pane = loader.load();
//
//        Scene scene = new Scene(pane);
//        stage.setMaximized(true);
//        stage.setScene(scene);
//        stage.show();
//    //
//    }

    //public Stage popupStage= new Stage();

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
    public void  fitToParent(AnchorPane gp){
//        gp.setMaxWidth(Double.MAX_VALUE);
//        gp.setMaxHeight(Double.MAX_VALUE);
//gp.setPrefWidth(50);
        AnchorPane.setTopAnchor(gp, 0.0);
        AnchorPane.setBottomAnchor(gp, 0.0);
        AnchorPane.setLeftAnchor(gp, 0.0);
  //      System.out.print("set anchor");
        AnchorPane.setRightAnchor(gp, 0.0);
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
