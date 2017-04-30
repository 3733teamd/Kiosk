package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Model.Entities.Directory;
import com.cs3733.teamd.Model.Entities.Professional;
import com.cs3733.teamd.Model.Entities.Tag;
import com.cs3733.teamd.Model.Entities.VisitingBlock;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import org.controlsfx.control.textfield.TextFields;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

/**
 * Created by Anh Dao on 4/6/2017.
 */
public class EditTagScreenController extends AbsController {
    public CheckBox selectConnectable;
    public CheckBox restrictedButton;
    public SplitPane tagPropertyPane;
    public StackPane bottomTagPropertys;
    public StackPane topTagProperties;
    Directory dir = Directory.getInstance();
    @FXML
    public TextArea tagTextArea;
    public TextField searchTagBar;
    public Button BackButton;
    public AnchorPane MMGpane;
    @FXML
    public TextField tagNameTxt;
    @FXML
    private Button addProf;
    @FXML
    private Button newTagNameBtn;
    @FXML
    private Button deleteProf;
    @FXML
    private TextField profSearchField;

    List<Professional> filtered = new ArrayList<Professional>();
    List<Tag> filteredTag = new ArrayList<>();

    ObservableList<Professional> searchResults = FXCollections.observableArrayList();
    ObservableList<Tag> searchResultsTag = FXCollections.observableArrayList();


