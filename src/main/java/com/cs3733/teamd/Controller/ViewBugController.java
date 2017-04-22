package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Model.Entities.Directory;
import com.cs3733.teamd.Model.Entities.DirectoryInterface;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.List;

/**
 * Created by benjamin on 4/18/2017.
 */
public class ViewBugController {
    @FXML
    public Button exitBugs;
    @FXML
    public AnchorPane MMGpane;
    @FXML
    private ListView<String> listView;

    private DirectoryInterface dir;

    @FXML
    public void initialize() {
        dir = Directory.getInstance();
        List<String> bugReports = dir.getBugReports();
        System.out.println(bugReports);
        listView.setItems(FXCollections.observableList(bugReports));
    }

    @FXML
    public void leaveBugs(ActionEvent actionEvent) {
        Stage closeStage= (Stage) exitBugs.getScene().getWindow();
        closeStage.close();
    }
}
