package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Allyk on 4/4/2017.
 */
public abstract class AbsController {

    public void switchScreen(AnchorPane gp, String ViewPath) throws IOException{
        Stage stage = (Stage) gp.getScene().getWindow();
        AnchorPane pane ;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ViewPath), Main.bundle);

        pane = loader.load();

        Scene scene = new Scene(pane);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    //
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

}
