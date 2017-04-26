package com.cs3733.teamd.Model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by Stephen on 4/25/2017.
 */
public class HospitalLoader {
    private static HospitalLoader instance = null;
    private JSONParser parser;
    private JSONObject object;

    private HospitalLoader() {
        parser = new JSONParser();
    }


    public static HospitalLoader getInstance() {
        if(instance == null) {
            instance = new HospitalLoader();
        }
        return instance;
    }

    private JSONArray loadHospitalsObject() {
        try {
            String filename = getClass().getClassLoader().getResource("hospitals/hospitals.json").getFile();
            System.out.println(filename);
            FileReader f = new FileReader(filename);
            Object o = parser.parse(f);
            JSONObject jsonObject = (JSONObject) o;
            JSONArray hospitalsJson = (JSONArray) jsonObject.get("hospitals");
            return hospitalsJson;
        } catch(ParseException pe) {
            pe.printStackTrace();
            return null;
        } catch (FileNotFoundException fe) {
            fe.printStackTrace();
            return null;
        } catch (IOException ie) {
            ie.printStackTrace();
            return null;
        }
    }

    public List<String> loadHospitals() {

        JSONArray hospitalsJson = loadHospitalsObject();
        Iterator<Object> it = hospitalsJson.iterator();
        List<String> ret = new ArrayList<String>();
        while(it.hasNext()) {
            JSONObject hospitalJson = (JSONObject)it.next();
            ret.add((String)hospitalJson.get("hospitalId"));
        }
        return ret;
    }

    public Hospital loadHospitalFromId(String id) {
        JSONArray hospitalsJson = loadHospitalsObject();


        Iterator<Object> it = hospitalsJson.iterator();

        while(it.hasNext()) {
            JSONObject hospitalJson = (JSONObject)it.next();
            System.out.println((String)hospitalJson.get("hospitalId"));
            if(((String)hospitalJson.get("hospitalId")).compareTo(id) == 0) {
                String name = (String)hospitalJson.get("name");
                String hospitalId = id;
                Integer dbVersion = ((Long)hospitalJson.get("dbVersion")).intValue();
                JSONArray floors = (JSONArray)hospitalJson.get("floors");
                Map<Integer, String> floorFiles = new HashMap<Integer, String>();
                Iterator<Object> it2 = floors.iterator();
                while(it2.hasNext()) {
                    JSONObject floorsJson = (JSONObject)it2.next();
                    floorFiles.put(((Long)floorsJson.get("number")).intValue(), (String)floorsJson.get("file"));
                }
                return new Hospital(name, hospitalId, dbVersion, floorFiles);
            }
        }
        return null;
    }
}
