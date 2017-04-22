package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Main;
import com.cs3733.teamd.Model.*;
import com.cs3733.teamd.Model.Entities.Directory;
import com.cs3733.teamd.Model.Entities.Node;
import com.cs3733.teamd.Model.Entities.Tag;
import javafx.application.Platform;
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
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.util.*;
//TODO deleate connections
//TODO update/ add
//TODO tags
//TODO neighbors
//TODO add inital nodes
//TODO floor changes
//TODO starting location, tag starting location
/**
 * Created by Anh Dao on 4/6/2017.
 */
public class EditMapScreenController extends MapController{


    //color constants for convenience
    private static Color CUR_SELECTED_COLOR = Color.GREEN;
    private static Color OTHER_SELECTED_COLOR = Color.BLUE;
    private static Color DEFAULT_COLOR = Color.RED;
    private static Color ELEVATOR_COLOR = Color.YELLOW;
    private static Color CLICKED_ON_COLOR = Color.BLACK;

    public String errorString = Main.bundle.getString("InvalidAction");

    public boolean loading=false;
    public boolean edgeTail = false;
    public ListView currentTagBox;
    public TextField searchAllTags;
    public ListView allTagBox;
    public Button addTagButton;
    public Button removeTagButton;
    public Button removeNodeButton;
    public Button disconnectNodeButton;
    public Label errorBox;
    public RadioButton chooseDFSButton;
    public RadioButton chooseBFSButton;
    public RadioButton chooseAStarButton;
    public ToggleGroup algSelectGorup;

    public Button bugReports;

    //public Label errorBox;
    Directory dir = Directory.getInstance();
    ApplicationConfiguration config = ApplicationConfiguration.getInstance();

    public Button EditProf;
    public Button EditTag;
    public Button LoginButton;
    public Button CreateUserButton;
    public Button SpanishButton;
    public Button BackButton;
    public Button addNode;
    public Button connectNode;
    public Label xLoc;
    public Label yLoc;
    public ImageView floorMap;
    public TextArea tagList;
    public TextArea neighborsList;
    public AnchorPane MMGpane;
    public AnchorPane imagePane;
    public TextField addTag;
    public ChoiceBox FloorMenu;
    @FXML
    private TextField timeoutField;

    //HashMap<List<CircleNode>,Line> circleLines;

    public ScrollPane scrollPane;

    @FXML
    private Button disconnectNodeBtn;

    LinkedList<CircleNode> selectedCircles = new LinkedList<CircleNode>();
    LinkedList<Line> floorLines = new LinkedList<Line>();
    LinkedList<CircleNode> floorCircs = new LinkedList<CircleNode>();
    HashMap<Node, CircleNode> circleMap = new HashMap<Node, CircleNode>();

    /*replaced with proxy pattern*/
//    public Map<Integer, Image> imageHashMap = new HashMap<>();
    ImageInterface imgInt = new ProxyImage();

    private Tag selectedTag;
    private Tag selectedCurrentTag;

    double orgSceneX, orgSceneY;

    public Node s1 = new Node(1,1,0);
    public Node s2 = new Node(1,1,0);
    public CircleNode select1 = null; //=createCircle(s1,1,Color.TRANSPARENT);
    public CircleNode select2 = null;//=createCircle(s2,1,Color.TRANSPARENT);

    public int s;
    public int sa;
    public CircleNode scirc;
    public Boolean switchS =true;
    public int floor =4;
    final double SCALE_DELTA = 1.1;
    int onFloor = Main.currentFloor;




    @FXML
    private Pane mapCanvas;

    private List<Node> nodeList = dir.getNodes();
    private List<Tag> allTheTags =dir.getTags();
    List<String> allTagNames = new ArrayList<String>();
    int i=50;

    LinkedList<Integer> floors = new LinkedList<Integer>();
    public static ObservableList<Integer> floorDropDown = FXCollections.observableArrayList();

