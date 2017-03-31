package com.cs3733.teamd.entities;

import java.util.LinkedList;

/**
 * Created by Me on 3/31/2017.
 */
public class Professional {
    String name;
    String title;
    LinkedList<Tag> locations = new LinkedList<Tag>();

    public Professional(String name, String title){
        this.name = name;
        this.title = title;
    }
}
