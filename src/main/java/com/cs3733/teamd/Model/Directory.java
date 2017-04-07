package com.cs3733.teamd.Model;

import com.cs3733.teamd.Database.DBHandler;
import com.cs3733.teamd.Model.Node;
import com.cs3733.teamd.Model.Professional;
import com.cs3733.teamd.Model.Tag;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    private List<Node> allNodes = new ArrayList<Node>();
    private List<Tag> allTags = new ArrayList<Tag>();
    private List<Professional> allProfs = new ArrayList<Professional>();
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
        //Tag newTag = new Tag(tagName);
        //allTags.add(newTag);
        //SAVE TO DATABASE
        //notifyUpdate();
        return  null;
    }
    public Node createNewNode(int x, int y, int ID){
        //Node newNode = new Node(x,y,ID);
        //newNode.setID(dbHandler.generateKeyForNode());
        //allNodes.add(newNode);
        //SAVE TO DATABASE
        notifyUpdate();
        return null;
    }
    public Professional creaNewProf(String name, ArrayList<Title> titles, int ID){
        Professional newProf = new Professional(name,titles,ID);
        nextProfID++;
        //newProf.setID(dbHandler.generateKeyForProf());
        allProfs.add(newProf);
        //SAVE TO DATABASE
        notifyUpdate();
        return  null;
    }

    /*
    public void deleteTag(Tag t){
        allTags.remove(t);
        //REMOVE FROM DATABSE
        notifyUpdate();
    }
*/
    @Override
    public Node saveNode(int x, int y, int floor) {
        int id = this.dbHandler.saveNode(x,y,floor);
        if(id == -1) {
            return null;
        } else {
            Node n = new Node(x,y,floor,id);
            this.allNodes.add(n);
            return n;
        }

    }
    @Override
    public boolean deleteNode(Node n){
        // Can not delete nodes with neighbors
        if(n.getNodes().size() > 0) {
            return false;
        }
        boolean dbResult = dbHandler.deleteNode(n.getID());
        if(dbResult) {
            this.allNodes.remove(n);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean updateNode(Node n) {
        boolean dbResult = dbHandler.updateNode(n.getX(), n.getY(), n.getID());

        return dbResult;
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
    public Tag saveTag(String name) {
        int id = this.dbHandler.saveTag(name);
        if(id == -1) {
            return null;
        } else {
            Tag t = new Tag(name,id);
            this.allTags.add(t);
            return t;
        }
    }

    @Override
    public boolean updateTag(Tag t) {
        boolean dbResult = dbHandler.updateTag(t.getTagName(),t.getId());
        return dbResult;
    }

    @Override
    public boolean deleteTag(Tag t) {
        // Can not delete nodes with neighbors

        boolean dbResult = dbHandler.deleteTag(t.getID());
        if(dbResult) {
            this.allTags.remove(t);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Professional saveProfessional(String name) {
        int id = this.dbHandler.saveProfessional(name);
        if(id == -1) {
            return null;
        } else {
            Professional p = new Professional(name,id);
            this.allProfs.add(p);
            return p;
        }
    }

    @Override
    public Professional saveProfessional(String name, List<Title> titles, List<Tag> tags) {
     return null;
    }

    @Override
    public boolean updateProfessional(Professional p) {
        boolean dbResult = dbHandler.updateProfessional(p.name,p.getID());
        return dbResult;
    }

    @Override
    public boolean removeProfessional(Professional p) {
        // Can not delete nodes with neighbors

        boolean dbResult = dbHandler.deleteProfessional(p.getID());
        if(dbResult) {
            this.allProfs.remove(p);
            return true;
        } else {
            return false;
        }
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
        return this.allNodes;
    }

    @Override
    public List<Tag> getTags() {
        return this.allTags;
    }

    @Override
    public List<Professional> getProfessionals() {
        return this.allProfs;
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
