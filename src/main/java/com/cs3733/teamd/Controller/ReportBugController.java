package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Main;
import com.cs3733.teamd.Model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;

/**
 * Created by benjamin on 4/18/2017.
 */
public class ReportBugController {


    public TextArea commentField;
    public Button cancelBug;
    public TextField destinationBugField;
    public Button submitBug;

    public void submitBugPress(ActionEvent actionEvent) {
        String nodeName = destinationBugField.getText();
        String comment = commentField.getText();



        closeReportBug();
    }

    public void cancelBugPress(ActionEvent actionEvent) {

        closeReportBug();
    }

    private void closeReportBug(){
        Stage closeStage= (Stage) cancelBug.getScene().getWindow();
        closeStage.close();
        System.out.println("should be closing!!!");
    }

}
