package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Main;
import com.cs3733.teamd.Model.Node;
import com.cs3733.teamd.Model.Pathfinder;
import com.cs3733.teamd.Model.Tag;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.LinkedList;

//import com.cs3733.teamd.Model.Location;


public class MapMenuController {
    static ObservableList<String> roomsList =
            FXCollections.observableArrayList( "Select Room", "3A", "3B","3C" );
    static ObservableList<String> serviceList =
            FXCollections.observableArrayList( "Select Service", "Allergy", "Blood Test","ICU","Oranges", "Emergency Room" );




    LinkedList<Tag> visibleLocations = new LinkedList<Tag>();
    public static ObservableList<Tag> roomDropDown = FXCollections.observableArrayList();
    public Button largerTextButton;
    public Button SearchButton;
    public Button LoginButton;
    public Button BackButton;
    public Button MenuButton;

    public Button SubmitButton;

    public AnchorPane pane;
    public Label select;
    public Label instruct;
    public Text menu;

    @FXML
    public ChoiceBox DestinationSelect;
    public ChoiceBox StartSelect;

    static public LinkedList<Node> pathNodes= new LinkedList<>();

    @FXML
    private void initialize(){
       // RoomSelect.setValue("Select Room");
       // RoomSelect.setItems(roomsList);
       // ServiceSelect.setValue("Select Service");
        //ServiceSelect.setItems(serviceList);

        setText();
        //visibleLocations.add(new Tag("Example Tag"));
        roomDropDown.addAll(visibleLocations);
        DestinationSelect.setItems(roomDropDown);
        StartSelect.setItems(roomDropDown);
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
        //Main.roomSelected = RoomSelect.getValue().toString();
        //Main.serviceSelected = ServiceSelect.getValue().toString();
        Main.window.setScene(Main.MapDirectionsScene);


        ///-----------------------------------------------------------------Just Picks the First Node In A Tag!!!!!
        Tag destinationTag = (Tag) DestinationSelect.getValue();
        Tag startTag = (Tag) StartSelect.getValue();
        System.out.println("start" +startTag.tagName);
        System.out.println("end" +destinationTag.tagName);

        Pathfinder pathfinder = new Pathfinder(startTag.getNodes().getFirst(), destinationTag.getNodes().getFirst());

        MapDirectionsController mapDirectionsController = new MapDirectionsController();
       // mapDirectionsController.plotPath(pathfinder.shortestPath());
        pathNodes =pathfinder.shortestPath();

        Main.window.show();
        Main.backRoot = Main.MapMenuScene;


//        MapDirectionsController mapDirectionsController = new MapDirectionsController();
                //  <Button fx:id="SubmitButton" layoutX="585.0" layoutY="571.0" mnemonicParsing="false" onAction="#submitSearch" text="Submit">

    }

    @FXML
    public void setText(){
        SearchButton.setText(Main.bundle.getString("search"));
        LoginButton.setText(Main.bundle.getString("login"));
        MenuButton.setText(Main.bundle.getString("menu"));
        BackButton.setText(Main.bundle.getString("back"));
        menu.setText(Main.bundle.getString("MapMenu"));
        instruct.setText(Main.bundle.getString("instruct2"));
        select.setText(Main.bundle.getString("instruct3"));
        SubmitButton.setText(Main.bundle.getString("submit"));
        if(Main.Langugage.equals("Spanish") ){
            menu.setX(-175);
            //menu.setTranslateX(-175);
        }
        else if(Main.Langugage.equals("English") ){
           // menu.setTranslateX(-175);
            menu.setX(0);
        }
    }
}
