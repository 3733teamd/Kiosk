package com.cs3733.teamd.entities;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.LinkedList;

/**
 * Created by Me on 3/31/2017.
 */
public class Node {

     Point coord = new Point();
     LinkedList<Tag> traits = new LinkedList<Tag>();
     LinkedList<Node> neighbors = new LinkedList<Node>();

    public Node(int x, int y){
        coord.setLocation(x,y);
    }


    public void addTag(Tag t){
        traits.add(t);
        t.locations.add(this);
    }


}
