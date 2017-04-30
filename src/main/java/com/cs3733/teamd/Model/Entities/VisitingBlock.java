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

    public VisitingBlock() {
        open = new Calendar.Builder().build();
        close = new Calendar.Builder().build();
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

    public void setOpenTimeInMillis(long millis) {
        open.setTimeInMillis(millis);
    }

    public void setCloseTimeInMillis(long millis) {
        close.setTimeInMillis(millis);
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
    public Calendar getClose() { return close; }

    public String toString(){

        String range = stringTime(open) + " to " + stringTime(close);

        return range;

    }

    public String stringTime(Calendar c){
        String minutes = Integer.toString(c.get(Calendar.MINUTE));
        String hours = Integer.toString(c.get(Calendar.HOUR_OF_DAY));

        if(minutes.length()<2){
            minutes = "0" + minutes;
        }
        
        return (hours + ":" + minutes);

    }
}
