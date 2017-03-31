package com.cs3733.teamd.entities;

import java.util.LinkedList;

/**
 * Created by Me on 3/31/2017.
 */
public class Tag {


     String title;
     LinkedList<Node> locations = new LinkedList<Node>();

    public Tag(String title){
        this.title = title;
    }

}