    private void setAlgGroupListener() {
        chooseAStarButton.setUserData(ApplicationConfiguration.SearchAlgorithm.A_STAR);
        chooseDFSButton.setUserData(ApplicationConfiguration.SearchAlgorithm.DFS);
        chooseBFSButton.setUserData(ApplicationConfiguration.SearchAlgorithm.BFS);

        switch (config.getCurrentSearchAlgorithm()){
            case A_STAR:
                algSelectGorup.selectToggle(chooseAStarButton);
                break;
            case DFS:
                algSelectGorup.selectToggle(chooseDFSButton);
                break;
            case BFS:
                algSelectGorup.selectToggle(chooseBFSButton);
                break;
        }


        algSelectGorup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                if (new_toggle != null) {
                    config.setCurrentSearchAlgorithm((ApplicationConfiguration.SearchAlgorithm) new_toggle.getUserData());
                }
            }
        });
    }
    //timeout
    Timer timer = new Timer();
    int counter = 0;
    private volatile boolean running = true;

    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            counter++;
            //System.out.println("edit map" + counter);
        }
    };

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (running) {
                try {

                    if (counter == MementoController.timeoutTime) {
                        running = false;
                        timer.cancel();
                        timerTask.cancel();
                        Platform.runLater(resetKiosk);
                        break;
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException exception) {
                    timer.cancel();
                    timerTask.cancel();
                    running = false;
                    break;
                }
            }
        }
    };
    Thread timerThread = new Thread(runnable);
    Runnable resetKiosk = new Runnable() {
        @Override
        public void run() {
            timer.cancel();
            timer.purge();
            running = false;
            timerThread.interrupt();

            //logout user
            dir.logoutUser();
            try {
               /* originator.getStateFromMemento(careTaker.get(0));
                switchScreen(MMGpane, originator.getState());*/
                MementoController.toOriginalScreen(MMGpane);
                MementoController.originator.getStateFromMemento(MementoController.careTaker.get(0));
                switchScreen(MMGpane, MementoController.originator.getState());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    };

    @FXML
    public void initialize(){
        super.initialize(this.scrollPane, this.floorMap, this.mapCanvas);

        this.zoomPercent = 100.0;
        if(ApplicationConfiguration.getInstance().timeoutEnabled()) {
            timer.scheduleAtFixedRate(timerTask, 30, 1000);
            timerThread.start();
        }
        setAlgGroupListener();
        setFloorSliderListener();
        overrideScrollWheel();
        panMethods();
        timer.scheduleAtFixedRate(timerTask, 30, 1000);
        timerThread.start();
        setKeyListeners();

        MMGpane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                counter = 0;
                System.out.println("counter resets");
            }
        });
        MMGpane.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                counter = 0;
            }
        });
        MMGpane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                counter = 0;
            }
        });
        MMGpane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                counter = 0;
            }
        });

        errorBox.setText("");
        xLoc.setText("");
        yLoc.setText("");
        //String[] sug= {"app","cat", "orage", "adsdf", " ddddd", "ddees"};
        initializeCircleMap();

        allTagBox.setItems(FXCollections.observableList(allTheTags));
        System.out.println(floor);
        //if floor<100 its falkner, so display the prof verions
        if(floor<100) {
            floorMap.setImage(imgInt.display(floor + 1000));
        }else{
            floorMap.setImage(imgInt.display(floor));
        }
        for (int i = 0 ; i<allTheTags.size(); i++) {
            allTagNames.add(allTheTags.get(i).getTagName());
        }
        TextFields.bindAutoCompletion(searchAllTags,allTagNames);
        connectNode.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER && select1 != null && select2 != null)  {
                    connectNode(select1,select2);
                }
            }
        });



        FloorMenu.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                if(new_val!=null) {
                    floor = new_val.intValue();
                    FloorMenu.setValue(floor);
                    System.out.println(floor);

                    //if floor<100 its falkner, so display the prof verions
                    if (floor < 100) {
                        floorMap.setImage(imgInt.display(floor + 1000));
                    } else {
                        floorMap.setImage(imgInt.display(floor));
                    }

                }
                imagePane.getChildren().removeAll(floorCircs);
                imagePane.getChildren().removeAll(floorLines);
                floorCircs.clear();
                floorLines.clear();

                drawfloorNodes();

            }
        });

        floors.clear();
        if(floors.size() == 0){
            floors.addLast(1);
            floors.addLast(2);
            floors.addLast(3);
            floors.addLast(4);
            floors.addLast(5);
            floors.addLast(6);
            floors.addLast(7);
            floors.addLast(102);
            floors.addLast(103);
            floors.addLast(104);
        }
        floorDropDown.clear();

        floorDropDown.addAll(floors);
        FloorMenu.setItems(floorDropDown);
        FloorMenu.setValue(floorDropDown.get(0));

        allTagBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tag>() {
            @Override
            public void changed(ObservableValue<? extends Tag> observable,
                                Tag oldValue, Tag newValue) {

                selectedTag = newValue;
            }


        });
        currentTagBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tag>() {
            @Override
            public void changed(ObservableValue<? extends Tag> observable,
                                Tag oldValue, Tag newValue) {

                selectedCurrentTag = newValue;
            }


        });

        drawfloorNodes();

        if(ApplicationConfiguration.getInstance().getCurrentLanguage()
                == ApplicationConfiguration.Language.SPANISH){
            EditTag.setFont(Font.font("System",14));
            LoginButton.setFont(Font.font("System",14));
            CreateUserButton.setFont(Font.font("System",14));

        } else {
            EditTag.setFont(Font.font("System",20));
            LoginButton.setFont(Font.font("System",20));
            CreateUserButton.setFont(Font.font("System",20));
        }

        //drawfloorNodes();
