package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Model.*;
import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;


import java.awt.event.MouseEvent;
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
    private TextField profName;

    @FXML
    private Button modifyName;

    @FXML
    private ListView<ProTitle> allTitleList;

    @FXML
    private ListView<ProTitle> curTitleList;

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


    private Professional selectedProf;



    @FXML
    public void initialize(){

        Directory dir = Directory.getInstance();

        //fill in alltags
        ObservableList<Tag> tagObjList = FXCollections.observableArrayList(dir.getTags());
        allTagsList.setItems(tagObjList);

        //fill in allProfs
        ObservableList<Professional> profObjList = FXCollections.observableArrayList(dir.getProfessionals());
        allProfList.setItems(profObjList);

        for(ProTitle t : dir.getTitles()){
            System.out.println(t.toString());
        }

        //fil in allTitles
        ObservableList<ProTitle> titleObjList = FXCollections.observableArrayList(dir.getTitles());
        allTitleList.setItems(titleObjList);



        //allProfs listens for selection
        allProfList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Professional>() {
            @Override
            public void changed(ObservableValue<? extends Professional> observable,
                                Professional oldValue, Professional newValue) {

                updateCurrentProfList(newValue);
            }


        });


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




    private void updateCurrentProfList(Professional p){
        selectedProf = p;
        curTitleList.setItems( FXCollections.observableArrayList(p.getTitles()));
        curTagsList.setItems(FXCollections.observableArrayList(p.getTags()));
        profName.setText(p.name);
    }





    /*
        selectedProf = allProfList.getSelectionModel().getSelectedItem();
        updateCurrentProfList(selectedProf);
        System.out.print("sdffdas");
        */

}
