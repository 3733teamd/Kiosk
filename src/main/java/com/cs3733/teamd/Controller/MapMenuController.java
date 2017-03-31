package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Main;
import com.cs3733.teamd.entities.Professional;
import com.cs3733.teamd.entities.Tag;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;


public class MapMenuController {
    static ObservableList<Professional> roomDropDown =
            FXCollections.observableArrayList(new Professional("Ryan", Professional.Title.DR, "cardiology"), new Professional("andy", Professional.Title.DR, "Neuroscience"));

    static ObservableList<Tag> serviceDropDown =
            FXCollections.observableArrayList( new Tag("Allergy"), new Tag("Blood Test"), new Tag("ICU"));

    public Button largerTextButton;
    public Button SearchButton;
    public Button LoginButton;
    public Button BackButton;
    public Button MenuButton;

    public Button SubmitButton;

    @FXML
    public ChoiceBox RoomSelect;
    @FXML
    public ChoiceBox ServiceSelect;


    @FXML
    private void initialize(){
        RoomSelect.setValue("Select Room");
        RoomSelect.setItems(roomDropDown);
        ServiceSelect.setValue("Select Service");
        ServiceSelect.setItems(serviceDropDown);
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


    public void submitSearch(ActionEvent actionEvent) {
        Professional desiredProfessional = (Professional) RoomSelect.getValue();
        Tag desiredTag = (Tag) ServiceSelect.getValue();
    }
}
