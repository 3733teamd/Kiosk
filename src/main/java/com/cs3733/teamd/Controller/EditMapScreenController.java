package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Main;
import com.cs3733.teamd.Model.CircleNode;
import com.cs3733.teamd.Model.Node;
import com.cs3733.teamd.Model.Directory;
import com.cs3733.teamd.Model.Tag;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import org.controlsfx.control.textfield.TextFields;

import javax.xml.soap.Text;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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

    public ListView currentTagBox;
    public TextField searchAllTags;
    public ListView allTagBox;
    public Button addTagButton;
    public Button removeTagButton;
    public Button removeNodeButton;
    public Button disconnectNodeButton;
    //public Label errorBox;
    Directory dir = Directory.getInstance();

    public Button EditProf;
    public Button EditTag;
    public Slider floorSlider;
    public Button LoginButton;
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
    HashMap<List<CircleNode>,Line> circleLines;

    @FXML
    private Button disconnectNodeBtn;

    HashMap<Node, CircleNode> circleMap = new HashMap<Node, CircleNode>();


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

    public int imageW = 1091;
    public int imageH = 606;
    public double scale = 8.4;
    public int offset_x = 160*12;
    public int offset_y = 80*12;

    private List<Node> nodeList = dir.getNodes();
    private List<Tag> ListofTags =dir.getTags();

    int i=50;

    @FXML
    public void initialize(){
        //errorBox.setText(" ");
        xLoc.setText("");
        yLoc.setText("");
        String[] sug= {"app","cat", "orage", "adsdf", " ddddd", "ddees"};

        allTagBox.setItems(FXCollections.observableList(dir.getTags()));
        /*addTag.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER)  {
                    String text = addTag.getText();
                    System.out.println(text);
                }
            }
        });*/

        //TextFields.bindAutoCompletion(addTag,ListofTags);


        floorSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                //floorSlider.setText(String.format("%.2f", new_val));
                floor= (int)new_val;
                System.out.println("floor"+floor);
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
    public void toSpanish(){

    }

    private Point getConvertedPoint(Node node) { //conversion from database to canvas
        int x = node.getX();
        int y = node.getY();
        Point p = new Point((int) ((x-offset_x)/scale), (int) (imageH-(y-offset_y)/scale));
        return p;
    }

    @FXML
    public void addNode(){
        CircleNode circ = createCircle(dir.saveNode(50,50,4), 5, Color.RED);
        imagePane.getChildren().add(circ);
        //Node newn = new Node//dir.saveNode((int)circ.getCenterX(), (int)circ.getCenterY(), floor);
        //nodeList.add(newn);

        //add to directory
    }

    @FXML
    public void connectNode(){
        Line line = connect(select1,select2);
        select1.lineMap.put(select2,line);
        select2.lineMap.put(select1,line);

        //dir.addNodetoNode()

        imagePane.getChildren().add(line);
        updateNeighbors();

        //update
    }


    private CircleNode createCircle(Node n, double r, Color color) {
        System.out.println("Node ID:"+n.getID()+" x: "+n.getX()+" y: "+n.getY());
        CircleNode circle = new CircleNode(n.getX(), n.getY(), r, color,n);
        circleMap.put(n, circle);

        circle.setCursor(Cursor.HAND);
        //nodes.put(circle, n);

        circle.setOnMousePressed((t) -> {
            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();


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

            circle.setFill(Color.RED);

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

        dir.updateNode(select1.referenceNode);

    }


    private void updateNeighbors(){ //doesn't work

    }

    private void drawfloorNodes(){
        for(Node n: dir.getNodes()){
            if(n.getFloor()==floor){
                CircleNode circ = createCircle(n, 5, Color.RED);
                imagePane.getChildren().add(circ);

                //System.out.println(circ.getCenterX());
               // nodes.put(circ, n);
            }
        }

        //System.out.println(circleMap.size());
        //draws stored connections

        for(int i=0; i<circleMap.size(); i++){
            CircleNode circ = circleMap.get(circleMap.keySet().toArray()[i]);
            Node n = circ.referenceNode;

            if(n.getFloor()==floor){

                for (int j=0; j<circ.referenceNode.getNodes().size(); j++){

                    CircleNode circ2 = circleMap.get(circ.referenceNode.getNodes().get(j));
                    select1 = circ;
                    select2 = circ2;
                    connectNode();

                }
            }
        }



    }


    public void addTagToCurrentNode(ActionEvent actionEvent) {
        if(selectedTag != null){
            dir.addNodeTag(select1.referenceNode,selectedTag);
            currentTagBox.setItems(FXCollections.observableArrayList(select1.referenceNode.getTags()));
            currentTagBox.refresh();
        }

    }

    public void removeTagFromCurrentNode(ActionEvent actionEvent) {
        if(selectedCurrentTag != null){
            dir.removeNodeTag(select1.referenceNode,selectedCurrentTag);
            currentTagBox.setItems(FXCollections.observableArrayList(select1.referenceNode.getTags()));
            currentTagBox.refresh();
        }
    }

    public void disconnectCircleNodes(ActionEvent actionEvent) {
        System.out.print(select1.lineMap.get(select2).getStartX());
        imagePane.getChildren().remove(select1.lineMap.get(select2));
        select1.lineMap.remove(select2);
        select2.lineMap.remove(select1);

        //dir.removeNodeFromNodes()
        updateNeighbors();
    }

    public void removeCircleNode(ActionEvent actionEvent) {
        if (select1.referenceNode.getNodes().size() > 0){
            //errorBox.setText("Node must have no connections before it is deleted");
        }
    }


}
