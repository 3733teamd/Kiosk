package com.cs3733.teamd;

import java.util.List;

/**
 * Created by sdmichelini on 3/29/17.
 */
public class HospitalProfessional {
    // Which locations are they in?
    private List<Location> locations;

    // Name of the professional
    private String name;
    // Create hte health professional
    public HospitalProfessional(String name, List<Location> locations) {
        this.name = name;
        this.locations = locations;
    }

    /**
     * Get the locations of a healh professional
     * @return
     */
    public List<Location> getLocations() {
        return locations;
    }

    /**
     * get the name of a health professional
     * @return
     */
    public String getName() {
        return name;
    }
}
