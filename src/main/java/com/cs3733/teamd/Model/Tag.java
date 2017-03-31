package com.cs3733.teamd.Model;

import java.util.LinkedList;

/**
 * Created by Me on 3/31/2017.
 */
public class Tag {


     String trait;
     LinkedList<Node> locations = new LinkedList<Node>();
     LinkedList<Professional> occupants = new LinkedList<Professional>();

    public Tag(String trait){
        this.trait = trait;
    }

    //adds a node, and adds this tag to the other node, ENFORCES MUTUAL KNOWLEDGE
    public void addNode(Node n){
        locations.add(n);
        if(!n.traits.contains(this)){
            n.addTag(this);
        }
    }


    public void addProf(Professional p){
        occupants.add(p);
        if(!p.locations.contains(this)){
            p.locations.add(this);
        }
    }


    public String toString(){
        return trait;
    }

}

