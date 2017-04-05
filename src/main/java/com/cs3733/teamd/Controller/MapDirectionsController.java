package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Main;
import com.cs3733.teamd.Model.Node;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.IOException;
import java.util.LinkedList;

//import javafx.application.Application;
//import javafx.scene.Scene;
//import javafx.stage.Stage;

/**
 * Created by Allyk on 3/26/2017.
 */
//TODO Avoid using the onMouseMove. Redesign to use models instead.
public class MapDirectionsController extends AbsController{

    public Button largerTextButton;
    public Button SearchButton;
    public Button LoginButton;
    public Button BackButton;
    public Button MenuButton;

    public Label redKey;
    public Label greenKey;
    public Label yellowKey;

    public ImageView mapImage;
    public TextArea textDirectionsDisplay;
    public Canvas MapCanvas;
    public AnchorPane pane;
    public Text menu;
    public AnchorPane pane2;

    public int imageW = 900;
    public int imageH = 500;
    public double scale = 8.4;
    public int offset_x = 160*12;
    public int offset_y = 80*12;
    public AnchorPane MMGpane;

    public GraphicsContext gc;

    @FXML private void initialize()
    {
        setText();
        gc = MapCanvas.getGraphicsContext2D();
        draw();
    }

    //Search button
    @FXML
    public void onSearch(ActionEvent actionEvent) throws IOException {
        gc.clearRect(0, 0, MapCanvas.getWidth(), MapCanvas.getHeight());
        switchScreen(MMGpane, "/Views/MapMenu.fxml",  "/Views/MapDirections.fxml");
    }

    //Login button
    @FXML
    public void onLogin(ActionEvent actionEvent) throws IOException{
        gc.clearRect(0, 0, MapCanvas.getWidth(), MapCanvas.getHeight());
        switchScreen(MMGpane, "/Views/Login.fxml", "/Views/MapDirections.fxml");
    }

    //Back button
    @FXML
    public void onBack(ActionEvent actionEvent) throws  IOException{
        //clear canvas for further drawings
        gc.clearRect(0, 0, MapCanvas.getWidth(), MapCanvas.getHeight());
        switchScreen(MMGpane, Main.backString,  "/Views/MapDirections.fxml");
    }

    //Menu button
    @FXML
    public void onMenu(ActionEvent actionEvent) throws  IOException{
        gc.clearRect(0, 0, MapCanvas.getWidth(), MapCanvas.getHeight());
        switchScreen(MMGpane, "/Views/Main.fxml",  "/Views/MapDirections.fxml");
    }

    @FXML
    private void draw(){
        plotPath(MapMenuController.pathNodes);
    }

    private Point getConvertedPoint(Node node) { //conversion from database to canvas
        int x = node.getX();
        int y = node.getY();
        Point p = new Point((int) ((x-offset_x)/scale), (int) (imageH-(y-offset_y)/scale));
        return p;
    }

    private void drawShapes(GraphicsContext gc, LinkedList<Point> path) { //draw path on canvas from List of Nodes generated from Pathfinder
        gc.setFill(Color.RED);
        gc.setStroke(Color.BLUE);
        Point previous = null;
        gc.setLineWidth(2);
        int pathlength = path.size();
        int radius = 4;
        for  (int i = 0; i < pathlength; i++){
            Point current = path.getFirst();
            if(i == pathlength-1){
                gc.setFill(Color.YELLOW);
            }
            gc.fillOval(current.getX(), current.getY(), radius*2, radius*2);
            if(previous != null){
                gc.strokeLine(previous.getX() + radius, previous.getY() + radius,
                        current.getX() + radius, current.getY() + radius);
            }
            previous = current;
            path.pop();
            gc.setFill(Color.GREEN);
        }
    }


    @FXML
    public void setText(){ //translate to Spanish
        SearchButton.setText(Main.bundle.getString("search"));
        LoginButton.setText(Main.bundle.getString("login"));
        MenuButton.setText(Main.bundle.getString("menu"));
        BackButton.setText(Main.bundle.getString("back"));
        menu.setText(Main.bundle.getString("MapDirections"));

        if(Main.Langugage.equals("Spanish") ){
            menu.setX(-100);
        }
        else if(Main.Langugage.equals("English") ){
            menu.setX(100);
        }
    }

    public void plotPath(LinkedList<Node> path){
        LinkedList<Point> points = new LinkedList<>();
        for (Node node: path) {
            points.add(getConvertedPoint(node));
        }
        drawShapes(gc, points);
    }
}
