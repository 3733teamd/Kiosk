package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Main;
import com.cs3733.teamd.Model.Directory;
import com.cs3733.teamd.Model.Permissions;
import com.cs3733.teamd.Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;

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

    @FXML
    public void initialize() {
        roleSelection.setItems(rolesList);
    }
    //Back button
    @FXML
    public void onBack(ActionEvent actionEvent) throws IOException {
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
