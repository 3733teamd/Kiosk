package com.cs3733.teamd.entities;

import java.util.LinkedList;

/**
 * Created by Me on 3/31/2017.
 */
public class Professional {
    String name;
    String dept;
    Title title;
    LinkedList<Tag> locations = new LinkedList<Tag>();

    public Professional(String name, Title title, String dept) {
        this.name = name;
        this.title = title;
        this.dept = dept;
    }

    //enum of viable titles
    public enum Title{
        DR("Dr.");
        String text;

        Title(String text){
            this.text = text;
        }

        @Override
        public String toString(){
            return text;
        }

    }
    


}

