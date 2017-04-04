package com.cs3733.teamd;

import com.cs3733.teamd.Controller.EditMapController;
import com.cs3733.teamd.Controller.MapMenuController;
import com.cs3733.teamd.Database.DBHandler;
import com.cs3733.teamd.Model.Node;
import com.cs3733.teamd.Model.Professional;
import com.cs3733.teamd.Model.Tag;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;


public class Main extends Application {

    public static String Langugage="English";
    public static Stage window;
    public static String backString;// ="/Views/MapDirections.fxml";

    public static Parent rootMain;

    public static Scene backRoot;
    public static String roomSelected = "Select Room";
    public static String serviceSelected = "Select Service";



    public static Scene MainScene;//=new Scene(rootMain, 2124, 1010);


    public static Locale local = new Locale("en", "US");
    public static Locale spanish = new Locale("es","SP");
    public static ResourceBundle bundle =ResourceBundle.getBundle("MyLabels", local);
    @Override
    public void start(Stage primaryStage) throws Exception{

        window=primaryStage;
        rootMain = FXMLLoader.load(getClass().getResource("/Views/Main.fxml"), bundle);

        MainScene=new Scene(rootMain, 1300, 800);

        window.setTitle("Pathfinding Application");
        window.setScene(MainScene);
        window.show();

    }

    public static void main(String[] args) {

        DBHandler database;

        try {
            database = new DBHandler();
        } catch (Exception e) {
            System.err.print("Could not construct DBHandler.\nExiting...\n");
            return;
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
        try {
            database.Load();
            nodes = database.nodes;
            tags = database.tags;
            professionals = database.professionals;


            database.Close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.print("Could not load data from database.\nExiting...\n");
            return;
        }

        try {
            MapMenuController mapMenuController = new MapMenuController();
            mapMenuController.roomDropDown.addAll(tags);
            mapMenuController.DestinationSelect.setValue(mapMenuController.roomDropDown);
            mapMenuController.StartSelect.setValue(mapMenuController.roomDropDown);
        }catch(Exception e){

        }


        try {
            EditMapController editMapController = new EditMapController();
            editMapController.nodeDropDown.addAll(nodes);
            editMapController.StartNodeSelect.setValue(editMapController.nodeDropDown);
            editMapController.ConnectionSelect.setValue(editMapController.nodeDropDown);
            editMapController.tagDropDown.addAll(tags);
            editMapController.TagBox.setValue(editMapController.tagDropDown);
        }catch(Exception e){

        }

        // launch window
        launch(args);

    }
}





