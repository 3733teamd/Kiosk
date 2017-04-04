package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Main;
import com.cs3733.teamd.Model.Tag;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
public class EditServiceController {

    public Button largerTextButton;
    public Button SearchButton;
    public Button LoginButton;
    public Button BackButton;
    public Button MenuButton;
    public Button submitRemoveService;
    public Button submitModifyService;
    public Button submitAddService;

    public TextField addService;

    public ChoiceBox addRoomSelect;
    public ChoiceBox modifyServiceSelect;
    public ChoiceBox modifyRoomSelect;
    public ChoiceBox removeServiceSelect;

    private String newService;
    private String modifiedRoom;
    private String modifiedService;
    private String removedService;

    public AnchorPane pane;
    public Text menu;
    public Label ModRoomNumLabel;
    public Label remServiceLabel;
    public Label addServiceLabel;


    // some room/service samples
    static ObservableList<String> roomsList =
            FXCollections.observableArrayList( "Select Room", "3A", "3B","3C" );
    static ObservableList<String> serviceList =
            FXCollections.observableArrayList( "Select Service", "Allergy", "Blood Test","ICU","Oranges", "Emergency Room" );

    @FXML
    private void initialize(){
//        RoomSelect.setValue("Select Room");
        modifyRoomSelect.setItems(roomsList);
//        ServiceSelect.setValue("Select Service");
        modifyServiceSelect.setItems(serviceList);
        removeServiceSelect.setItems(serviceList);

    }

    @FXML
    public void onSearch(ActionEvent actionEvent) throws IOException {
        Main.window.hide();
        Main.window.setScene(Main.MapMenuScene);
        Main.window.show();
        Main.backRoot = Main.EditServiceScene;
    }

    @FXML
    public void onLogin(ActionEvent actionEvent) throws IOException{
        Main.window.hide();
        Main.window.setScene(Main.LoginScene);
        Main.window.show();
        Main.backRoot = Main.EditServiceScene;
    }
    @FXML
    public void onBack(ActionEvent actionEvent) throws  IOException{
        Main.window.hide();
        Main.window.setScene(Main.backRoot);
        Main.window.show();
        Main.backRoot = Main.EditServiceScene;
    }

    @FXML
    public void onMenu(ActionEvent actionEvent) throws IOException{
        Main.window.hide();
        Main.window.setScene(Main.MainScene);
        Main.window.show();
        Main.backRoot = Main.LoginScene;
    }

    @FXML
    public void onSubmitAdd(ActionEvent actionEvent) throws  IOException{
        Main.window.hide();
        newService = addService.getText();
        System.out.print("new service: " + newService);

        //-------------------------------------------------------this is the tag that needs to be added to the database
        Tag t = new Tag(this.addService.getText());

        Main.window.setScene(Main.AdminMenuScene);
        Main.window.show();
        Main.backRoot = Main.EditServiceScene;
    }
    @FXML
    public void onSubmitModify(ActionEvent actionEvent) throws  IOException{
        Main.window.hide();

        modifiedRoom = modifyRoomSelect.getValue().toString();
        modifiedService = modifyServiceSelect.getValue().toString();
        System.out.print("modified service: " + modifiedService);
        System.out.print("modified room: " + modifiedRoom);

        Main.window.setScene(Main.AdminMenuScene);
        Main.window.show();
        Main.backRoot = Main.EditServiceScene;
    }

    @FXML
    public void onSubmitRemove(ActionEvent actionEvent) throws  IOException{
        Main.window.hide();

        removedService = removeServiceSelect.getValue().toString();
        System.out.print("removed service: " + removedService);

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
        menu.setText(Main.bundle.getString("EditSD"));

        ModRoomNumLabel.setText(Main.bundle.getString("ModRN"));
        addServiceLabel.setText(Main.bundle.getString("AddService"));
        remServiceLabel.setText(Main.bundle.getString("RemService"));
        submitRemoveService.setText(Main.bundle.getString("submit"));
        submitModifyService.setText(Main.bundle.getString("submit"));
        submitAddService.setText(Main.bundle.getString("submit"));
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
