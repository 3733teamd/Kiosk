package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Main;
import com.cs3733.teamd.Model.Entities.Directory;
import com.cs3733.teamd.Model.Permissions;
import com.cs3733.teamd.Model.Entities.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Anh Dao on 4/11/2017.
 */
public class CreateUserScreenController extends AbsController {

    @FXML
    private Label errorIndicator;

    @FXML
    private Label userText;

    @FXML
    private TextField newUsername;

    @FXML
    private Label pwText;


    @FXML
    private PasswordField newPassword;

    @FXML
    private Button BackButton;

    @FXML
    private AnchorPane MMGpane;

    @FXML
    private Button CreateUserButton;

    @FXML
    private ChoiceBox roleSelection;



    static ObservableList<String> rolesList =
            FXCollections.observableArrayList( "admin", "prof" );

    //public Label errorBox;
    Directory dir = Directory.getInstance();
    //timeout
    Timer timer = new Timer();
    int counter = 0;
    private volatile boolean running = true;

    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            counter++;
            System.out.println("createnewU counting: " + counter);
        }
    };

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (running) {
                try {

                    if (counter == timeoutTime) {
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
                switchScreen(MMGpane, "/Views/UserScreen.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    };

    @FXML
    public void initialize() {
        timer.scheduleAtFixedRate(timerTask, 30, 1000);
        timerThread.start();
        roleSelection.setItems(rolesList);

        MMGpane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {counter = 0;
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
        newUsername.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.out.println("key pressed");
                counter = 0;
            }
        });
        newPassword.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.out.println("pw pressed");
                counter = 0;
            }
        });
    }
    //Back button
    @FXML
    public void onBack(ActionEvent actionEvent) throws IOException {

        timer.cancel();
        timer.purge();
        running = false;
        timerThread.interrupt();
        switchScreen(MMGpane, "/Views/EditMapScreen.fxml");

    }
    @FXML
    public void onCreateUser(ActionEvent actionEvent) throws IOException {
        Directory dir = Directory.getInstance();
        User u = dir.getCurrentUser();
        if(u == null) {
            errorIndicator.setText(Main.bundle.getString("NoUserLoggedIn"));
        } else if(!u.hasPermission(Permissions.CREATE_ADMIN)) {
            errorIndicator.setText(Main.bundle.getString("InsufficientPermissions"));
            roleSelection.getValue();
        } else {
            String role = (String)roleSelection.getValue();
            if(role.length() == 0 ||role =="null") {
                errorIndicator.setText(Main.bundle.getString("PleaseSelectaRole"));
            } else {
                int result = dir.createUser(newUsername.getText(), newPassword.getText());
                if(result <= 0) {
                    errorIndicator.setText(Main.bundle.getString("Nameisalreadytaken"));
                } else {
                    errorIndicator.setText(Main.bundle.getString("NewUser") +" "+newUsername.getText()+" "+Main.bundle.getString("created"));
                    // Now try to assign a role
                    boolean roleResult = dir.addRoleToUser(result, role);
                    if(!roleResult) {
                        errorIndicator.setText(Main.bundle.getString("ErrorAddingRoletoUser"));
                    }
                }
            }
        }
    }
}
