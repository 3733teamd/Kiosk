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

    public boolean hasMultipleLanguages() {
        return multipleLanguages;
    }

    public void setMultipleLanguages(boolean multipleLanguages) {
        this.multipleLanguages = multipleLanguages;
    }

    private boolean multipleLanguages = true;

    public Set<Integer> getDbVersions() {
        return dbVersions;
    }

    public void setDbVersions(Set<Integer> dbVersions) {
        this.dbVersions = dbVersions;
    }

    private Set<Integer> dbVersions;

    Hospital(String name, String hospitalId, Integer dbVersion, Map<Integer, String> floorFiles) {
        this.name = name;
        this.hospitalId = hospitalId;
        this.dbVersion = dbVersion;
        this.floorFiles = floorFiles;
    }

    public void setDbVersion(Integer dbVersion) {
        this.dbVersion = dbVersion;
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

    public String getDbPath() {
        return "hospitals/"+hospitalId+"/dump."+dbVersion.toString()+".sql";
    }

    public String getImagePath(int floor) {
        if(!floorFiles.keySet().contains(floor)) {
            return null;
        }
        return "hospitals/"+hospitalId+"/"+floorFiles.get(floor);
    }

}
