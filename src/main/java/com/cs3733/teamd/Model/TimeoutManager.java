package com.cs3733.teamd.Model;

/**
 * Created by Ryan on 4/27/2017.
 */
public class TimeoutManager {
    private static TimeoutManager timer;

    private TimeoutManager(){
        timer = new TimeoutManager();
    }

    public TimeoutManager getTimer(){
        if (timer == null){
            timer = new TimeoutManager();
        }
        return timer;
    }
}
