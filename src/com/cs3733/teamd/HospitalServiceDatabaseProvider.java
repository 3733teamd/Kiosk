package com.cs3733.teamd;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This file will provide
 *
 * Created by sdmichelini on 3/28/17.
 */
public class HospitalServiceDatabaseProvider {
    // Get all of the services
    private static String SELECT_ALL_STRING = "SELECT * FROM SERVICES";
    // create a new service in the db
    private static String INSERT_STING = "INSERT INTO SERVICES (name, floor, room) VALUES (?,?,?)";

    public static List<HospitalService> loadHospitalServicesFromDb(Connection dbConn) throws SQLException {
        // Create the list
        List<HospitalService> returnList = new ArrayList<HospitalService>();
        // Create the SQL Statement
        Statement sqlStatement = dbConn.createStatement();
        // Grab the results
        ResultSet results = sqlStatement.executeQuery(SELECT_ALL_STRING);
        // Loop the results and create a Hospital Service for each of them
        while(results.next()) {

            String name = results.getString("name");
            Integer floor = results.getInt("floor");
            String room = results.getString("room");

            HospitalService s = new HospitalService(name, floor, room);
            // Add it to the list
            returnList.add(s);
        }
        return returnList;
    }

    public static void addHospitalServiceToDb(HospitalService s, Connection dbConn) throws SQLException {
        // Use a prepared statement
        PreparedStatement ps = dbConn.prepareStatement(INSERT_STING);
        ps.setString(1, s.getName());
        ps.setInt(2, s.getFloor());
        ps.setString(3, s.getRoom());
        ps.execute();
    }
}
