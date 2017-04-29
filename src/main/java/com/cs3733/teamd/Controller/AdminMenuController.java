package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Main;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * Created by Ryan on 4/27/2017.
 */
public class AdminMenuController extends AbsController{

    public JFXButton toEditMap;
    public JFXButton toEditTag;
    public JFXButton toEditProf;
    public JFXButton toEnhancedMap;
    public JFXButton toAddUser;
    public JFXButton toBugView;
    public AnchorPane MMGpane;
    @FXML
    private ComboBox<String> LanguageButton;

    final String[] languages = new String[] { "English", "\u0045\u0073\u0070\u0061\u00f1\u006f\u006c", "\u0046\u0072\u0061\u006e\u00e7\u0061\u0069\u0073", "\u4e2d\u6587", "\u0050\u006f\u0072\u0074\u0075\u0067\u0075\u00ea\u0073" };
    public static ObservableList<String> languageDropDown = FXCollections.observableArrayList();


    @FXML
    public void initialize() {

        if(languageDropDown.size()==0){
            languageDropDown.addAll(languages);
        }
        LanguageButton.setItems(languageDropDown);
        LanguageButton.getSelectionModel().select(Main.bundle.getString("Language"));

    }

    public void goToEditMap(ActionEvent actionEvent) {
        try {
            super.switchScreen(MMGpane, "/Views/EditMapScreen.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToEditTag(ActionEvent actionEvent) {
        try {
            super.switchScreen(MMGpane, "/Views/EditTagScreen.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToEditProf(ActionEvent actionEvent) {
        try {
            super.switchScreen(MMGpane, "/Views/EditProfScreen.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToEnhancedMap(ActionEvent actionEvent) {
        try {
            super.switchScreen(MMGpane, "/Views/UserScreen.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToAddUser(ActionEvent actionEvent) {
        try {
            super.switchScreen(MMGpane, "/Views/CreateUserScreen.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToBugView(ActionEvent actionEvent) {
        try {
            super.popupScreen(MMGpane, "/Views/ViewBugScreen.fxml", "Bug Reports");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void setLanguageListener() throws IOException {
        System.out.println("val"+LanguageButton.getSelectionModel().getSelectedItem());
        super.switchLanguage(LanguageButton.getSelectionModel().getSelectedItem());
        switchScreen(MMGpane,"/Views/AdminMenuScreen.fxml");
        //setSpanishText();

    }

    @FXML
    void onSyncPopup(ActionEvent event) {
        try {
            super.popupScreen(MMGpane, "/Views/SyncPopupScreen.fxml", "Sync");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logout(ActionEvent actionEvent) {
        try {
            super.switchScreen(MMGpane, "/Views/UserScreen.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}