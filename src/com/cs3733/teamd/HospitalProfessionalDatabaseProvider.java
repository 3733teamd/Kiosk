package com.cs3733.teamd;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

/**
 * Connects to the Database
 *
 * Retrieves Providers and Thier Locations from the Database
 *
 * Created by sdmichelini on 3/29/17.
 */
public class HospitalProfessionalDatabaseProvider {
    // SQL Statements
    private static String SELECT_ALL_PROFESSIONALS = "SELECT * FROM PROVIDERS";
    private static String SELECT_ALL_LOCATIONS = "SELECT * FROM LOCATIONS";

    private static String INSERT_LOCATION = "INSERT INTO LOCATIONS (floor, room, p_id) VALUES (?,?,?)";
    private static String INSERT_PROFESSIONAL = "INSERT INTO PROVIDERS (name) VALUES (?)";

    /**
     * Retrieves all the locations from the database
     * @param c - SQL Connections
     * @return Locations in the Hospital
     * @throws SQLException
     */
    public static List<Location> getAllLocations(Connection c) throws SQLException {
        List<Location> locations = new ArrayList<Location>();

        Statement sqlStatement = c.createStatement();

        // Grab the results
        ResultSet results = sqlStatement.executeQuery(SELECT_ALL_LOCATIONS);

        // Loop the results and create a Location for each of them
        while(results.next()) {

            int floor = results.getInt("floor");
            String room = results.getString("room");
            int professionalId = results.getInt("p_id");

            Location l = new Location(floor, professionalId, room);
            // Add it to the list
            locations.add(l);
        }

        return locations;
    }

    /**
     * Retrieves all of the health professionals and their locations from the DB
     * @param c - SQL Connection
     * @return - Providers and Thier Locations
     * @throws SQLException
     */
    public static List<HospitalProfessional> getAllProfessionals(Connection c) throws SQLException {
        List<Location> locations = getAllLocations(c);

        List<HospitalProfessional> professionals = new ArrayList<HospitalProfessional>();

        Statement sqlStatement = c.createStatement();

        // Grab the results
        ResultSet results = sqlStatement.executeQuery(SELECT_ALL_PROFESSIONALS);

        // Loop the results and create a Location for each of them
        while(results.next()) {

            int professionalId = results.getInt("id");
            String name = results.getString("name");

            // Filter out all the locations
            List<Location> professionalLocations = locations.stream()
                    .filter(location -> { return location.getProfessionalId() == professionalId; })
                    .collect(Collectors.toList());
            // Add it to the list
            professionals.add(new HospitalProfessional(name, professionalLocations));
        }

        return professionals;
    }

    /**
     * Adds a Professional and Thier Locations to the DB
     * @param p - Professional
     * @param c - SQL Connection
     * @throws SQLException
     */
    public static void setProfessional(HospitalProfessional p, Connection c) throws SQLException {
        String[] generatedColumns = { "id" };
        PreparedStatement s = c.prepareStatement(INSERT_PROFESSIONAL, Statement.RETURN_GENERATED_KEYS);
        s.setString(1, p.getName());
        int affectedRows = s.executeUpdate();

        int provider = -1;
        if(affectedRows == 0) {
            throw new SQLException();
        } else {
            ResultSet rs = s.getGeneratedKeys();
            if(rs.next()){
                provider = rs.getInt(1);
            }
        }

        // Group into one transaction
        for(Location l: p.getLocations()) {
            PreparedStatement s1 = c.prepareStatement(INSERT_LOCATION);
            l.setProfessionalId(provider);
            s1.setInt(1, l.getFloor());
            s1.setString(2, l.getRoom());
            s1.setInt(3, l.getProfessionalId());
            s1.execute();
        }


    }
}
