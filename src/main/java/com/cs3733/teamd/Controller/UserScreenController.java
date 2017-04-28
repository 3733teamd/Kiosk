package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Main;
import com.cs3733.teamd.Model.*;
import com.cs3733.teamd.Model.Entities.Directory;
import com.cs3733.teamd.Model.Entities.DirectoryInterface;
import com.cs3733.teamd.Model.Entities.Node;
import com.cs3733.teamd.Model.Entities.Professional;
import com.cs3733.teamd.Model.Entities.Tag;
import com.cs3733.teamd.Controller.KioskGame.src.game.GameMain;
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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;
import java.lang.Object;
import javafx.application.Platform;


import java.io.IOException;
import java.util.*;

/**
 * Created by Anh Dao on 4/6/2017.
 */
public class UserScreenController extends MapController {

    //connect to facade
    DirectoryInterface dir = Directory.getInstance();

    //shift nodes to align with image
    private static int USERSCREEN_X_OFFSET = 60;
    private static int USERSCREEN_Y_OFFSET = 30;

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
    public Button WalkHospitalButton;
    boolean haveMidFloor = false;
    @FXML
    private Slider floorSlider;
    @FXML
    public Button aboutButton;
    @FXML
    public Button reportButton1;

    public ImageView floorMap;
    public AnchorPane imagePane;
    @FXML
    public Pane mapCanvas;
    public AnchorPane MMGpane;

    //text directions
    @FXML
    //private TextArea directions;
    private ListView<String> directions ;
    private ObservableList<String> dirList;

    @FXML
    private ImageView aboutImage;

    public ImageView reportButton;

    @FXML
    private ComboBox<String> LanguageButton;

    //proxy pattern for maps
    ImageInterface imgInt = new ProxyImage();
    public int floorNum = Main.currentFloor;

    private static LinkedList<Node> pathNodes;
    int onFloor = Main.currentFloor;
    int indexOfElevator = 0;
    String output = "";

    Tag starttag = null;
    private int startfloor = 0;
    private int midfloor = 0;
    private int destfloor = 0;

    final double SCALE_DELTA = 1.1;
    double orgSceneX, orgSceneY;

    public ScrollPane scrollPane;


    LinkedList<Integer> floors = new LinkedList<Integer>();

    public static ObservableList<Integer> floorDropDown = FXCollections.observableArrayList();
    private Map<String, String> tagAssociations;

    //LinkedList<String> languages = new LinkedList<>();
    final String[] languages = new String[] { "English", "\u0045\u0073\u0070\u0061\u00f1\u006f\u006c", "\u0046\u0072\u0061\u006e\u00e7\u0061\u0069\u0073", "\u4e2d\u6587", "\u0050\u006f\u0072\u0074\u0075\u0067\u0075\u00ea\u0073" };

    public static ObservableList<String> languageDropDown = FXCollections.observableArrayList();

    public Boolean languagechange=false;
    int value;

    private class Offset {
        public Offset(int x, int y) {
            this.x = x;
            this.y = y;
        }
        int x;
        int y;
    }

    private Map<Integer, Offset> offsets= new HashMap<Integer, Offset>();

    private void setOffsets() {
        this.offsets.put(1, new Offset(0, 0));
        this.offsets.put(2, new Offset(0, 0));
        this.offsets.put(3, new Offset(0, 0));
        this.offsets.put(4, new Offset(0, -10));
        this.offsets.put(5, new Offset(0, 0));
        this.offsets.put(6, new Offset(0, 0));
        this.offsets.put(7, new Offset(0, 0));
        this.offsets.put(101, new Offset(0, 0));
        this.offsets.put(102, new Offset(0, 0));
        this.offsets.put(103, new Offset(0, 0));
        this.offsets.put(104, new Offset(0, 0));
    }


