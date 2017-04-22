package com.cs3733.teamd.Model;

/**
 * Created by Allyk on 4/22/2017.
 */
public class Memento {
    private String state;

    public Memento(String state){
        this.state = state;
    }

    public String getState(){
        return state;
    }
}
