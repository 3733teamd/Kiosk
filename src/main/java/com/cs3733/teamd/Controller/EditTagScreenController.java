package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Model.Directory;
import com.cs3733.teamd.Model.Tag;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.textfield.TextFields;

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
    public TextField tagName;

    List tags = dir.getTags();
    List Profs= dir.getProfessionals();
    List<Tag> allTheTags= dir.getTags();
    private Tag chosenTag=null;

    @FXML ListView<String> tagList;// = new ListView<>();

    @FXML
    private ListView<String> allProffessionals;

    @FXML
    private ListView<String> currentProfessionals;


    //Back button
    @FXML
    public void onBack(ActionEvent actionEvent) throws IOException {
        switchScreen(MMGpane, "/Views/EditMapScreen.fxml");
    }
    @FXML
    public void initialize(){
       // List chosenProf=chosenTag.getProfs();
        TextFields.bindAutoCompletion(searchTagBar, allTheTags);
        ObservableList<String> tag = FXCollections.observableArrayList(tags);
        tagList.setItems(tag);
        ObservableList<String> names = FXCollections.observableArrayList(Profs);
        System.out.println(names);
        allProffessionals.setItems(names);
        //ObservableList<String> chosen = FXCollections.observableArrayList(chosenProf);
       // allProffessionals.setItems(chosen);
        searchTagBar.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER)  {
                    String text = searchTagBar.getText();
                    //TODO set chosen tag to tag from bar
                    System.out.println(chosenTag);
                    tagName.setText(searchTagBar.getText());
                    //System.out.println(text);
                }
            }
        });
        tagList.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
                    public void changed(ObservableValue<? extends String> ov,
                                        String old_val, String new_val) {
                    }
                });

    }
    
}
