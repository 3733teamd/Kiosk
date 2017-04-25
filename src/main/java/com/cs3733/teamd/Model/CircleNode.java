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
        this.referenceNode = n;
        setDefaultColor();
        beingDragged = false;
        beingHovered = false;
    }

    public void setDefaultColor(){
        if(referenceNode.hasElevator() && referenceNode.hasRestricted()){
            defaultColor = Color.YELLOW.darker();
        }
        else if(referenceNode.hasElevator()){
            defaultColor = Color.YELLOW;
        }else if(referenceNode.hasRestricted()){
            defaultColor = Color.DARKGRAY;
        }else{
            defaultColor = Color.RED;
        }
        this.setFill(defaultColor);
    }

    public void setSelected(){
        this.setFill(Color.GREEN);
    }

    public void setOtherSelected(){
        this.setFill(Color.BLUE);
    }


}
