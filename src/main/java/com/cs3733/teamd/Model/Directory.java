package com.cs3733.teamd.Model;

import com.cs3733.teamd.Model.Node;
import com.cs3733.teamd.Model.Professional;
import com.cs3733.teamd.Model.Tag;

import java.util.ArrayList;

/**
 * Used by CONTROLLERS to store ALL ENTITIES
 * Knows the DB Connection so it can SAVE ENTITIES
 */
public class Directory {

    private static Directory dir = new Directory();

    public static Directory getInstance(){
        return dir;
    }

    private ArrayList<Node> allNodes = new ArrayList<Node>();
    private ArrayList<Tag> allTags = new ArrayList<Tag>();
    private ArrayList<Professional> allProfs = new ArrayList<Professional>();

    private Directory( ){}

    public void initialize(ArrayList<Node> nodes,ArrayList<Tag> tags, ArrayList<Professional> profs){
        allNodes = nodes;
        allTags = tags;
        allProfs = profs;
    }

    public ArrayList<Tag> getAllTags() {
        return allTags;
    }

    public ArrayList<Node> getAllNodes() {
        return allNodes;
    }

    public ArrayList<Professional> getAllProfs() {
        return allProfs;
    }

    public void createNewTag(String tagName){
        Tag newTag = new Tag(tagName);
        allTags.add(newTag);
        //SAVE TO DATABASE

    }
    public void createNewNode(int x, int y){
        Node newNode = new Node(x,y);
        allNodes.add(newNode);
        //SAVE TO DATABASE
    }
    public void creaNewProf(String name, ArrayList<Title> titles){
        Professional newProf = new Professional(name,titles);
        allProfs.add(newProf);
        //SAVE TO DATABASE
    }
    public void deleteTag(Tag t){
        allTags.remove(t);
        //REMOVE FROM DATABSE
    }
    public void deleteNode(Node n){
        allNodes.remove(n);
        //REMOVE FROM DATABASE
    }
    public void deleteProf(Professional p){
        allProfs.remove(p);
        //REMOVE FROM DATABASE
    }
}
