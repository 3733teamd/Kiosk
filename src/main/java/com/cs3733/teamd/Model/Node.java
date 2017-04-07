package com.cs3733.teamd.Model;

import java.awt.*;
import java.util.LinkedList;

/**
 * Created by Me on 3/31/2017.
 */
public class Node {

    public Point coord = new Point();
    private LinkedList<Tag> tags = new LinkedList<Tag>();
    private LinkedList<Node> nodes = new LinkedList<Node>();
    //DB key
    private int ID;

    public int getID(){
        return ID;
    }

    public LinkedList<Tag> getTags(){
        return tags;
    }
    public Node(int x, int y){

        coord.setLocation(x,y);
    }

    public Node(int x, int y, int ID){
        coord.setLocation(x,y);
        this.ID = ID;
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

    public void setID(int ID){
        this.ID = ID;
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

    public void destroyNode(){
        for(Node neighbor : nodes){
            neighbor.removeNeighbor(this);
        }
    }

    public void removeNeighbor(Node n){
        if(this.nodes.contains(n)){
            this.nodes.remove(n);
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

    @Override
    public String toString(){
        return "("+this.getX()+","+this.getY()+")";
    }

    public String toSql(){
        return("("+ID+","+coord.x+","+coord.y+")");
    }
}