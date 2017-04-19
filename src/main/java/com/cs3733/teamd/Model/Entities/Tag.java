package com.cs3733.teamd.Model.Entities;

import java.util.LinkedList;

/**
 * Created by Me on 3/31/2017.
 */
public class Tag {


    Directory dir = Directory.getInstance();
    private String tagName;
    private LinkedList<Node> nodes = new LinkedList<Node>();
    private LinkedList<Professional> profs = new LinkedList<Professional>();
    private boolean connectable;


    public boolean isConnectable() {
        return connectable;
    }

    public void setConnectable(boolean b){
        connectable = b;
    }



    public int getId() {
        return id;
    }

    private int id;

    public int getID(){
        return id;
    }

     public LinkedList<Professional> getProfs(){
         return profs;
     }

    public Tag(String name, int id){
         this.tagName = name;
         this.id = id;
         this.connectable = false;
    }

    public Tag(String name, int id, boolean c){
        this.tagName = name;
        this.id = id;
        this.connectable = c;
    }

    public String getTagName(){
        return tagName;
    }

    public void setTagName(String s){
        tagName = s;
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

    public LinkedList<Node> getNodes(){
        return this.nodes;
    }


    public String toString(){
        return tagName;
    }

    public boolean updateConnections(){
        // Is it connectable?
        System.out.println("Update Connections");
        if(isConnectable()){
            // Check each node in the tag...check what it is connected to
            for (int i=0; i<nodes.size()-1; i++){
                Node n = nodes.get(i);
                for (int j=i+1; j<nodes.size(); j++){
                    Node nn = nodes.get(j);
                    if(n.getID() != nn.getID()
                            && (!n.getNodes().contains(nn))
                            && (n.getFloor() != nn.getFloor())){
                        dir.saveEdge(n, nn);
                        System.out.println("Connecting ID1: "+n.getID()+"ID2: "+nn.getID());
                    }
                }
            }
            System.out.println("isConnectable() size:" + nodes.size());
            return true;
        }else{
            for (int i=0; i<nodes.size()-1; i++){
                Node n = nodes.get(i);
                for (int j=i+1; j<nodes.size(); j++){
                    Node nn = nodes.get(j);
                    if((n.getID() != nn.getID())
                            && (n.getNodes().contains(nn))
                            && (n.getFloor() != nn.getFloor())){
                        dir.deleteEdge(n,nn);
                        System.out.println(getTagName());
                        System.out.println("Disconnected ID1: "+n.getID()+"ID2: "+nn.getID());

                    } else {
                        System.out.println("Failed to Disconnect ID1: "+n.getID()+"ID2: "+nn.getID()+" in Tag: "+this.getTagName());
                    }
                }
            }
            System.out.println("!isConnectable() size:" + nodes.size());

        }
        return false;
    }

}

