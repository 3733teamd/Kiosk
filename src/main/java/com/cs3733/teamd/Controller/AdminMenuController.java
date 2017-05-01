package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Main;
import com.cs3733.teamd.Model.ApplicationConfiguration;
import com.cs3733.teamd.Model.Entities.Directory;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Ryan on 4/27/2017.
 */
public class AdminMenuController extends AbsController{
    Directory dir = Directory.getInstance();

    public JFXButton toEditMap;
    public JFXButton toEditTag;
    public JFXButton toEditProf;
    public JFXButton toEnhancedMap;
    public JFXButton toAddUser;
    public JFXButton toBugView;
    public AnchorPane MMGpane;
    public TextField timeOutTextBox;
    @FXML
    private ComboBox<String> LanguageButton;

    final String[] languages = new String[] { "English", "\u0045\u0073\u0070\u0061\u00f1\u006f\u006c", "\u0046\u0072\u0061\u006e\u00e7\u0061\u0069\u0073", "\u4e2d\u6587", "\u0050\u006f\u0072\u0074\u0075\u0067\u0075\u00ea\u0073" };
    public static ObservableList<String> languageDropDown = FXCollections.observableArrayList();


    //timeout
    Timer timer = new Timer();
    int counter = 0;
    private volatile boolean running = true;

    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {

            counter++;
            //System.out.println("login " + counter);
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
                MementoController.toOriginalScreen(MMGpane);
                MementoController.originator.getStateFromMemento(MementoController.careTaker.get(0));
                switchScreen(MMGpane, MementoController.originator.getState());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    };

    @FXML
    public void initialize() {
        timer.scheduleAtFixedRate(timerTask, 30, 1000);
        timerThread.start();
        if(languageDropDown.size()==0){
            languageDropDown.addAll(languages);
        }
        LanguageButton.setItems(languageDropDown);
        LanguageButton.getSelectionModel().select(Main.bundle.getString("Language"));
        if(!ApplicationConfiguration.getInstance().getHospital().hasMultipleLanguages()) {
            LanguageButton.setDisable(true);
        } else {
            LanguageButton.setDisable(false);
        }

        //timer up counter
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
        LanguageButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                counter = 0;
            }
        });
        timeOutTextBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                counter = 0;
            }
        });
        timeOutTextBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                counter = 0;
            }
        });
    }

    public void goToEditMap(ActionEvent actionEvent) {
        timer.cancel();
        timer.purge();
        running = false;
        timerThread.interrupt();
        try {
            super.switchScreen(MMGpane, "/Views/EditMapScreen.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToEditTag(ActionEvent actionEvent) {
        timer.cancel();
        timer.purge();
        running = false;
        timerThread.interrupt();
        try {
            super.switchScreen(MMGpane, "/Views/EditTagScreen.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToEditProf(ActionEvent actionEvent) {
        timer.cancel();
        timer.purge();
        running = false;
        timerThread.interrupt();
        try {
            super.switchScreen(MMGpane, "/Views/EditProfScreen.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToEnhancedMap(ActionEvent actionEvent) {
        timer.cancel();
        timer.purge();
        running = false;
        timerThread.interrupt();
        try {
            super.switchScreen(MMGpane, "/Views/UserScreen.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToAddUser(ActionEvent actionEvent) {
        timer.cancel();
        timer.purge();
        running = false;
        timerThread.interrupt();
        try {
            super.switchScreen(MMGpane, "/Views/CreateUserScreen.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToBugView(ActionEvent actionEvent) {
        timer.cancel();
        timer.purge();
        running = false;
        timerThread.interrupt();
        try {
            super.popupScreen(MMGpane, "/Views/ViewBugScreen.fxml", "Bug Reports");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    }

    @FXML
    void onSyncPopup(ActionEvent event) {
        timer.cancel();
        timer.purge();
        running = false;
        timerThread.interrupt();
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