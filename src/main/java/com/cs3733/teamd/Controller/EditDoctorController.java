package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Main;
import com.cs3733.teamd.Model.Professional;
import com.cs3733.teamd.Model.Tag;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by Allyk on 3/26/2017.
 */
public class EditDoctorController {

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

    public ChoiceBox addRoomSelect;
    public ChoiceBox modifyDoctorSelect;
    public ChoiceBox modifyRoomSelect;
    public ChoiceBox removeDoctorSelect;
    public AnchorPane pane;
    public Text menu;
    public Label addRToD;
    public Label addD;
    public Label ModRN;
    public Label remD;
    public ComboBox titleBox;
    public ChoiceBox addDoctorSelectRoom;
    public ChoiceBox addRoomToDoc;


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
    public void submitRemoveDoctor(ActionEvent actionEvent) throws IOException{
        Professional p = (Professional) removeDoctorSelect.getValue(); //get doctor from choice box
        //TODO: Find p in the Database, and remove

        Main.window.hide();
        Main.window.setScene(Main.AdminMenuScene);
        Main.window.show();
        Main.backRoot = Main.EditServiceScene;
    }
    @FXML
    public void submitModifyDoctor(ActionEvent actionEvent) throws IOException{
        //TODO: implement in the next iteration, and probably change the name of the fields/function as well

        Main.window.hide();
        Main.window.setScene(Main.AdminMenuScene);
        Main.window.show();
        Main.backRoot = Main.EditServiceScene;
    }
    @FXML
    public void submitAddDoctor(ActionEvent actionEvent) throws IOException{

        LinkedList<Tag> loT = new LinkedList<Tag>();
        Tag t = (Tag) addRoomSelect.getValue();
        loT.add(t);
        Professional p = new Professional(addDoctorLabel.getText() );


        Main.window.hide();
        Main.window.setScene(Main.AdminMenuScene);
        Main.window.show();
        Main.backRoot = Main.EditServiceScene;
    }
    @FXML
    public void submitAddRoomDoctor(ActionEvent actionEvent) throws IOException{
        //Working with Room to add "addDoctorSelectRoom", and Doctor to receive room "addRoomToDoc"
        Tag t = (Tag) addDoctorSelectRoom.getValue();
        Professional p = (Professional) addRoomToDoc.getValue();

        t.addProf(p); //add doctor to room, and vice versa
        //TODO: Add to database

        Main.window.hide();
        Main.window.setScene(Main.AdminMenuScene);
        Main.window.show();
        Main.backRoot = Main.EditServiceScene;
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

}
