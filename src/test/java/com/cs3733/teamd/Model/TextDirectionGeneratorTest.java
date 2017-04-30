package com.cs3733.teamd.Model;


import com.cs3733.teamd.Database.DBHandler;
import com.cs3733.teamd.Model.Entities.Directory;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Stephen on 4/13/2017.
 */
public class TextDirectionGeneratorTest {

    public static DBHandler getDBHanlder() {
        DBHandler database;
        Directory dir = Directory.getInstance();

        try {
            database = new DBHandler();
        } catch (Exception e) {
            System.err.print("Could not construct DBHandler.\nExiting...\n");
            return null;
        }

        try {
            database.setup();
        } catch (SQLException e) {
            if(e.getSQLState().equals("X0Y32")){ // Error code for TABLE EXISTS
                System.out.println("Skipping setup as tables are already made");
            } else {
                System.err.println("ERROR: creation of database failed");
                e.printStackTrace();
            }
        } catch (IOException e){
            System.err.println("ERROR: reading in data file");
            e.printStackTrace();
        }
        return database;
    }

    @Test
    public void test() {
        getDBHanlder().nodes.get(0);
    }
//
//    @Test
//    public void testBasicEnglishDirections(){
//        List<String> expectedOutput = new ArrayList<String>(
//                Arrays.asList(
//                        "Proceed straight",
//                        "and then turn left",
//                        "and then you have arrived at your destination")
//        );
//        assertEquals(
//                expectedOutput,
//                TextDirectionGenerator.getDirectionsInLanguage(
//                new ArrayList<Direction>(Arrays.asList(
//                                TextDirectionGenerator.DirectionType.GO_STRAIGHT,
//                                TextDirectionGenerator.DirectionType.TURN_LEFT,
//                                TextDirectionGenerator.DirectionType.ARRIVED)
//                ))
//        );
//    }
//
//    @Test
//    public void testRightTurnEnglish() {
//        List<String> expectedOutput = new ArrayList<String>(
//                Arrays.asList(
//                        "Proceed straight",
//                        "and then turn right",
//                        "and then you have arrived at your destination")
//        );
//        assertEquals(expectedOutput, TextDirectionGenerator.getDirectionsInLanguage(
//                new ArrayList<TextDirectionGenerator.DirectionType>(
//                        Arrays.asList(
//                                TextDirectionGenerator.DirectionType.GO_STRAIGHT,
//                                TextDirectionGenerator.DirectionType.TURN_RIGHT,
//                                TextDirectionGenerator.DirectionType.ARRIVED)
//                ),
//                new ArrayList<String>()
//        ));
//    }
}
