package com.cs3733.teamd.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


import java.io.IOException;

/**
 * Created by Anh Dao on 4/6/2017.
 */
public class EditProfScreenController extends AbsController {
    @FXML
    private AnchorPane pane;

    @FXML
    private Button submitButton;

    @FXML
    private Button BackButton;

    @FXML
    private TextField searchProfessionalBar;

    @FXML
    private TableView<?> ProfessionalTable;

    @FXML
    private TableColumn<?, ?> ProfessionalsList;

    @FXML
    private Button addNewProf;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField ProfName;

    @FXML
    private Button modifyName;

    @FXML
    private TableColumn<?, ?> allTitleTable;

    @FXML
    private TableColumn<?, ?> currentTitleTable;

    @FXML
    private TextField searchTitle;

    @FXML
    private Button addTitle;

    @FXML
    private Button deleteTitle;

    @FXML
    private TableColumn<?, ?> allTags;

    @FXML
    private TableColumn<?, ?> currentTags;

    @FXML
    private TextField chooseTag;

    @FXML
    private Button addTag;

    @FXML
    private Button deleteTag;

    @FXML
    public void initialize(){

    }

    @FXML
    public void onBack(ActionEvent actionEvent) throws IOException {
        switchScreen(pane, "/Views/EditMapScreen.fxml");
    }

    @FXML
    void AddTag(ActionEvent event) {

    }

    @FXML
    void AddTitle(ActionEvent event) {

    }

    @FXML
    void DeleteTag(ActionEvent event) {

    }

    @FXML
    void DeleteTitle(ActionEvent event) {

    }

    @FXML
    void addNewProf(ActionEvent event) {

    }


}
