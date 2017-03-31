package com.cs3733.teamd.entities;

import java.util.LinkedList;

/**
 * Created by Me on 3/31/2017.
 */
public class Professional {
    public String name;
    public String title;
    public String department;
    LinkedList<Tag> locations = new LinkedList<Tag>();

    public Professional(String name, String title, String department){
        this.name = name;
        this.title = title;
        this.department = department;
    }

    public String toString(){
        return title + " " + name + " (" + department +")";
    }


}
