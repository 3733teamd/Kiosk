package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Model.CircleNode;
import com.cs3733.teamd.Model.Entities.Node;
import javafx.scene.Cursor;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Stephen on 4/20/2017.
 */
public class MapController extends AbsController {
    // Height and width of the ImageView(from the FXML)
    protected static final double IMAGE_WIDTH = 844.0;
    protected static final double IMAGE_HEIGHT = 606.0;

    protected double zoomPercent;

    private ScrollPane scrollPane;
    private ImageView floorMap;
    private Pane mapCanvas;

    private List<Node> nodes;

    private int floor;

    private Map<Node, CircleNode> circleNodeMap;

    public void initialize(ScrollPane pane, ImageView floorMap, Pane mapCanvas) {
        this.scrollPane = pane;
        this.floorMap = floorMap;
        this.mapCanvas = mapCanvas;

        this.scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        this.scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        this.circleNodeMap = new HashMap<Node, CircleNode>();

        zoomPercent = 100.0;
    }

    protected double getImageXFromZoom(double xClick) {
        // Figure out the X and Y of where the zoom occured
        double viewportWidth = IMAGE_WIDTH / floorMap.getScaleX();

        // Bar center will go between 0.0 and 1.0 which is (viewportWidth/2) to (IMAGE_WIDTH-(viewportWidth/2))
        double xUpperLeft = (scrollPane.getHvalue() * (IMAGE_WIDTH - viewportWidth));

        double xPercent = xClick/IMAGE_WIDTH;

        return (xUpperLeft + (xPercent * viewportWidth));
    }

    protected double getImageYFromZoom(double yClick) {
        // Figure out the X and Y of where the zoom occured

        double viewportHeight = IMAGE_HEIGHT / floorMap.getScaleY();

        // Bar center will go between 0.0 and 1.0 which is (viewportWidth/2) to (IMAGE_WIDTH-(viewportWidth/2))
        double yUpperLeft = (scrollPane.getVvalue() * (IMAGE_HEIGHT - viewportHeight));

        double yPercent = yClick/IMAGE_HEIGHT;

        return (yUpperLeft + (yPercent * viewportHeight));
    }

    protected void setZoomAndScale(double xBarPosition, double yBarPosition, boolean zoomIn) {

        floorMap.setScaleX(zoomPercent/100.0);
        floorMap.setScaleY(zoomPercent/100.0);
        mapCanvas.setScaleX(zoomPercent/100.0);
        mapCanvas.setScaleY(zoomPercent/100.0);

        if(zoomIn) {
            scrollPane.setHvalue(xBarPosition);
            scrollPane.setVvalue(yBarPosition);
        } else {
            scrollPane.setHvalue(scrollPane.getHvalue());
            scrollPane.setVvalue(scrollPane.getVvalue());
        }

    }

    protected void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    protected void setCircleMap(Map<Node, CircleNode> circleMap) {
        this.circleNodeMap = circleMap;
    }

    protected void addCircle(Node n, Color c) {
        CircleNode circle = new CircleNode(n.getX(), n.getY(), 5.0, c,n);
        circle.setCursor(Cursor.HAND);
        System.out.println(n);
        circleNodeMap.put(n, circle);
    }

    protected void addCircle(Node n, Color c, double r) {
        CircleNode circle = new CircleNode(n.getX(), n.getY(), r, c,n);
        circle.setCursor(Cursor.HAND);
        System.out.println(n);
        circleNodeMap.put(n, circle);
    }

    private CircleNode createDefaultCircle(Node n) {
        CircleNode circle = new CircleNode(n.getX(), n.getY(), 5.0, Color.BLUE,n);
        circle.setCursor(Cursor.HAND);

        return circle;
    }

    protected void drawNodes() {
        mapCanvas.getChildren().clear();
        // Draw all of the nodes that are on the current floor
        for(Node n: nodes) {
            CircleNode currentNode = circleNodeMap.get(n);
            System.out.println(currentNode);
            if(currentNode == null) {
                currentNode = createDefaultCircle(n);
            }
            // Draw it
            if(n.getFloor() == floor) {
                mapCanvas.getChildren().add(currentNode);
            }
        }
    }
    // What floor do we draw on?
    protected void setFloor(int floor) {
        this.floor = floor;
    }
}
