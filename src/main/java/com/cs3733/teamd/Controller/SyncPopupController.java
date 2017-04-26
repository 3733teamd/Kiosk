package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Database.DBHandler;
import com.cs3733.teamd.Model.ApplicationConfiguration;
import com.cs3733.teamd.Model.Entities.Directory;
import com.cs3733.teamd.Model.Entities.DirectoryInterface;
import com.cs3733.teamd.Model.Hospital;
import com.cs3733.teamd.Model.HospitalLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * Created by sdmichelini on 4/26/17.
 */
public class SyncPopupController {
    @FXML
    public Button exitAbout;

    @FXML
    private Text hospitalName;

    @FXML
    private Text dbVersionText;

    @FXML
    private AnchorPane MMGpane;

    private Hospital h;

    @FXML
    public void initialize() {
        h = ApplicationConfiguration.getInstance().getHospital();
        this.hospitalName.setText(h.getName());
        this.dbVersionText.setText(h.getDbVersion().toString());
    }

    @FXML
    void saveDatabaseToFile(ActionEvent event) {
        DBHandler database = ApplicationConfiguration.getInstance().getDatabase();
        String filename = getClass().getClassLoader().getResource("hospitals/hospitals.json").getFile();
        System.out.println(filename);
        File f2 = new File(filename);
        h.setDbVersion(h.getDbVersion() + 1);
        database.dumpDatabaseToSqlStatements(
                f2.getAbsolutePath()
                        .replaceFirst(
                                "hospitals.json",
                                h.getHospitalId()+"/dump."+h.getDbVersion()+".sql")
        );

        HospitalLoader.getInstance().saveHospital(h);
        System.out.println("Dumped");
    }


    @FXML
    public void leaveAbout(ActionEvent actionEvent) throws IOException {
        Stage closeStage= (Stage) exitAbout.getScene().getWindow();
        closeStage.close();
        //System.out.println("should be closing!!!");
    }
}
