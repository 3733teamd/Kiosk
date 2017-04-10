package com.cs3733.teamd.Model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.HashMap;

/**
 * Created by Ryan on 4/9/2017.
 */
public class CircleNode extends Circle {
    public Node referenceNode;
    public HashMap<CircleNode,Line> lineMap = new HashMap<CircleNode,Line>();

    public CircleNode(double x, double y, double r, Color c, Node n){
        super(x,y,r,c);
        this.referenceNode = n;
    }



}
