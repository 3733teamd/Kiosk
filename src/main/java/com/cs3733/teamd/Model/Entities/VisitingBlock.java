package com.cs3733.teamd.Model.Entities;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Ryan on 4/26/2017.
 */
public class VisitingBlock {
    private Calendar close;
    private Calendar open;

    public VisitingBlock(String o, String c) throws Exception{

        open = new Calendar.Builder().build();
        close = new Calendar.Builder().build();
        if(validateString(o) && validateString(c)){
            setOpen(o);
            setClosed(c);
        }else{
            throw new Exception();
        }

    }

    public boolean validateString(String s){
        System.out.println(s);


        if(s.length()<4||s.length()>5) {
            return false;
        }else if(s.indexOf(":")<0 || s.indexOf(":")>2){
            int colonI = s.indexOf(":");
            int hours = Integer.parseInt(s.substring(0,colonI));
            int minutes = Integer.parseInt(s.substring(colonI+1));
            if(hours>23 || minutes>59) {
                return false;
            }else{
                return true;
            }
        }
        return true;
    }

    public void setOpen(String s){
        int colonI = s.indexOf(":");
        int hours = Integer.parseInt(s.substring(0,colonI));
        int minutes = Integer.parseInt(s.substring(colonI+1));

        open.set(Calendar.HOUR_OF_DAY, hours);
        open.set(Calendar.MINUTE, minutes);
    }

    public void setClosed(String s){
        int colonI = s.indexOf(":");
        int hours = Integer.parseInt(s.substring(0,colonI));
        int minutes = Integer.parseInt(s.substring(colonI+1));

        close.set(Calendar.HOUR_OF_DAY, hours);
        close.set(Calendar.MINUTE, minutes);
    }

    public Calendar getOpen(){
        return open;
    }

    public String getBlockRange(){

        String range = open.get(Calendar.HOUR_OF_DAY) +
                ":" +
                open.get(Calendar.MINUTE) +
                " to " +
                close.get(Calendar.HOUR_OF_DAY) +
                ":" +
                close.get(Calendar.MINUTE);

        return range;

    }
    
}
