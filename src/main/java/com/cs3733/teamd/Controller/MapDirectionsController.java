package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
//import javafx.application.Application;
import javafx.scene.Group;
//import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
//import javafx.stage.Stage;
import com.cs3733.teamd.Model.Node;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by Allyk on 3/26/2017.
 */
public class MapDirectionsController {

    public Button largerTextButton;
    public Button SearchButton;
    public Button LoginButton;
    public Button BackButton;
    public Button MenuButton;

    public ImageView mapImage;
    public TextArea textDirectionsDisplay;
    public Canvas MapCanvas;
    public AnchorPane pane;
    public Text menu;
    //public Label instructv;
    //public Label instructt;


    @FXML private void initialize()
    {
        setText();

        GraphicsContext gc = MapCanvas.getGraphicsContext2D();
        LinkedList<Node> path = new LinkedList<Node>();
        path.add(new Node(328, 310));
        path.add(new Node(328, 113));
        path.add(new Node(358, 113));
        path.add(new Node(358, 98));

        drawShapes(gc, path);

        if (Main.roomSelected == "Select Room") {
            if (Main.serviceSelected == "Allergy"){
                // Map data goes here
            }
            if (Main.serviceSelected == "Blood Test"){
                // Map data goes here
            }
            if (Main.serviceSelected == "ICU"){
                // Map data goes here
            }
            if (Main.serviceSelected == "Oranges"){
                // Map data goes here
            }
            if (Main.serviceSelected == "Emergency Room"){
                // Map data goes here
            }
        }
        if (Main.serviceSelected == "Select Service"){
            if (Main.roomSelected == "3A"){
                // Map data goes here
            }
            if (Main.serviceSelected == "3B"){
                // Map data goes here
            }
            if (Main.serviceSelected == "3C"){
                // Map data goes here
            }
        }
    }

    @FXML
    public void onSearch(ActionEvent actionEvent) throws IOException {
        Main.window.hide();
        Main.window.setScene(Main.MapMenuScene);
        Main.window.show();
        Main.backRoot = Main.MapDirectionsScene;
    }

    @FXML
    public void onLogin(ActionEvent actionEvent) throws IOException{
        Main.window.hide();
        Main.window.setScene(Main.LoginScene);
        Main.window.show();
        Main.backRoot = Main.MapDirectionsScene;
    }
    @FXML
    public void onBack(ActionEvent actionEvent) throws  IOException{
        Main.window.hide();
        Main.window.setScene(Main.backRoot);
        Main.window.show();
        Main.backRoot = Main.MapDirectionsScene;
    }

    private void drawShapes(GraphicsContext gc, LinkedList<Node> path) {
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        Node previous = null;
        gc.setLineWidth(2);
        int pathlength = path.size();
        int radius = 3;
        for  (int i = 0; i < pathlength; i++){
            Node current = path.getFirst();
            gc.fillOval(current.getX(), current.getY(), radius*2, radius*2);
            if(previous != null){
                gc.strokeLine(previous.getX() + radius, previous.getY() + radius,
                        current.getX() + radius, current.getY() + radius);
            }
            //System.out.printf(current.getX() + "" + current.getY());
            previous = current;
            path.pop();
        }
        /*gc.strokeLine(40, 10, 10, 40);
        gc.fillOval(10, 60, 30, 30);
        gc.strokeOval(60, 60, 30, 30);
        gc.fillRoundRect(110, 60, 30, 30, 10, 10);
        gc.strokeRoundRect(160, 60, 30, 30, 10, 10);
        gc.fillArc(10, 110, 30, 30, 45, 240, ArcType.OPEN);
        gc.fillArc(60, 110, 30, 30, 45, 240, ArcType.CHORD);
        gc.fillArc(110, 110, 30, 30, 45, 240, ArcType.ROUND);
        gc.strokeArc(10, 160, 30, 30, 45, 240, ArcType.OPEN);
        gc.strokeArc(60, 160, 30, 30, 45, 240, ArcType.CHORD);
        gc.strokeArc(110, 160, 30, 30, 45, 240, ArcType.ROUND);
        gc.fillPolygon(new double[]{10, 40, 10, 40},
                new double[]{210, 210, 240, 240}, 4);
        gc.strokePolygon(new double[]{60, 90, 60, 90},
                new double[]{210, 210, 240, 240}, 4);
        gc.strokePolyline(new double[]{110, 140, 110, 140},
                new double[]{210, 210, 240, 240}, 4);*/
    }


    @FXML
    public void setText(){
        SearchButton.setText(Main.bundle.getString("search"));
        LoginButton.setText(Main.bundle.getString("login"));
        MenuButton.setText(Main.bundle.getString("menu"));
        BackButton.setText(Main.bundle.getString("back"));
        menu.setText(Main.bundle.getString("MapMenu"));
        //instructv.setText(Main.bundle.getString("instruct2"));
        //instructt.setText(Main.bundle.getString("instruct2"));
        System.out.print(Main.Langugage);

        if(Main.Langugage.equals("Spanish") ){
            menu.setX(-100);
            //menu.setTranslateX(-175);
        }
        else if(Main.Langugage.equals("English") ){
            // menu.setTranslateX(-175);
            menu.setX(0);
        }
    }
}
