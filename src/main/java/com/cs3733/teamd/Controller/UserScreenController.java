package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Main;
import com.cs3733.teamd.Model.*;
import com.cs3733.teamd.Model.Entities.Directory;
import com.cs3733.teamd.Model.Entities.DirectoryInterface;
import com.cs3733.teamd.Model.Entities.Node;
import com.cs3733.teamd.Model.Entities.Tag;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
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
import org.controlsfx.control.textfield.TextFields;


import java.awt.Point;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Anh Dao on 4/6/2017.
 */
public class UserScreenController extends AbsController{

    //connect to facade
    DirectoryInterface dir = Directory.getInstance();

    //shift nodes to align with image
    private static int USERSCREEN_X_OFFSET = -5;
    private static int USERSCREEN_Y_OFFSET = -90;

    //Boundary objects
    @FXML
    public Button LoginButton;
    public Button SpanishButton;
    public Button SearchButton;
    public Button SetButton;
    public TextField TypeDestination;
    public Text EnterDest;
    public Text floor;
    public Label directionLabel;
    public ChoiceBox FloorMenu;
    public Button StartFloorButton;
    public Button MiddleFloorButton;
    public Button EndFloorButton;
    @FXML
    public ImageView floorMap;
    public AnchorPane imagePane;
    public Canvas MapCanvas;
    public AnchorPane MMGpane;
    @FXML
    private TextArea directions;
    public GraphicsContext gc;

    //proxy pattern for maps
    ImageInterface imgInt = new ProxyImage();
    public int floorNum = Main.currentFloor;

    private static LinkedList<Node> pathNodes;
    int onFloor = Main.currentFloor;
    int indexOfElevator = 0;
    String output = "";
    Tag starttag = null;
    private int startfloor = 0;
    private int destfloor = 0;

    final double SCALE_DELTA = 1.1;
    double orgSceneX, orgSceneY;

    public ScrollPane scrollPane;



    private int midfloor = 0;
    LinkedList<Integer> floors = new LinkedList<Integer>();
    public static ObservableList<Integer> floorDropDown = FXCollections.observableArrayList();

    @FXML
    private void initialize()
    {
        overrideScrollWheel();
        panMethods();
        TextFields.bindAutoCompletion(TypeDestination,dir.getTags());
        setSpanishText();
        directions.setText(output);
        floorMap.setImage(imgInt.display(floorNum));
        if(floors.size() == 0){
            floors.addLast(1);
            floors.addLast(2);
            floors.addLast(3);
            floors.addLast(4);
            floors.addLast(5);
            floors.addLast(6);
            floors.addLast(7);
        }
        floorDropDown.addAll(floors);
        FloorMenu.setItems(floorDropDown);
        FloorMenu.setValue(floorDropDown.get(0));
        setFloorMenuListener();
        StartFloorButton.setVisible(false);
        MiddleFloorButton.setVisible(false);
        EndFloorButton.setVisible(false);

        gc = MapCanvas.getGraphicsContext2D();
        if(pathNodes != null) {
            draw();
        }
    }

