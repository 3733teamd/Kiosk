package com.cs3733.teamd.entities;

import java.util.LinkedList;

/**
 * Created by Me on 3/31/2017.
 */
public class Tag {
    private String title;
    private LinkedList<Node> locations = new LinkedList<Node>();

    public Tag(String title){
        this.title = title;
    }
    public void setTitle(String title){
        this.title = title;
    }
}
