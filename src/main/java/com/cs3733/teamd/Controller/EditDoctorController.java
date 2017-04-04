package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;

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
    public TextField titleBox;

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
        Main.window.hide();

        Main.window.setScene(Main.AdminMenuScene);
        Main.window.show();
        Main.backRoot = Main.EditServiceScene;
    }
    @FXML
    public void submitModifyDoctor(ActionEvent actionEvent) throws IOException{
        Main.window.hide();
        Main.window.setScene(Main.AdminMenuScene);
        Main.window.show();
        Main.backRoot = Main.EditServiceScene;
    }
    @FXML
    public void submitAddDoctor(ActionEvent actionEvent) throws IOException{
        Main.window.hide();
        Main.window.setScene(Main.AdminMenuScene);
        Main.window.show();
        Main.backRoot = Main.EditServiceScene;
    }
    @FXML
    public void submitAddRoomDoctor(ActionEvent actionEvent) throws IOException{
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
