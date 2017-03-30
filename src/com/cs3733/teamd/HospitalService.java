package com.cs3733.teamd;

/**
 * Created by sdmichelini on 3/28/17.
 */
public class HospitalService {

    private String name;
    private String room;
    private int floor;
    public HospitalService(String name, int floor, String room) {
        this.name = name;
        this.floor = floor;
        this.room = room;
    }

    public String getName() {
        return name;
    }

    public String getRoom() {
        return room;
    }

    public int getFloor() {
        return floor;
    }
}
