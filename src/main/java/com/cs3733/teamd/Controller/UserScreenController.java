package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Controller.IterationOne.MapDirectionsController;
import com.cs3733.teamd.Main;
import com.cs3733.teamd.Model.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import org.controlsfx.control.textfield.TextFields;

//import javax.xml.soap.Text;

import java.awt.Point;
import java.io.IOException;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.List;

/**
 * Created by Anh Dao on 4/6/2017.
 */
public class UserScreenController extends AbsController{
    Directory dir = Directory.getInstance();
    private static final int MIN_PIXELS = 10;
    public Button LoginButton;
    public Button SpanishButton;
    public Button SearchButton;
    public TextField TypeDestination;
    public Text EnterDest;
    public Text floor;
    public Label directionLabel;
    @FXML
    private Slider floorSlider;

    public ImageView floorMap;
    public AnchorPane imagePane;


    //proxy pattern
    ImageInterface imgInt = new ProxyImage();
    public int floorNum = Main.currentFloor;

    public Canvas MapCanvas;
    public int imageW = 1091;
    public int imageH = 693;
    public double scale = 8.4;
    public int offset_x = 160*12;
    public int offset_y = 80*12;
    public AnchorPane MMGpane;
    @FXML
    private TextArea directions;
    public GraphicsContext gc;
    private static LinkedList<Node> pathNodes;
    private List<Tag> nodeList = dir.getTags();

    int onFloor = Main.currentFloor;
    int indexOfElevator = 0;

    final double SCALE_DELTA = 1.1;
    double orgSceneX, orgSceneY;

    public ScrollPane scrollPane;

