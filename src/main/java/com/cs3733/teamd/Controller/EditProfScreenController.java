package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Model.Directory;
import com.cs3733.teamd.Model.Professional;
import com.cs3733.teamd.Model.Tag;
import com.cs3733.teamd.Model.Title;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;


import java.io.IOException;

/**
 * Created by Anh Dao on 4/6/2017.
 */
public class EditProfScreenController extends AbsController {


    public static ObservableList<Professional> drop = FXCollections.observableArrayList();

    @FXML
    private AnchorPane pane;

    @FXML
    private Button submitButton;

    @FXML
    private Button BackButton;

    @FXML
    private ListView<Professional> allProfList;

    @FXML
    private TextField searchProfessionalBar;

    @FXML
    private Button addNewProf;

    @FXML
    private Button deletePorf;

    @FXML
    private TextField ProfName;

    @FXML
    private Button modifyName;

    @FXML
    private ListView<Title> allTitleList;

    @FXML
    private ListView<Title> curTitleList;

    @FXML
    private TextField searchTitle;

    @FXML
    private Button addTitle;

    @FXML
    private Button deleteTitle;

    @FXML
    private ListView<Tag> allTagsList;

    @FXML
    private ListView<Tag> curTagsList;

    @FXML
    private TextField searchTitle1;

    @FXML
    private Button addTag;

    @FXML
    private Button deleteTag;

    @FXML
    public void initialize(){

        Directory dir = Directory.getInstance();

        ObservableList<Tag> tagObjList = FXCollections.observableArrayList(dir.getTags());
        allTagsList.setItems(tagObjList);

        ObservableList<Professional> profObjList = FXCollections.observableArrayList(dir.getProfessionals());
        allProfList.setItems(profObjList);


        ObservableList list = FXCollections.observableList(drop);
       // ProfessionalsList.setCellValueFactory(new PropertyValueFactory<>(acronyms));
       /// list.add(new Professional("Abb",12));
        ///ProfessionalTable.setItems(list);
        ///ProfessionalsList.setCellFactory(new PropertyValueFactory("name"));
       // ProfessionalTable.getColumns().setAll(ProfessionalsList);
      /*
        ProfessionalsList.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> p) {
                String[] x = p.getValue();
                if (x != null && x.length>0) {
                    return new SimpleStringProperty(x[0]);
                } else {
                    return new SimpleStringProperty("<no name>");
                }
            }
        });*/
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
    void DeleteTitle(ActionEvent event) {

    }

    @FXML
    void addNewProf(ActionEvent event) {

    }

    @FXML
    void deleteTag(ActionEvent event) {

    }





}
