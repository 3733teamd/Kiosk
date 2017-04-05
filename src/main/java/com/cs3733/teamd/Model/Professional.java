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
    private ArrayList<Title> titles = new ArrayList<Title>();
    private LinkedList<Tag> tags = new LinkedList<Tag>();






    public Professional(String name) {
        this.name = name;
        this.titles = titles;
    }

    public Professional(String name, ArrayList<Title> titles) {
        this.name = name;
        this.titles = titles;
    }

    public Professional(String name, ArrayList<Title> titles, LinkedList<Tag> tags) {
        this.name = name;
        this.titles = titles;
        this.tags = tags;
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

    public LinkedList<Tag> getTags() {
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

    public void rmvAllTitles(){
        titles = new ArrayList<Title>();
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


}

