package com.cs3733.teamd;

import com.cs3733.teamd.Database.DBHandler;

import com.cs3733.teamd.Model.ApplicationConfiguration;
import com.cs3733.teamd.Model.Entities.*;
import com.cs3733.teamd.Model.Hospital;
import com.cs3733.teamd.Model.HospitalLoader;
import com.cs3733.teamd.Model.Originator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;


public class Main extends Application {


    //public static String Langugage="English";
    public static Stage window;
    public Boolean languageEnglish = true;
    public static Parent rootMain;
    
    public static Scene MainScene;//=new Scene(rootMain, 2124, 1010);

    public static String DestinationSelected = "";
    public static int currentFloor = 1;

    public static Locale local = new Locale("en", "US");
    public static Locale spanish = new Locale("es","SP");
    public static ResourceBundle bundle =ResourceBundle.getBundle("MyLabels", local);
    public static String backString;// = "/Views/Iteration1/GameMain.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception{

        window=primaryStage;
        rootMain = FXMLLoader.load(getClass().getResource("/Views/UserScreen.fxml"), bundle);


        MainScene=new Scene(rootMain);
        window.setMaximized(true);
        window.setTitle("Pathfinding Application");
        window.setScene(MainScene);
        window.show();
       // window.setFullScreen(true);
    }

    public static void main(String[] args) {


        Hospital h = HospitalLoader.getInstance().loadDefaultHospital();
        if(h == null) {
            System.err.println("HOSPITAL CAN NOT BE LOADED");
        } else {
            System.out.println(h.getDbVersions());
        }
        ApplicationConfiguration.getInstance().setHospital(h);

        DBHandler database;
        Directory dir = Directory.getInstance();

        try {
            database = new DBHandler();
        } catch (Exception e) {
            System.err.print("Could not construct DBHandler.\nExiting...\n");
            return;
        }

        try {
            if(h != null) {
                database.setupWithFileName(h.getDbPath(), ApplicationConfiguration.getInstance().getAuthFile());
            } else {
                System.err.println("Loading from default import");
                database.setup();
            }

        } catch (SQLException e) {
             if(e.getSQLState().equals("X0Y32")){ // Error code for TABLE EXISTS
                System.out.println("Skipping setup as tables are already made");
            } else {
                System.err.println("ERROR: creation of database failed");
                e.printStackTrace();
            }
        } catch (IOException e){
            System.err.println("ERROR: reading in data file");
            e.printStackTrace();
        }

//        try {
//            database.Setup();
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.err.print("Could not setup database.\nMaybe tables already created\n");
//        }

        ArrayList<Node> nodes;
        ArrayList<Tag> tags;
        ArrayList<Professional> professionals;
        List<ProTitle> titles;
        try {
            database.load();
            nodes = database.nodes;
            tags = database.tags;
            professionals = database.professionals;
            titles = database.titles;
            //database.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.print("Could not load data from database.\nExiting...\n");
            return;
        }


        //set up DIRECTORY
        dir.initialize(nodes,tags,professionals,titles,database);
        ApplicationConfiguration.getInstance().setDatabase(database);

        //Populate Search Menus
        /*try {
            MapMenuController mapMenuController = new MapMenuController();
            mapMenuController.roomDropDown.addAll(tags);
            mapMenuController.DestinationSelect.setValue(mapMenuController.roomDropDown);
            mapMenuController.StartSelect.setValue(mapMenuController.roomDropDown);
        }catch(Exception e){

        }*/

        //Populate Search Menus
        /*try {
            EditMapController editMapController = new EditMapController();
            editMapController.nodeDropDown.addAll(nodes);
            //editMapController.StartNodeSelect.setValue(editMapController.nodeDropDown);
            //editMapController.ConnectionSelect.setValue(editMapController.nodeDropDown);
            editMapController.tagDropDown.addAll(tags);
            editMapController.TagBox.setValue(editMapController.tagDropDown);
        }catch(Exception e){

        }*/

        //Populate EditDoctor Scene
        /*try{
            EditDoctorController editDoctorController = new EditDoctorController();
            System.out.println(tags.size());
            editDoctorController.tagList.addAll(tags);
            editDoctorController.professionalList.addAll(professionals);
        }catch(Exception e){

        }*/


        // launch window
        launch(args);

        
        //database.dumpDatabaseToSqlStatements("DBImports.txt");
        try {
            //database.dumpDatabaseToSqlStatements("dump.sql.import");
            database.close();
        }catch (SQLException e){
            System.out.println(e);
        }


    }

}





