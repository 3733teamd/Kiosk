package com.cs3733.teamd.Model;

import com.cs3733.teamd.Database.DBHandler;
import com.cs3733.teamd.Model.Node;
import com.cs3733.teamd.Model.Professional;
import com.cs3733.teamd.Model.Tag;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Used by CONTROLLERS to store ALL ENTITIES
 * Knows the DB Connection so it can SAVE ENTITIES
 */
public class Directory implements DirectoryInterface {

    private static Directory dir = new Directory();

    public static Directory getInstance(){
        return dir;
    }

    int nextNodeID;
    int nextProfID;

    private ArrayList<Node> allNodes = new ArrayList<Node>();
    private ArrayList<Tag> allTags = new ArrayList<Tag>();
    private ArrayList<Professional> allProfs = new ArrayList<Professional>();
    private DBHandler dbHandler;

    private Directory( ){}

    public void initialize(ArrayList<Node> nodes, ArrayList<Tag> tags, ArrayList<Professional> profs, DBHandler dbHandler){
        allNodes = nodes;
        allTags = tags;
        allProfs = profs;
        this.dbHandler = dbHandler;
        this.nextNodeID = nextNodeID;
        this.nextProfID = nextProfID;
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

    public void notifyUpdate() {
        try {
            dbHandler.open();
            dbHandler.save();
            dbHandler.close();
        } catch (SQLException e) {
            System.out.println("UNABLE TO PERSIST TO DATABASE");
            e.printStackTrace();
        } catch(Exception e) {
            System.out.println("UNABLE TO PERSIST TO DATABASE");
            e.printStackTrace();
        }
    }


    public Tag createNewTag(String tagName){
        Tag newTag = new Tag(tagName);
        allTags.add(newTag);
        //SAVE TO DATABASE
        notifyUpdate();
        return  newTag;
    }
    public Node createNewNode(int x, int y, int ID){
        Node newNode = new Node(x,y,ID);

        //newNode.setID(dbHandler.generateKeyForNode());
        allNodes.add(newNode);
        //SAVE TO DATABASE
        notifyUpdate();
        return newNode;
    }
    public Professional creaNewProf(String name, ArrayList<Title> titles, int ID){
        Professional newProf = new Professional(name,titles,ID);
        nextProfID++;
        //newProf.setID(dbHandler.generateKeyForProf());
        allProfs.add(newProf);
        //SAVE TO DATABASE
        notifyUpdate();
        return  newProf;
    }
    public void deleteTag(Tag t){
        allTags.remove(t);
        //REMOVE FROM DATABSE
        notifyUpdate();
    }

    @Override
    public Node saveNode(int x, int y, int floor) {
        return null;
    }
    @Override
    public boolean deleteNode(Node n){
        return false;
    }

    @Override
    public boolean updateNode(Node n) {
        return false;
    }

    @Override
    public boolean saveEdge(Node n1, Node n2) {
        return false;
    }

    @Override
    public boolean deleteEdge(Node n1, Node n2) {
        return false;
    }

    @Override
    public Tag addTag(String name) {
        return null;
    }

    @Override
    public boolean updateTag(Tag t) {
        return false;
    }

    @Override
    public boolean removeTag(Tag t) {
        return false;
    }

    @Override
    public Professional saveProfessional(String name) {
        return null;
    }

    @Override
    public Professional saveProfessional(String name, List<Title> titles, List<Tag> tags) {
        return null;
    }

    @Override
    public boolean updateProfessional(Professional p) {
        return false;
    }

    @Override
    public boolean removeProfessional(Professional p) {
        return false;
    }

    @Override
    public boolean addTagToProfessional(Professional p, Tag t) {
        return false;
    }

    @Override
    public boolean removeTagFromProfessional(Professional p, Tag t) {
        return false;
    }

    @Override
    public boolean addTitleToProfessional(Professional p, Title t) {
        return false;
    }

    @Override
    public boolean removeTitleFromProfessional(Professional p, Title t) {
        return false;
    }

    @Override
    public List<Node> getNodes() {
        return null;
    }

    @Override
    public List<Tag> getTags() {
        return null;
    }

    @Override
    public List<Professional> getProfessionals() {
        return null;
    }

    @Override
    public User loginUser(String username, String password) {
        return null;
    }

    @Override
    public User getCurrentUser() {
        return null;
    }

    public void deleteProf(Professional p){
        allProfs.remove(p);
        //REMOVE FROM DATABASE
        notifyUpdate();
    }

}
