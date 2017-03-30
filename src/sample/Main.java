package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static String Langugage;
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

    public static Scene MainScene;//=new Scene(rootMain, 2124, 1010);
    public static Scene LoginScene;
    public static Scene MapMenuScene;
    public static Scene MapDirectionsScene;
    public static Scene AdminMenuScene;
    public static Scene EditServiceScene;
    public static Scene EditDoctorScene;
    public static Scene EditMapScene;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Langugage = "English";
        window=primaryStage;
        rootMain = FXMLLoader.load(getClass().getResource("Views/Main.fxml"));
        rootLogin = FXMLLoader.load(getClass().getResource("Views/Login.fxml"));
        rootMapMenu = FXMLLoader.load(getClass().getResource("Views/MapMenu.fxml"));
        rootMapDirections = FXMLLoader.load(getClass().getResource("Views/MapDirections.fxml"));
        rootAdminMenu = FXMLLoader.load(getClass().getResource("Views/AdminMenu.fxml"));
        rootEditService = FXMLLoader.load(getClass().getResource("Views/EditService.fxml"));
        rootEditDoctor = FXMLLoader.load(getClass().getResource("Views/EditDoctor.fxml"));
        rootEditMap = FXMLLoader.load(getClass().getResource("Views/EditMap.fxml"));

        MainScene=new Scene(rootMain, 2124, 1010);
        LoginScene=new Scene(rootLogin, 2124, 1010);
        MapMenuScene=new Scene(rootMapMenu, 2124, 1010);
        MapDirectionsScene=new Scene(rootMapDirections, 2124, 1010);
        AdminMenuScene=new Scene(rootAdminMenu, 2124, 1010);
        EditDoctorScene=new Scene(rootEditDoctor, 2124, 1010);
        EditServiceScene=new Scene(rootEditService, 2124, 1010);
        EditMapScene=new Scene(rootEditMap, 2124, 1010);

        window.setTitle("Pathfinding Application");
        window.setScene(MainScene);
        window.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}





