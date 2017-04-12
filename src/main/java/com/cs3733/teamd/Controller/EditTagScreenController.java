package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Model.Directory;
import com.cs3733.teamd.Model.Professional;
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
import java.util.stream.Collectors;

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
    public TextField tagNameTxt;

    @FXML
    private Button addProf;

    @FXML
    private Button newTagNameBtn;

    @FXML
    private Button deleteProf;

    @FXML
    private TextField profSearchField;

    List tags = dir.getTags();
    List Profs= dir.getProfessionals();
    List<Professional> unfiltered = dir.getProfessionals();
    List<Professional> filtered = new ArrayList<Professional>();
    ObservableList<Professional> searchResults = FXCollections.observableArrayList();
    List<Tag> allTheTags= dir.getTags();
    List<String> allTagNames = new ArrayList<String>();
    private Tag chosenTag=null;

    @FXML
    private Button addNewTagBtn;

    @FXML
    private Button deleteTagBtn;

    Tag selectedTag;
    Professional selectedCurProf;
    Professional selectedProf;

    @FXML ListView<Tag> tagList;// = new ListView<>();

    @FXML
    private ListView allProffessionals;
    //private ListView<String> allProffessionals;

    @FXML
    private ListView<Professional> currentProfessionals;


    //Back button
    @FXML
    public void onBack(ActionEvent actionEvent) throws IOException {
        switchScreen(MMGpane, "/Views/EditMapScreen.fxml");
    }
    @FXML
    public void initialize(){
        addProf.setOpacity(.5);
        deleteProf.setOpacity(.5);
        newTagNameBtn.setOpacity(.5);
        addProf.setDisable(true);
        deleteProf.setDisable(true);
        newTagNameBtn.setDisable(true);
       // List chosenProf=chosenTag.getProfs();
        for (int i = 0 ; i<allTheTags.size(); i++) {
            allTagNames.add(allTheTags.get(i).getTagName());
        }
        TextFields.bindAutoCompletion(searchTagBar, allTagNames);
        tagList.setItems(FXCollections.observableArrayList(dir.getTags()));
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
                    tagNameTxt.setText(searchTagBar.getText());

                    //System.out.println(text);
                }
            }
        });
        tagList.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tag>() {
                    public void changed(ObservableValue<? extends Tag> ov,
                                        Tag old_val, Tag new_val) {
                        selectedTag = new_val;
                        if(selectedTag!=null) {
                            currentProfessionals.setItems(FXCollections.observableArrayList(selectedTag.getProfs()));
                            currentProfessionals.refresh();

                            tagNameTxt.clear();
                            tagNameTxt.setPromptText(selectedTag.toString());

                        }
                        addProf.setOpacity(1.0);
                        deleteProf.setOpacity(1.0);
                        newTagNameBtn.setOpacity(1.0);
                        addProf.setDisable(false);
                        deleteProf.setDisable(false);
                        newTagNameBtn.setDisable(false);

                    }

                });

        currentProfessionals.getSelectionModel().selectedItemProperty().addListener(
        new ChangeListener<Professional>() {
            public void changed(ObservableValue<? extends Professional> ov,
                                Professional old_val, Professional new_val) {
                selectedCurProf = new_val;

            }
        });

        allProffessionals.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<Professional>() {
            public void changed(ObservableValue<? extends Professional> ov,
                                Professional old_val, Professional new_val) {
                selectedProf = new_val;

            }
        });

        profSearchField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                //String text = searchTagBar.getText();

                displayResult(profSearchField.getText() + event.getText());
            }
        });
        
    }

    @FXML
    void newTagName(ActionEvent event) {
        if(selectedTag != null){
            selectedTag.setTagName(tagNameTxt.getText());
            dir.updateTag(selectedTag);
            tagList.refresh();
            tagNameTxt.clear();
        }
    }

    @FXML
    void addProf(ActionEvent event) {
        if(selectedProf!=null && selectedTag != null){
            dir.addTagToProfessional(selectedProf,selectedTag);
            currentProfessionals.setItems(FXCollections.observableArrayList(selectedTag.getProfs()));
            currentProfessionals.refresh();
        }
    }

    @FXML
    void deleteProf(ActionEvent event) {
        if(selectedTag != null && selectedCurProf != null){
            dir.removeTagFromProfessional(selectedCurProf,selectedTag);
            currentProfessionals.setItems(FXCollections.observableArrayList(selectedTag.getProfs()));
            currentProfessionals.refresh();
        }

    }

    @FXML
    public void displayResult(String value){

        for (Professional d: unfiltered){
            filtered = unfiltered.stream().filter((p) -> p.getName().toLowerCase().contains(value.toLowerCase())).collect(Collectors.toList());
        }

        searchResults.setAll(filtered);

        allProffessionals.setItems(searchResults);
    }
    //TODO: is deleted from database wrongly, causes fatal error
    @FXML
    void deleteTag(ActionEvent event) {
        if(selectedTag != null){

            dir.deleteTag(selectedTag);
            tagList.setItems(FXCollections.observableArrayList(dir.getTags()));
            tagList.refresh();
        }
    }
    
    @FXML
    void addTag(ActionEvent event) {
        if (searchTagBar.getText() == null ||searchTagBar.getText()==" "||searchTagBar.getText()==""|| searchTagBar.getText().length()<=1) {

        }
        else {
            dir.saveTag(searchTagBar.getText());
            tagList.setItems(FXCollections.observableArrayList(dir.getTags()));
            tagList.refresh();
            searchTagBar.clear();
        }
    }
}
