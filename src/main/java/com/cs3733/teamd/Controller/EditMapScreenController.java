package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Main;
import com.cs3733.teamd.Model.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
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

import javax.imageio.ImageIO;
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
public class EditMapScreenController extends AbsController{

    public String errorString = "Invalid Action";

    public ListView currentTagBox;
    public TextField searchAllTags;
    public ListView allTagBox;
    public Button addTagButton;
    public Button removeTagButton;
    public Button removeNodeButton;
    public Button disconnectNodeButton;
    public Label errorBox;
    //public Label errorBox;
    Directory dir = Directory.getInstance();

    public Button EditProf;
    public Button EditTag;
    public Slider floorSlider;
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
    //HashMap<List<CircleNode>,Line> circleLines;

    public ScrollPane scrollPane;

    @FXML
    private Button disconnectNodeBtn;

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

    @FXML
    private Pane mapCanvas;

    private List<Node> nodeList = dir.getNodes();
    private List<Tag> allTheTags =dir.getTags();
    List<String> allTagNames = new ArrayList<String>();
    int i=50;

    @FXML
    public void initialize(){
        errorBox.setText("");
        xLoc.setText("");
        yLoc.setText("");
        //String[] sug= {"app","cat", "orage", "adsdf", " ddddd", "ddees"};
        initializeCircleMap();

        allTagBox.setItems(FXCollections.observableList(allTheTags));
         floorMap.setImage(imgInt.display(floor));
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


        floorSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                if (!floorSlider.isValueChanging()) {
                    floor = new_val.intValue();
                    floorSlider.setValue(floor);
                    //floorMap.setImage(imageHashMap.get(floor));
                    floorMap.setImage(imgInt.display(floor));

                    //TODO: heart of error
                    imagePane.getChildren().removeAll(floorCircs);
                    imagePane.getChildren().removeAll(floorLines);
                    floorCircs.clear();
                    floorLines.clear();

                    drawfloorNodes();

                }
            }
        });

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


