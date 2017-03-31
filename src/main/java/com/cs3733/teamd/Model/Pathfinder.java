package com.cs3733.teamd.Model;

/**
 * Created by tom on 3/31/17.
 */
public class Pathfinder {

    private Node start;
    private Node end;

    public Pathfinder(Node start, Node end){
        this.start = start;
        this.end = end;
    }

    public double distanceBetween(Node a, Node b){
        return Math.sqrt(
                Math.pow((double) a.getX() - b.getX(), 2) +
                Math.pow((double) a.getY() - b.getY(), 2));
    }

}
