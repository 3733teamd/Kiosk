package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Database.DBHandler;
import com.cs3733.teamd.Model.ApplicationConfiguration;
import com.cs3733.teamd.Model.Entities.Directory;
import com.cs3733.teamd.Model.Entities.DirectoryInterface;
import com.cs3733.teamd.Model.Hospital;
import com.cs3733.teamd.Model.HospitalLoader;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableIntegerArray;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sdmichelini on 4/26/17.
 */
public class SyncPopupController implements IOperationListener {
    @FXML
    public Button exitAbout;

    @FXML
    private Text hospitalName;

    @FXML
    private Text dbVersionText;

    @FXML
    private ComboBox<Integer> dbVersionBox;

    @FXML
    private AnchorPane MMGpane;

    @FXML
    private ComboBox<String> hospitalVersionBox;

    private Hospital h;

    @FXML
    public void initialize() {
        synchronizeScreenToCurrentState();
    }

    private void synchronizeScreenToCurrentState() {
        h = ApplicationConfiguration.getInstance().getHospital();
        this.hospitalName.setText(h.getName());
        this.dbVersionText.setText(h.getDbVersion().toString());
        List<Integer> list = new ArrayList<Integer>();
        for(Integer version: h.getDbVersions()) {
            list.add(version);
        }
        this.dbVersionBox.setItems(FXCollections.observableList(list));
        this.dbVersionBox.setValue(h.getDbVersion());

        this.hospitalVersionBox.setItems(FXCollections.observableList(HospitalLoader.getInstance().loadHospitals()));
        this.hospitalVersionBox.setValue(h.getHospitalId());
    }

    @FXML
    void saveAsNewVersion(ActionEvent event) {
        h.setDbVersion(h.getDbVersion() + 1);
        saveCurrentVersion(event);
    }

    @FXML
    void saveCurrentVersion(ActionEvent event) {
        DBHandler database = ApplicationConfiguration.getInstance().getDatabase();
        String filename = getClass().getClassLoader().getResource("hospitals/hospitals.json").getFile();
        System.out.println(filename);
        File f2 = new File(filename);
        database.dumpDatabaseToSqlStatements(
                f2.getAbsolutePath()
                        .replaceFirst(
                                "hospitals.json",
                                h.getHospitalId()+"/dump."+h.getDbVersion()+".sql")
        );

        HospitalLoader.getInstance().saveHospital(h);
        System.out.println("Dumped");
        synchronizeScreenToCurrentState();
    }



    @FXML
    void onLoadVersion(ActionEvent event) {
        try {
            Integer version = this.dbVersionBox.getValue();
            this.dbVersionText.setText("Loading...");
            System.out.println(version);
            h.setDbVersion(version);
            IOperationListener listener = this;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    HospitalLoader.getInstance().saveHospital(h);
                    Directory dir = Directory.getInstance();
                    boolean result = dir.changeToNewFile(h.getDbPath());
                    if(result) {
                        System.out.println("Success");
                        listener.onOperationSuccess(version);
                    } else {
                        System.err.println("Failure");
                        listener.onOperationFailure(version);
                    }
                }
            }).start();


        } catch(NumberFormatException e) {
            this.dbVersionText.setText("Load Version Error.");
            e.printStackTrace();
        }

    }

    @FXML
    void onLoadHospital(ActionEvent event) {

    }

    @FXML
    public void leaveAbout(ActionEvent actionEvent) throws IOException {
        Stage closeStage= (Stage) exitAbout.getScene().getWindow();
        closeStage.close();
        //System.out.println("should be closing!!!");
    }

    @Override
    public void onOperationSuccess(Object o) {
        Platform.runLater(new Runnable() {
            @Override public void run() {
                synchronizeScreenToCurrentState();
            }
        });

    }

    @Override
    public void onOperationFailure(Object o) {
        Platform.runLater(new Runnable() {
            @Override public void run() {
                dbVersionText.setText("Directory Reload Error.");
            }
        });
    }
}
