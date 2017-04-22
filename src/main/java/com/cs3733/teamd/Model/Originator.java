package com.cs3733.teamd.Model;

/**
 * Created by Allyk on 4/22/2017.
 */
public class Originator {
    private String state;


    public void setState(String state){
        this.state = state;
    }

    public String getState(){
        return state;
    }

    public Memento saveStateToMemento(){
        return new Memento(state);
    }

    public void getStateFromMemento(Memento memento){
        state = memento.getState();
    }
}
