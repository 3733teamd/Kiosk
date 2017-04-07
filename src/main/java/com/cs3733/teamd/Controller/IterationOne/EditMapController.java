package com.cs3733.teamd.Controller.IterationOne;

import com.cs3733.teamd.Controller.AbsController;
import com.cs3733.teamd.Main;
import com.cs3733.teamd.Model.Directory;
import com.cs3733.teamd.Model.Node;
import com.cs3733.teamd.Model.Tag;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Allyk on 3/26/2017.
 */
public class EditMapController extends AbsController {

    Directory dir = Directory.getInstance();
    ArrayList<Node> currentNodes = dir.getAllNodes();
    public static ObservableList<Node> nodeDropDown = FXCollections.observableArrayList();

    ArrayList<Tag> currentTags = dir.getAllTags();
    public static ObservableList<Tag> tagDropDown = FXCollections.observableArrayList();

    public Button largerTextButton;
    public Button SearchButton;
    public Button LoginButton;
    public Button BackButton;
    public Button MenuButton;

    public ImageView mapImage;
    public Button addRoomButton;
    public Button removeRoomButton;
    public TextField xLocation;
    public TextField yLocation;
    public Button addConnectionButton;
    public Button removeConnectionButton;

    public AnchorPane pane;
    public Text menu;
    public Label name;
    public Label yloc;
    public Label xloc;
    public Label nodeLabel;
    public Label NodeOne;
    public Label NodeTwo;

    public AnchorPane MMGpane;


    @FXML
    public ChoiceBox StartNodeSelect;
    public ChoiceBox ConnectionSelect;
    public ChoiceBox TagBox;

    //initialize scene and data
    @FXML
    private void initialize(){

        setText();
        nodeDropDown.addAll(currentNodes);
        StartNodeSelect.setItems(nodeDropDown);
        StartNodeSelect.setValue(nodeDropDown.get(0));
        ConnectionSelect.setItems(nodeDropDown);
        ConnectionSelect.setValue(nodeDropDown.get(0));
        tagDropDown.addAll(currentTags);
        TagBox.setItems(tagDropDown);
        TagBox.setValue(tagDropDown.get(0));
    }

    //Search button
//    @FXML
//    public void onSearch(ActionEvent actionEvent) throws IOException {
//        switchScreen(MMGpane, "/Views/Iteration1/MapMenu.fxml", "/Views/Iteration1/Login.fxml");
//
//    }
//
//    //Login button
//    @FXML
//    public void onLogin(ActionEvent actionEvent) throws IOException{
//        switchScreen(MMGpane, "/Views/Iteration1/Login.fxml", "/Views/Iteration1/Login.fxml");
//
//    }
//
//    //Back button
//    @FXML
//    public void onBack(ActionEvent actionEvent) throws  IOException{
//        switchScreen(MMGpane, Main.backString, "/Views/Iteration1/EditMap.fxml");
//
//    }
//
//    //Menu button
//    @FXML
//    public void onMenu(ActionEvent actionEvent) throws IOException{
//        switchScreen(MMGpane, "/Views/Iteration1/Main.fxml", "/Views/Iteration1/Login.fxml");
//
//    }

    //Add Room button
    @FXML
    public void onAddRoom(ActionEvent actionEvent) throws IOException{

        int newxlocation = Integer.parseInt(xloc.getText());
        int newylocation = Integer.parseInt(yloc.getText());
        Tag typeTag = (Tag) TagBox.getValue();

        //Node newNode = dir.createNewNode(newxlocation, newylocation);

    }

    //Remove Room button
    public void onRemoveRoom (ActionEvent actionEvent) throws IOException{
    }

      //Add Connection button
    public void onAddConnection(ActionEvent actionEvent) throws IOException{
    }

    //Remove Connection button
    public void onRemoveConnection (ActionEvent actionEvent) throws IOException{
    }

    //Spanish translation
    @FXML
    public void setText(){
        SearchButton.setText(Main.bundle.getString("search"));
        LoginButton.setText(Main.bundle.getString("login"));
        MenuButton.setText(Main.bundle.getString("menu"));
        BackButton.setText(Main.bundle.getString("back"));
        menu.setText(Main.bundle.getString("EditM"));

        yloc.setText(Main.bundle.getString("Xloc"));
        xloc.setText(Main.bundle.getString("Yloc"));
        addRoomButton.setText(Main.bundle.getString("AddRoom"));
        removeRoomButton.setText(Main.bundle.getString("RemRoom"));
        nodeLabel.setText(Main.bundle.getString("nodeType"));
        NodeOne.setText(Main.bundle.getString("n1"));
        NodeTwo.setText(Main.bundle.getString("n2"));

        addRoomButton.setText(Main.bundle.getString("AddRoom"));
        removeRoomButton.setText(Main.bundle.getString("RemRoom"));

        addConnectionButton.setText(Main.bundle.getString("ac"));
        removeConnectionButton.setText(Main.bundle.getString("rc"));
        
        if(Main.Langugage.equals("Spanish") ){
            menu.setX(-100);
        }
        else if(Main.Langugage.equals("English") ){
            menu.setX(0);
            menu.setFont(Font.font("System", 91));

        }
    }

    //Add button
    @FXML
    public void onSubmitAdd(ActionEvent actionEvent) throws  IOException{

    }
}
