package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;

/**
 * Created by Allyk on 4/4/2017.
 */
public abstract class AbsController {

    public FXMLLoader switchScreen(AnchorPane gp, String ViewPath, String currView) throws IOException{

        AnchorPane pane;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ViewPath), Main.bundle);
        Main.backString =currView;
        System.out.println(Main.backString);

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

}
