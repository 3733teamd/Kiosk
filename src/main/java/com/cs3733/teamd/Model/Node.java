package com.cs3733.teamd.Model;

import java.awt.*;
import java.util.LinkedList;

/**
 * Created by Me on 3/31/2017.
 */
public class Node {

     Point coord = new Point();
     private LinkedList<Tag> traits = new LinkedList<Tag>();
     private LinkedList<Node> neighbors = new LinkedList<Node>();

    public Node(int x, int y){
        coord.setLocation(x,y);
    }


    public void addTag(Tag t){
        traits.add(t);
        if(!t.containsNode(this)){
            t.addNode(this);
        }
    }

    public void rmvTag(Tag t){
        if(traits.contains(t)){
            traits.remove(t);
        }
        if(t.containsNode(this)){
           t.rmvNode(this);
        }
    }

    public void addNode(Node n){
        neighbors.add(n);
        if(!n.neighbors.contains(this)){
            n.addNode(this);
        }
    }
    public void rmvNode(Node n){
        if(neighbors.contains(n)){
            neighbors.remove(n);
        }
        if(n.neighbors.contains(this)){
            n.rmvNode(this);
        }
    }

    public boolean containsNode(Node n){
        return (this.neighbors.contains(n));
    }

    public boolean containsTag(Tag t){
        return (this.traits.contains(t));
    }


}
