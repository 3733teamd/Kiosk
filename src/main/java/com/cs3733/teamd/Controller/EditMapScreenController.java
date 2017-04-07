package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Model.Node;
import com.cs3733.teamd.Model.Directory;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import org.controlsfx.control.textfield.TextFields;

import javax.xml.soap.Text;
import java.io.IOException;
import java.util.ArrayList;
//TODO deleate connections
//TODO update/ add
//TODO tags
//TODO neighbors
//TODO add inital nodes
//TODO floor changes
//TODO
/**
 * Created by Anh Dao on 4/6/2017.
 */
public class EditMapScreenController extends AbsController{

    Directory dir = Directory.getInstance();

    public Button EditProf;
    public Button EditTag;
    public Slider floorSlider;
    public Button LoginButton;
    public Button SpanishButton;
    public Button addNode;
    public Button connectNode;
    public Label xLoc;
    public Label yLoc;
    public ImageView floorMap;
    public TextArea tagList;
    public TextArea neighborsList;
    public AnchorPane pane;
    public AnchorPane imagePane;
    public TextField addTag;


    double orgSceneX, orgSceneY;

    public enum states{
        add,select
    };
    public Node s1 = new Node(1,1,0);
    public Node s2 = new Node(1,1,0);
    public Circle select1 =createCircle(s1,1,Color.TRANSPARENT);
    public Circle select2=createCircle(s2,1,Color.TRANSPARENT);

    public int s;
    public int sa;
    public Circle scirc;
    public Boolean switchS =true;
    private states state =states.select;

    private ArrayList<Node> temp =new ArrayList<>();

    int i=50;

    @FXML
    public void initialize(){
        xLoc.setText("");
        yLoc.setText("");
        String[] sug= {"app","cat", "orage", "adsdf", " ddddd", "ddees"};

        addTag.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER)  {
                    String text = addTag.getText();
                    System.out.println(text);
                }
            }
        });

        TextFields.bindAutoCompletion(addTag,sug);
    }


    @FXML
    public void Logout() throws IOException{
        switchScreen(pane, "/Views/UserScreen.fxml");
    }

    @FXML
    public void toEditProf() throws  IOException{
        switchScreen(pane, "/Views/EditDoctorScreen.fxml");
    }
    @FXML
    public void toEditTag() throws  IOException{
        switchScreen(pane, "/Views/EditTagScreen.fxml");
    }
    @FXML
    public void toSpanish(){

    }

    @FXML
    public void addNode(){
        Circle circ = createCircle(new Node(50,50,i), 5, Color.RED);
        imagePane.getChildren().add(circ);
        Node newn = new Node((int)circ.getCenterX(), (int)circ.getCenterY(), i);
        temp.add(newn);
        i++;
        //add to directory
    }

    @FXML
    public void connectNode(){
        Line line = connect(select1,select2);
        imagePane.getChildren().add(line);
        updateNeighbors();

        //update
    }


    private Circle createCircle(Node n, double r, Color color) {
        Circle circle = new Circle(n.getX(), n.getY(), r, color);

        circle.setCursor(Cursor.HAND);

        circle.setOnMousePressed((t) -> {
            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();

            Circle c = (Circle) (t.getSource());
            c.toFront();
            xLoc.setText(new Integer((int)c.getCenterX()).toString());
            yLoc.setText(new Integer((int)c.getCenterY()).toString());

            scirc = c;
            if(switchS ==true){
                select1 =c;
                s1.setID(n.getID());
                s= n.getID();
                //System.out.println("s1" + select1.getCenterX());
                switchS=false;
                c.setFill(Color.BLACK);

            }
            else{
                select2=c;
                s2.setID(n.getID());
                sa=n.getID();
                c.setFill(Color.BLACK);
                //System.out.println("s2:" +select2.getCenterX());
                switchS=true;
            }
            System.out.println(temp);
            System.out.println(temp.get(0).getX());
        });
        circle.setOnMouseReleased((t)->{
            state=states.select;
            //if(select1!=circle ||select2!=circle){
                circle.setFill(Color.RED);
           // }
            updatePosition();
            System.out.println(temp.get(0).getNodes());

        });

        circle.setOnMouseDragged((t) -> {
            //if(state ==states.add) {
            state=states.add;
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


    private Line connect(Circle c1, Circle c2) {
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

    private void updatePosition(){
        for(Node n: temp){
//            if(n.getID()==s){
//                n.=scirc.getCenterX();
//            } //set to xLoc and yLoc?
            System.out.println("Id" +s1.getID());

            if(n.getID()==s){
                Node a = new Node(new Integer(xLoc.getText()), new Integer(yLoc.getText()), s);
                n.coord.x= (new Integer(xLoc.getText()));
                n.coord.y=( new Integer(yLoc.getText()));
            }
        }
    }


    private void updateNeighbors(){ //doesn't work
        for(Node n:temp){
            System.out.println("Id" +s1.getID());
            if(n.getID() ==s1.getID()){
                for(Node a:temp){
                    if(n.getID() ==sa){
                        n.addNode(a);
                        a.addNode(n);
                    }
                }
            }
        }
    }




}
