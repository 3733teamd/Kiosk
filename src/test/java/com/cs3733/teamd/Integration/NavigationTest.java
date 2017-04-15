package com.cs3733.teamd.Integration;

import com.cs3733.teamd.Database.DBHandler;
import com.cs3733.teamd.Model.Entities.Node;
import com.cs3733.teamd.Model.Pathfinder;
import com.cs3733.teamd.Model.Entities.Tag;
import org.junit.Test;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Stephen on 4/5/2017.
 */
public class NavigationTest {
    /**
     * Return's the DBHandler for the test
     * @return
     */
    private DBHandler getTestHandler() {
        DBHandler handler = null;
        try {
            Connection c = DriverManager.getConnection("jdbc:derby:test_db;create=true");
            handler = new DBHandler(c);
        } catch(SQLException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        return handler;
    }

    private void createSchemas(DBHandler d) {
        Connection c = d.getConnection();
        try {
            Statement s = c.createStatement();
            d.setupTables(s);
        } catch (SQLException e) {
            e.printStackTrace();
            fail();
        }
    }

    private void dropSchemas(DBHandler d) {
        try {
            d.drop();
        } catch(SQLException e) {
            e.printStackTrace();
            fail();
        }

    }

    @Test
    public void testNavigation() {
        DBHandler handler = getTestHandler();
        createSchemas(handler);
        Statement s = null;
        try {
            s = handler.getConnection().createStatement();
            handler.loadDbEntriesFromFileIntoDb(s, "/DatabaseImports/DBInitialImports.txt");
            handler.load();
        } catch (SQLException e) {
            e.printStackTrace();
            fail();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }

        ArrayList<Tag> tags = handler.tags;
        boolean failed = false;
        for(Tag t1: tags) {
            for(Tag t2: tags) {
                if(t1.getTagName() != t2.getTagName()) {
                    //System.out.println(t1.getTagName()+" to "+t2.getTagName());
                    boolean hasPath = false;
                    for(Node n1: t1.getNodes()) {
                        for(Node n2: t2.getNodes()) {
                            Pathfinder p = new Pathfinder(n1, n2);
                            hasPath = p.hasPath();
                            if(hasPath) {
                                break;
                            }
                        }
                    }
                    if(!hasPath) {
                        System.out.println("no path between "+t1.getTagName()+" and "+t2.getTagName());
                        failed =
                     true;
                    }
                }
            }

        }
        dropSchemas(handler);
        assertFalse(failed);
    }
}
