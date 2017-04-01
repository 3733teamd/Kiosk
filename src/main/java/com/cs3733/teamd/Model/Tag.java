package com.cs3733.teamd.Model;

import java.util.LinkedList;

/**
 * Created by Me on 3/31/2017.
 */
public class Tag {


     String trait;
     private LinkedList<Node> nodes = new LinkedList<Node>();
     private LinkedList<Professional> profs = new LinkedList<Professional>();

    public Tag(String trait){
        this.trait = trait;
    }

    //adds a node, and adds this tag to the other node, ENFORCES MUTUAL KNOWLEDGE
    public void addNode(Node n){
        nodes.add(n);
        if(!n.containsTag(this)){
            n.addTag(this);
        }
    }


    public void addProf(Professional p){
        profs.add(p);
        if(!p.containsTag(this)){
            p.addTag(this);
        }
    }

    public void rmvProf(Professional p){
        if (profs.contains(p)){
            profs.remove(p);
            if(p.containsTag(this)) {
                p.rmvTag(this);
            }
        }
    }

    public void rmvNode(Node n){
        if (nodes.contains(n)){
            nodes.remove(n);
            if(n.containsTag(this)) {
                n.rmvTag(this);
            }
        }
    }

    public boolean containsNode(Node n){
        return (this.nodes.contains(n));
    }

    public boolean containsProf(Professional p){
        return (this.profs.contains(p));
    }


    public String toString(){
        return trait;
    }

}

