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
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.LinkedList;

//import com.cs3733.teamd.Model.Location;


public class MapMenuController extends  AbsController{




    LinkedList<Tag> visibleLocations = new LinkedList<Tag>();
    public static ObservableList<Tag> roomDropDown = FXCollections.observableArrayList();
    public Button largerTextButton;
    public Button SearchButton;
    public Button LoginButton;
    public Button BackButton;
    public Button MenuButton;

    public Button SubmitButton;

    public AnchorPane pane;
    public Label selectL;
    public Label selectD;
    public Label instruct;
    public Text menu;

    public AnchorPane MMGpane;

    @FXML
    public ChoiceBox DestinationSelect;
    public ChoiceBox StartSelect;

    static public LinkedList<Node> pathNodes= new LinkedList<Node>();

    @FXML
    private void initialize(){
        setText();
        //visibleLocations.add(new Tag("Example Tag"));
        roomDropDown.addAll(visibleLocations);
        DestinationSelect.setValue(roomDropDown.get(0));
        DestinationSelect.setItems(roomDropDown);
        StartSelect.setValue(roomDropDown.get(0));
        StartSelect.setItems(roomDropDown);
    }

    //Menu button
    @FXML
    public void onMenu(ActionEvent actionEvent) throws IOException {
        switchScreen(MMGpane, "/Views/Main.fxml", "/Views/MapMenu.fxml");
    }

    //Login button
    @FXML
    public void onLogin(ActionEvent actionEvent) throws IOException{
        switchScreen(MMGpane, "/Views/Login.fxml", "/Views/MapMenu.fxml");
    }

    //Back button
    @FXML
    public void onBack(ActionEvent actionEvent) throws  IOException{
        System.out.println(Main.backString);
        switchScreen(MMGpane, Main.backString, "/Views/MapMenu.fxml");
    }

    //Submit button
    @FXML
    public void onSubmit(ActionEvent actionEvent) throws  IOException{

        ///-----------------------------------------------------------------Just Picks the First Node In A Tag!!!!!
        Tag destinationTag = (Tag) DestinationSelect.getValue();
        Tag startTag = (Tag) StartSelect.getValue();
        System.out.println("start:\t" +startTag.tagName);
        System.out.println("end:\t" +destinationTag.tagName);

        Pathfinder pathfinder = new Pathfinder(startTag.getNodes().getFirst(), destinationTag.getNodes().getFirst());

        pathNodes =pathfinder.shortestPath();

        switchScreen(MMGpane, "/Views/MapDirections.fxml", "/Views/MapMenu.fxml");
    }

    @FXML
    public void setText(){
        SearchButton.setText(Main.bundle.getString("search"));
        LoginButton.setText(Main.bundle.getString("login"));
        MenuButton.setText(Main.bundle.getString("menu"));
        BackButton.setText(Main.bundle.getString("back"));
        menu.setText(Main.bundle.getString("MapMenu"));
        instruct.setText(Main.bundle.getString("instruct2"));
        selectL.setText(Main.bundle.getString("SelectL"));
        selectD.setText(Main.bundle.getString("SelectD"));
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
