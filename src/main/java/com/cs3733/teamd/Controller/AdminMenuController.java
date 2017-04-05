package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;

/**
 * Created by Allyk on 3/26/2017.
 */
public class AdminMenuController extends AbsController {

    public Button largerTextButton;
    public Button SearchButton;
    public Button LoginButton;
    public Button BackButton;
    public Button MenuButton;

    public Button EditServiceDirectory;
    public Button EditMap;
    public Button EditDoctorDirectory;

    public AnchorPane pane;
    public Text menu;
    public AnchorPane MMGpane;

    @FXML
    public void initialize(){
        setText();
    }

    @FXML
    public void onSearch(ActionEvent actionEvent) throws IOException {
        /*Main.window.hide();
        Main.window.setScene(Main.MapMenuScene);
        Main.window.show();
        Main.backRoot = Main.AdminMenuScene;*/

        switchScreen(MMGpane, "/Views/MapMenu.fxml", "/Views/Login.fxml");

    }

    @FXML
    public void onLogin(ActionEvent actionEvent) throws IOException{
        /*Main.window.hide();
        Main.window.setScene(Main.LoginScene);
        Main.window.show();
        Main.backRoot = Main.AdminMenuScene;*/
        switchScreen(MMGpane, "/Views/Login.fxml", "/Views/Login.fxml");

    }

    @FXML
    public void onBack(ActionEvent actionEvent) throws  IOException{
        /*Main.window.hide();
        Main.window.setScene(Main.backRoot);
        Main.window.show();
        Main.backRoot = Main.AdminMenuScene;*/
        switchScreen(MMGpane, Main.backString, "/Views/AdminMenu.fxml");

    }

    @FXML
    public void onEditServiceDirectory(ActionEvent actionEvent) throws IOException {
        /*Main.window.hide();
        Main.window.setScene(Main.EditServiceScene);
        Main.window.show();
        Main.backRoot = Main.AdminMenuScene;*/
    }

    @FXML
    public void onEditMap(ActionEvent actionEvent) throws IOException {
        /*Main.window.hide();
        Main.window.setScene(Main.EditMapScene);
        Main.window.show();
        Main.backRoot = Main.AdminMenuScene;*/
        switchScreen(MMGpane, "/Views/EditMap.fxml", "/Views/AdminMenu.fxml");


    }
    @FXML
    public void onEditDoctorDirectory(ActionEvent actionEvent) throws IOException {
        /*ain.window.hide();
        Main.window.setScene(Main.EditDoctorScene);
        Main.window.show();
        Main.backRoot = Main.AdminMenuScene;*/
        switchScreen(MMGpane, "/Views/EditDoctor.fxml", "/Views/AdminMenu.fxml");

    }

    @FXML
    public void onMenu(ActionEvent actionEvent) throws IOException{
        /*Main.window.hide();
        Main.window.setScene(Main.MainScene);
        Main.window.show();
        Main.backRoot = Main.LoginScene;*/
        switchScreen(MMGpane, "/Views/Main.fxml", "/Views/AdminMenu.fxml");

    }
    @FXML
    public void setText(){
        SearchButton.setText(Main.bundle.getString("search"));
        LoginButton.setText(Main.bundle.getString("login"));
        MenuButton.setText(Main.bundle.getString("menu"));
        BackButton.setText(Main.bundle.getString("back"));
        menu.setText(Main.bundle.getString("AdminMenu"));
        EditDoctorDirectory.setText(Main.bundle.getString("EditDD"));
        EditServiceDirectory.setText(Main.bundle.getString("EditSD"));
        EditMap.setText(Main.bundle.getString("EditM"));


        if(Main.Langugage.equals("Spanish") ){
            menu.setX(-250);
            //menu.setTranslateX(-175);
            //EditDoctorDirectory.setLayoutX(440);
            //EditServiceDirectory.setLayoutX(440);
            //EditMap.setLayoutX(530);
        }
        else if(Main.Langugage.equals("English") ){
            // menu.setTranslateX(-175);
            //EditDoctorDirectory.setLayoutX(520);
            //EditServiceDirectory.setLayoutX(520);
            //EditMap.setLayoutX(590);
            menu.setX(0);
        }
    }



}
