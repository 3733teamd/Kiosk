package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Model.Directory;
import com.cs3733.teamd.Model.Tag;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anh Dao on 4/6/2017.
 */
public class EditTagScreenController extends AbsController {
    Directory dir = Directory.getInstance();
    public TextArea tagTextArea;
    public TextField searchTagBar;
    ObservableList<String> seasonList = FXCollections.<String>observableArrayList("Spring", "Summer", "Fall", "Winter");

    public Button BackButton;
    public AnchorPane MMGpane;
    public Button addNewTag;
    List tags = dir.getTags();
    List Profs= dir.getProfessionals();

    @FXML ListView<String> tagList;// = new ListView<>();

    @FXML
    private ListView<String > allProffessionals;

    @FXML
    private ListView<String> currentProfessionals;


    //Back button
    @FXML
    public void onBack(ActionEvent actionEvent) throws IOException {
        switchScreen(MMGpane, "/Views/EditMapScreen.fxml");
    }
    @FXML
    public void initialize(){

        ObservableList<String> tag = FXCollections.observableArrayList(tags);
        tagList.setItems(tag);
        ObservableList<String> names = FXCollections.observableArrayList(Profs);
        allProffessionals.setItems(names);

        


    }
    
}
