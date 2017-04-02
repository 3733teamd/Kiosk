package com.cs3733.teamd.Model;

import java.awt.*;
import java.util.LinkedList;

/**
 * Created by Me on 3/31/2017.
 */
public class Node {

    Point coord = new Point();
    private LinkedList<Tag> tags = new LinkedList<Tag>();
    private LinkedList<Node> nodes = new LinkedList<Node>();

    public Node(int x, int y){
        coord.setLocation(x,y);
    }

    public double getDist(Node n){
        return coord.distance(n.coord);
    }


    public void addTag(Tag t){
        tags.add(t);
        if(!t.containsNode(this)){
            t.addNode(this);
        }
    }

    public void rmvTag(Tag t){
        if(tags.contains(t)){
            tags.remove(t);
        }
        if(t.containsNode(this)){
            t.rmvNode(this);
        }
    }

    public void addNode(Node n){
        nodes.add(n);
        if(!n.nodes.contains(this)){
            n.addNode(this);
        }
    }

    public LinkedList<Node> getNodes(){
        return this.nodes;
    }

    public void rmvNode(Node n){
        if(nodes.contains(n)){
            nodes.remove(n);
        }
        if(n.nodes.contains(this)){
            n.rmvNode(this);
        }
    }

    public int getX(){
        return this.coord.x;
    }

    public int getY(){
        return this.coord.y;
    }

    public boolean containsNode(Node n){
        return (this.nodes.contains(n));
    }

    public boolean containsTag(Tag t){
        return (this.tags.contains(t));
    }
}