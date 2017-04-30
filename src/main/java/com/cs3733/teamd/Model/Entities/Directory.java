package com.cs3733.teamd.Model.Entities;

import com.cs3733.teamd.Controller.IObservable;
import com.cs3733.teamd.Database.DBHandler;
import javafx.application.Platform;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
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

    private List<Node> allNodes = new ArrayList<Node>();
    private List<Tag> allTags = new ArrayList<Tag>();
    private List<Professional> allProfs = new ArrayList<Professional>();
    private List<ProTitle> titles = new ArrayList<ProTitle>();
    private DBHandler dbHandler;
    public HashMap<String,Integer> floorNums;
    public HashMap<Integer,String> floorNames;

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
        floorNums = new HashMap<>();
        floorNames = new HashMap<>();
        List<String> tempFloorNames = new LinkedList<>();
        List<Integer> tempFloorNums = new LinkedList<>();
        tempFloorNames.add("Flk 1");
        tempFloorNames.add("Flk 2");
        tempFloorNames.add("Flk 3");

        tempFloorNums.add(new Integer(1));
        tempFloorNums.add(new Integer(2));
        tempFloorNums.add(new Integer(3));

        for(int i = 0; i<tempFloorNames.size();i++){
            floorNames.put(tempFloorNums.get(i),tempFloorNames.get(i));
            floorNums.put(tempFloorNames.get(i),tempFloorNums.get(i));
        }




    }

    @Override
    public HashMap<Integer, String> getFloorNames() {
        return floorNames;
    }

    public HashMap<String, Integer> getFloorNums() {
        return floorNums;
    }

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
        boolean dbResult = dbHandler.updateTag(
                t.getTagName(),
                t.getId(),
                t.isConnectable(),
                t.isRestricted());
        // Now update the visiting hours
        List<VisitingBlock> vistingBlocks = t.getVisitingBlockObjs();
        dbResult = dbHandler.removeVisitingHour(t.getID());
        for(VisitingBlock block: vistingBlocks) {
            dbResult = dbHandler.addVisitingHour(t.getID(),
                    new java.sql.Timestamp(block.getOpen().getTimeInMillis()),
                    new java.sql.Timestamp(block.getClose().getTimeInMillis())
            );
        }
        return dbResult;
    }

    @Override
    public synchronized boolean deleteTag(Tag t) {
        // Delete the NodeTags
        for(Node n: this.getNodes()) {
            if(n.containsTag(t)) {
                removeNodeTag(n, t);
            }
        }
        // Delete the professional tags
        for(Professional p: this.getProfessionals()) {
            if(p.containsTag(t)) {
                removeTagFromProfessional(p, t);
            }
        }

        boolean dbResult = dbHandler.deleteTag(t.getID());
        if(dbResult) {
            this.allTags.remove(t);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public synchronized Professional saveProfessional(String name) {
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
    public Professional saveProfessional(String name, List<ProTitle> titles, List<Tag> tags) {
     return null;
    }

    @Override
    public synchronized boolean updateProfessional(Professional p) {
        boolean dbResult = dbHandler.updateProfessional(p.name,p.getID());
        return dbResult;
    }

    @Override
    public synchronized boolean  removeProfessional(Professional p) {

        for(Tag t : p.getTags()){
            boolean result = dbHandler.removeTagFromProfessional(t.getId(),p.getID());
        }
        p.rmvAllTags();

        for(ProTitle pt : p.getTitles()){
            boolean result = dbHandler.removeTitleFromProfessional(pt.getId(),p.getID());
        }
        p.rmvAllTitles();

        boolean dbResult = dbHandler.deleteProfessional(p.getID());
        if(dbResult) {
            this.allProfs.remove(p);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public synchronized boolean addTagToProfessional(Professional p, Tag t) {
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
    public synchronized boolean addNodeTag(Node n, Tag t) {
        boolean dbResult = dbHandler.addNodeTag(n.getID(), t.getID());
        if(dbResult) {
            n.addTag(t);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public synchronized boolean removeNodeTag(Node n, Tag t) {
        boolean dbResult = dbHandler.removeNodeTag(n.getID(), t.getID());
        if(dbResult) {
            n.rmvTag(t);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public synchronized boolean addTitleToProfessional(Professional p, ProTitle t) {
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

    @Override
    public int createUser(String username, String password) {
        String password_hash = "";
        try {
            password_hash = User.calculateHash(password);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        int user_id = dbHandler.createUser(username, password_hash);
        if(user_id > 0) {
            return user_id;
        } else {
            return -1;
        }
    }

    @Override
    public boolean addRoleToUser(int user_id, String role) {
        return dbHandler.addRoleToUser(user_id,role);
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

    @Override
    public boolean addBugReport(Report report) {
        boolean result = dbHandler.addBugReport(report.tagText, report.commentText);
        if(!result) {
            System.err.println("Error Adding Bug Report");
            return result;
        }
        return result;
    }

    @Override
    public List<Report> getBugReports() {
        return dbHandler.getBugReports();
    }

    @Override
    public boolean changeToNewFile(String filename) {
        try {
            this.dbHandler.emptyExceptUsers();
            Connection c = this.dbHandler.getConnection();
            Statement s = c.createStatement();
            this.dbHandler.loadDbEntriesFromFileIntoDb(s, filename);
            this.dbHandler.load();
            s.close();
            this.allNodes.clear();
            this.allNodes = this.dbHandler.nodes;
            this.allTags.clear();
            this.allTags = this.dbHandler.tags;
            this.allProfs.clear();
            this.allProfs = this.dbHandler.professionals;
            System.out.println(allNodes);
            Platform.runLater(new Runnable() {
                @Override public void run() {
                    for(IObservable observable: observables) {
                        observable.notifyUpdate();
                    }
                }
            });

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    List<IObservable> observables = new ArrayList<IObservable>();

    @Override
    public void addObserver(IObservable observable) {
        if(!observables.contains(observable)) {
            observables.add(observable);
        }
    }
    @Override
    public void removeObservers() {
        observables.clear();
    }

    public boolean deleteBugReport(Report report){
        boolean result = dbHandler.deleteBugReport(report.tagText, report.commentText);
        if(!result) {
            System.err.println("Error Deleting Bug Report");
            return result;
        }
        return result;
    }
}
