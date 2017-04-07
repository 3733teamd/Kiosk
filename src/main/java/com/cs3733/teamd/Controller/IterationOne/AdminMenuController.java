package com.cs3733.teamd.Controller.IterationOne;

import com.cs3733.teamd.Controller.AbsController;
import com.cs3733.teamd.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

//    @FXML
//    //search button
//    public void onSearch(ActionEvent actionEvent) throws IOException {
//        switchScreen(MMGpane, "/Views/Iteration1/MapMenu.fxml", "/Views/Iteration1/Login.fxml");
//
//    }
//
//    @FXML
//    //login button
//    public void onLogin(ActionEvent actionEvent) throws IOException{
//        switchScreen(MMGpane, "/Views/Iteration1/Login.fxml", "/Views/Iteration1/Login.fxml");
//
//    }
//
//    //Back button
//    @FXML
//    public void onBack(ActionEvent actionEvent) throws  IOException{
//        switchScreen(MMGpane, Main.backString, "/Views/Iteration1/AdminMenu.fxml");
//
//    }

//    @FXML
//    //Edit Service Directory button - not used
//    public void onEditServiceDirectory(ActionEvent actionEvent) throws IOException {
//
//    }
//
//    @FXML
//    //Edit Map button
//    public void onEditMap(ActionEvent actionEvent) throws IOException {
//        switchScreen(MMGpane, "/Views/Iteration1/EditMap.fxml", "/Views/Iteration1/AdminMenu.fxml");
//    }
//
//    @FXML
//    //Edit Doctor Directory button
//    public void onEditDoctorDirectory(ActionEvent actionEvent) throws IOException {
//        switchScreen(MMGpane, "/Views/Iteration1/EditDoctor.fxml", "/Views/Iteration1/AdminMenu.fxml");
//    }
//
//    @FXML
//    //Menu button
//    public void onMenu(ActionEvent actionEvent) throws IOException{
//        switchScreen(MMGpane, "/Views/Iteration1/Main.fxml", "/Views/Iteration1/Login.fxml");
//
//    }
    @FXML
    //translate to Spanish
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
        }
        else if(Main.Langugage.equals("English") ){
            menu.setX(0);
        }
    }



}