    @FXML
    private void initialize()
    {
        setOffsets();
        super.initialize(this.scrollPane, this.floorMap, this.mapCanvas);

        System.out.println(dir.getNodes());
        /*
            This code will find all of the tags and then all of the professionals and then merge the two.
            The final result is a list of all the tags and professionals intertwined so that
            a user can see a list of rooms and a list of professionals...
         */
        Map<String, List<String>> professionalTagMerge = new HashMap<String, List<String>>();
        tagAssociations = new HashMap<String, String>();
        for(Tag t: dir.getTags()) {
            // Don't add restricted
            if(t.isRestricted() && (dir.getCurrentUser() == null)) {
                continue;
            }
            professionalTagMerge.put(t.toString(), new ArrayList<String>());
        }
        for(Professional p: dir.getProfessionals()) {
            for(Tag t: p.getTags()) {
                // Don't add restricted
                if(t.isRestricted() && (dir.getCurrentUser() == null)) {
                    continue;
                }
                professionalTagMerge.get(t.toString()).add(p.getName());
            }
        }
        // Now convert it into a list...
        List<String> mergedTagProfessionalList = new ArrayList<String>();
        for(String tag: professionalTagMerge.keySet()) {
            for(String professional: professionalTagMerge.get(tag)) {
                String textDisplay = tag+"-"+professional;
                mergedTagProfessionalList.add(textDisplay);
                tagAssociations.put(textDisplay, tag);
            }
            mergedTagProfessionalList.add(tag);
            tagAssociations.put(tag, tag);
        }

        TextFields.bindAutoCompletion(TypeDestination,mergedTagProfessionalList);
        overrideScrollWheel();
        panMethods();
        // TextFields.bindAutoCompletion(TypeDestination,dir.getTags());
        setSpanishText();
//        directions.setText(output); //dir change type
        floorMap.setImage(imgInt.display(floorNum));
        floors.clear();
        if(ApplicationConfiguration.getInstance().getHospital() == null) {
            if(floors.size() == 0){
                //user views
                floors.addLast(1);
                floors.addLast(2);
                floors.addLast(3);
                floors.addLast(4);
                floors.addLast(5);
                floors.addLast(6);
                floors.addLast(7);
                //belkin house view
                floors.addLast(102);
                floors.addLast(103);
                floors.addLast(104);
                //add belkin professional views
            }
        } else {
            floors.addAll(ApplicationConfiguration.getInstance().getHospital().getFloorSet());
        }


        floorDropDown.clear();
        floorDropDown.addAll(floors);

        if(languageDropDown.size()==0){
            languageDropDown.addAll(languages);
        }
            LanguageButton.setItems(languageDropDown);
            LanguageButton.getSelectionModel().select(Main.bundle.getString("Language"));

        FloorMenu.setItems(floorDropDown);


        /*setLanguageListener();*/

        setFloorMenuListener();
        StartFloorButton.setVisible(false);
        MiddleFloorButton.setVisible(false);
        EndFloorButton.setVisible(false);
        findStartTag();
        //super.addZoomRestriction(3, new ZoomRestriction(1176.0/3000.0,10.0/3000.0,0.63,1.0));
        super.setFloor(onFloor);
        setupMap();

        if(init){
            //originator.setState("/Views/UserScreen.fxml");
            //careTaker.add(originator.saveStateToMemento());
            //added
            //originator.getStateFromMemento(careTaker.get(0));
            //System.out.println("first  "+originator.getState());
            MementoController.addCareTaker("/Views/UserScreen.fxml");
            init=false;
        }

    }



    private void drawStartTagAndTags() {
        if(starttag.getNodes().size() > 0) {
            List<Node> startNodes = starttag.getNodes();
            super.clearNodes();
            super.setNodes(startNodes);
            super.removeConnections();
            boolean restrictedTagsAllowed = false;
            restrictedTagsAllowed = (dir.getCurrentUser() != null);
            for(Tag t: dir.getTags()) {
                // Don't add restricted tag if user can't see them
                if(t.isRestricted() && !restrictedTagsAllowed) {
                    continue;
                }
                if(t != starttag) {
                    for(Node n: t.getNodes()) {
                        super.addCircle(n, 7.0);
                    }
                    super.appendNodes(t.getNodes());
                }
            }
            super.addCircle(startNodes.get(0), 10.0);
            super.drawNodes();
        }
    }

