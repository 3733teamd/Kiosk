package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Model.*;
import com.cs3733.teamd.Model.Entities.Directory;
import com.cs3733.teamd.Model.Entities.ProTitle;
import com.cs3733.teamd.Model.Entities.Professional;
import com.cs3733.teamd.Model.Entities.Tag;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import org.controlsfx.control.textfield.TextFields;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

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
    private Button deleteProf;

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

    @FXML
    private TextField searchTagList;

    private Professional selectedProf;
    private Tag selectedTag;
    private Tag selectedCurTag;
    private ProTitle selectedTitle;
    private ProTitle selectedCurTitle;

    Directory dir = Directory.getInstance();
    List<Professional> allTheProfs= dir.getProfessionals();
    List<String> allProfNames = new ArrayList<String>();

    List<Professional> filtered = new ArrayList<Professional>();
    List<Tag> filteredTag = new ArrayList<>();

    ObservableList<Professional> searchResults = FXCollections.observableArrayList();
    ObservableList<Tag> searchResultsTag = FXCollections.observableArrayList();

    //timeout
    Timer timer = new Timer();
    int counter = 0;
    private volatile boolean running = true;

    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            counter++;
            System.out.println("editProf counting: "+counter);
        }
    };

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (running) {
                try {

                    if (counter == MementoController.timeoutTime) {
                        running = false;
                        timer.cancel();
                        timerTask.cancel();
                        Platform.runLater(resetKiosk);
                        break;
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException exception) {
                    timer.cancel();
                    timerTask.cancel();
                    running = false;
                    break;
                }
            }
        }
    };
    Thread timerThread = new Thread(runnable);
    Runnable resetKiosk = new Runnable() {
        @Override
        public void run() {
            timer.cancel();
            timer.purge();
            running = false;
            timerThread.interrupt();

            //logout user
            dir.logoutUser();
            try {
                MementoController.toOriginalScreen(pane);
                MementoController.originator.getStateFromMemento(MementoController.careTaker.get(0));
                switchScreen(pane, MementoController.originator.getState());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    };


    @FXML
    public void initialize(){
        timer.scheduleAtFixedRate(timerTask, 30, 1000);
        timerThread.start();

        //fill in alltags
        //setAllTagsList();
        //fill in allProfs
        setAllProfList();
        //fil in allTitles
        //setAllTitleList();
        //allProfs listens for selection
        createAllProfListListener();
        //allTags listesns for selection
        createAllTagListListener();
        //you know the drill
        createCurTagListListener();

        createAllTitleListListener();
        createCurTitleListListener();

        //disable buttons
        deleteTitle.setOpacity(.5);
        deleteTag.setOpacity(.5);
        addTitle.setOpacity(.5);
        addTag.setOpacity(.5);
        deleteProf.setOpacity(.5);
        modifyName.setOpacity(.5);
        modifyName.setDisable(true);
        deleteTitle.setDisable(true);
        deleteTag.setDisable(true);
        addTitle.setDisable(true);
        addTag.setDisable(true);
        deleteProf.setDisable(true);


        ObservableList list = FXCollections.observableList(drop);

        setText();
        for (int i = 0 ; i<allTheProfs.size(); i++) {
            allProfNames.add(allTheProfs.get(i).getName());
        }
       // TextFields.bindAutoCompletion(searchProfessionalBar, allProfNames);

        //timer resets if mouse moved
        //timer.scheduleAtFixedRate(timerTask, 30, 1000);
        //timerThread.start();
        pane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                counter = 0;
                //System.out.println("counter resets");
            }
        });
        pane.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                counter = 0;
            }
        });
        pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                counter = 0;
            }
        });
        pane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                counter = 0;
            }
        });
        allProfList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                counter = 0;
            }
        });

        //make list view dynamic
        searchProfessionalBar.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                selectedProf = null;
                clearAllFields();
                //String text = searchTagBar.getText();

                displayResult(searchProfessionalBar.getText() + event.getText());
            }
        });
        searchTagList.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                //String text = searchTagBar.getText();

                displayResultAllTag(searchTagList.getText() + event.getText());
            }
        });
    }

    private void createCurTagListListener(){

        curTagsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tag>() {
            @Override
            public void changed(ObservableValue<? extends Tag> observable,
                                Tag oldValue, Tag newValue) {

                selectedCurTag = newValue;


            }
        });

    }

    private void createCurTitleListListener(){

        curTitleList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ProTitle>() {
            @Override
            public void changed(ObservableValue<? extends ProTitle> observable,
                                ProTitle oldValue, ProTitle newValue) {

                selectedCurTitle = newValue;
            }
        });

    }
    private void createAllTitleListListener(){

        allTitleList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ProTitle>() {
            @Override
            public void changed(ObservableValue<? extends ProTitle> observable,
                                ProTitle oldValue, ProTitle newValue) {
                selectedTitle = newValue;

            }
        });

    }



    private void createAllProfListListener(){
        allProfList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Professional>() {
            @Override
            public void changed(ObservableValue<? extends Professional> observable,
                                Professional oldValue, Professional newValue) {


                updateCurrentProfList(newValue);
                setAllTagsList();
                setAllTitleList();
                //change opacity
                deleteTitle.setOpacity(1.0);
                deleteTag.setOpacity(1.0);
                addTitle.setOpacity(1.0);
                addTag.setOpacity(1.0);
                deleteProf.setOpacity(1.0);
                modifyName.setOpacity(1);
                modifyName.setDisable(false);
                deleteTitle.setDisable(false);
                deleteTag.setDisable(false);
                addTitle.setDisable(false);
                addTag.setDisable(false);
                deleteProf.setDisable(false);

            }

        });
    }

     private void createAllTagListListener(){
        allTagsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tag>() {
            @Override
            public void changed(ObservableValue<? extends Tag> observable,
                                Tag oldValue, Tag newValue) {

                updateCurrentTagList(newValue);
            }


        });
    }

    public void displayResult(String value){

        for (Professional d: dir.getProfessionals()){
            filtered = dir.getProfessionals().stream().filter((p) -> p.getName().toLowerCase().contains(value.toLowerCase())).collect(Collectors.toList());
        }

        searchResults.setAll(filtered);

        allProfList.setItems(searchResults);
    }

    @FXML
    public void displayResultAllTag(String value){

        for (Tag d: dir.getTags()){
            filteredTag = dir.getTags().stream().filter((p) -> p.getTagName().toLowerCase().contains(value.toLowerCase())).collect(Collectors.toList());
        }

        searchResultsTag.setAll(filteredTag);

        allTagsList.setItems(searchResultsTag);
    }


    private void setAllTagsList(){
        //ObservableList<Tag> tagObjList = FXCollections.observableArrayList(dir.getTags());
        allTagsList.setItems(FXCollections.observableArrayList(dir.getTags()));
    }

    private void setAllProfList(){
        //ObservableList<Professional> profObjList = ;
        allProfList.setItems(FXCollections.observableArrayList(dir.getProfessionals()));
    }

    private void setAllTitleList(){
        //ObservableList<ProTitle> titleObjList = ;
        allTitleList.setItems(FXCollections.observableArrayList(dir.getTitles()));
    }


    @FXML
    public void onBack(ActionEvent actionEvent) throws IOException {
        timer.cancel();
        timer.purge();
        running = false;
        timerThread.interrupt();
        switchScreen(pane, "/Views/AdminMenuScreen.fxml");
    }

    @FXML
    void addTag(ActionEvent event) {
        if(selectedTag != null && selectedProf != null) {
            dir.addTagToProfessional(selectedProf,selectedTag);
            curTagsList.setItems(FXCollections.observableArrayList(selectedProf.getTags()));
            //curTagsList.refresh();
        }
    }

    @FXML
    void addTitle(ActionEvent event) {
        if(selectedProf != null && selectedTitle != null){
            dir.addTitleToProfessional(selectedProf,selectedTitle);
            dir.updateProfessional(selectedProf);
            curTitleList.setItems(FXCollections.observableArrayList( selectedProf.getTitles()));
            curTitleList.refresh();
        }
    }

    @FXML
    void deleteTitle(ActionEvent event) {
        if(selectedProf != null && selectedCurTitle != null){
            dir.removeTitleFromProfessional(selectedProf,selectedCurTitle);

            curTitleList.setItems(FXCollections.observableArrayList( selectedProf.getTitles()));
            curTitleList.refresh();
        }
    }

    @FXML
    void addNewProf(ActionEvent event) {
        String noSpace = searchProfessionalBar.getText().replaceAll("\\s","");
        if(noSpace==null ||noSpace==""||noSpace.length()<=1){

        }
        else {
            System.out.println("print" + searchProfessionalBar.getText() + "print");
            dir.saveProfessional(searchProfessionalBar.getText());
            searchProfessionalBar.clear();
            allProfList.setItems(FXCollections.observableArrayList(dir.getProfessionals()));
            allProfList.refresh();
            searchProfessionalBar.clear();

        }
        searchProfessionalBar.setText("");
    }

    @FXML
    void deleteTag(ActionEvent event) {
        if(selectedCurTag != null){
            selectedProf.getTags().remove(selectedCurTag);
            dir.removeTagFromProfessional(selectedProf,selectedCurTag);
            dir.updateProfessional(selectedProf);
            curTagsList.setItems(FXCollections.observableArrayList(selectedProf.getTags()));
        }
    }

    @FXML
    void deleteProf(ActionEvent event) {
        if(selectedProf!=null){
            System.out.println(dir.removeProfessional(selectedProf));
            System.out.println(dir.removeProfessional(selectedProf));
            setAllProfList();
            allProfList.refresh();
        }
    }

    @FXML
    void modifyName(ActionEvent event) {
        String noSpace = searchProfessionalBar.getText().replaceAll("\\s","");
        if(noSpace == null||noSpace==""||noSpace.length()<=1) {
            searchProfessionalBar.setText("");
        }
        else{
            selectedProf.name = (profName.getText());
            profName.clear();
            dir.updateProfessional(selectedProf);
            allProfList.refresh();
            //setAllProfList();
            profName.clear();
        }
    }





    private void updateCurrentProfList(Professional p){

        if(p != null) {
            selectedProf = p;
            curTitleList.setItems(FXCollections.observableArrayList(p.getTitles()));
            curTagsList.setItems(FXCollections.observableArrayList(p.getTags()));
            profName.setPromptText(p.name);
        }
    }

    private void updateCurrentTagList(Tag t){
        if(t != null) {
            selectedTag = t;
        }
    }





    /*
        selectedProf = allProfList.getSelectionModel().getSelectedItem();
        updateCurrentProfList(selectedProf);
        System.out.print("sdffdas");
        */

    private void setText(){
        if(ApplicationConfiguration.getInstance().getCurrentLanguage()
                == ApplicationConfiguration.Language.SPANISH){
            addNewProf.setFont(Font.font("System",14));
            deleteProf.setFont(Font.font("System",14));
        }
        else{
            addNewProf.setFont(Font.font("System",20));
            deleteProf.setFont(Font.font("System",20));
        }
    }

    private void clearAllFields(){
        allTagsList.setItems(FXCollections.observableArrayList());
        allTagsList.refresh();
        allTitleList.setItems((FXCollections.observableArrayList()));
        allTitleList.refresh();
        profName.setPromptText("");
    }


}