    String output = "";
    Tag starttag = null;
    @FXML private void initialize() {
        TextFields.bindAutoCompletion(TypeDestination, nodeList);
        setText();
        directions.setText(output);
        floorMap.setImage(imgInt.display(floorNum));

        floorSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val)  {
                if (!floorSlider.isValueChanging()) {


                    onFloor = new_val.intValue();
                    floorSlider.setValue(onFloor);
                    //floorMap.setImage(imageHashMap.get(onFloor));
                    floorMap.setImage(imgInt.display(onFloor));
                    gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
                    output = "";
                    directions.setText(output);
                    System.out.println(onFloor);

                    if (pathNodes != null) {
                        draw();
                    }

                }
            }
        });

        gc = MapCanvas.getGraphicsContext2D();
        if (pathNodes != null) {
            draw();
        }

        //added initalize
        //zoom functions?
        imagePane.getChildren();//.add(group);
        floorMap.setPreserveRatio(true);
        /*imagePane.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override public void handle(ScrollEvent event) {
                event.consume();

                if (event.getDeltaY() == 0) {
                    return;
                }

                double scaleFactor =
                        (event.getDeltaY() > 0)
                                ? SCALE_DELTA
                                : 1/SCALE_DELTA;

                floorMap.setScaleX(floorMap.getScaleX() * scaleFactor);
                floorMap.setScaleY(floorMap.getScaleY() * scaleFactor);
                MapCanvas.setScaleX(MapCanvas.getScaleX()*scaleFactor);
                MapCanvas.setScaleY(MapCanvas.getScaleY()*scaleFactor);

            }
        });

        imagePane.setOnMousePressed((t) -> {
                    orgSceneX = t.getSceneX();
                    orgSceneY = t.getSceneY();


                    AnchorPane c = (AnchorPane) (t.getSource());
                    c.toFront();
                });
        imagePane.setOnMouseDragged((t) -> {
            //if(state ==states.add) {
            double offsetX = t.getSceneX() - orgSceneX;
            double offsetY = t.getSceneY() - orgSceneY;

            AnchorPane c = (AnchorPane) (t.getSource());

            c.setTranslateX(c.getLayoutX()+offsetX);
            c.setTranslateY(c.getLayoutY()+offsetY);


            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();


            //}
        });*/
        final double SCALE_DELTA = 1.1;
        //final Group group = new Group(floorMap, MapCanvas);

        //imagePane.getChildren().add(group);

        final Group scrollContent = new Group(floorMap, MapCanvas);
       scrollPane.setContent(scrollContent);

        scrollPane.viewportBoundsProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> observable,
                                Bounds oldValue, Bounds newValue) {
                imagePane.setMinSize(newValue.getWidth(), newValue.getHeight());
            }
        });

        scrollPane.setPrefViewportWidth(256);
        scrollPane.setPrefViewportHeight(256);

        scrollPane.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                event.consume();

                if (event.getDeltaY() == 0) {
                    return;
                }

                double scaleFactor = (event.getDeltaY() > 0) ? SCALE_DELTA
                        : 1 / SCALE_DELTA;

                // amount of scrolling in each direction in scrollContent coordinate
                // units
                Point2D scrollOffset = figureScrollOffset(scrollContent, scrollPane);


                floorMap.setScaleX(floorMap.getScaleX() * scaleFactor);
                floorMap.setScaleY(floorMap.getScaleY() * scaleFactor);
                MapCanvas.setScaleX(MapCanvas.getScaleX()*scaleFactor);
                MapCanvas.setScaleY(MapCanvas.getScaleY()*scaleFactor);

                // move viewport so that old center remains in the center after the
                // scaling
                repositionScroller(scrollContent, scrollPane, scaleFactor, scrollOffset);
                System.out.println("scrolling");

            }
        });

        // Panning via drag....
        final ObjectProperty<Point2D> lastMouseCoordinates = new SimpleObjectProperty<Point2D>();
        scrollContent.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lastMouseCoordinates.set(new Point2D(event.getX(), event.getY()));
            }
        });

        scrollContent.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double deltaX = event.getX() - lastMouseCoordinates.get().getX();
                double extraWidth = scrollContent.getLayoutBounds().getWidth() - scrollPane.getViewportBounds().getWidth();
                double deltaH = deltaX * (scrollPane.getHmax() - scrollPane.getHmin()) / extraWidth;
                double desiredH = scrollPane.getHvalue() - deltaH;
                scrollPane.setHvalue(Math.max(0, Math.min(scrollPane.getHmax(), desiredH)));

                double deltaY = event.getY() - lastMouseCoordinates.get().getY();
                double extraHeight = scrollContent.getLayoutBounds().getHeight() - scrollPane.getViewportBounds().getHeight();
                double deltaV = deltaY * (scrollPane.getHmax() - scrollPane.getHmin()) / extraHeight;
                double desiredV = scrollPane.getVvalue() - deltaV;
                scrollPane.setVvalue(Math.max(0, Math.min(scrollPane.getVmax(), desiredV)));
            }
        });



    }

    private Point2D figureScrollOffset(Group scrollContent, ScrollPane scroller) {
        double extraWidth = scrollContent.getLayoutBounds().getWidth() - scroller.getViewportBounds().getWidth();
        double hScrollProportion = (scroller.getHvalue() - scroller.getHmin()) / (scroller.getHmax() - scroller.getHmin());
        double scrollXOffset = hScrollProportion * Math.max(0, extraWidth);
        double extraHeight = scrollContent.getLayoutBounds().getHeight() - scroller.getViewportBounds().getHeight();
        double vScrollProportion = (scroller.getVvalue() - scroller.getVmin()) / (scroller.getVmax() - scroller.getVmin());
        double scrollYOffset = vScrollProportion * Math.max(0, extraHeight);
        return new Point2D(scrollXOffset, scrollYOffset);
    }

    private void repositionScroller(Group scrollContent, ScrollPane scroller, double scaleFactor, Point2D scrollOffset) {
        double scrollXOffset = scrollOffset.getX();
        double scrollYOffset = scrollOffset.getY();
        double extraWidth = scrollContent.getLayoutBounds().getWidth() - scroller.getViewportBounds().getWidth();
        if (extraWidth > 0) {
            double halfWidth = scroller.getViewportBounds().getWidth() / 2 ;
            double newScrollXOffset = (scaleFactor - 1) *  halfWidth + scaleFactor * scrollXOffset;
            scroller.setHvalue(scroller.getHmin() + newScrollXOffset * (scroller.getHmax() - scroller.getHmin()) / extraWidth);
        } else {
            scroller.setHvalue(scroller.getHmin());
        }
        double extraHeight = scrollContent.getLayoutBounds().getHeight() - scroller.getViewportBounds().getHeight();
        if (extraHeight > 0) {
            double halfHeight = scroller.getViewportBounds().getHeight() / 2 ;
            double newScrollYOffset = (scaleFactor - 1) * halfHeight + scaleFactor * scrollYOffset;
            scroller.setVvalue(scroller.getVmin() + newScrollYOffset * (scroller.getVmax() - scroller.getVmin()) / extraHeight);
        } else {
            scroller.setHvalue(scroller.getHmin());
        }
    }
    //////////////////////

    //Spanish button to change language to Spanish
    @FXML
    public void onSpanish(ActionEvent actionEvent) throws  IOException{
        //TODO : CHANGE INTO SWITCH STATEMENT FOR MULTIPLE LANGUAGES
        if(Main.Langugage == "English") {
            Main.Langugage = "Spanish";
            Main.bundle = ResourceBundle.getBundle("MyLabels", Main.spanish);
        }
        else{
            Main.Langugage = "English";

            Main.bundle = ResourceBundle.getBundle("MyLabels", Main.local);
        }

        switchScreen(MMGpane,"/Views/UserScreen.fxml");

        setText();
    }

    @FXML
    public void onLogin(ActionEvent actionEvent) throws IOException {
        pathNodes=null;
        switchScreen(MMGpane, "/Views/UserScreen.fxml");

        switchScreen(MMGpane, "/Views/LoginScreen.fxml");
    }

    //Spanish translation
    public void setText(){
        SpanishButton.setText(Main.bundle.getString("spanish"));
        SearchButton.setText(Main.bundle.getString("search"));
        LoginButton.setText(Main.bundle.getString("login"));
        directionLabel.setText(Main.bundle.getString("directions"));
        EnterDest.setText(Main.bundle.getString("enterDes"));
        floor.setText(Main.bundle.getString("floor"));

        if(Main.Langugage =="Spanish"){
            LoginButton.setFont(Font.font("System",14));
        }
        else{
            LoginButton.setFont(Font.font("System",20));
        }

    }

    //Search button, generates path and directions on submit
    @FXML
    public void onSearch(ActionEvent actionEvent) throws IOException{
        //stores the destination inputted
        Main.DestinationSelected = TypeDestination.getText();
        //Gets nodes and tags from directory
        int numtags = dir.getTags().size();
        int numnodes = dir.getNodes().size();
        //Makes a temporary holder for values
        Tag curtag;
        String Start = "Kiosk";
        for(int itr = 0; itr < numtags; itr++){
            curtag = dir.getTags().get(itr);
            //If match is found create path to node from start nodes
            if(Start.equals(curtag.getTagName())){
                starttag = curtag;
            }

        }
        if(starttag != null) {
            Main.currentFloor = starttag.getNodes().getFirst().getFloor();
            //Iterates through all existing tags
            for (int itr = 0; itr < numtags; itr++) {
                curtag = dir.getTags().get(itr);
                //If match is found create path to node from start nodes
                if (Main.DestinationSelected.equals(curtag.getTagName())) {
                    Pathfinder pathfinder =
                            //will get updated to actually be the starting node
                            new Pathfinder(starttag.getNodes().getFirst(),
                                    curtag.getNodes().getFirst());
                    //use the shortest path
                    pathNodes = pathfinder.shortestPath();
                }
            }
        }
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        output = "";
        directions.setText(output);
        System.out.println(onFloor);

        if(pathNodes != null) {
            draw();
        }

    }

    @FXML
    //Starts path displaying process
    private void draw(){
        plotPath(UserScreenController.pathNodes);
    }

    //Converts a node to a point to display on map
    private Point getConvertedPoint(Node node) { //conversion from database to canvas
        int x = node.getX();
        int y = node.getY();
        //Point p = new Point((int) ((x-offset_x)/scale), (int) (imageH-(y-offset_y)/scale));
        Point p = new Point(x, y);
        return p;
    }

    //Converts a given path of nodes to a path of points and then draws it
    public void plotPath(LinkedList<Node> path){
        LinkedList<Point> pointsStartFloor = new LinkedList<>();
        LinkedList<Point> pointsEndFloor = new LinkedList<>();
        int index = 0;
        for (Node node: path) {
            if(node.getFloor() == onFloor) {
                System.out.println("Node.getfloor" + node.getFloor());
                System.out.println("plot"+ onFloor);
                pointsStartFloor.add(getConvertedPoint(node));
                index++;
            }
            else{
                pointsEndFloor.add(getConvertedPoint(node));
            }
        }
        indexOfElevator = index;
        if(starttag.getNodes().getFirst().getFloor() == onFloor) {
            drawShapes(gc, pointsStartFloor);
        }
        else if(path.getLast().getFloor() == onFloor){
            drawShapes(gc, pointsEndFloor);
        }
    }

    //Function to actually draw a path
    private void drawShapes(GraphicsContext gc, LinkedList<Point> path) {
        //color for start node
        gc.setFill(javafx.scene.paint.Color.GREEN);
        //color for edges
        gc.setStroke(javafx.scene.paint.Color.BLUE);
        //intialize point previous as null due to start not having a previous
        Point previous = null;
        //Create a list to hold text directions
        LinkedList<String> TextDirections = new LinkedList<String>();
        //Set line width
        gc.setLineWidth(3);
        //Saving the length of the path
        int pathlength = path.size();
        //holder for straight indicator
        String curdir = "";
        //Generate placeholders for text directions
        for (int str = 0; str < pathlength; str++){
            TextDirections.add("");
        }
        //Set radius of first node
        int radius = 7;

        //Iterate through the path
        for  (int i = 0; i < pathlength; i++){
            //get the point for current iterations
            Point current = path.get(i);
            //If it is the last point in the path
            if(i == pathlength-1){
                radius = 7;
                gc.setFill(javafx.scene.paint.Color.RED);
            }
            //draw the point
            gc.fillOval(current.getX(), current.getY(), radius*2, radius*2);
            //draw line from previous to current
            if(previous != null){
                gc.strokeLine(previous.getX() + radius, previous.getY() + radius,
                        current.getX() + radius, current.getY() + radius);
            }


            //first point directions
            if(i == 0){
                String temp = "";
                if(Main.Langugage == "Spanish"){
                    temp = "Comenzando y mirando hacia el quiosco" + "\n";
                }
                else{
                    temp = "Starting at and facing the kiosk " + "\n";
                }
                TextDirections.set(i, temp);
            }
            // every point between first and second to last
            if(i > 0 && i+2 < pathlength) {
                //Assign point
                Point oldnode = path.get(i - 1);
                Point currnode = path.get(i);
                Point nextnode = path.get(i + 1);
                //Run helper functions to update text
                if(Main.Langugage == "Spanish") {
                    TextDirections = getTextEsp(oldnode, currnode, nextnode, curdir, TextDirections, i);
                }
                else {
                    TextDirections = getText(oldnode, currnode, nextnode, curdir, TextDirections, i);
                }
                    curdir = setCurdir(oldnode, currnode, nextnode, curdir, i);
            }
            // second to last point
            if(i == pathlength - 2) {
                //Assign point
                Point oldnode = path.get(i - 1);
                Point currnode = path.get(i);
                Point nextnode = path.get(i + 1);
                //Run helper functions to update text
                if(Main.Langugage == "Spanish") {
                    TextDirections = getTextMidHallwayEsp(oldnode, currnode, nextnode, curdir, TextDirections, i);
                }
                else {
                    TextDirections = getTextMidHallway(oldnode, currnode, nextnode, curdir, TextDirections, i);
                }
                curdir = setCurdir(oldnode, currnode, nextnode, curdir, i);
                //System.out.println("Second to last" + TextDirections.getLast());
            }
            //last point
            if(i == pathlength -1){
                String temp = "";
                if(Main.Langugage == "Spanish") {
                    temp = "Terminando a " + Main.DestinationSelected;
                }
                else{
                    temp = "Ending at " + Main.DestinationSelected;
                }
                TextDirections.set(i, temp);
            }

            //Update for next loop
            previous = current;
            //Set intermediate points to be smaller and blue
            gc.setFill(javafx.scene.paint.Color.BLUE);
            radius = 5;
        }

        //iterate through to find directions that are not blanks
        for( int stritr = 0; stritr < pathlength; stritr++){
            String tempstr;
            //Make text directions without blanks
            if(stritr == 0){
                tempstr  = TextDirections.get(stritr);
                if(tempstr != "i"){
                    output = output + tempstr;
                }
            }
            if(stritr > 0 && stritr < pathlength - 1) {
                tempstr = TextDirections.get(pathlength - stritr - 1);
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
        directions.setText(output);
        // print output to show path directions
        //System.out.println(output);
    }

    //helper function to determine if the change in x direction if more significant than the change in y direction
    public int moresig(double x, double y){
        //System.out.println("Xvalue = " + x + " Yvalue = " + y);
        if(x >= 0){
            if(y >= 0){
                if(x >= y){
                    return 1; // X more signifcant
                }
                if(y > x){
                    return 2; // Ymore significant
                }
            }
            if(y < 0){
                if(x >= y*-1){
                    return 1; // X more signifcant
                }
                if(y*-1 > x){
                    return 2; // Y more signifcant
                }
            }
        }
        if(x < 0){
            if(y >= 0){
                if(x*-1 >= y){
                    return 1; // X more signifcant
                }
                if(y > x*-1){
                    return 2; // Y more signifcant
                }
            }
            if(y < 0){
                if(x*-1 >= y*-1){
                    return 1; // X more signifcant
                }
                if(y*-1 > x*-1){
                    return 2; // Y more signifcant
                }
            }
        }
        return 0; // Doesn't occur
    }

    //Helper function to generate text for most cases
    private LinkedList<String> getText(Point oldnode, Point currnode, Point nextnode,
                                       String curdir, LinkedList<String> TextDirections, int i ){
        //Get values
        double oldnodex = oldnode.getX();
        double oldnodey = oldnode.getY();
        double currentnodex = currnode.getX();
        double currentnodey = currnode.getY();
        double nextnodex = nextnode.getX();
        double nextnodey = nextnode.getY();

        //System.out.println(oldnodex + "" + oldnodey);
        //Get transitions
        double oldcurx = currentnodex - oldnodex;
        double oldcury = currentnodey - oldnodey;
        double curnextx = nextnodex - currentnodex;
        double curnexty = nextnodey - currentnodey;

        double ocmorsig = 0;
        double cnmorsig = 0;

        //Get orientations
        ocmorsig = moresig(oldcurx, oldcury);
        cnmorsig = moresig(curnextx, curnexty);

        //System.out.println("Regular getText " + ocmorsig + "" + cnmorsig);

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
                            TextDirections.set(i, "Go straight until end of hallway " + "\n");
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
                            TextDirections.set(i, "Go straight until end of hallway " + "\n");
                        }
                    }
                }
            }
            // cny more significant than cnx
            if(cnmorsig == 2){
                // oc is movement to the right
                if(oldcurx >= 0){
                    // cn is movement downwards
                    if(curnexty >= 0){
                        curdir = "";
                        TextDirections.set(i, "Turn left " + "\n");
                    }
                    // cn is movement upwards
                    if(curnexty < 0){
                        curdir = "";
                        TextDirections.set(i, "Turn right " + "\n");
                    }
                }
                // oc is movement to the left
                if(oldcurx < 0){
                    // cn is movement downwards
                    if(curnexty >= 0){
                        curdir = "";
                        TextDirections.set(i, "Turn right " + "\n");
                    }
                    // cn is movement upwards
                    if(curnexty < 0){
                        curdir = "";
                        TextDirections.set(i, "Turn left " + "\n");
                    }
                }
            }
        }

        // ocy more significant than ocx
        if(ocmorsig == 2){
            // cnx more significant than cny
            if(cnmorsig == 1){
                // oc is movement downwards
                if(oldcury >= 0){
                    // cn is movement to the right
                    if(curnextx >= 0){
                        curdir = "";
                        TextDirections.set(i, "Turn right " + "\n");
                    }
                    // cn is movement to the left
                    if(curnextx < 0){
                        curdir = "";
                        TextDirections.set(i, "Turn left " + "\n");
                    }
                }
                // oc is movement upwards
                if(oldcury < 0){
                    // ocn is movement to the right
                    if(curnextx >= 0){
                        curdir = "";
                        TextDirections.set(i, "Turn left " + "\n");
                    }
                    // cn is movement to the left
                    if(curnextx < 0){
                        curdir = "";
                        TextDirections.set(i, "Turn right " + "\n");
                    }
                }
            }
            // cny more significant than cnx
            if(cnmorsig == 2){
                // oc is movement downwards
                if(oldcury >= 0){
                    // cn is movement upwards
                    if(curnexty < 0){
                        // turn around case
                    }
                    // cn is movement downwards
                    if(curnexty >= 0){
                        if(curdir != "Straight"){
                            curdir = "Straight";
                            TextDirections.set(i, "Go straight until end of hallway " + "\n");
                        }
                    }
                }
                // oc is movement upwards
                if(oldcury < 0){
                    // cn is movement upwards
                    if(curnexty < 0){
                        if(curdir != "Straight"){
                            curdir = "Straight";
                            TextDirections.set(i, "Go straight until end of hallway " + "\n");
                        }
                    }
                    // cn is movement downwards
                    if(curnexty >= 0){
                        //turn around case
                    }
                }
            }
        }
        //Return the updated text directions
        return TextDirections;
    }

    //Helper function to generate text for case of reaching an elevator or the destination
    private LinkedList<String> getTextMidHallway(Point oldnode, Point currnode, Point nextnode,
                                                 String curdir, LinkedList<String> TextDirections, int i ){
        double oldnodex = oldnode.getX();
        double oldnodey = oldnode.getY();
        double currentnodex = currnode.getX();
        double currentnodey = currnode.getY();
        double nextnodex = nextnode.getX();
        double nextnodey = nextnode.getY();

        double oldcurx = currentnodex - oldnodex;
        double oldcury = currentnodey - oldnodey;
        double curnextx = nextnodex - currentnodex;
        double curnexty = nextnodey - currentnodey;

        double ocmorsig = 0;
        double cnmorsig = 0;

        ocmorsig = moresig(oldcurx, oldcury);
        cnmorsig = moresig(curnextx, curnexty);

        //System.out.println("Edge getText " + ocmorsig + "" + cnmorsig);

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
                    // cn is movement downwards
                    if(curnexty >= 0){
                        curdir = "";
                        TextDirections.set(i, "Turn left in current hallway " + "\n");
                    }
                    // cn is movement upwards
                    if(curnexty < 0){
                        curdir = "";
                        TextDirections.set(i, "Turn right in current hallway " + "\n");
                    }
                }
                // oc is movement to the left
                if(oldcurx < 0){
                    // cn is movement downwards
                    if(curnexty >= 0){
                        curdir = "";
                        TextDirections.set(i, "Turn right in current hallway " + "\n");
                    }
                    // cn is movement upwards
                    if(curnexty < 0){
                        curdir = "";
                        TextDirections.set(i, "Turn left in current hallway " + "\n");
                    }
                }
            }
        }

        // ocy more significant than ocx
        if(ocmorsig == 2){
            // cnx more significant than cny
            if(cnmorsig == 1){
                // oc is movement downwards
                if(oldcury >= 0){
                    // cn is movement to the right
                    if(curnextx >= 0){
                        curdir = "";
                        TextDirections.set(i, "Turn right in current hallway " + "\n");
                    }
                    // cn is movement to the left
                    if(curnextx < 0){
                        curdir = "";
                        TextDirections.set(i, "Turn left in current hallway " + "\n");
                    }
                }
                // oc is movement upwards
                if(oldcury < 0){
                    // ocn is movement to the right
                    if(curnextx >= 0){
                        curdir = "";
                        TextDirections.set(i, "Turn left in current hallway " + "\n");
                    }
                    // cn is movement to the left
                    if(curnextx < 0){
                        curdir = "";
                        TextDirections.set(i, "Turn right in current hallway " + "\n");
                    }
                }
            }
            // cny more significant than cnx
            if(cnmorsig == 2){
                //shouldnt happen due to node layout
            }
        }
        return TextDirections;
    }

    //Helper function to update curdir for other functions
    private String setCurdir(Point oldnode, Point currnode, Point nextnode,
                             String curdir, int i){
        double oldnodex = oldnode.getX();
        double oldnodey = oldnode.getY();
        double currentnodex = currnode.getX();
        double currentnodey = currnode.getY();
        double nextnodex = nextnode.getX();
        double nextnodey = nextnode.getY();

        double oldcurx = currentnodex - oldnodex;
        double oldcury = currentnodey - oldnodey;
        double curnextx = nextnodex - currentnodex;
        double curnexty = nextnodey - currentnodey;

        double ocmorsig = 0;
        double cnmorsig = 0;

        ocmorsig = moresig(oldcurx, oldcury);
        cnmorsig = moresig(curnextx, curnexty);

        //System.out.println("Curdir getText " + ocmorsig + "" + cnmorsig);

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
                        }
                    }
                }
            }
            // cny more significant than cnx
            if(cnmorsig == 2){
                // oc is movement to the right
                if(oldcurx >= 0){
                    // cn is movement downwards
                    if(curnexty >= 0){
                        curdir = "";
                    }
                    // cn is movement upwards
                    if(curnexty < 0){
                        curdir = "";
                    }
                }
                // oc is movement to the left
                if(oldcurx < 0){
                    // cn is movement downwards
                    if(curnexty >= 0){
                        curdir = "";
                    }
                    // cn is movement upwards
                    if(curnexty < 0){
                        curdir = "";
                    }
                }
            }
        }

        // ocy more significant than ocx
        if(ocmorsig == 2){
            // cnx more significant than cny
            if(cnmorsig == 1){
                // oc is movement downwards
                if(oldcury >= 0){
                    // cn is movement to the right
                    if(curnextx >= 0){
                        curdir = "";
                    }
                    // cn is movement to the left
                    if(curnextx < 0){
                        curdir = "";
                    }
                }
                // oc is movement upwards
                if(oldcury < 0){
                    // ocn is movement to the right
                    if(curnextx >= 0){
                        curdir = "";
                    }
                    // cn is movement to the left
                    if(curnextx < 0){
                        curdir = "";
                    }
                }
            }
            // cny more significant than cnx
            if(cnmorsig == 2){
                // oc is movement downwards
                if(oldcury >= 0){
                    // cn is movement upwards
                    if(curnexty < 0){
                        // turn around case
                    }
                    // cn is movement downwards
                    if(curnexty >= 0){
                        if(curdir != "Straight"){
                            curdir = "Straight";
                        }
                    }
                }
                // oc is movement upwards
                if(oldcury < 0){
                    // cn is movement upwards
                    if(curnexty < 0){
                        if(curdir != "Straight"){
                            curdir = "Straight";
                        }
                    }
                    // cn is movement downwards
                    if(curnexty >= 0){
                        //turn around case
                    }
                }
            }
        }
        return curdir;
    }

    //Helper function to generate text for most cases but in spanish
    private LinkedList<String> getTextEsp(Point oldnode, Point currnode, Point nextnode,
                                       String curdir, LinkedList<String> TextDirections, int i ){
        //Get values
        double oldnodex = oldnode.getX();
        double oldnodey = oldnode.getY();
        double currentnodex = currnode.getX();
        double currentnodey = currnode.getY();
        double nextnodex = nextnode.getX();
        double nextnodey = nextnode.getY();

        //System.out.println(oldnodex + "" + oldnodey);
        //Get transitions
        double oldcurx = currentnodex - oldnodex;
        double oldcury = currentnodey - oldnodey;
        double curnextx = nextnodex - currentnodex;
        double curnexty = nextnodey - currentnodey;

        double ocmorsig = 0;
        double cnmorsig = 0;

        //Get orientations
        ocmorsig = moresig(oldcurx, oldcury);
        cnmorsig = moresig(curnextx, curnexty);

        //System.out.println("Regular getText " + ocmorsig + "" + cnmorsig);

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
                            TextDirections.set(i, "ir recto hasta el final del pasillo " + "\n");
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
                            TextDirections.set(i, "ir recto hasta el final del pasillo " + "\n");
                        }
                    }
                }
            }
            // cny more significant than cnx
            if(cnmorsig == 2){
                // oc is movement to the right
                if(oldcurx >= 0){
                    // cn is movement downwards
                    if(curnexty >= 0){
                        curdir = "";
                        TextDirections.set(i, "dobla a la izquierda " + "\n");
                    }
                    // cn is movement upwards
                    if(curnexty < 0){
                        curdir = "";
                        TextDirections.set(i, "dobla a la derecha " + "\n");
                    }
                }
                // oc is movement to the left
                if(oldcurx < 0){
                    // cn is movement downwards
                    if(curnexty >= 0){
                        curdir = "";
                        TextDirections.set(i, "dobla a la derecha " + "\n");
                    }
                    // cn is movement upwards
                    if(curnexty < 0){
                        curdir = "";
                        TextDirections.set(i, "dobla a la izquierda " + "\n");
                    }
                }
            }
        }

        // ocy more significant than ocx
        if(ocmorsig == 2){
            // cnx more significant than cny
            if(cnmorsig == 1){
                // oc is movement downwards
                if(oldcury >= 0){
                    // cn is movement to the right
                    if(curnextx >= 0){
                        curdir = "";
                        TextDirections.set(i, "dobla a la derecha " + "\n");
                    }
                    // cn is movement to the left
                    if(curnextx < 0){
                        curdir = "";
                        TextDirections.set(i, "dobla a la izquierda " + "\n");
                    }
                }
                // oc is movement upwards
                if(oldcury < 0){
                    // ocn is movement to the right
                    if(curnextx >= 0){
                        curdir = "";
                        TextDirections.set(i, "dobla a la izquierda " + "\n");
                    }
                    // cn is movement to the left
                    if(curnextx < 0){
                        curdir = "";
                        TextDirections.set(i, "dobla a la derecha " + "\n");
                    }
                }
            }
            // cny more significant than cnx
            if(cnmorsig == 2){
                // oc is movement downwards
                if(oldcury >= 0){
                    // cn is movement upwards
                    if(curnexty < 0){
                        // turn around case
                    }
                    // cn is movement downwards
                    if(curnexty >= 0){
                        if(curdir != "Straight"){
                            curdir = "Straight";
                            TextDirections.set(i, "ir recto hasta el final del pasillo " + "\n");
                        }
                    }
                }
                // oc is movement upwards
                if(oldcury < 0){
                    // cn is movement upwards
                    if(curnexty < 0){
                        if(curdir != "Straight"){
                            curdir = "Straight";
                            TextDirections.set(i, "ir recto hasta el final del pasillo " + "\n");
                        }
                    }
                    // cn is movement downwards
                    if(curnexty >= 0){
                        //turn around case
                    }
                }
            }
        }
        //Return the updated text directions
        return TextDirections;
    }

    //Helper function to generate text for case of reaching an elevator or the destination
    private LinkedList<String> getTextMidHallwayEsp(Point oldnode, Point currnode, Point nextnode,
                                                 String curdir, LinkedList<String> TextDirections, int i ){
        double oldnodex = oldnode.getX();
        double oldnodey = oldnode.getY();
        double currentnodex = currnode.getX();
        double currentnodey = currnode.getY();
        double nextnodex = nextnode.getX();
        double nextnodey = nextnode.getY();

        double oldcurx = currentnodex - oldnodex;
        double oldcury = currentnodey - oldnodey;
        double curnextx = nextnodex - currentnodex;
        double curnexty = nextnodey - currentnodey;

        double ocmorsig = 0;
        double cnmorsig = 0;

        ocmorsig = moresig(oldcurx, oldcury);
        cnmorsig = moresig(curnextx, curnexty);

        //System.out.println("Edge getText " + ocmorsig + "" + cnmorsig);

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
                    // cn is movement downwards
                    if(curnexty >= 0){
                        curdir = "";
                        TextDirections.set(i, "dobla a la izquierda en el pasillo actual " + "\n");
                    }
                    // cn is movement upwards
                    if(curnexty < 0){
                        curdir = "";
                        TextDirections.set(i, "dobla a la derecha en el pasillo actual " + "\n");
                    }
                }
                // oc is movement to the left
                if(oldcurx < 0){
                    // cn is movement downwards
                    if(curnexty >= 0){
                        curdir = "";
                        TextDirections.set(i, "dobla a la derecha en el pasillo actual " + "\n");
                    }
                    // cn is movement upwards
                    if(curnexty < 0){
                        curdir = "";
                        TextDirections.set(i, "dobla a la izquierda en el pasillo actual " + "\n");
                    }
                }
            }
        }

        // ocy more significant than ocx
        if(ocmorsig == 2){
            // cnx more significant than cny
            if(cnmorsig == 1){
                // oc is movement downwards
                if(oldcury >= 0){
                    // cn is movement to the right
                    if(curnextx >= 0){
                        curdir = "";
                        TextDirections.set(i, "dobla a la derecha en el pasillo actual " + "\n");
                    }
                    // cn is movement to the left
                    if(curnextx < 0){
                        curdir = "";
                        TextDirections.set(i, "dobla a la izquierda en el pasillo actual " + "\n");
                    }
                }
                // oc is movement upwards
                if(oldcury < 0){
                    // ocn is movement to the right
                    if(curnextx >= 0){
                        curdir = "";
                        TextDirections.set(i, "dobla a la izquierda en el pasillo actual " + "\n");
                    }
                    // cn is movement to the left
                    if(curnextx < 0){
                        curdir = "";
                        TextDirections.set(i, "dobla a la derecha en el pasillo actual " + "\n");
                    }
                }
            }
            // cny more significant than cnx
            if(cnmorsig == 2){
                //shouldnt happen due to node layout
            }
        }
        return TextDirections;
    }
}