//
        timeoutField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER && !timeoutField.getText().isEmpty())  {
                        if(timeoutField.getText().matches("-?\\d+(\\.\\d+)?")){
                            MementoController.timeoutTime=Integer.parseInt(timeoutField.getText());
                            System.out.println("timeout"+ MementoController.timeoutTime);
                        }

                }
            }
        });

    }//initialize end

    private void setFloorSliderListener(){

        FloorMenu.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                if(new_val!=null && old_val!=null)
                floor = new_val.intValue();
                FloorMenu.setValue(floor);
                //floorMap.setImage(imageHashMap.get(floor));
                floorMap.setImage(imgInt.display(floor));

                /*//TODO: heart of error
                imagePane.getChildren().removeAll(floorCircs);
                imagePane.getChildren().removeAll(floorLines);
                floorCircs.clear();
                floorLines.clear();
*/
                drawfloorNodes();

            }
        });
    }
    private void panMethods(){

        //zoom functions
        //imagePane.getChildren();
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
                if(event.isSecondaryButtonDown()){
                    if(!event.isShiftDown()){
                        deselectAllNodes();
                    }else{
                        deselectMostRecentNode();
                    }

                }
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

    private void deselectMostRecentNode() {
        selectedCircles.getLast().setFill(selectedCircles.getLast().defaultColor);
        selectedCircles.removeLast();
        selectedCircles.getLast().setFill(CUR_SELECTED_COLOR);
        currentTagBox.setItems(FXCollections.observableList(selectedCircles.getLast().referenceNode.getTags()));
        currentTagBox.refresh();
    }

    private void overrideScrollWheel() {
        scrollPane.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                if (event.getDeltaY() != 0.0) {
                    zoomPercent = (zoomPercent + (event.getDeltaY()/2.5));
                    if(zoomPercent < 100.0) {
                        zoomPercent = 100.0;
                    } else if(zoomPercent > 500.0) {
                        zoomPercent = 500.0;
                    }
                    double xPercent = event.getX()/IMAGE_WIDTH;
                    double yPercent = event.getY()/IMAGE_HEIGHT;
                    System.out.println(
                            "Percent: "+zoomPercent+" X:" +
                            getImageXFromZoom(event.getX())
                            +" Y: "+getImageYFromZoom(event.getY()));

                    //scales with scroll wheel
                    setZoomAndScale(xPercent, yPercent, (event.getDeltaY() > 1.0));
                } else {
                    event.consume();
                }
                event.consume();
            }
        });
    }
    //Login button
    @FXML
    public void onCreateUser(ActionEvent actionEvent) throws IOException {
        timer.cancel();
        timer.purge();
        running = false;
        timerThread.interrupt();
        switchScreen(MMGpane, "/Views/CreateUserScreen.fxml");
    }

    //Back button
    @FXML
    public void onBack(ActionEvent actionEvent) throws  IOException{
        timer.cancel();
        timer.purge();
        running = false;
        timerThread.interrupt();
        dir.logoutUser();
        switchScreen(MMGpane, "/Views/UserScreen.fxml");
    }
    @FXML
    public void Logout() throws IOException{
        timer.cancel();
        timer.purge();
        running = false;
        timerThread.interrupt();
        dir.logoutUser();
        switchScreen(MMGpane, "/Views/UserScreen.fxml");
    }

    @FXML
    public void toEditProf() throws  IOException{
        timer.cancel();
        timer.purge();
        running = false;
        timerThread.interrupt();
        switchScreen(MMGpane, "/Views/EditProfScreen.fxml");
    }
    @FXML
    public void toEditTag() throws  IOException{
        timer.cancel();
        timer.purge();
        running = false;
        timerThread.interrupt();
        switchScreen(MMGpane, "/Views/EditTagScreen.fxml");
    }
    @FXML
    public void addNodeButtonPressed(){
        addNode(50,50);

    }
    private void addNode(int x, int y){
        CircleNode circ = createCircle(dir.saveNode(x,y,floor), 5, DEFAULT_COLOR);
        floorCircs.add(circ);
        mapCanvas.getChildren().add(circ);
    }
    @FXML
    public void connectNodePressed(){
        connectAllSelectedNodes();
    }

    private void connectNode(CircleNode s1, CircleNode s2){

        if((s1.referenceNode.getFloor() == s2.referenceNode.getFloor()) || edgeTail) {

            Line line = makeLineForCircleNodes(s1, s2);
            line.setStyle("-fx-stroke: red;");
            s1.lineMap.put(s2, line);
            s2.lineMap.put(s1, line);

            if (loading == false) {
                boolean response = dir.saveEdge(s1.referenceNode, s2.referenceNode);
                if (response == false) {
                    errorBox.setText(errorString);
                } else {
                    errorBox.setText("");
                }
            }

            if (s1.referenceNode.getFloor() != s2.referenceNode.getFloor()) {
                line.setFill(Color.YELLOW);
                line.setStrokeWidth(2);
                mapCanvas.getChildren().add(line);
            } else {
                mapCanvas.getChildren().add(line);
            }
        }
    }

    private CircleNode createCircle(Node n, double r, Color color) {
        System.out.println("Node ID:"+n.getID()+" x: "+n.getX()+" y: "+n.getY());
        CircleNode circle = new CircleNode(n.getX(), n.getY(), r, color,n);

        circleMap.put(n, circle);

        circle.setCursor(Cursor.HAND);

        circle.setOnMousePressed((mouse) -> {
            if(mouse.isPrimaryButtonDown()) {
                scrollPane.setPannable(false);
                orgSceneX = mouse.getSceneX();
                orgSceneY = mouse.getSceneY();

                CircleNode c = (CircleNode) (mouse.getSource());
                c.toFront();
                xLoc.setText(new Integer((int) c.getCenterX()).toString());
                yLoc.setText(new Integer((int) c.getCenterY()).toString());



                if(!mouse.isShiftDown()){
                    deselectAllNodes();
                    addNodeToSelection(c);

                }else{
                    addNodeToSelection(c);
                }
                scirc = c;
                /*
                if (select1 == null) {
                    select1 = c;
                    s1.setID(n.getID());
                    s = n.getID();
                    c.setFill(CLICKED_ON_COLOR);
                } else {
                    if (select2 != null) {
                        if (select2.referenceNode.hasElevator()) {
                            select2.setFill(ELEVATOR_COLOR);
                        } else {
                            select2.setFill(DEFAULT_COLOR);
                        }
                    }

                    select2 = select1;
                    select1 = c;
                    s = n.getID();
                    c.setFill(CLICKED_ON_COLOR);
                }
                */
            }
        });
        circle.setOnMouseReleased((t)->{

            /*
            if(circle.referenceNode.hasElevator()){
                circle.setFill(ELEVATOR_COLOR);
            }else {
                circle.setFill(DEFAULT_COLOR);
            }

            select1.setFill(Color.GREEN);
            if(select2 != null && select1 != select2){
                select2.setFill(Color.BLUE);
            }

            updatePosition(t);
            */
            scrollPane.setPannable(true);

        });

        circle.setOnMouseDragged((t) -> {
            //if(state ==states.add) {
            double offsetX = t.getSceneX() - orgSceneX;
            double offsetY = t.getSceneY() - orgSceneY;

            Circle c = (Circle) (t.getSource());

            c.setCenterX((c.getCenterX() + offsetX/mapCanvas.getScaleX()));//*1091/mapCanvas.getScaleX());
            c.setCenterY((c.getCenterY() + offsetY/mapCanvas.getScaleY()));

            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();

            xLoc.setText(new Integer((int) c.getCenterX()).toString());
            yLoc.setText(new Integer((int) c.getCenterY()).toString());

            //}
        });
        return circle;
    }

    private void addNodeToSelection(CircleNode c) {
        //order matters so we know what the chain is to connect them
        for(CircleNode cn : selectedCircles){
            //stub
        }
        //colors

        currentTagBox.setItems(FXCollections.observableList(c.referenceNode.getTags()));
        currentTagBox.refresh();

        if(!selectedCircles.isEmpty()) {
            selectedCircles.getLast().setFill(OTHER_SELECTED_COLOR);
        }
        selectedCircles.addLast(c);
        selectedCircles.getLast().setFill(CUR_SELECTED_COLOR);

    }

    private void deselectAllNodes() {
        for(CircleNode cn : selectedCircles){
            cn.setFill(cn.defaultColor);
        }
        selectedCircles.clear();
    }

    private void connectAllSelectedNodes(){
        for(int i = 0; i<selectedCircles.size()-1;i++){
            connectNode(selectedCircles.get(i),selectedCircles.get(i+1));
        }
    }
    private void disconnectAllSelectedNodes(){
        for(CircleNode cn: selectedCircles){
            for(CircleNode other: selectedCircles){
                try{
                    disconnectCircleNodes(cn, other);
                }catch(NullPointerException e){

                }
            }
        }
    }

    private Line makeLineForCircleNodes(CircleNode c1, CircleNode c2) {
        Line line = new Line();
        floorLines.add(line);

        line.startXProperty().bind(c1.centerXProperty());
        line.startYProperty().bind(c1.centerYProperty());

        line.endXProperty().bind(c2.centerXProperty());
        line.endYProperty().bind(c2.centerYProperty());

        line.setStrokeWidth(1);
        line.setStrokeLineCap(StrokeLineCap.BUTT);

        return line;
    }

    private void updatePosition(MouseEvent m){
        select1.referenceNode.setCoord((int)select1.getCenterX(),(int)select1.getCenterY());

        boolean response = dir.updateNode(select1.referenceNode);

        if(response){
            errorBox.setText("");
        }else{
            errorBox.setText(errorString);
        }

    }

    @FXML
    private void clickedOnPane(MouseEvent m){
        if(m.getButton().equals(MouseButton.PRIMARY) && m.getClickCount() == 2 ){
            addNode((int)m.getX(),(int)m.getY());
        }
    }

    private void initializeCircleMap(){
        for(Node n : dir.getNodes()){
            if(n.hasElevator()) {
                CircleNode circ = createCircle(n, 5, ELEVATOR_COLOR);
            }else{
                CircleNode circ = createCircle(n, 5, DEFAULT_COLOR);
            }
        }
    }

    private void drawfloorNodes(){
        mapCanvas.getChildren().clear();
        for(Node n: dir.getNodes()){
            if(n.getFloor()==floor){
                //CircleNode circ = createCircle(n, 5, Color.RED);
                try {
                    mapCanvas.getChildren().add(circleMap.get(n));
                    floorCircs.add(circleMap.get(n));
                }catch (IllegalArgumentException e){
                    System.out.println(e);
                }
            }
        }

        if(select1 != null && select2 != null) {
            System.out.println(select1.toString() + " " + select2.toString());
        }

        //setConnectingTags();

        for(int i=0; i<circleMap.size(); i++){
            CircleNode circ = circleMap.get(circleMap.keySet().toArray()[i]);
            Node n = circ.referenceNode;
            //select1 = circ;


            if(n.getFloor()==floor){

                //for (int j=0; j<circ.referenceNode.getNodes().size(); j++){
                for (Node n2 : circ.referenceNode.getNodes()){

                    CircleNode circ2 = circleMap.get(n2);
                    //select2 = circ2;
                    loading = true;
                    connectNode(circ,circ2);
                    loading = false;

                }
            }

        }

        if(select1 != null && select2 != null) {
            System.out.println(select1.toString() + " " + select2.toString());
        }
    }

    @FXML
    public void addTagToCurrentNode(ActionEvent actionEvent) {
        if(selectedCircles.getLast() != null){
            boolean response = dir.addNodeTag(selectedCircles.getLast().referenceNode,selectedTag);
            if(response){
                errorBox.setText("");
                currentTagBox.setItems(FXCollections.observableArrayList(selectedCircles.getLast().referenceNode.getTags()));
            }else{
                errorBox.setText(errorString);
            }

            selectedTag.updateConnections();
            drawfloorNodes();
            currentTagBox.refresh();
        }

    }
    @FXML
    public void removeTagFromCurrentNode(ActionEvent actionEvent) {
        if(selectedCurrentTag != null){
            //setConnectingTags();
            // Remove Connecting Tags...
            for(Node n2: select1.referenceNode.getNodes()) {
                if(n2.getFloor() != select1.referenceNode.getFloor()) {
                    dir.deleteEdge(select1.referenceNode, n2);
                }
            }
            boolean response = dir.removeNodeTag(select1.referenceNode,selectedCurrentTag);
            if(response){
                errorBox.setText("");
                currentTagBox.setItems(FXCollections.observableArrayList(select1.referenceNode.getTags()));
            }else{
                errorBox.setText(errorString);
            }


            drawfloorNodes();
            currentTagBox.refresh();
        }
    }

    public void disconnectCircleNodesButton(ActionEvent actionEvent) {
        disconnectAllSelectedNodes();
    }

    private void disconnectCircleNodes(CircleNode cn1, CircleNode cn2){
        boolean response = dir.deleteEdge(cn1.referenceNode,cn2.referenceNode);
        if(response){
            errorBox.setText("");
            Line l = cn1.lineMap.get(cn2);
            mapCanvas.getChildren().remove(l);
            System.out.println(cn1.lineMap.size());
            cn1.lineMap.remove(cn2);
            cn2.lineMap.remove(cn1);
            drawfloorNodes();
        }else{
            errorBox.setText(errorString);
        }
    }

    public void removeCircleNodePressed(ActionEvent actionEvent) {
        deleteAllSelectedNodes();
    }

    private void removeCircleNode(CircleNode cn){
        if(dir.deleteNode(cn.referenceNode)){
            errorBox.setText("");
            mapCanvas.getChildren().remove(cn);
        }else{
            errorBox.setText(errorString);
        }
    }

    public void doneDrag(DragEvent dragEvent) {

    }

    //Spanish button to change language to Spanish
    @FXML
    public void toSpanish(ActionEvent actionEvent) throws  IOException{
        super.switchLanguage();
        switchScreen(MMGpane,"/Views/EditMapScreen.fxml");
    }

    public void setConnectingTags(){
        for (Tag t: dir.getTags()){
            t.updateConnections();
        }
    }

    public void viewBugReports(ActionEvent actionEvent) throws IOException {
        popupScreen(MMGpane, "/Views/ViewBugScreen.fxml", "View Bug Reports");
    }


    private void setKeyListeners() {
        MMGpane.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                switch (ke.getCode()){
                    case DELETE:
                        if(!ke.isShiftDown()){
                            deleteAllSelectedNodes();
                        }else{
                            forceDeleteAllSelectedNodes();
                        }

                        break;
                    case BACK_SPACE:
                        disconnectAllSelectedNodes();
                        break;
                    case ENTER:
                        connectAllSelectedNodes();
                        break;
                }

            }
        });
    }

    private void forceDeleteAllSelectedNodes() {
        disconnectAllSelectedNodes();
        deleteAllSelectedNodes();
    }

    private void deleteAllSelectedNodes() {
        for(CircleNode cn : selectedCircles){
            removeCircleNode(cn);
        }
    }


}
