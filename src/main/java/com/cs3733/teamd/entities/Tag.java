package com.cs3733.teamd.entities;

import java.util.LinkedList;

/**
 * Created by Me on 3/31/2017.
 */
public class Tag {


     String trait;
     LinkedList<Node> locations = new LinkedList<Node>();

    public Tag(String trait){
        this.trait = trait;
    }

    //adds a node, and adds this tag to the other node
    public void addNode(Node n){
        locations.add(n);
        n.traits.add(this);
    }


    public String toString(){
        return trait;
    }

}

