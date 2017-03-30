package com.cs3733.teamd;

/**
 * Created by sdmichelini on 3/29/17.
 */
public class Location {
    private int floor;
    private int professionalId;
    private String room;

    public Location(int floor, int professionalId,String room) {
        this.floor = floor;
        this.professionalId = professionalId;
        this.room = room;
    }

    public void setProfessionalId(int professionalId) {
        this.professionalId = professionalId;
    }

    public int getFloor() {
        return floor;
    }

    public int getProfessionalId() {
        return professionalId;
    }

    public String getRoom() {
        return room;
    }
}
