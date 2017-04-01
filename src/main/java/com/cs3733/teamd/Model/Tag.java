package com.cs3733.teamd.Model;

import java.util.LinkedList;

/**
 * Created by Me on 3/31/2017.
 */
public class Tag {


     String trait;
     private LinkedList<Node> locations = new LinkedList<Node>();
     private LinkedList<Professional> occupants = new LinkedList<Professional>();

    public Tag(String trait){
        this.trait = trait;
    }

    //adds a node, and adds this tag to the other node, ENFORCES MUTUAL KNOWLEDGE
    public void addNode(Node n){
        locations.add(n);
        if(!n.containsTag(this)){
            n.addTag(this);
        }
    }


    public void addProf(Professional p){
        occupants.add(p);
        if(!p.containsTag(this)){
            p.addTag(this);
        }
    }

    public void rmvProf(Professional p){
        if (occupants.contains(p)){
            occupants.remove(p);
            if(p.containsTag(this)) {
                p.rmvTag(this);
            }
        }
    }

    public void rmvNode(Node n){
        if (locations.contains(n)){
            locations.remove(n);
            if(n.containsTag(this)) {
                n.rmvTag(this);
            }
        }
    }

    public boolean containsNode(Node n){
        return (this.locations.contains(n));
    }

    public boolean containsProf(Professional p){
        return (this.occupants.contains(p));
    }


    public String toString(){
        return trait;
    }

}

