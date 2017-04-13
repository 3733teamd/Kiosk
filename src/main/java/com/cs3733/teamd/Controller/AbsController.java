package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

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
    private void  fitToParent(AnchorPane gp){
        AnchorPane.setTopAnchor(gp, 0.0);
        AnchorPane.setBottomAnchor(gp, 0.0);
        AnchorPane.setLeftAnchor(gp, 0.0);
        AnchorPane.setRightAnchor(gp, 0.0);
    }

    void switchLanguage() {
        //TODO : CHANGE INTO SWITCH STATEMENT FOR MULTIPLE LANGUAGES
        if(Main.Langugage == "English") {
            Main.Langugage = "Spanish";
            Main.bundle = ResourceBundle.getBundle("MyLabels", Main.spanish);
        }
        else{
            Main.Langugage = "English";
            Main.bundle = ResourceBundle.getBundle("MyLabels", Main.local);
        }
    }

}
