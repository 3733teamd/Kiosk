package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Main;
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
import java.util.LinkedList;

/**
 * Created by Allyk on 3/26/2017.
 */
public class EditMapController {

    LinkedList<Node> currentNodes = new LinkedList<Node>();
    public static ObservableList<Node> nodeDropDown = FXCollections.observableArrayList();

    LinkedList<Tag> currentTags = new LinkedList<Tag>();
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
    public Label nodetype;
    public Label nodeone;
    public Label nodetwo;


    @FXML
    public ChoiceBox StartNodeSelect;
    public ChoiceBox ConnectionSelect;
    public ChoiceBox TagBox;

    @FXML
    private void initialize(){

        setText();
        //visibleLocations.add(new Tag("Example Tag"));
        nodeDropDown.addAll(currentNodes);
        StartNodeSelect.setItems(nodeDropDown);
        ConnectionSelect.setItems(nodeDropDown);
        tagDropDown.addAll(currentTags);
        TagBox.setItems(tagDropDown);
    }

    @FXML
    public void onSearch(ActionEvent actionEvent) throws IOException {
        Main.window.hide();
        Main.window.setScene(Main.MapMenuScene);
        Main.window.show();
        Main.backRoot = Main.EditMapScene;
    }

    @FXML
    public void onLogin(ActionEvent actionEvent) throws IOException{
        Main.window.hide();
        Main.window.setScene(Main.LoginScene);
        Main.window.show();
        Main.backRoot = Main.EditMapScene;
    }
    @FXML
    public void onBack(ActionEvent actionEvent) throws  IOException{
        Main.window.hide();
        Main.window.setScene(Main.backRoot);
        Main.window.show();
        Main.backRoot = Main.EditMapScene;
    }

    @FXML
    public void onMenu(ActionEvent actionEvent) throws IOException{
        Main.window.hide();
        Main.window.setScene(Main.MainScene);
        Main.window.show();
        Main.backRoot = Main.LoginScene;
    }

    @FXML


    public void onAddRoom(ActionEvent actionEvent) throws IOException{
        /*int newxlocation = Integer.parseInt(xLocation.getText());
        int newylocation = Integer.parseInt(yLocation.getText());
        Tag typeTag = (Tag) TagBox.getValue();
        Node newNode = new Node(newxlocation, newylocation);
        newNode.addTag(typeTag);
        //nodes.add(newNode);
        Main.window.hide();
        Main.window.setScene(Main.EditMapScene);
        Main.window.show();*/
    }
    public void onRemoveRoom (ActionEvent actionEvent) throws IOException{
        /*int newxlocation = Integer.parseInt(xLocation.getText());
        int newylocation = Integer.parseInt(yLocation.getText());
        Tag typeTag = (Tag) TagBox.getValue();
        Node newNode = new Node(newxlocation, newylocation);
        newNode.addTag(typeTag);
        //nodes.remove(newNode);
        Main.window.hide();
        Main.window.setScene(Main.EditMapScene);
        Main.window.show();*/
    }
    public void onAddConnection(ActionEvent actionEvent) throws IOException{
        /*//((Node) StartNodeSelect.getValue()).addNode( (Node) ConnectionSelect.getValue());
        Main.window.hide();
        Main.window.setScene(Main.EditMapScene);
        Main.window.show();*/
    }
    public void onRemoveConnection (ActionEvent actionEvent) throws IOException{
        /*//((Node) StartNodeSelect.getValue()).rmvNode( (Node) ConnectionSelect.getValue());
        Main.window.hide();
        Main.window.setScene(Main.EditMapScene);
        Main.window.show();*/
    }

    @FXML
    public void setText(){
        SearchButton.setText(Main.bundle.getString("search"));
        LoginButton.setText(Main.bundle.getString("login"));
        MenuButton.setText(Main.bundle.getString("menu"));
        BackButton.setText(Main.bundle.getString("back"));
        menu.setText(Main.bundle.getString("EditM"));

//        name.setText(Main.bundle.getString("name"));
        yloc.setText(Main.bundle.getString("Xloc"));
        xloc.setText(Main.bundle.getString("Yloc"));
        addRoomButton.setText(Main.bundle.getString("AddRoom"));
        removeRoomButton.setText(Main.bundle.getString("RemRoom"));

        if(Main.Langugage.equals("Spanish") ){
            menu.setX(-100);
            //menu.setFont(Font.font("System", 75));
            //menu.setTranslateX(-175);

        }
        else if(Main.Langugage.equals("English") ){
            // menu.setTranslateX(-175);
            menu.setX(0);
            menu.setFont(Font.font("System", 91));

        }
    }

    @FXML
    public void onSubmitAdd(ActionEvent actionEvent) throws  IOException{
        Main.window.hide();

        Main.window.setScene(Main.AdminMenuScene);
        Main.window.show();
        Main.backRoot = Main.EditServiceScene;
    }
}
