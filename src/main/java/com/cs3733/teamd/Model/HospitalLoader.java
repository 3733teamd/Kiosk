package com.cs3733.teamd.Model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    public List<String> loadHospitals() {
        try {
            String filename = getClass().getClassLoader().getResource("hospitals/hospitals.json").getFile();
            System.out.println(filename);
            FileReader f = new FileReader(filename);
            Object o = parser.parse(f);
            JSONObject jsonObject = (JSONObject) o;
            JSONArray hospitalsJson = (JSONArray) jsonObject.get("hospitals");
            Iterator<Object> it = hospitalsJson.iterator();
            List<String> ret = new ArrayList<String>();
            while(it.hasNext()) {
                JSONObject hospitalJson = (JSONObject)it.next();
                ret.add((String)hospitalJson.get("hospitalId"));
            }
            return ret;
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
}
