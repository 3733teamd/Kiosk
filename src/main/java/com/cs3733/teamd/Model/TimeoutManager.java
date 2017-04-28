package com.cs3733.teamd.Model;

import com.cs3733.teamd.Controller.AbsController;
import javafx.scene.layout.Pane;

import java.util.LinkedList;

/**
 * Created by Ryan on 4/27/2017.
 */
public class TimeoutManager {
    //private LinkedList<AbsController> controllers;
    private static TimeoutManager timer;
    private AbsController currentController;
    private int seconds;


    private TimeoutManager(){
        timer = new TimeoutManager();
    }

    public TimeoutManager getTimer(){
        if (timer == null){
            timer = new TimeoutManager();
        }
        return timer;
    }

    public void setCurrentCurrent(AbsController c){
        currentController = c;
    }

    public void notifyController(){
        currentController.timeout();
    }




}