    private Tag chosenTag = null;

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
               /* originator.getStateFromMemento(careTaker.get(0));
                switchScreen(MMGpane, originator.getState());*/
                MementoController.toOriginalScreen(MMGpane);
                MementoController.originator.getStateFromMemento(MementoController.careTaker.get(0));
                switchScreen(MMGpane, MementoController.originator.getState());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    };

    //Back button
    @FXML
    public void onBack(ActionEvent actionEvent) throws IOException {
        switchScreen(MMGpane, "/Views/AdminMenuScreen.fxml");
    }
    @FXML
    public void initialize(){
        topTagProperties.maxHeightProperty().bind(tagPropertyPane.heightProperty().multiply(0));

        //make some buttons opaque
        addNewTagBtn.setOpacity(.5);
        deleteTagBtn.setOpacity(.5);
        addProf.setOpacity(.5);
        deleteProf.setOpacity(.5);
        newTagNameBtn.setOpacity(.5);
        addVisitHours.setOpacity(.5);
        removeVisitBlockButton.setOpacity(.5);
        //disable
        addNewTagBtn.setDisable(true);
        deleteTagBtn.setDisable(true);
        removeVisitBlockButton.setDisable(true);
        addVisitHours.setDisable(true);
        selectConnectable.setDisable(true);
        restrictedButton.setDisable(true);
        addProf.setDisable(true);
        deleteProf.setDisable(true);
        newTagNameBtn.setDisable(true);
        //Get all tag names and list them in searchTagBar
        List<String> allTagNames = new ArrayList<String>();
        for (Tag t : dir.getTags()) {
            allTagNames.add(t.getTagName());
        }
        /// TextFields.bindAutoCompletion(searchTagBar, allTagNames);//Not being used due to list view
        //List all tags in tagList
        tagList.setItems(FXCollections.observableArrayList(dir.getTags()));
        //ObservableList<String> names = FXCollections.observableArrayList((List)dir.getProfessionals());
        //System.out.println(names);
        //allProffessionals.setItems(names);


        tagList.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tag>() {
                    public void changed(ObservableValue<? extends Tag> ov,
                                        Tag old_val, Tag new_val) {
                        selectedTag = new_val;
                        if(selectedTag!=null) {
                            currentProfessionals.setItems(FXCollections.observableArrayList(selectedTag.getProfs()));
                            currentProfessionals.refresh();

                            allProffessionals.setItems(FXCollections.observableArrayList(dir.getProfessionals()));
                            tagNameTxt.clear();
                            tagNameTxt.setPromptText(selectedTag.toString());
                            visitingHourList.setItems(FXCollections.observableArrayList(selectedTag.getVisitingBlockObjs()));
                            visitingHourList.refresh();
                            allProffessionals.setItems(FXCollections.observableArrayList(dir.getProfessionals()));
                            addVisitHours.setOpacity(1.0);
                            addProf.setOpacity(1.0);
                            deleteProf.setOpacity(1.0);
                            deleteTagBtn.setOpacity(1);
                            //newTagNameBtn.setOpacity(1.0);
                            addVisitHours.setOpacity(1);
                            removeVisitBlockButton.setOpacity(1);
                            removeVisitBlockButton.setDisable(false);
                            addVisitHours.setDisable(false);
                            selectConnectable.setSelected(selectedTag.isConnectable());
                            selectConnectable.setDisable(false);
                            restrictedButton.setSelected(selectedTag.isRestricted());
                            restrictedButton.setDisable(false);
                            addProf.setDisable(false);
                            deleteProf.setDisable(false);
                            //newTagNameBtn.setDisable(false);
                            addVisitHours.setDisable(false);
                            deleteTagBtn.setDisable(false);
                            setTagPropertyButtons();

                        }else{
                            addProf.setOpacity(.5);
                            deleteProf.setOpacity(.5);
                            newTagNameBtn.setOpacity(.5);
                            addVisitHours.setOpacity(.5);
                            removeVisitBlockButton.setOpacity(.5);
                            //disable buttons
                            selectConnectable.setDisable(true);
                            restrictedButton.setDisable(true);
                            addProf.setDisable(true);
                            deleteProf.setDisable(true);
                            newTagNameBtn.setDisable(true);
                            addVisitHours.setDisable(true);
                            removeVisitBlockButton.setDisable(true);
                            tagNameTxt.clear();
                            tagNameTxt.setPromptText("");
                            clearResponsiveFields();
                        }


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

        visitingHourList.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<VisitingBlock>() {
                    public void changed(ObservableValue<? extends VisitingBlock> ov,
                                        VisitingBlock old_val, VisitingBlock new_val) {
                        selectedVB = new_val;

                    }
                });

        profSearchField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                //String text = searchTagBar.getText();

                displayResult(profSearchField.getText() + event.getText());
            }
        });
        searchTagBar.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                //String text = searchTagBar.getText();
                //make some buttons opaque
                //clearResponsiveFields();
                selectedTag = null;
                addProf.setOpacity(.5);
                deleteProf.setOpacity(.5);

                //disable buttons
                selectConnectable.setDisable(true);
                addProf.setDisable(true);
                deleteProf.setDisable(true);


                addNewTagBtn.setDisable(false);
                addNewTagBtn.setOpacity(1);
                displayResultAllTag(searchTagBar.getText() + event.getText());
            }
        });

        //Timeout up counter
        timer.scheduleAtFixedRate(timerTask, 30, 1000);
        timerThread.start();
        MMGpane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                counter = 0;
                System.out.println("counter resets");
            }
        });
        MMGpane.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                counter = 0;
            }
        });
        MMGpane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                counter = 0;
            }
        });
        MMGpane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                counter = 0;
            }
        });
        tagList.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                counter = 0;
            }
        });
        tagList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                counter = 0;
            }
        });
        tagNameTxt.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                setTagPropertyButtons();
            }
        });
        selectConnectable.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setTagPropertyButtons();
            }
        });
        restrictedButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setTagPropertyButtons();
            }
        });


    }

    @FXML
    void modifyTag(ActionEvent event) {
        String noSpace = tagNameTxt.getText().replaceAll("\\s","");
        if (noSpace == null ||noSpace==""|| noSpace.length()<1) {
            //searchTagBar.setText("");
        }
        else {
            selectedTag.setTagName(tagNameTxt.getText());
            dir.updateTag(selectedTag);
            tagList.refresh();
            tagNameTxt.setPromptText(tagNameTxt.getText());
            tagNameTxt.clear();
        }
        if(selectConnectable.isSelected() != selectedTag.isConnectable()) {
            selectedTag.setConnectable(selectConnectable.isSelected());
            dir.updateTag(selectedTag);
            tagList.refresh();
        }

        if(restrictedButton.isSelected() != selectedTag.isRestricted()){
            selectedTag.setRestricted(restrictedButton.isSelected());
            dir.updateTag(selectedTag);
            tagList.refresh();
        }
        setTagPropertyButtons();

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

        for (Professional d: dir.getProfessionals()){
            filtered = dir.getProfessionals().stream().filter((p) -> p.getName().toLowerCase().contains(value.toLowerCase())).collect(Collectors.toList());
        }

        searchResults.setAll(filtered);

        allProffessionals.setItems(searchResults);
    }

    @FXML
    public void displayResultAllTag(String value){

        for (Tag d: dir.getTags()){
            filteredTag = dir.getTags().stream().filter((p) -> p.getTagName().toLowerCase().contains(value.toLowerCase())).collect(Collectors.toList());
        }

        searchResultsTag.setAll(filteredTag);

        tagList.setItems(searchResultsTag);
    }

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
        String noSpace = searchTagBar.getText().replaceAll("\\s","");
        if (noSpace == null ||noSpace==""|| noSpace.length()<1) {

        }
        else {
            Tag t = dir.saveTag(searchTagBar.getText());
            tagList.setItems(FXCollections.observableArrayList(t));
            tagList.refresh();
        }
    }

    public TextField openTimeBox;
    public TextField closingTimeBox;
    public Button addVisitHours;
    public Button removeVisitBlockButton;

    ObservableList<VisitingBlock> visitingResults = FXCollections.observableArrayList();
    VisitingBlock selectedVB;
    public ListView visitingHourList;

    @FXML
    public void addToVisitingHourList(ActionEvent actionEvent) {
        String openingString = openTimeBox.getText();
        String closingString = closingTimeBox.getText();
        try {
            VisitingBlock b = new VisitingBlock(openingString, closingString);
            selectedTag.addBlock(b);
            if(dir.updateTag(selectedTag)) {
                visitingHourList.setItems(FXCollections.observableArrayList(selectedTag.getVisitingBlockObjs()));
                visitingHourList.refresh();

                openTimeBox.clear();
                closingTimeBox.clear();
            }



            System.out.println(b.toString());
        }catch(Exception e){
            System.out.println(e);
        }

    }
    @FXML
    public void removeVisitBlock(ActionEvent actionEvent) {
        selectedTag.removeBlock(selectedVB);
        visitingHourList.setItems(FXCollections.observableArrayList(selectedTag.getVisitingBlockObjs()));
        visitingHourList.refresh();
    }

    public void clearResponsiveFields(){
        //selectedTag=null;
        allProffessionals.setItems(FXCollections.observableArrayList());
        allProffessionals.refresh();
        currentProfessionals.setItems(FXCollections.observableArrayList());
        currentProfessionals.refresh();
        visitingHourList.setItems(FXCollections.observableArrayList());
        visitingHourList.refresh();
        openTimeBox.clear();
        closingTimeBox.clear();
        profSearchField.clear();
    }

    public void setTagPropertyButtons(){
        if(selectedTag.isConnectable() == selectConnectable.isSelected() &&
                selectedTag.isRestricted() == restrictedButton.isSelected() &&
                tagNameTxt.getText().equals("")){
            newTagNameBtn.setDisable(true);
            newTagNameBtn.setOpacity(.5);
        }else{
            newTagNameBtn.setDisable(false);
            newTagNameBtn.setOpacity(1);
        }
    }
}