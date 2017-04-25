package com.cs3733.teamd.Model;

import com.cs3733.teamd.Model.Entities.Node;
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
    private Color defaultColor;
    public boolean beingDragged;
    public boolean beingHovered;

    public CircleNode(double x, double y, double r, Node n){
        super(x,y,r);
        setDefaultColor();
        this.referenceNode = n;
        beingDragged = false;
        beingHovered = false;
    }

    public Color getDefaultColor(){
        return defaultColor;
    }

    public void setDefaultColor(){
        System.out.println("Setting Default Color");
        if (referenceNode.hasElevator()){
            defaultColor = Color.YELLOW;
        }else if(referenceNode.hasRestricted()){
            defaultColor = Color.DARKGRAY;
        }else{
            defaultColor = Color.RED;
        }
    }



}
