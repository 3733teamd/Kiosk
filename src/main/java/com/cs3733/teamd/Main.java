package com.cs3733.teamd;

import com.cs3733.teamd.Controller.MainController;
import com.cs3733.teamd.Controller.MapMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
    public static void dropTables(Connection connection){
        String dropServicesSql = "DROP TABLE SERVICES";
        String dropLocationsSql = "DROP TABLE LOCATIONS";
        String dropProvidersSql = "DROP TABLE PROVIDERS";
        try {
            Statement s = connection.createStatement();
            s.execute(dropLocationsSql);
            //s.execute(dropServicesSql);
            s.execute(dropProvidersSql);
        } catch (SQLException e) {
            System.err.println("Failed to drop tables, most likely because they do not exist");
            //e.printStackTrace();
        }
    }

    public static void createTables(Connection connection){
        String createProvidersSql = "CREATE TABLE PROVIDERS"
                + "(id INTEGER GENERATED ALWAYS AS IDENTITY" +
                "        (START WITH 1, INCREMENT BY 1),"
                + "name VARCHAR(50))";

        String createLocationsSql = "CREATE TABLE LOCATIONS"
                + "(id INTEGER GENERATED ALWAYS AS IDENTITY" +
                "        (START WITH 1, INCREMENT BY 1),"
                + "floor INTEGER,"
                + "room VARCHAR(20),"
                + "p_id INTEGER)";
        try {
            Statement s = connection.createStatement();
            s.execute(createLocationsSql);
            s.execute(createProvidersSql);
        } catch (SQLException e) {
            System.err.println("Failed to create tables");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("-------Embedded Java DB Connection Testing --------");
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Java DB Driver not found. Add the classpath to your module.");
            System.out.println("For IntelliJ do the following:");
            System.out.println("File | Project Structure, Modules, Dependency tab");
            System.out.println("Add by clicking on the green plus icon on the right of the window");
            System.out.println("Select JARs or directories. Go to the folder where the Java JDK is installed");
            System.out.println("Select the folder java/jdk1.8.xxx/db/lib where xxx is the version.");
            System.out.println("Click OK, compile the code and run it.");
            e.printStackTrace();
            return;
        }

        System.out.println("Java DB driver registered!");
        Connection connection = null;

        try {
            // substitute your database name for myDB
            connection = DriverManager.getConnection("jdbc:derby:db;create=true");
        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return;
        }

        dropTables(connection);
        createTables(connection);

        // launch window
        launch(args);

    }
}





