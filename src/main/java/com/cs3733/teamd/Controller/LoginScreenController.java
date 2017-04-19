package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Main;
import com.cs3733.teamd.Model.Entities.Directory;
import com.cs3733.teamd.Model.Entities.User;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Anh Dao on 4/6/2017.
 */
public class LoginScreenController extends AbsController {

    public TextField username;
    public PasswordField password;
    public Button LoginButton;
    public Button BackButton;
    public AnchorPane MMGpane;
    public Label errorIndicator;
    public Label userText;
    public Label pwText;

    Directory dir = Directory.getInstance();

    //timeout
    Timer timer = new Timer();
    int counter = 0;
    private volatile boolean running = true;

    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            counter++;
            System.out.println("login counting: " + counter);
        }
    };

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (running) {
                try {

                    if (counter == 10) {
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
    public void initialize(){
        timer.scheduleAtFixedRate(timerTask, 30, 1000);
        timerThread.start();
        setText();
        //timer resets if mouse moved
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
        username.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.out.println("key pressed");
                counter = 0;
            }
        });
        password.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.out.println("pw pressed");
                counter = 0;
            }
        });


    }
    //Back button
    @FXML
    public void onBack(ActionEvent actionEvent) throws  IOException{
        timer.cancel();
        timer.purge();
        running = false;
        timerThread.interrupt();
        switchScreen(MMGpane, "/Views/UserScreen.fxml");
    }
    //Login button
    @FXML
    public void onLogin(ActionEvent actionEvent) throws IOException {
        timer.cancel();
        timer.purge();
        running = false;
        timerThread.interrupt();

       String user = username.getText();
       String pass = password.getText();



       User u = dir.loginUser(user,pass);

       if (u != null){
           System.out.println("Successful log in as admin.\n");
           switchScreen(MMGpane, "/Views/EditMapScreen.fxml");
      } else{
           errorIndicator.setText(Main.bundle.getString("InvalidloginCredentials"));
        //
       }

    }

    //Spanish translation
    @FXML
    public void setText() {
       // SearchButton.setText(Main.bundle.getString("search"));
        LoginButton.setText(Main.bundle.getString("login"));
     //   MenuButton.setText(Main.bundle.getString("menu"));
        BackButton.setText(Main.bundle.getString("back"));
       // menu.setText(Main.bundle.getString("login"));
        userText.setText(Main.bundle.getString("username"));
        pwText.setText(Main.bundle.getString("password"));

    }

}
