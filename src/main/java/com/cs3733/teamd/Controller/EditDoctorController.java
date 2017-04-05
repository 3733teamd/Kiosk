package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Main;
import com.cs3733.teamd.Model.Professional;
import com.cs3733.teamd.Model.Tag;
import com.cs3733.teamd.Model.Title;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Allyk on 3/26/2017.
 */
public class EditDoctorController {

    LinkedList<String> userOptionList = new LinkedList<String>();

    public Button largerTextButton;
    public Button SearchButton;
    public Button LoginButton;
    public Button BackButton;
    public Button MenuButton;

    public Button submitRemoveDoctor;
    public Button submitModifyDoctor;
    public Button submitAddDoctor;
    public Button submitAddRoomDoctor;

    public TextField addDoctorLabel;

    public AnchorPane pane;
    public Text menu;
    public Label addRToD;
    public Label addD;
    public Label ModRN;
    public Label remD;


    public ObservableList<String> editProfOptions = FXCollections.observableArrayList();
    public static ObservableList<Tag> tagList = FXCollections.observableArrayList();
    public static ObservableList<Professional> professionalList = FXCollections.observableArrayList();
    public ChoiceBox<Title> titleBox;
    public ChoiceBox modifyOptions;
    public ChoiceBox modifyProf;
    public ChoiceBox modifySection;
    public ChoiceBox modifyTitle;


    @FXML private void initialize(){
        editProfOptions.add("Add To");
        editProfOptions.add("Update");

        //modifyTitle.setValue(Title.values()[0]);
        modifyTitle.setItems(FXCollections.observableArrayList(Title.values()));
        //modifySection.setValue(tagList.get(0));
        modifySection.setItems(tagList);
        //modifyProf.setValue((professionalList.get(0)));
        modifyProf.setItems(professionalList);
        //modifyOptions.setValue(editProfOptions.get(0));
        modifyOptions.setItems(editProfOptions);


    }


    @FXML
    public void onSearch(ActionEvent actionEvent) throws IOException {
        Main.window.hide();
        Main.window.setScene(Main.MapMenuScene);
        Main.window.show();
        Main.backRoot = Main.EditDoctorScene;
    }

    @FXML
    public void onLogin(ActionEvent actionEvent) throws IOException{
        Main.window.hide();
        Main.window.setScene(Main.LoginScene);
        Main.window.show();
        Main.backRoot = Main.EditDoctorScene;
    }

    @FXML
    public void onBack(ActionEvent actionEvent) throws  IOException{
        Main.window.hide();
        Main.window.setScene(Main.backRoot);
        Main.window.show();
        Main.backRoot = Main.EditDoctorScene;
    }

    @FXML
    public void onMenu(ActionEvent actionEvent) throws IOException{
        Main.window.hide();
        Main.window.setScene(Main.MainScene);
        Main.window.show();
        Main.backRoot = Main.LoginScene;
    }


        @FXML
    public void setText(){
        SearchButton.setText(Main.bundle.getString("search"));
        LoginButton.setText(Main.bundle.getString("login"));
        MenuButton.setText(Main.bundle.getString("menu"));
        BackButton.setText(Main.bundle.getString("back"));
        menu.setText(Main.bundle.getString("EditDD"));

        submitRemoveDoctor.setText(Main.bundle.getString("submit"));
        submitModifyDoctor.setText(Main.bundle.getString("submit"));
        submitAddDoctor.setText(Main.bundle.getString("submit"));
        submitAddRoomDoctor.setText(Main.bundle.getString("submit"));

        addRToD.setText(Main.bundle.getString("AddRtoD"));
        addD.setText(Main.bundle.getString("addDoctor"));
        ModRN.setText(Main.bundle.getString("ModRN"));
        remD.setText(Main.bundle.getString("remDoctor"));
        if(Main.Langugage.equals("Spanish") ){
            menu.setX(-80);
            menu.setFont(Font.font("System", 75));
            //menu.setTranslateX(-175);

        }
        else if(Main.Langugage.equals("English") ){
            // menu.setTranslateX(-175);
            menu.setX(0);
            menu.setFont(Font.font("System", 91));

        }
    }

    public void professionalSubmit(ActionEvent actionEvent) {
        String optionChoice = (String) modifyOptions.getValue();
        Professional currentProf = (Professional) modifyProf.getValue();
        Tag currentSection = (Tag) modifySection.getValue();
        Title currentTitle = (Title) modifyTitle.getValue();


        if(optionChoice == "Add To") {
            if(currentSection != null) {
                currentProf.addTag(currentSection);
            }
            if(currentTitle != null){
                currentProf.addTitle(currentTitle);
            }

        }else if(optionChoice == "Update") {
            if(currentSection != null) {
                currentProf.rmvAllTags();
                currentProf.addTag(currentSection);
            }
            if(currentTitle != null){
                currentProf.rmvAllTitles();
                currentProf.addTitle(currentTitle);
            }
        }


    }
}
