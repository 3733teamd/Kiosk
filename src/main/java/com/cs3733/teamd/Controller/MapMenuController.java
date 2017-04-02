package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import java.io.IOException;


public class MapMenuController {
    static ObservableList<String> roomsList =
            FXCollections.observableArrayList( "Select Room", "3A", "3B","3C" );
    static ObservableList<String> serviceList =
            FXCollections.observableArrayList( "Select Service", "Allergy", "Blood Test","ICU","Oranges", "Emergency Room" );


    public Button largerTextButton;
    public Button SearchButton;
    public Button LoginButton;
    public Button BackButton;
    public Button MenuButton;

    public Button SubmitButton;

    public AnchorPane pane;

    @FXML
    public ChoiceBox RoomSelect;
    @FXML
    public ChoiceBox ServiceSelect;


    @FXML
    private void initialize(){
        RoomSelect.setValue("Select Room");
        RoomSelect.setItems(roomsList);
        ServiceSelect.setValue("Select Service");
        ServiceSelect.setItems(serviceList);

        setText();
    }


    @FXML
    public void onMenu(ActionEvent actionEvent) throws IOException {
        Main.window.hide();
        Main.window.setScene(Main.MainScene);
        Main.window.show();
        Main.backRoot = Main.MapMenuScene; //
    }
    @FXML
    public void onLogin(ActionEvent actionEvent) throws IOException{
        Main.window.hide();
        Main.window.setScene(Main.LoginScene);
        Main.window.show();
        Main.backRoot = Main.MapMenuScene;
    }

    @FXML
    public void onBack(ActionEvent actionEvent) throws  IOException{
        Main.window.hide();
        Main.window.setScene(Main.backRoot);
        Main.window.show();
        Main.backRoot = Main.MapMenuScene;
    }

    @FXML
    public void onSubmit(ActionEvent actionEvent) throws  IOException{
        Main.window.hide();
        Main.roomSelected = RoomSelect.getValue().toString();
        Main.serviceSelected = ServiceSelect.getValue().toString();
        Main.window.setScene(Main.MapDirectionsScene);
        Main.window.show();
        Main.backRoot = Main.MapMenuScene;
    }

    @FXML
    public void setText(){
        SearchButton.setText(Main.bundle.getString("search"));
        LoginButton.setText(Main.bundle.getString("login"));
        MenuButton.setText(Main.bundle.getString("menu"));
        BackButton.setText(Main.bundle.getString("back"));

    }

}
