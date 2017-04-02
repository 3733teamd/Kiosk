package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Main;

import com.cs3733.teamd.Model.Tag;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.LinkedList;


public class MapMenuController {
    LinkedList<Tag> visibleLocations = new LinkedList<Tag>();
    static ObservableList<Tag> roomDropDown = FXCollections.observableArrayList();
    public Button largerTextButton;
    public Button SearchButton;
    public Button LoginButton;
    public Button BackButton;
    public Button MenuButton;

    public Button SubmitButton;

    @FXML
    public ChoiceBox LocationSelect;


    @FXML
    private void initialize(){
        visibleLocations.add(new Tag("Example Tag"));
        roomDropDown.addAll(visibleLocations);
        LocationSelect.setItems(roomDropDown);
    }

    @FXML
    public void onMenu(ActionEvent actionEvent) throws IOException {
        Main.window.hide();
        Main.window.setScene(Main.MainScene);
        Main.window.show();
        Main.backRoot = Main.LoginScene;
    }
    @FXML
    public void onLogin(ActionEvent actionEvent) throws IOException{
        Main.window.hide();
        Main.window.setScene(Main.LoginScene);
        Main.window.show();
        Main.backRoot = Main.MainScene;
    }

    @FXML
    public void onBack(ActionEvent actionEvent) throws  IOException{
        Main.window.hide();
        Main.window.setScene(Main.backRoot);
        Main.window.show();
        Main.backRoot = Main.MainScene;
    }

    public void submitSearch(ActionEvent actionEvent) {
        Tag desiredTag = (Tag) LocationSelect.getValue();
    }
}
