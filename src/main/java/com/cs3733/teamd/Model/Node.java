package com.cs3733.teamd.Model;

import java.awt.*;
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
        if(!t.locations.contains(this)){
            t.addNode(this);
        }
    }

    public int getX(){
        return this.coord.x;
    }
    public int getY(){
        return this.coord.y;
    }


}
