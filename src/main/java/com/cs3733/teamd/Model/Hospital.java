package com.cs3733.teamd.Model;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Stephen on 4/25/2017.
 */
public class Hospital {
    /// What is the name of the hospital?
    private String name;
    /// What is the id(eg: faulker)?
    private String hospitalId;
    /// What is the version of the database???
    private Integer dbVersion;
    /// Files for the floor
    private Map<Integer, String> floorFiles;

    Hospital(String name, String hospitalId, Integer dbVersion, Map<Integer, String> floorFiles) {
        this.name = name;
        this.hospitalId = hospitalId;
        this.dbVersion = dbVersion;
        this.floorFiles = floorFiles;
    }

    public String getName() {
        return name;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public Integer getDbVersion() {
        return dbVersion;
    }

    public Map<Integer, String> getFloorFiles() {
        return floorFiles;
    }

    public Set<Integer> getFloorSet() {
        return floorFiles.keySet();
    }

}
