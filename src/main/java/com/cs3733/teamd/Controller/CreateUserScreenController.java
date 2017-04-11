package com.cs3733.teamd.Controller;

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
    private Text userText;

    @FXML
    private TextField newUsername;

    @FXML
    private Text pwText;

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
            errorIndicator.setText("No User Logged In...");
        } else if(!u.hasPermission(Permissions.CREATE_ADMIN)) {
            errorIndicator.setText("Insufficient Permissions");
            roleSelection.getValue();
        } else {
            String role = (String)roleSelection.getValue();
            if(role.length() <= 0) {
                errorIndicator.setText("Please Select a Role...");
            } else {
                int result = dir.createUser(newUsername.getText(), newPassword.getText());
                if(result <= 0) {
                    errorIndicator.setText("Name is already taken...");
                } else {
                    errorIndicator.setText("New User "+newUsername.getText()+" created.");
                    // Now try to assign a role
                    boolean roleResult = dir.addRoleToUser(result, role);
                    if(!roleResult) {
                        errorIndicator.setText("Error Adding Role to User...");
                    }
                }
            }
        }
    }
}
