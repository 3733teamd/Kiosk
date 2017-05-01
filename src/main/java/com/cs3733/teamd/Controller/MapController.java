package com.cs3733.teamd.Controller;

import com.cs3733.teamd.Model.CircleNode;
import com.cs3733.teamd.Model.Entities.Directory;
import com.cs3733.teamd.Model.Entities.Node;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Stephen on 4/20/2017.
 */
public class MapController extends AbsController {
    // Height and width of the ImageView(from the FXML)
    Directory dir = Directory.getInstance();
    protected static final double IMAGE_WIDTH = 844.0;
    protected static final double IMAGE_HEIGHT = 606.0;

    protected double zoomPercent;

    private ScrollPane scrollPane;
    private ImageView floorMap;
    private Pane mapCanvas;

    private List<Node> nodes;

    private int floor;

    private Map<Node, CircleNode> circleNodeMap;

    private Label label;

    private List<Line> lines;

    private Map<Integer, ZoomRestriction> zoomRestrictionMap;

    public void initialize(ScrollPane pane, ImageView floorMap, Pane mapCanvas) {
        this.zoomRestrictionMap = new HashMap<Integer, ZoomRestriction>();
        this.scrollPane = pane;
        this.floorMap = floorMap;
        this.mapCanvas = mapCanvas;

        this.scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        this.scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        this.scrollPane.setPannable(true);

        this.circleNodeMap = new HashMap<Node, CircleNode>();

        this.label = new Label("Informative Text");

        lines = new ArrayList<Line>();

        zoomPercent = 100.0;
    }

    public void addZoomRestriction(Integer floor, ZoomRestriction restriction) {
        this.zoomRestrictionMap.put(floor,restriction);
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

    protected void setZoomAndBars(double zoomPercent, double xBarPosition, double yBarPosition) {
        this.zoomPercent = zoomPercent;
        setBarPositions(xBarPosition, yBarPosition, true);
    }

    protected void setBarPositions(double xBarPosition, double yBarPosition, boolean zoomIn) {
        double zoomMin = 100.0;
        double zoomMax = 1500.0;

        if(zoomPercent < zoomMin) {
            zoomPercent = zoomMin;
        } else if(zoomPercent > zoomMax) {
            zoomPercent = zoomMax;
        }

        floorMap.setScaleX(zoomPercent/100.0);
        floorMap.setScaleY(zoomPercent/100.0);
        mapCanvas.setScaleX(zoomPercent/100.0);
        mapCanvas.setScaleY(zoomPercent/100.0);

        if(zoomIn) {
            scrollPane.setHvalue(xBarPosition);
            scrollPane.setVvalue(yBarPosition);
        } else {
            scrollPane.setHvalue(xBarPosition);
            scrollPane.setVvalue(yBarPosition);
            //scrollPane.setHvalue(scrollPane.getHvalue());
            //scrollPane.setVvalue(scrollPane.getVvalue());
        }

    }

    protected void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    protected void appendNodes(List<Node> nodes) {
        this.nodes.addAll(nodes);
    }

    protected void setCircleMap(Map<Node, CircleNode> circleMap) {
        this.circleNodeMap = circleMap;
    }

    protected void addCircle(Node n, Color c) {
        addCircle(n,5.0);
    }

    protected void clearCircleMap() {
        this.circleNodeMap.clear();
    }

    protected void clearNodes() {
        if(this.nodes != null) {
            //this.nodes.clear();
        }
    }

    protected void removeConnections() {
        this.lines.clear();
    }

    protected void connectNode(Node n1, Node n2) {
        Line l = new Line();
        l.setStartX(n1.getX());
        l.setEndX(n2.getX());
        l.setStartY(n1.getY());
        l.setEndY(n2.getY());

        l.setStrokeWidth(3.0);
        l.setStroke(Color.BLACK);

        this.lines.add(l);
    }

    private double getNodeX(Node n) {
        if(this.zoomRestrictionMap.get(this.floor) != null) {
            double xOffset = this.zoomRestrictionMap.get(this.floor).minX * IMAGE_WIDTH;
            return n.getX() - xOffset;
        } else {
            return (double)n.getX();
        }
    }

    private double getNodeY(Node n) {
        if(this.zoomRestrictionMap.get(this.floor) != null) {
            double yOffset = this.zoomRestrictionMap.get(this.floor).minY * IMAGE_HEIGHT;
            return n.getY() - yOffset;
        } else {
            return (double)n.getY();
        }
    }

    protected void addCircle(Node n, double r) {
        CircleNode circle = new CircleNode(getNodeX(n), getNodeY(n), r,n);
        circle.setCursor(Cursor.HAND);
        circle.setOnMouseEntered((event) -> {
            if(n.getTags().size() > 0) {
                System.out.println("Hover");
                label.setText(n.mostSpecificTagName());
                mapCanvas.getChildren().add(label);
                label.setLayoutX(getNodeX(n) - 35.0);
                label.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
                label.setPadding(new Insets(10));
                label.setTextFill(Color.WHITE);
                if(n.getY() < 50) {
                    label.setLayoutY(getNodeY(n) + 50);
                } else {
                    label.setLayoutY(getNodeY(n) - 50);
                }

            }
        });
        circle.setOnMouseExited((event) -> {
            if(n.getTags().size() > 0) {
                System.out.println("End Hover");
                mapCanvas.getChildren().removeAll(label);
            }
        });
        circleNodeMap.put(n, circle);
    }



    protected void drawNodes() {
        mapCanvas.getChildren().clear();
        mapCanvas.getChildren().addAll(this.lines);
        // Draw all of the nodes that are on the current floor
        for(Node n: nodes) {
            CircleNode currentNode = circleNodeMap.get(n);

            // Draw it
            if(n.getFloor() == floor && currentNode != null) {
                mapCanvas.getChildren().removeAll(currentNode);
                //System.out.println( "+++++++++++++++++++++++++");
                currentNode.setDefaultColor();
                //System.out.println(currentNode.defaultColor.getRed());
                mapCanvas.getChildren().add(currentNode);
            }
        }

    }
    // What floor do we draw on?
    protected void setFloor(int floor) {
        this.floor = floor;
        zoomToFloor();
    }

    private void zoomToFloor() {
        // No path

        double minX = IMAGE_WIDTH;
        double maxX = 0;
        double minY = IMAGE_HEIGHT;
        double maxY = 0;

        for(Node n: dir.getNodes()) {
            if(n.getFloor() == floor) {
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

        double percentWidth = requiredWidth/IMAGE_WIDTH;
        double percentHeight = requiredHeight/IMAGE_HEIGHT;

        double zoom = 1.0;

        if(percentHeight > percentWidth) {
            zoom = (1.0/percentHeight);
        } else {
            zoom = (1.0/percentWidth);
        }

        double xPercent = (minX + (requiredWidth/2.0))/IMAGE_WIDTH;
        double yPercent = (minY + (requiredHeight/2.0))/IMAGE_HEIGHT;

        double zoomPercentNew = (zoom * 100.0);

        if(zoomPercentNew < 100.0) {
            zoomPercentNew = 100.0;
        }

        setZoomAndBars(zoomPercentNew, xPercent, yPercent);
    }

    protected class ZoomRestriction {
        public double minX, minY, maxX, maxY;

        public ZoomRestriction(double minX, double minY, double maxX, double maxY) {
            this.minX = minX;
            this.minY = minY;
            this.maxX = maxX;
            this.maxY = maxY;
        }
    }
}
