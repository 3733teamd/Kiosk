package com.cs3733.teamd.Model;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Me on 3/31/2017.
 */
//TODO Separate first and last name so names may be represented better
//TODO represent full titles as well as acronyms.
//TODO May require overiding a javaFX method for converting class to string instead of using toString in class
//TODO Make general improvements to toString
public class Professional {
    String name;
    private int ID;
    private ArrayList<Title> titles = new ArrayList<>();
    private LinkedList<Tag> tags = new LinkedList<>();

    public Professional(String name, int ID) {
        this.name = name;
        this.titles = titles;
    }

    //This is called during RUNTIME and adds itself to the DB
    public Professional(String name, ArrayList<Title> titles) {
        this.name = name;
        this.titles = titles;
        //TODO: add to DB
    }

    //This is called during STARTUP when loaded from the db
    public Professional(String name, ArrayList<Title> titles, int ID) {
        this.name = name;
        this.titles = titles;
        this.ID = ID;
    }

    public void setID(int ID){
        this.ID = ID;
    }
    public void setTitles(ArrayList<Title> titles) {
        this.titles = titles;
    }

    public void addTitle(Title title) {
        this.titles.add(title);
    }

    public void addTag(Tag t){
        tags.add(t);
        if(!t.containsProf(this)){
            t.addProf(this);
        }
    }

    public void rmvTag(Tag t){
        if(tags.contains(t)){
            tags.remove(t);
        }
        if(t.containsProf(this)){
            t.rmvProf(this);
        }
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



}

