package com.cs3733.teamd.Model;

/**
 * Created by tom on 4/15/17.
 */
public class PathNotFoundException extends Exception {
    public PathNotFoundException(){};

    public PathNotFoundException(String message){
        super(message);
    }
}