    private void drawPath() {
        TextDirectionGenerator g = new TextDirectionGenerator(pathNodes, onFloor);
        List<String> directionsArray = g.generateTextDirections();
        String output = "";
        for(String directionString: directionsArray) {
            output += directionString + "\n";
        }
        dirList = FXCollections.observableArrayList(directionsArray);
        directions.setItems(dirList);
        directions.setCellFactory(dir -> new ListCell<String>() {
            ImageView iconView = new ImageView();

            int i=0;
            @Override
            protected void updateItem(String dir, boolean empty) {
                super.updateItem(dir,empty);
                if(empty) {
                    setText(null);
                    setGraphic(null);
                } else {
 //                   for (TextDirectionGenerator.Direction d : dirText) {
                        if (dir.contains("proceed from") || dir.contains("Proceed from")) {
                            System.out.println(".PROCEED_FROM_TAG");
                            iconView.setImage(new Image(getClass().getClassLoader().getResourceAsStream("dir_icons/procceed.png")));
                        }
                        else if (dir.contains("straight")) {
                            System.out.println(".go straight");
                            iconView.setImage(new Image(getClass().getClassLoader().getResourceAsStream("dir_icons/straight.png")));
                        } else if (dir.contains("turn left")) {
                            System.out.println(".turn left");
                            iconView.setImage(new Image(getClass().getClassLoader().getResourceAsStream("dir_icons/left.png")));
                        } else if (dir.contains("slight left")) {
                            iconView.setImage(new Image(getClass().getClassLoader().getResourceAsStream("dir_icons/slight left.png")));
                        }else if (dir.contains("turn right")) {
                            iconView.setImage(new Image(getClass().getClassLoader().getResourceAsStream("dir_icons/right.png")));
                        } else if (dir.contains("slight right")) {
                            iconView.setImage(new Image(getClass().getClassLoader().getResourceAsStream("dir_icons/slight right.png")));
                        }else if (dir.contains("arrive")) {
                            iconView.setImage(new Image(getClass().getClassLoader().getResourceAsStream("dir_icons/arrive.png")));
                        }else if (dir.contains("elevator")) {
                            System.out.println(".proccede to elevator");
                            iconView.setImage(new Image(getClass().getClassLoader().getResourceAsStream("dir_icons/elevator.png")));
                        }
                        setGraphic(iconView);
                        iconView.setFitHeight(50);
                        iconView.setFitWidth(50);
                        setText(dir);
                }
//                i=i+1;
            }
        });

//        directions.setText(output);

        super.setNodes(pathNodes);
        super.removeConnections();

        if(startfloor == 0 || destfloor == 0 || midfloor == 0) {
            midfloor = 1;
            startfloor = pathNodes.getLast().getFloor();
            destfloor = pathNodes.getFirst().getFloor();
        }

        // Add in points of interest
        for(int i = 0; i < pathNodes.size(); i++) {
            // Destination
            if(i == 0) {
                super.addCircle(pathNodes.get(0), 7.0);
            }
            if(i < (pathNodes.size() - 1)) {
                if(pathNodes.get(i + 1).getFloor() != pathNodes.get(i).getFloor()) {
                    super.addCircle(pathNodes.get(i), 7.0);
                    //super.addCircle(pathNodes.get(i + 1), 7.0);
                } else {
                    if(pathNodes.get(i).getFloor() == onFloor) {
                        super.connectNode(pathNodes.get(i), pathNodes.get(i+1));
                    }
                }

            }
            if((pathNodes.get(i).getFloor() == midfloor) && (destfloor != midfloor) && (startfloor != midfloor)) {
                haveMidFloor = true;
            }
        }
        //super.addCircle(pathNodes.getLast(), 8.0);

        super.drawNodes();

        StartFloorButton.setVisible(true);
        MiddleFloorButton.setVisible(true);
        EndFloorButton.setVisible(true);

        disableAppropriateFloorButtons();

    }

    private void disableAppropriateFloorButtons() {

        if(onFloor == startfloor){
            StartFloorButton.setDisable(true);
            MiddleFloorButton.setDisable(false);
            EndFloorButton.setDisable(false);
        }else if(onFloor == midfloor){
            StartFloorButton.setDisable(false);
            MiddleFloorButton.setDisable(true);
            EndFloorButton.setDisable(false);
        }else if(onFloor == destfloor){
            StartFloorButton.setDisable(false);
            MiddleFloorButton.setDisable(false);
            EndFloorButton.setDisable(true);
        }else{
            StartFloorButton.setDisable(true);
            MiddleFloorButton.setDisable(true);
            EndFloorButton.setDisable(true);
        }
        if(!haveMidFloor){
            MiddleFloorButton.setDisable(true);
        }
    }

