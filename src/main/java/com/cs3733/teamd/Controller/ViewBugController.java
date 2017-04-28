package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Model.Entities.Directory;
import com.cs3733.teamd.Model.Entities.DirectoryInterface;
import com.cs3733.teamd.Model.Entities.Report;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.List;

/**
 * Created by benjamin on 4/18/2017.
 */
public class ViewBugController extends AbsController {
    @FXML
    public Button exitBugs;
    @FXML
    public AnchorPane MMGpane;
    public Button bugDeleteBtn;
    public TableView table;
    public TableColumn numberColumn;
    public TableColumn tagColumn;
    public TableColumn commentColumn;
    public TableColumn statusColumn;
    public Button closeReportBtn;
    @FXML
    private ListView<String> listView;

    private DirectoryInterface dir;

    @FXML
    public void initialize() {
        dir = Directory.getInstance();
        //numberColumn.setCellValueFactory(new PropertyValueFactory<Report, String>("ID"));
        tagColumn.setCellValueFactory(new PropertyValueFactory<Report, String>("tagText"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<Report, String>("commentText"));
        //statusColumn.setCellValueFactory(new PropertyValueFactory<Report, String>("status"));
        updateBugReportTable();
    }

    @FXML
    public void leaveBugs(ActionEvent actionEvent) {
        /*Stage closeStage= (Stage) exitBugs.getScene().getWindow();
        closeStage.close();*/
        try {
            switchScreen(MMGpane, "/Views/AdminMenuScreen.fxml");
        }catch (Exception e){
            System.out.println(e);
        }
    }


    public void deleteBug(ActionEvent actionEvent) {
        Report selectedList;
        selectedList=(Report)table.getSelectionModel().getSelectedItem();
        dir.deleteBugReport(selectedList);
        updateBugReportTable();
    }

    public void updateBugReportTable(){
        ObservableList<Report> data = FXCollections.observableArrayList(dir.getBugReports());
        table.setItems(data);
    }

    public void closeReport(ActionEvent actionEvent) {
    }

//    public void closeReport(ActionEvent actionEvent) {
//        Report selectedReport;
//        selectedReport = (Report)table.getSelectionModel().getSelectedItem();
//        System.out.println(selectedReport.tagText);
//        System.out.println(selectedReport.commentText);
//        System.out.println(selectedReport.status);
//        boolean result = dir.setBugClosed(selectedReport);
//        System.out.println("setBug Closed finished");
//        updateBugReportTable();
//    }
}
