package com.cs3733.teamd;

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

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;


public class Main extends Application {

    public static String Langugage="English";
    public static Stage window;

    public static Parent rootMain;
    public static Parent rootLogin;
    public static Parent rootMapMenu;
    public static Parent rootMapDirections;
    public static Parent rootAdminMenu;
    public static Parent rootEditService;
    public static Parent rootEditDoctor;
    public static Parent rootEditMap;

    public static Scene backRoot;
    public static String roomSelected = "Select Room";
    public static String serviceSelected = "Select Service";



    public static Scene MainScene;//=new Scene(rootMain, 2124, 1010);
    public static Scene LoginScene;
    public static Scene MapMenuScene;
    public static Scene MapDirectionsScene;
    public static Scene AdminMenuScene;
    public static Scene EditServiceScene;
    public static Scene EditDoctorScene;
    public static Scene EditMapScene;

    public static Locale local = new Locale("en", "US");
    public static Locale spanish = new Locale("es","SP");
    public static ResourceBundle bundle =ResourceBundle.getBundle("MyLabels", local);
    @Override
    public void start(Stage primaryStage) throws Exception{

        window=primaryStage;
        rootMain = FXMLLoader.load(getClass().getResource("/Views/Main.fxml"), bundle);
        rootLogin = FXMLLoader.load(getClass().getResource("/Views/Login.fxml"), bundle);
        rootMapMenu = FXMLLoader.load(getClass().getResource("/Views/MapMenu.fxml"),bundle);
        rootMapDirections = FXMLLoader.load(getClass().getResource("/Views/MapDirections.fxml"),bundle);
        rootAdminMenu = FXMLLoader.load(getClass().getResource("/Views/AdminMenu.fxml"),bundle);
        rootEditService = FXMLLoader.load(getClass().getResource("/Views/EditService.fxml"),bundle);
        rootEditDoctor = FXMLLoader.load(getClass().getResource("/Views/EditDoctor.fxml"),bundle);
        rootEditMap = FXMLLoader.load(getClass().getResource("/Views/EditMap.fxml"),bundle);

        MainScene=new Scene(rootMain, 1300, 800);
        LoginScene=new Scene(rootLogin, 1300, 800);
        MapMenuScene=new Scene(rootMapMenu, 1300, 800);
        MapDirectionsScene=new Scene(rootMapDirections, 1300, 800);
        AdminMenuScene=new Scene(rootAdminMenu, 1300, 800);
        EditDoctorScene=new Scene(rootEditDoctor, 1300, 800);
        EditServiceScene=new Scene(rootEditService, 1300, 800);
        EditMapScene=new Scene(rootEditMap, 1300, 800);
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


        try {
            database.Setup();
        } catch (SQLException e) {
            if(e.getSQLState().equals("X0Y32")){ // Error code for TABLE EXISTS
                System.out.println("Skipping setup as tables are already made");
            } else {
                System.err.println("ERROR: creation of database failed");
            }
        } catch (IOException e){
            System.err.println("ERROR: reading in data file");
            e.printStackTrace();
        }

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

        // launch window
        launch(args);

    }
}





