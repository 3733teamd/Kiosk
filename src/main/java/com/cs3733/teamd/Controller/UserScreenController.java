package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Controller.IterationOne.MapDirectionsController;
import com.cs3733.teamd.Main;
import com.cs3733.teamd.Model.Directory;
import com.cs3733.teamd.Model.Node;
import com.cs3733.teamd.Model.Pathfinder;
import com.cs3733.teamd.Model.Tag;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.*;

import java.awt.*;
import java.awt.Color;
import java.io.IOException;
import java.util.LinkedList;
import java.util.ResourceBundle;

/**
 * Created by Anh Dao on 4/6/2017.
 */
public class UserScreenController extends AbsController{
    public Button LoginButton;
    public Button SpanishButton;
    public Button SearchButton;
    public TextField TypeDestination;
    public Canvas MapCanvas;

    public int imageW = 1091;
    public int imageH = 693;
    public double scale = 8.4;
    public int offset_x = 160*12;
    public int offset_y = 80*12;
    public AnchorPane MMGpane;

    private Boolean languageEnglish =true;
    public GraphicsContext gc;
    private static LinkedList<Node> pathNodes;

    @FXML private void initialize()
    {
        setText();
        gc = MapCanvas.getGraphicsContext2D();
        if(pathNodes != null) {
            draw();
        }
    }


    //Spanish button to change language to Spanish
    @FXML
    public void onSpanish(ActionEvent actionEvent){
        languageEnglish = !languageEnglish;
        if(languageEnglish ==false) {
            Main.Langugage = "Spanish";
            Main.bundle = ResourceBundle.getBundle("MyLabels", Main.spanish);
        }
        else{
            Main.Langugage = "English";
            Main.bundle = ResourceBundle.getBundle("MyLabels", Main.local);
        }

        Main.window.hide();
        Main.window.setScene(Main.MainScene);
        Main.window.show();

        setText();
    }

    //Search button
    @FXML
    public void onSearch(ActionEvent actionEvent) throws IOException{
        Main.DestinationSelected = TypeDestination.getText();
        Directory dir = Directory.getInstance();
        int numtags = dir.getTags().size();
        int numnodes = dir.getNodes().size();
        Tag curtag;
        for(int itr = 0; itr < numtags; itr++){
            curtag = dir.getTags().get(itr);
            if(Main.DestinationSelected.equals(curtag.getTagName())){
                Pathfinder pathfinder =
                        new Pathfinder(dir.getNodes().get(numnodes-1),
                                        curtag.getNodes().getFirst());

                pathNodes = pathfinder.shortestPath();
            }

        }
        switchScreen(MMGpane, "/Views/UserScreen.fxml");
    }
    //Login button
    @FXML
    public void onLogin(ActionEvent actionEvent) throws IOException {
        switchScreen(MMGpane, "/Views/LoginScreen.fxml");
    }

    //Spanish translation
    public void setText(){

    }

    @FXML
    private void draw(){
        plotPath(UserScreenController.pathNodes);
    }

    private Point getConvertedPoint(Node node) { //conversion from database to canvas
        int x = node.getX();
        int y = node.getY();
        Point p = new Point((int) ((x-offset_x)/scale), (int) (imageH-(y-offset_y)/scale));
        return p;
    }

    public void plotPath(LinkedList<Node> path){
        LinkedList<Point> points = new LinkedList<>();
        for (Node node: path) {
            points.add(getConvertedPoint(node));
        }
        drawShapes(gc, points);
    }

