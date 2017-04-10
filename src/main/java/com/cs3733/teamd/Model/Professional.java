package com.cs3733.teamd.Model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Me on 3/31/2017.
 */
//TODO Separate first and last name so names may be represented better
//TODO represent full titles as well as acronyms.
//TODO May require overiding a javaFX method for converting class to string instead of using toString in class
//TODO Make general improvements to toString
public class Professional {
    public  String name;
    private int ID;
    private List<ProTitle> titles = new ArrayList<ProTitle>();
    private List<Tag> tags = new LinkedList<Tag>();


    public int getID() {
        return ID;
    }
    //This is called during RUNTIME and adds itself to the DB
    public Professional(String name, List<ProTitle> titles, int ID) {
        this.name = name;
        this.titles = titles;
        this.ID = ID;
    }

    //This is called during STARTUP when loaded from the db. Will load titles via addtitle.
    public Professional(String name, int ID) {
        this.name = name;
        this.ID = ID;
    }

    public void setID(int ID){
        this.ID = ID;
    }

    public Professional(String name, List<ProTitle> titles, LinkedList<Tag> tags) {
        this.name = name;
        this.titles = titles;
        this.tags = tags;
    }

    public void setTitles(ArrayList<ProTitle> titles) {
        this.titles = titles;
    }

    public void addTitle(ProTitle title) {
        this.titles.add(title);
    }

    public List<ProTitle> getTitles() {
        return titles;
    }

    public String getName(){
        return this.name;
    }
    public void addTag(Tag t){
        tags.add(t);
        if(!t.containsProf(this)){
            t.addProf(this);
        }
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void rmvTag(Tag t){
        if(tags.contains(t)){
            tags.remove(t);
        }
        if(t.containsProf(this)){
            t.rmvProf(this);
        }
    }

    public void rmvTitle(ProTitle t) {
        Iterator<ProTitle> iter = this.titles.iterator();
        while(iter.hasNext()) {
            ProTitle next = iter.next();
            if(next.getId() == t.getId()) {
                iter.remove();
                break;
            }
        }
    }

    public void rmvAllTitles(){
        titles = new ArrayList<ProTitle>();
    }

    public void rmvAllTags(){
        tags = new LinkedList<Tag>();
    }

    public boolean containsTag(Tag t){
        return tags.contains(t);
    }

    public LinkedList<Node> getNodes(){
        LinkedList<Node> output = new LinkedList<Node>();

        for(Tag t : tags){
            output.addAll(t.getNodes());
        }

        return output;
    }

    @Override
    public String toString(){
        return titles.toString() +" " + name;
    }

    public String toSql(){
        return("("+ID+",'"+name+"')");
    }

}

