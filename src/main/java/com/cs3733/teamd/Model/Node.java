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

    public void rmvTag(Tag t){
        traits.remove(t);
        if(t.locations.contains(this)){
           t.locations.remove(this);
        }
    }

    public void addNode(Node n){
        neighbors.add(n);
        if(!n.neighbors.contains(this)){
            n.addNode(this);
        }
    }
    public void rmvNode(Node n){
        neighbors.remove(n);
        if(n.neighbors.contains(this)){
            n.rmvNode(this);
        }
    }


}