    private void drawShapes(GraphicsContext gc, LinkedList<Point> path) { //draw path on canvas from List of Nodes generated from Pathfinder
        gc.setFill(javafx.scene.paint.Color.GREEN);
        gc.setStroke(javafx.scene.paint.Color.BLUE);
        Point previous = null;
        LinkedList<String> TextDirections = new LinkedList<String>();
        gc.setLineWidth(3);
        int pathlength = path.size();
        for (int str = 0; str < pathlength; str++){
            TextDirections.add("");
        }
        int radius = 7;
        for  (int i = 0; i < pathlength; i++){
            Point current = path.get(i);
            if(i == pathlength-1){
                radius = 7;
                gc.setFill(javafx.scene.paint.Color.RED);
            }
            gc.fillOval(current.getX(), current.getY(), radius*2, radius*2);
            //draw line from previous to current
            if(previous != null){
                gc.strokeLine(previous.getX() + radius, previous.getY() + radius,
                        current.getX() + radius, current.getY() + radius);
            }
            //first node
            if(i == 0){
                String temp = "Starting at and facing the kiosk ";
                TextDirections.set(i, temp);
            }
            //holder for straight indicator
            String curdir = "";
            // every node between first and second to last
            if(i > 0 && i+2 < pathlength) {
                double oldnodex = path.get(i - 1).getX();
                double oldnodey = path.get(i - 1).getY();
                double currentnodex = path.get(i).getX();
                double currentnodey = path.get(i).getY();
                double nextnodex = path.get(i).getX();
                double nextnodey = path.get(i).getY();

                double oldcurx = currentnodex - oldnodex;
                double oldcury = currentnodey - oldnodey;
                double curnextx = nextnodex - currentnodex;
                double curnexty = nextnodey - currentnodey;

                double ocmorsig = 0;
                double cnmorsig = 0;

                ocmorsig = moresig(oldcurx, oldcury);
                cnmorsig = moresig(curnextx, curnexty);

                // ocx more significant than ocy
                if(ocmorsig == 1){
                    // cnx more significant than cny
                    if(cnmorsig == 1){
                        // oc is movement to the right
                        if(oldcurx >= 0){
                            // cn is movement to the right
                            if(curnextx >= 0){
                                if(curdir != "Straight"){
                                    curdir = "Straight";
                                    TextDirections.set(i, "Go straight until end of hallway ");
                                }
                            }
                            // cn is movement to the left
                            if(curnextx < 0){
                                // turn around case shouldnt activate
                            }
                        }
                        // oc is movement to the left
                        if(oldcurx < 0){
                            // cn is movement to the right
                            if(curnextx >= 0){
                                //turn around case
                            }
                            // cn is movement to the left
                            if(curnextx < 0){
                                if(curdir != "Straight"){
                                    curdir = "Straight";
                                    TextDirections.set(i, "Go straight until end of hallway ");
                                }
                            }
                        }
                    }
                    // cny more significant than cnx
                    if(cnmorsig == 2){
                        // oc is movement to the right
                        if(oldcurx >= 0){
                            // cn is movement upwards
                            if(curnexty >= 0){
                                curdir = "";
                                TextDirections.set(i, "Turn right ");
                            }
                            // cn is movement downwards
                            if(curnexty < 0){
                                curdir = "";
                                TextDirections.set(i, "Turn left ");
                            }
                        }
                        // oc is movement to the left
                        if(oldcurx < 0){
                            // cn is movement upwards
                            if(curnexty >= 0){
                                curdir = "";
                                TextDirections.set(i, "Turn left ");
                            }
                            // cn is movement downwards
                            if(curnexty < 0){
                                curdir = "";
                                TextDirections.set(i, "Turn right ");
                            }
                        }
                    }
                }

                // ocy more significant than ocx
                if(ocmorsig == 2){
                    // cnx more significant than cny
                    if(cnmorsig == 1){
                        // oc is movement upwards
                        if(oldcury >= 0){
                            // cn is movement to the right
                            if(curnextx >= 0){
                                curdir = "";
                                TextDirections.set(i, "Turn left ");
                            }
                            // cn is movement to the left
                            if(curnextx < 0){
                                curdir = "";
                                TextDirections.set(i, "Turn right ");
                            }
                        }
                        // oc is movement downwards
                        if(oldcury < 0){
                            // ocn is movement to the right
                            if(curnextx >= 0){
                                curdir = "";
                                TextDirections.set(i, "Turn right ");
                            }
                            // cn is movement to the left
                            if(curnextx < 0){
                                curdir = "";
                                TextDirections.set(i, "Turn left ");
                            }
                        }
                    }
                    // cny more significant than cnx
                    if(cnmorsig == 2){
                        // oc is movement upwards
                        if(oldcury >= 0){
                            // cn is movement downwards
                            if(curnexty < 0){
                                // turn around case
                            }
                            // cn is movement upwards
                            if(curnexty >= 0){
                                if(curdir != "Straight"){
                                    curdir = "Straight";
                                    TextDirections.set(i, "Go straight until end of hallway ");
                                }
                            }
                        }
                        // oc is movement downwards
                        if(oldcury < 0){
                            // cn is movement downwards
                            if(curnexty < 0){
                                if(curdir != "Straight"){
                                    curdir = "Straight";
                                    TextDirections.set(i, "Go straight until end of hallway ");
                                }
                            }
                            // cn is movement upwards
                            if(curnexty >= 0){
                                //turn around case
                            }
                        }
                    }
                }
            }
            // second to last node
            if(i == pathlength - 2) {
                double oldnodex = path.get(i - 1).getX();
                double oldnodey = path.get(i - 1).getY();
                double currentnodex = path.get(i).getX();
                double currentnodey = path.get(i).getY();
                double nextnodex = path.get(i).getX();
                double nextnodey = path.get(i).getY();

                double oldcurx = currentnodex - oldnodex;
                double oldcury = currentnodey - oldnodey;
                double curnextx = nextnodex - currentnodex;
                double curnexty = nextnodey - currentnodey;

                double ocmorsig = 0;
                double cnmorsig = 0;

                ocmorsig = moresig(oldcurx, oldcury);
                cnmorsig = moresig(curnextx, curnexty);

                // ocx more significant than ocy
                if(ocmorsig == 1){
                    // cnx more significant than cny
                    if(cnmorsig == 1){
                        // shouldnt happen in node layout
                    }
                    // cny more significant than cnx
                    if(cnmorsig == 2){
                        // oc is movement to the right
                        if(oldcurx >= 0){
                            // cn is movement upwards
                            if(curnexty >= 0){
                                curdir = "";
                                TextDirections.set(i, "Turn right mid-hallway ");
                            }
                            // cn is movement downwards
                            if(curnexty < 0){
                                curdir = "";
                                TextDirections.set(i, "Turn left mid-hallway ");
                            }
                        }
                        // oc is movement to the left
                        if(oldcurx < 0){
                            // cn is movement upwards
                            if(curnexty >= 0){
                                curdir = "";
                                TextDirections.set(i, "Turn left mid-hallway ");
                            }
                            // cn is movement downwards
                            if(curnexty < 0){
                                curdir = "";
                                TextDirections.set(i, "Turn right mid-hallway ");
                            }
                        }
                    }
                }

                // ocy more significant than ocx
                if(ocmorsig == 2){
                    // cnx more significant than cny
                    if(cnmorsig == 1){
                        // oc is movement upwards
                        if(oldcury >= 0){
                            // cn is movement to the right
                            if(curnextx >= 0){
                                curdir = "";
                                TextDirections.set(i, "Turn left mid-hallway ");
                            }
                            // cn is movement to the left
                            if(curnextx < 0){
                                curdir = "";
                                TextDirections.set(i, "Turn right mid-hallway ");
                            }
                        }
                        // oc is movement downwards
                        if(oldcury < 0){
                            // ocn is movement to the right
                            if(curnextx >= 0){
                                curdir = "";
                                TextDirections.set(i, "Turn right mid-hallway ");
                            }
                            // cn is movement to the left
                            if(curnextx < 0){
                                curdir = "";
                                TextDirections.set(i, "Turn left mid-hallway ");
                            }
                        }
                    }
                    // cny more significant than cnx
                    if(cnmorsig == 2){
                        //shouldnt happen due to node layout
                    }
                }
            }
            //last node
            if(i == pathlength -1){
                String temp = "Ending at " + Main.DestinationSelected;
                TextDirections.set(i, temp);
            }
            previous = current;
            gc.setFill(javafx.scene.paint.Color.BLUE);
            radius = 5;
        }
        String output = "";
        //iterate through to find directions that are not blanks
        for( int stritr = 0; stritr < pathlength; stritr++){
            String tempstr;
            if(stritr == 0){
                tempstr  = TextDirections.get(stritr);
                if(tempstr != "i"){
                    output = output + tempstr;
                }
            }
            if(stritr > 0 && stritr < pathlength - 1) {
                tempstr = TextDirections.get(stritr);
                if (tempstr != "i") {
                    output = output + tempstr;
                }
            }
            if(stritr == pathlength-1){
                tempstr  = TextDirections.get(stritr);
                if(tempstr != "i"){
                    output = output + tempstr;
                }
            }
        }
        // print output to show path directions
        System.out.println(output);
    }


    public int moresig(double x, double y){
        if(x >= 0){
            if(y >= 0){
                if(x >= y){
                    return 1;
                }
                if(y > x){
                    return 2;
                }
            }
            if(y < 0){
                if(x >= y*-1){
                    return 1;
                }
                if(y*-1 > x){
                    return 2;
                }
            }
        }
        if(x < 0){
            if(y >= 0){
                if(x*-1 >= y){
                    return 1;
                }
                if(y > x*-1){
                    return 2;
                }
            }
            if(y < 0){
                if(x*-1 >= y*-1){
                    return 1;
                }
                if(y*-1 > x*-1){
                    return 2;
                }
            }
        }
        return 0;
    }
}