    //Function to set the listener for the floor choice box
    private void setFloorMenuListener(){
        FloorMenu.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                onFloor = new_val.intValue();
                FloorMenu.setValue(onFloor);
                floorMap.setImage(imgInt.display(onFloor));
                gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
                output = "";
                directions.setText(output);
                System.out.println(onFloor);

                if(pathNodes != null) {
                    draw();


                }
            }
        });

    }


    private void panMethods(){

        //zoom functions
        imagePane.getChildren();
        floorMap.setPreserveRatio(true);
        final double SCALE_DELTA = 1.1;

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
                if (event.getButton() == MouseButton.SECONDARY) {
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
            }
        });


    }

    private void overrideScrollWheel() {
        scrollPane.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                double scaleFactor = 0;
                if (event.getDeltaY() > 0) {
                    scaleFactor = SCALE_DELTA;


                } else if (event.getDeltaY() < 0) {
                    scaleFactor = 1 / SCALE_DELTA;
                } else {
                    event.consume();
                }

                floorMap.setScaleX(floorMap.getScaleX() * scaleFactor);
                floorMap.setScaleY(floorMap.getScaleY() * scaleFactor);
                MapCanvas.setScaleX(MapCanvas.getScaleX() * scaleFactor);
                MapCanvas.setScaleY(MapCanvas.getScaleY() * scaleFactor);
                event.consume();
            }
        });
    }

    //Spanish button to change language to Spanish
    @FXML
    public void onSpanish(ActionEvent actionEvent) throws  IOException{
        super.switchLanguage();
        pathNodes = null;
        switchScreen(MMGpane,"/Views/UserScreen.fxml");
        setSpanishText();
    }

    @FXML
    public void onLogin(ActionEvent actionEvent) throws IOException {
        pathNodes=null;
        switchScreen(MMGpane, "/Views/UserScreen.fxml");
        switchScreen(MMGpane, "/Views/LoginScreen.fxml");
    }

    //Spanish translation
    public void setSpanishText(){
        SpanishButton.setText(Main.bundle.getString("spanish"));
        SearchButton.setText(Main.bundle.getString("search"));
        LoginButton.setText(Main.bundle.getString("login"));
        directionLabel.setText(Main.bundle.getString("directions"));
        EnterDest.setText(Main.bundle.getString("enterDes"));
        floor.setText(Main.bundle.getString("floor"));

        if(ApplicationConfiguration.getInstance().getCurrentLanguage()
                == ApplicationConfiguration.Language.SPANISH){
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
        int tagCount = dir.getTags().size();
        int nodeCount = dir.getNodes().size();
        //Makes a temporary holder for values
        Tag currentTag;

        String startTagString = "Kiosk";
        if(starttag == null) {
            for (int itr = 0; itr < tagCount; itr++) {
                currentTag = dir.getTags().get(itr);
                //If match is found create path to node from start nodes
                if (startTagString.equals(currentTag.getTagName())) {
                    starttag = currentTag;
                }

            }
        }
        // Do we have a starting tag???
        if(starttag != null) {
            // What floor is the Kiosk on?
            Main.currentFloor = starttag.getNodes().getFirst().getFloor();
            System.out.println("HEre");
            //Iterates through all existing tags
            for (int itr = 0; itr < tagCount; itr++) {

                currentTag = dir.getTags().get(itr);
                // Have we found our destination?
                if (Main.DestinationSelected.equals(currentTag.getTagName())) {
                    double record = 9999999999999999999999.0;
                    Pathfinder bestPath = null;

                    for (Node n : currentTag.getNodes()) {
                        // Let's find the shortest path
                        Pathfinder attempt = new Pathfinder(starttag.getNodes().getFirst(), n);
                        if (attempt.pathLength(attempt.shortestPath()) < record) {
                            bestPath = attempt;
                        }
                    }
                    // Is there a path from the source to the destination?
                    if (bestPath != null) {
                        pathNodes = bestPath.shortestPath();
                    } else {
                        System.out.println("Failed to find best path out of many\n");
                        Pathfinder pathfinder = new Pathfinder(
                                starttag.getNodes().getFirst(),
                                currentTag.getNodes().getFirst()
                        );
                        //use the shortest path
                        pathNodes = pathfinder.shortestPath();
                    }
                }
            }
        }
        // Clear the canvas
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        output = "";
        directions.setText(output);
        System.out.println(onFloor);

        if(pathNodes != null) {
            draw();
        }

    }

    @FXML
    //Function to allow the user to change the start to wherever they wish
    public  void onSet(ActionEvent actionEvent) throws IOException{
        int tagCount = dir.getTags().size();
        Tag currentTag;
        String startTagString = TypeDestination.getText();

        for (int itr = 0; itr < tagCount; itr++) {
            currentTag = dir.getTags().get(itr);
            //If match is found create path to node from start nodes
            if (startTagString.equals(currentTag.getTagName())) {
                starttag = currentTag;
            }

        }
    }

    @FXML
    //Starts path displaying process
    private void draw(){
        plotPath(UserScreenController.pathNodes);
    }

    //Converts a node to a point to display on map
    private Point getConvertedPoint(Node node) { //conversion from database to canvas
        int x = node.getX() + USERSCREEN_X_OFFSET;
        int y = node.getY() + USERSCREEN_Y_OFFSET+25;
        //Point p = new Point((int) ((x-offset_x)/scale), (int) (imageH-(y-offset_y)/scale));
        Point p = new Point(x, y);
        return p;
    }

    //Converts a given path of nodes to a path of points and then draws it
    public void plotPath(LinkedList<Node> path){
        LinkedList<Point> pointsStartFloor = new LinkedList<>();
        LinkedList<Point> pointsMidFloor = new LinkedList<>();
        LinkedList<Point> pointsEndFloor = new LinkedList<>();
        // ensure values are reset
        int index = 0;
        startfloor = 0;
        destfloor = 0;
        midfloor = 0;
        StartFloorButton.setVisible(false);
        MiddleFloorButton.setVisible(false);
        EndFloorButton.setVisible(false);
        startfloor = starttag.getNodes().getFirst().getFloor();
        destfloor = path.getFirst().getFloor();
        System.out.println(destfloor);
        for (Node node: path) {
            if(node.getFloor() == startfloor) {
                System.out.println("Node.getfloor" + node.getFloor());
                System.out.println("plot"+ startfloor);
                pointsStartFloor.add(getConvertedPoint(node));
                index++;
            } else if(node.getFloor() == destfloor){
                System.out.println("Node.getfloor" + node.getFloor());
                pointsEndFloor.add(getConvertedPoint(node));
            }
            else if(node.getFloor() == 1 && startfloor != 1 && destfloor != 1){
                pointsMidFloor.add(getConvertedPoint(node));
            }
        }
        TextDirectionGenerator g = new TextDirectionGenerator(
                path,
                onFloor
        );
        List<String> directionsArray = g.generateTextDirections();
        String output = "";
        for(String directionString: directionsArray) {
            output += directionString + "\n";
        }
        directions.setText(output);
        for(String token: directionsArray) {
            System.out.println(token);
        }
        indexOfElevator = index;
        if(startfloor == onFloor) {
            System.out.println("startfloor");
            drawPathFromPoints(gc, pointsStartFloor);
            EndFloorButton.setVisible(true);
            if(midfloor != 0){
                MiddleFloorButton.setVisible(true);
            }
        }
        
        else if(destfloor == onFloor){
            System.out.println("destfloor");
            drawPathFromPoints(gc, pointsEndFloor);
            StartFloorButton.setVisible(true);
            if(midfloor != 0){
                MiddleFloorButton.setVisible(true);
            }
        }
        else if(midfloor == onFloor){
            System.out.println("midfloor");
            drawPathFromPoints(gc, pointsMidFloor);
            StartFloorButton.setVisible(true);
            EndFloorButton.setVisible(true);
        }
        else{
            StartFloorButton.setVisible(true);
            EndFloorButton.setVisible(true);
            if(midfloor != 0){
                MiddleFloorButton.setVisible(true);
            }
        }
    }

    //Function to actually draw a path
    private void drawPathFromPoints(GraphicsContext gc, LinkedList<Point> path) {
        System.out.println("Drawing");
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

            //Update for next loop
            previous = current;
            //Set intermediate points to be smaller and blue
            gc.setFill(javafx.scene.paint.Color.BLUE);
            radius = 5;
        }
    }

    @FXML
    //Function to allow the user to change to the starting floor of path
    public  void ShowStart(ActionEvent actionEvent) throws IOException{
        if(startfloor != 0) {
            onFloor = startfloor;
            FloorMenu.setValue(onFloor);
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

    @FXML
    //Function to allow the user to change to the middle floor of path
    public  void ShowMiddle(ActionEvent actionEvent) throws IOException{
        if(midfloor != 0) {
            onFloor = midfloor;
            FloorMenu.setValue(onFloor);
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

    @FXML
    //Function to allow the user to change to the ending floor of path
    public  void ShowEnd(ActionEvent actionEvent) throws IOException{
        if(destfloor != 0) {
            onFloor = destfloor;
            FloorMenu.setValue(onFloor);
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
}