    //Function to set the listener for the floor choice box
    private void setFloorMenuListener(){
        FloorMenu.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                if(new_val != null) {
                    onFloor = new_val.intValue();
                    FloorMenu.setValue(onFloor);
                }
                floorMap.setImage(imgInt.display(onFloor));
                // Notify super class
                setFloor(onFloor);
                output = "";
                dirList = FXCollections.observableArrayList(output);
//                directions.setText(output);
                directions.setItems(dirList);

                System.out.println(onFloor);
               // directions.setText(output);
                //System.out.println(onFloor);

                setupMap();
            }
        });

    }

    @FXML
    private void setLanguageListener() throws IOException {

        //value=(Integer.parseInt( LanguageButton.getValue()));
        System.out.println("val"+LanguageButton.getSelectionModel().getSelectedItem());
            //super.switchLanguage();
        super.switchLanguage(LanguageButton.getSelectionModel().getSelectedItem());

        super.switchScreen(MMGpane,"/Views/UserScreen.fxml");


        setSpanishText();

    }

    private void panMethods() {

        //zoom functions
        imagePane.getChildren();
        floorMap.setPreserveRatio(true);
        final double SCALE_DELTA = 1.1;

        final Group scrollContent = new Group(floorMap, mapCanvas);
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

    //report Bug button pressed
    @FXML
    public void reportBug( ) throws IOException {
        popupScreen(MMGpane, "/Views/ReportBugScreen.fxml", "Report Bug");
    }

    //About button pressed
    @FXML
    public void getAbout( ) throws IOException{
        popupScreen(MMGpane, "/Views/AboutPopupScreen.fxml", "About");
    }

    /**
     * Find's the start tag...
     */
    private void findStartTag() {
        //Gets nodes and tags from directory
        int tagCount = dir.getTags().size();
        int nodeCount = dir.getNodes().size();
        //Makes a temporary holder for values
        Tag currentTag;

        String startTagString = "Kiosk";
        for(int itr = 0; itr < tagCount; itr++){
            currentTag = dir.getTags().get(itr);
            //If match is found create path to node from start nodes
            if(startTagString.equals(currentTag.getTagName())){
                starttag = currentTag;
            }

        }
    }


    private void overrideScrollWheel() {
        scrollPane.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                if (event.getDeltaY() != 0.0) {
                    zoomPercent = (zoomPercent + (event.getDeltaY()/2.5));

                    double xPercent = event.getX()/IMAGE_WIDTH;
                    System.out.println(event.getX());
                    double yPercent = event.getY()/IMAGE_HEIGHT;
                    /*System.out.println(
                            "Percent: "+zoomPercent+" X:" +
                                    getImageXFromZoom(event.getX())
                                    +" Y: "+getImageYFromZoom(event.getY()));*/

                    //scales with scroll wheel
                    setBarPositions(xPercent, yPercent, (event.getDeltaY() > 1.0));
                } else {
                    event.consume();
                }
                event.consume();
            }
        });
    }

    //Spanish button to change language to Spanish
    @FXML
    public void onSpanish(ActionEvent actionEvent) throws  IOException{
        //super.switchLanguage();
        //pathNodes = null;
        switchScreen(MMGpane,"/Views/UserScreen.fxml");
        setSpanishText();
    }

    @FXML
    public void onLogin(ActionEvent actionEvent) throws IOException {
        pathNodes=null;
        if(dir.getCurrentUser() != null) {
            dir.logoutUser();
            switchScreen(MMGpane, "/Views/UserScreen.fxml");
        } else {
            switchScreen(MMGpane, "/Views/LoginScreen.fxml");
        }
//        switchScreen(MMGpane, "/Views/UserScreen.fxml");

    }

    //Spanish translation
    public void setSpanishText(){
//        SpanishButton.setText(Main.bundle.getString("spanish"));
       // SearchButton.setText(Main.bundle.getString("search"));
        /*if(dir.getCurrentUser() != null) {
            LoginButton.setText(Main.bundle.getString("Logout"));
        } else {
            LoginButton.setText(Main.bundle.getString("login"));
        }
*/
        //directionLabel.setText(Main.bundle.getString("directions"));
        //EnterDest.setText(Main.bundle.getString("enterDes"));
        //floor.setText(Main.bundle.getString("floor"));

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
    public void onSearch(ActionEvent actionEvent) throws Exception {
        //stores the destination inputted
        Main.DestinationSelected = tagAssociations.get(TypeDestination.getText());

        //Makes a temporary holder for values
        Tag currentTag;
        int tagCount = dir.getTags().size();
        int nodeCount = dir.getNodes().size();

        // Do we have a starting tag???
        //System.out.println(starttag);
        if(starttag != null) {
            // What floor is the Kiosk on?
            Main.currentFloor = starttag.getNodes().getFirst().getFloor();
            //System.out.println("HEre");
            //Iterates through all existing tags
            currentTag = null;

            for(Tag tag: dir.getTags()){
                if (Main.DestinationSelected.equals(tag.getTagName())){
                    currentTag = tag;
                }
            }



            Pathfinder pf = new Pathfinder(starttag.getNodes().getFirst(), currentTag.getNodes());


            pathNodes = pf.shortestPath();
        }
        // Clear the canvas
        //gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        output = "";
      //  directions.setText(output);
        //System.out.println(onFloor);
        dirList = FXCollections.observableArrayList(output);
//        directions.setText(output);
        directions.setItems(dirList);

        System.out.println(onFloor);

        setupMap();

    }

    private void findZoomWithPath() {
        // No path
        if(pathNodes.size() == 0) {
            return;
        }
        double minX = super.IMAGE_WIDTH;
        double maxX = 0;
        double minY = super.IMAGE_HEIGHT;
        double maxY = 0;

        for(Node n: pathNodes) {
            if(n.getFloor() == onFloor) {
                if(n.getX() > maxX) {
                    maxX = n.getX();
                }
                if(n.getX() < minX) {
                    minX = n.getX();
                }
                if(n.getY() > maxY) {
                    maxY = n.getY();
                }
                if(n.getY() < minY) {
                    minY = n.getY();
                }
            }
        }
        // What zoom will we be at?
        double requiredWidth = maxX - minX;
        double requiredHeight = maxY - minY;

        double percentWidth = requiredWidth/super.IMAGE_WIDTH;
        double percentHeight = requiredHeight/super.IMAGE_HEIGHT;

        double zoom = 1.0;

        if(percentHeight > percentWidth) {
            zoom = (1.0/percentHeight);
        } else {
            zoom = (1.0/percentWidth);
        }

        double xPercent = (minX + (requiredWidth/2.0))/super.IMAGE_WIDTH;
        double yPercent = (minY + (requiredHeight/2.0))/super.IMAGE_HEIGHT;

        double zoomPercentNew = (zoom * 100.0)/2.0;

        if(zoomPercentNew < 100.0) {
            zoomPercentNew = 100.0;
        }

        super.setZoomAndBars(zoomPercentNew, xPercent, yPercent);
    }

    //Function to allow the user to change the start to wherever they wish
    @FXML
    public void onSet(ActionEvent actionEvent) throws IOException{
        int tagCount = dir.getTags().size();
        Tag currentTag;
        String startTagString = tagAssociations.get(TypeDestination.getText());

        for (int itr = 0; itr < tagCount; itr++) {
            currentTag = dir.getTags().get(itr);
            //If match is found create path to node from start nodes
            if (startTagString.equals(currentTag.getTagName())) {
                //System.out.println(currentTag.getTagName());
                starttag = currentTag;
                drawStartTagAndTags();
                break;
            }

        }
    }

    @FXML
    //Function to allow the user to change to the starting floor of path
    public  void ShowStart(ActionEvent actionEvent) throws IOException{
        if((pathNodes != null) && (pathNodes.getLast() != null)) {
            onFloor = pathNodes.getLast().getFloor();
            FloorMenu.setValue(onFloor);
            floorMap.setImage(imgInt.display(onFloor));
            //gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
            output = "";
            dirList = FXCollections.observableArrayList(output);
//            directions.setText(output);
            directions.setItems(dirList);

            System.out.println(onFloor);
        //    directions.setText(output);
            //System.out.println(onFloor);

            setupMap();
            disableAppropriateFloorButtons();

        }
    }

    @FXML
    //Function to allow the user to change to the middle floor of path
    public  void ShowMiddle(ActionEvent actionEvent) throws IOException{
        if(midfloor != 0) {
            onFloor = midfloor;
            FloorMenu.setValue(onFloor);
            floorMap.setImage(imgInt.display(onFloor));
            //gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
            output = "";
        //    directions.setText(output);
            //System.out.println(onFloor);
            dirList = FXCollections.observableArrayList(output);
//            directions.setText(output);
            directions.setItems(dirList);

            System.out.println(onFloor);

            setupMap();
            disableAppropriateFloorButtons();

        }
    }

    @FXML
    //Function to allow the user to change to the ending floor of path
    public  void ShowEnd(ActionEvent actionEvent) throws IOException{
        if((pathNodes != null) && (pathNodes.getFirst() != null)) {
            onFloor = pathNodes.getFirst().getFloor();
            FloorMenu.setValue(onFloor);
            floorMap.setImage(imgInt.display(onFloor));
            //gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
            output = "";
            dirList = FXCollections.observableArrayList(output);
            directions.setItems(dirList);
//            directions.setText(output);
            System.out.println(onFloor);

            setupMap();
            disableAppropriateFloorButtons();

        }
    }

    /**
     * Set's up the map
     */
    private void setupMap() {
        // Draw Tags
        if(starttag != null) {
            if(pathNodes == null) {
                super.clearCircleMap();
                drawStartTagAndTags();
            }
        }
        if(pathNodes != null) {
            System.out.println("setupMap()");
            findZoomWithPath();
            drawPath();
        }
    }

    @FXML
    //Function to allow the user to walk through a path
    public  void PlayPath(ActionEvent actionEvent) throws IOException {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new GameMain().start(new Stage());
                }catch(Exception e){
                    System.out.println("Failed to run game");
                }
            }
        });
    }
}
