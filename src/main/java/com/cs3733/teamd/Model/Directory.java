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
    private ArrayList<Node> allNodes = new ArrayList<Node>();
    private ArrayList<Tag> allTags = new ArrayList<Tag>();
    private ArrayList<Professional> allProfs = new ArrayList<Professional>();

    public Directory(ArrayList<Node> nodes,ArrayList<Tag> tags, ArrayList<Professional> profs){
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

    public void createNewTag(){

    }
    public void createNewNode(){

    }
    public void creaNewProf(){

    }
    public void deleteTag(){

    }
    public void deleteNode(){

    }
    public void deleteProf(){
        
    }

}