//added initalize
        //zoom functions?
        final double SCALE_DELTA = 1.1;
        //final Group group = new Group(floorMap, MapCanvas);

        //imagePane.getChildren().add(group);

        final Group scrollContent = new Group(imagePane, floorMap, mapCanvas);
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


               // imagePane.setScaleX(imagePane.getScaleX()*scaleFactor);
               // imagePane.setScaleY(imagePane.getScaleY()*scaleFactor);

                floorMap.setScaleX(floorMap.getScaleX() * scaleFactor);
                floorMap.setScaleY(floorMap.getScaleY() * scaleFactor);
                mapCanvas.setScaleX(mapCanvas.getScaleX()*scaleFactor);
                mapCanvas.setScaleY(mapCanvas.getScaleY()*scaleFactor);

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

        drawfloorNodes();


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
    //Login button
    @FXML
    public void onCreateUser(ActionEvent actionEvent) throws IOException {
        switchScreen(MMGpane, "/Views/CreateUserScreen.fxml");
    }

    //Back button
    @FXML
    public void onBack(ActionEvent actionEvent) throws  IOException{
        switchScreen(MMGpane, "/Views/UserScreen.fxml");
    }
    @FXML
    public void Logout() throws IOException{
        switchScreen(MMGpane, "/Views/UserScreen.fxml");
    }

    @FXML
    public void toEditProf() throws  IOException{
        switchScreen(MMGpane, "/Views/EditProfScreen.fxml");
    }
    @FXML
    public void toEditTag() throws  IOException{
        switchScreen(MMGpane, "/Views/EditTagScreen.fxml");
    }


    @FXML
    public void addNode(){
        CircleNode circ = createCircle(dir.saveNode(50,50,floor), 5, Color.RED);
        imagePane.getChildren().add(circ);
        //Node newn = new Node//dir.saveNode((int)circ.getCenterX(), (int)circ.getCenterY(), floor);
        //nodeList.add(newn);

        //add to directory
    }

    @FXML
    public void connectNodePressed(){
        connectNode(select1,select2);
    }

    private void connectNode(CircleNode s1, CircleNode s2){
        Line line = connect(s1,s2);
        line.setStyle("-fx-stroke: red;");
        s1.lineMap.put(s2,line);
        s2.lineMap.put(s1,line);

        if(loading==false) {
            boolean response = dir.saveEdge(s1.referenceNode, s2.referenceNode);
            if(response == false){
                errorBox.setText(errorString);
            }else{
                errorBox.setText("");
            }
        }

        if (s1.referenceNode.getFloor() != s2.referenceNode.getFloor()) {
            line.setFill(Color.YELLOW);
            line.setStrokeWidth(2);
            imagePane.getChildren().add(line);
        }else{
            imagePane.getChildren().add(line);
        }

    }


    private CircleNode createCircle(Node n, double r, Color color) {
        System.out.println("Node ID:"+n.getID()+" x: "+n.getX()+" y: "+n.getY());

        if (n.hasElevator()){
            color = Color.YELLOW;
        }else{
            color = Color.RED;
        }
        CircleNode circle = new CircleNode(n.getX(), n.getY(), r, color,n);

        circleMap.put(n, circle);

        circle.setCursor(Cursor.HAND);
        //nodes.put(circle, n);

        circle.setOnMousePressed((t) -> {
            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();
            System.out.println(circle.referenceNode.getFloor());


            CircleNode c = (CircleNode) (t.getSource());
            c.toFront();
            xLoc.setText(new Integer((int)c.getCenterX()).toString());
            yLoc.setText(new Integer((int)c.getCenterY()).toString());

            currentTagBox.setItems(FXCollections.observableList(c.referenceNode.getTags()));
            currentTagBox.refresh();

            scirc = c;

            if (select1 == null){
                select1 = c;
                s1.setID(n.getID());
                s= n.getID();
                c.setFill(Color.BLACK);
            }else {
                if(select2 != null) {
                    select2.setFill(Color.RED);
                }

                select2 = select1;
                select1 = c;
                s= n.getID();
                c.setFill(Color.BLACK);
            }


            System.out.println(select1.toString() + " " + select2.toString());


            /*if(switchS ==true){
                //select1.setFill(Color.RED);
                select1 =c;
                s1.setID(n.getID());
                s= n.getID();
                //System.out.println("s1" + select1.getCenterX());
                switchS=false;
                c.setFill(Color.BLACK);

            }
            else{
                select1.setFill(Color.GREEN);
                select2=c;
                s2.setID(n.getID());
                sa=n.getID();
                c.setFill(Color.BLACK);
                //System.out.println("s2:" +select2.getCenterX());
                switchS=true;
            }*/
//            System.out.println(nodes.get(c).getX());
        });
        circle.setOnMouseReleased((t)->{

            if(circle.referenceNode.hasElevator()) {
                circle.setFill(Color.YELLOW);
            }else{
                circle.setFill(Color.RED);
            }

            select1.setFill(Color.GREEN);
            if(select2 != null && select1 != select2){
                select2.setFill(Color.BLUE);
            }

            updatePosition(t);




        });

        circle.setOnMouseDragged((t) -> {
            //if(state ==states.add) {
            double offsetX = t.getSceneX() - orgSceneX;
            double offsetY = t.getSceneY() - orgSceneY;

            Circle c = (Circle) (t.getSource());

            c.setCenterX(c.getCenterX() + offsetX);
            c.setCenterY(c.getCenterY() + offsetY);

            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();

            xLoc.setText(new Integer((int) c.getCenterX()).toString());
            yLoc.setText(new Integer((int) c.getCenterY()).toString());

            //}
        });
        return circle;
    }


    private Line connect(CircleNode c1, CircleNode c2) {
        Line line = new Line();
        floorLines.add(line);

        line.startXProperty().bind(c1.centerXProperty());
        line.startYProperty().bind(c1.centerYProperty());

        line.endXProperty().bind(c2.centerXProperty());
        line.endYProperty().bind(c2.centerYProperty());

        line.setStrokeWidth(1);
        line.setStrokeLineCap(StrokeLineCap.BUTT);
//        line.getStrokeDashArray().setAll(1.0, 4.0);

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

    }

    private void initializeCircleMap(){
        for(Node n : dir.getNodes()){
            if (n.hasElevator()){
                CircleNode circ = createCircle(n, 5, Color.YELLOW);
            }else {
                CircleNode circ = createCircle(n, 5, Color.RED);
            }
        }
    }

    private void drawfloorNodes(){
        for(Node n: dir.getNodes()){
            if(n.getFloor()==floor){
                //CircleNode circ = createCircle(n, 5, Color.RED);
                try {
                    imagePane.getChildren().add(circleMap.get(n));
                    floorCircs.add(circleMap.get(n));
                }catch (IllegalArgumentException e){
                    System.out.println(e);
                }

                //System.out.println(circ.getCenterX());
               // nodes.put(circ, n);
            }
        }

        if(select1 != null && select2 != null) {
            System.out.println(select1.toString() + " " + select2.toString());
        }


        //System.out.println(circleMap.size());
        //draws stored connections

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


    public void addTagToCurrentNode(ActionEvent actionEvent) {
        if(selectedTag != null||select1==null){
            boolean response = dir.addNodeTag(select1.referenceNode,selectedTag);
            selectedTag.updateConnections();

            if(select1.referenceNode.hasElevator()){
                select1.setFill(Color.YELLOW);
            }


            if(response){
                errorBox.setText("");
                currentTagBox.setItems(FXCollections.observableArrayList(select1.referenceNode.getTags()));
            }else{
                errorBox.setText(errorString);
            }

            currentTagBox.refresh();
        }

    }

    public void removeTagFromCurrentNode(ActionEvent actionEvent) {
        if(selectedCurrentTag != null){
            boolean response = dir.removeNodeTag(select1.referenceNode,selectedCurrentTag);
            if(selectedCurrentTag.isConnectable()){
                selectedCurrentTag.updateConnections();
            }
            if(response){
                errorBox.setText("");
                currentTagBox.setItems(FXCollections.observableArrayList(select1.referenceNode.getTags()));
            }else{
                errorBox.setText(errorString);
            }

            currentTagBox.refresh();
        }
    }

    public void disconnectCircleNodes(ActionEvent actionEvent) {
        System.out.print(select1.lineMap.get(select2).getStartX());

        boolean response = dir.deleteEdge(select1.referenceNode,select2.referenceNode);
        if(response){
            errorBox.setText("");
            Line l = select1.lineMap.get(select2);

            //System.out.println("Line xcoord" +l.getEndX());

            imagePane.getChildren().remove(l);
            System.out.println(select1.lineMap.size());
            select1.lineMap.remove(select2);
            select2.lineMap.remove(select1);
        }else{
            errorBox.setText(errorString);
        }



    }

    public void removeCircleNode(ActionEvent actionEvent) {
        boolean response = dir.deleteNode(select1.referenceNode);
        if(response){
            errorBox.setText("");
            imagePane.getChildren().remove(select1);
            select1 = select2;
            select2= null;
        }else{
            errorBox.setText(errorString);
        }

    }


    public void doneDrag(DragEvent dragEvent) {

    }

    //Spanish button to change language to Spanish
    @FXML
    public void toSpanish(ActionEvent actionEvent) throws  IOException{
        //TODO : CHANGE INTO SWITCH STATEMENT FOR MULTIPLE LANGUAGES
        if(Main.Langugage == "English") {
            Main.Langugage = "Spanish";
            Main.bundle = ResourceBundle.getBundle("MyLabels", Main.spanish);
        }
        else{
            Main.Langugage = "English";

            Main.bundle = ResourceBundle.getBundle("MyLabels", Main.local);
        }

        switchScreen(MMGpane,"/Views/EditMapScreen.fxml");


    }
}
