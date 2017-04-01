package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

import java.io.IOException;

/**
 * Created by Allyk on 3/26/2017.
 */
public class MapDirectionsController {

    public Button largerTextButton;
    public Button SearchButton;
    public Button LoginButton;
    public Button BackButton;
    public Button MenuButton;

    public ImageView mapImage;
    public TextArea textDirectionsDisplay;

    @FXML private void initialize()
    {
        if (Main.roomSelected == "Select Room") {
            if (Main.serviceSelected == "Allergy"){
                // Map data goes here
            }
            if (Main.serviceSelected == "Blood Test"){
                // Map data goes here
            }
            if (Main.serviceSelected == "ICU"){
                // Map data goes here
            }
            if (Main.serviceSelected == "Oranges"){
                // Map data goes here
            }
            if (Main.serviceSelected == "Emergency Room"){
                // Map data goes here
            }
        }
        if (Main.serviceSelected == "Select Service"){
            if (Main.roomSelected == "3A"){
                // Map data goes here
            }
            if (Main.serviceSelected == "3B"){
                // Map data goes here
            }
            if (Main.serviceSelected == "3C"){
                // Map data goes here
            }
        }
    }

    @FXML
    public void onSearch(ActionEvent actionEvent) throws IOException {
        Main.window.hide();
        Main.window.setScene(Main.MapMenuScene);
        Main.window.show();
        Main.backRoot = Main.MapDirectionsScene;
    }

    @FXML
    public void onLogin(ActionEvent actionEvent) throws IOException{
        Main.window.hide();
        Main.window.setScene(Main.LoginScene);
        Main.window.show();
        Main.backRoot = Main.MapDirectionsScene;
    }
    @FXML
    public void onBack(ActionEvent actionEvent) throws  IOException{
        Main.window.hide();
        Main.window.setScene(Main.backRoot);
        Main.window.show();
        Main.backRoot = Main.MapDirectionsScene;
    }

}
