package com.cs3733.teamd.Model;

import javafx.application.Application;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by Stephen on 4/25/2017.
 */
public class HospitalLoader {
    private static HospitalLoader instance = null;
    private JSONParser parser;

    private JSONObject root;

    private HospitalLoader() {
        parser = new JSONParser();
    }


    public static HospitalLoader getInstance() {
        if(instance == null) {
            instance = new HospitalLoader();
        }
        return instance;
    }

    private boolean hospitalsExist() {
        File test = new File(ApplicationConfiguration.getInstance()
                .getFullFilePath("hospitals"));
        return test.isDirectory();
    }

    /**
     * This function recursively copy all the sub folder and files from sourceFolder to destinationFolder
     *
     * This source is from: http://howtodoinjava.com/core-java/io/how-to-copy-directories-in-java/
     * */
    private static void copyFolder(File sourceFolder, File destinationFolder) throws IOException
    {
        //Check if sourceFolder is a directory or file
        //If sourceFolder is file; then copy the file directly to new location
        if (sourceFolder.isDirectory())
        {
            //Verify if destinationFolder is already present; If not then create it
            if (!destinationFolder.exists())
            {
                destinationFolder.mkdir();
                System.out.println("Directory created :: " + destinationFolder);
            }

            //Get all files from source directory
            String files[] = sourceFolder.list();

            //Iterate over all files and copy them to destinationFolder one by one
            for (String file : files)
            {
                File srcFile = new File(sourceFolder, file);
                File destFile = new File(destinationFolder, file);

                //Recursive function call
                copyFolder(srcFile, destFile);
            }
        }
        else
        {
            //Copy the file content from one place to another
            Files.copy(sourceFolder.toPath(), destinationFolder.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File copied :: " + destinationFolder);
        }
    }

    private static void unzip(InputStream is, String destDir) {
        File dir = new File(destDir);
        // create output directory if it doesn't exist
        if(!dir.exists()) dir.mkdirs();
        FileInputStream fis;
        //buffer for read and write data to file
        byte[] buffer = new byte[1024];
        try {
            ZipInputStream zis = new ZipInputStream(is);
            ZipEntry ze = zis.getNextEntry();
            while(ze != null){
                String fileName = ze.getName();
                File newFile = new File((fileName));
                if(!fileName.contains(".")) {
                    ze = zis.getNextEntry();
                } else {
                    System.out.println("Unzipping to "+newFile.getAbsolutePath());
                    //create directories for sub directories in zip
                    System.out.println(newFile);
                    new File(newFile.getParent()).mkdirs();
                    FileOutputStream fos = new FileOutputStream(newFile);
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    fos.close();
                    //close this ZipEntry
                    zis.closeEntry();
                    ze = zis.getNextEntry();
                }

            }
            //close last ZipEntry
            zis.closeEntry();
            zis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean setupLocalHospitalDirectory() {
        if(!hospitalsExist()) {
            System.out.println("hospitals directory does not exist. creating...");

            File f = new File("hospitals");
            f.mkdir();
            f = new File("hospitals/apartment");
            f.mkdir();
            f = new File("hospitals/faulkner");
            f.mkdir();
            unzip(getClass().getClassLoader().getResourceAsStream("hospitals.zip"), "");
            return true;


        } else {
            return true;
        }
    }

    private JSONArray loadHospitalsObject() {

        try {
            String filename = ApplicationConfiguration.getInstance()
                    .getFullFilePath("hospitals/hospitals.json");

            if(filename == null) {
                return null;
            }
            FileReader f = new FileReader(filename);
            Object o = parser.parse(f);
            root = (JSONObject) o;
            JSONArray hospitalsJson = (JSONArray) root.get("hospitals");
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
        if(hospitalsJson == null) {
            return null;
        }
        Iterator<Object> it = hospitalsJson.iterator();
        List<String> ret = new ArrayList<String>();
        while(it.hasNext()) {
            JSONObject hospitalJson = (JSONObject)it.next();
            ret.add((String)hospitalJson.get("hospitalId"));
        }
        return ret;
    }

    private void findDbVersions(Hospital h) {
        File f = new File(ApplicationConfiguration.getInstance()
                .getFullFilePath("hospitals/"+h.getHospitalId()));

        File[] dirFiles = f.listFiles();

        Set<Integer> versions = new HashSet<Integer>();
        for(File file: dirFiles) {
            System.out.println(file);
            String name = file.getName();
            name = name.replace("dump.", "").replace(".sql","");
            try {
                Integer version = Integer.parseInt(name);
                versions.add(version);
            }catch(NumberFormatException e) {

            }

        }
        h.setDbVersions(versions);

    }

    public Hospital loadDefaultHospital() {
        setupLocalHospitalDirectory();
        try {
            String filename = ApplicationConfiguration.getInstance()
                    .getFullFilePath("hospitals/hospitals.json");
            if(filename == null) {
                return null;
            }
            FileReader f = new FileReader(filename);
            Object o = parser.parse(f);
            root = (JSONObject) o;
            String currentHospital = (String) root.get("currentHospital");
            return loadHospitalFromId(currentHospital);
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

    public Hospital loadHospitalFromId(String id) {
        JSONArray hospitalsJson = loadHospitalsObject();
        if(hospitalsJson == null) {
            System.err.println("Your path probably has spaces.");
            return null;
        }
        Iterator<Object> it = hospitalsJson.iterator();

        while(it.hasNext()) {
            JSONObject hospitalJson = (JSONObject)it.next();
            System.out.println((String)hospitalJson.get("hospitalId"));
            if(((String)hospitalJson.get("hospitalId")).compareTo(id) == 0) {
                String name = (String)hospitalJson.get("name");
                String hospitalId = id;
                Integer dbVersion = ((Long)hospitalJson.get("dbVersion")).intValue();
                JSONArray floors = (JSONArray)hospitalJson.get("floors");
                boolean multipleLanguages = (Boolean)hospitalJson.get("multipleLanguages");
                Map<Integer, String> floorFiles = new HashMap<Integer, String>();
                Iterator<Object> it2 = floors.iterator();
                while(it2.hasNext()) {
                    JSONObject floorsJson = (JSONObject)it2.next();
                    floorFiles.put(((Long)floorsJson.get("number")).intValue(), (String)floorsJson.get("file"));
                }
                Hospital h = new Hospital(name, hospitalId, dbVersion, floorFiles);
                h.setMultipleLanguages(multipleLanguages);
                findDbVersions(h);
                return h;
            }
        }
        return null;
    }

    public boolean saveCurrentHospital(String newHospital) {
        if(root == null) {
            System.err.println("Error with Current Hospital");
            return false;
        }

        root.put("currentHospital", newHospital);

        String filename = ApplicationConfiguration.getInstance()
                .getFullFilePath("hospitals/hospitals.json");
        if(filename == null) {

            return false;
        }
        try (FileWriter file = new FileWriter(filename)) {
            file.write(root.toJSONString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + root);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean saveHospital(Hospital h) {
        JSONArray hospitalsJson = loadHospitalsObject();
        if(hospitalsJson == null) {
            return false;
        }

        Iterator<Object> it = hospitalsJson.iterator();

        while(it.hasNext()) {
            JSONObject hospitalJson = (JSONObject)it.next();
            if(((String)hospitalJson.get("hospitalId")).compareTo(h.getHospitalId()) == 0) {
                System.out.println(h.getDbVersion());
                hospitalJson.put("dbVersion",(Long)h.getDbVersion().longValue());
                System.out.println(root.toJSONString());
                // try-with-resources statement based on post comment below :)
                String filename = ApplicationConfiguration.getInstance()
                        .getFullFilePath("hospitals/hospitals.json");
                if(filename == null) {
                    return false;
                }
                try (FileWriter file = new FileWriter(filename)) {
                    file.write(root.toJSONString());
                    System.out.println("Successfully Copied JSON Object to File...");
                    System.out.println("\nJSON Object: " + root);
                    return true;
                } catch (IOException e) {
                    return false;
                }

            }
        }
        return false;
    }
}
