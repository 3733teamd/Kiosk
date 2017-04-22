package com.cs3733.teamd.Model;

/**
 * Created by Allyk on 4/22/2017.
 */
import java.util.ArrayList;
import java.util.List;

public class CareTaker {
    public List<Memento> mementoList = new ArrayList<Memento>();

    public void add(Memento state){
        mementoList.add(state);
    }

    public Memento get(int index){

        return mementoList.get(index);
    }
}