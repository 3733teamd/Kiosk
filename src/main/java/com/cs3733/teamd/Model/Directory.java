package com.cs3733.teamd.Model;

import com.cs3733.teamd.Database.DBHandler;
import com.cs3733.teamd.Model.Node;
import com.cs3733.teamd.Model.Professional;
import com.cs3733.teamd.Model.Tag;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
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
    private List<ProTitle> titles = new ArrayList<ProTitle>();
    private DBHandler dbHandler;

    private Directory( ){}

    private User curUser = null;

    public void initialize(
            ArrayList<Node> nodes,
            ArrayList<Tag> tags,
            ArrayList<Professional> profs,
            List<ProTitle> titles,
            DBHandler dbHandler){
        allNodes = nodes;
        allTags = tags;
        allProfs = profs;
        this.dbHandler = dbHandler;
        this.nextNodeID = nextNodeID;
        this.nextProfID = nextProfID;
        this.titles = titles;
    }
    @Deprecated
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

    @Deprecated
    public synchronized Tag createNewTag(String tagName){

        return  null;
    }
    @Deprecated
    public Node createNewNode(int x, int y, int ID){

        return null;
    }
    @Deprecated
    public synchronized Professional creaNewProf(String name, ArrayList<Title> titles, int ID){

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
    public synchronized Node saveNode(int x, int y, int floor) {
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
    public synchronized boolean deleteNode(Node n){

        // Can not delete nodes with neighbors
        if((n.getNodes().size() > 0)) {
            return false;
        }
        //delete all node-tags
        for(Tag t : n.getTags()){
            removeNodeTag(n,t);
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
    public synchronized boolean updateNode(Node n) {
        boolean dbResult = dbHandler.updateNode(n.getX(), n.getY(), n.getID());

        return dbResult;
    }

    @Override
    public synchronized boolean saveEdge(Node n1, Node n2) {
        boolean dbResult = dbHandler.addEdge(n1.getID(), n2.getID());
        if(!dbResult) {
            return false;
        } else {
            n1.addNode(n2);
            return true;
        }
    }

    @Override
    public synchronized boolean deleteEdge(Node n1, Node n2) {
        boolean dbResult = dbHandler.removeEdge(n1.getID(), n2.getID());
        if(!dbResult) {
            return false;
        } else {
            n1.rmvNode(n2);
            return true;
        }
    }

    @Override
    public synchronized Tag saveTag(String name) {
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
    public synchronized boolean updateTag(Tag t) {
        boolean dbResult = dbHandler.updateTag(t.getTagName(),t.getId());
        return dbResult;
    }

    @Override
    public boolean deleteTag(Tag t) {
        // Can not delete tags with neighbors

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

    //TODO: implement this
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
    public synchronized boolean  removeProfessional(Professional p) {

        for(Tag t : p.getTags()){
            removeTagFromProfessional(p,t);
        }

        for(ProTitle pt : p.getTitles()){
            removeTitleFromProfessional(p,pt);
        }

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
        boolean dbResult = dbHandler.addTagToProfessional(t.getId(), p.getID());
        if(dbResult) {
            p.addTag(t);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public synchronized boolean removeTagFromProfessional(Professional p, Tag t) {
        boolean dbResult = dbHandler.removeTagFromProfessional(t.getId(), p.getID());
        if(dbResult) {
            p.rmvTag(t);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean addNodeTag(Node n, Tag t) {
        boolean dbResult = dbHandler.addNodeTag(n.getID(), t.getID());
        if(dbResult) {
            n.addTag(t);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean removeNodeTag(Node n, Tag t) {
        boolean dbResult = dbHandler.removeNodeTag(n.getID(), t.getID());
        if(dbResult) {
            n.rmvTag(t);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean addTitleToProfessional(Professional p, ProTitle t) {
        // TODO: FIX ID
        boolean dbResult = dbHandler.addTitleToProfessional(t.getId(), p.getID());
        if(dbResult) {
            p.addTitle(t);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public synchronized boolean removeTitleFromProfessional(Professional p, ProTitle t) {
        // TODO: FIX ID
        boolean dbResult = dbHandler.removeTitleFromProfessional(t.getId(), p.getID());
        if(dbResult) {
            p.rmvTitle(t);
            return true;
        } else {
            return false;
        }
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
        String password_hash = "";
        try {
            password_hash = User.calculateHash(password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int user_id = dbHandler.getUser(username, password_hash);
        if(user_id < 1) {
            return null;
        }
        List<String> roles = dbHandler.getRolesForUser(user_id);
        if(roles == null) {
            roles = new ArrayList<String>();
        }
        User u = new User(username, roles);
        curUser = u;
        return u;
    }

    @Override
    public void logoutUser() {
        curUser = null;
    }

    @Override
    public User getCurrentUser() {
        return curUser;
    }

    public void deleteProf(Professional p){
        allProfs.remove(p);
        //REMOVE FROM DATABASE
        notifyUpdate();
    }

    @Override
    public List<ProTitle> getTitles() {
        return titles;
    }

    @Override
    public ProTitle addTitle(String acronym, String title) {
        int dbResult = dbHandler.addTitle(acronym, title);
        if(dbResult == -1) {
            return null;
        } else {
            ProTitle t = new ProTitle(acronym, title, dbResult);
            titles.add(t);
            return t;
        }
    }

}
