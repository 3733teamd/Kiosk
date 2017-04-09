package com.cs3733.teamd.Model;

/**
 * Created by sdmichelini on 4/8/17.
 */
public class ProTitle {
    private String acronym;
    private String name;
    private int id;
    public ProTitle(String acronym, String name, int id){
        this.acronym = acronym;
        this.name = name;
        this.id = id;
    }

    public String getAcronym() {
        return acronym;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString(){
        return acronym;
    }
}
