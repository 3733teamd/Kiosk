package com.cs3733.teamd.Database;

import org.junit.Test;

import java.io.IOException;
import java.sql.*;

import static org.junit.Assert.*;

/**
 * Created by Stephen on 4/5/2017.
 */
public class DBHandlerTest {
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

    @Test
    public void regularDatabaseCanBeOpened() {
        try {
            DBHandler handler = new DBHandler();
            handler.close();
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testDatabaseCanBeOpened() {
        try {
            DBHandler handler = getTestHandler();
            handler.close();
        } catch (SQLException e) {
            e.printStackTrace();
            fail(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
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
    public void testSchemas() {
        DBHandler handler = getTestHandler();
        createSchemas(handler);
        dropSchemas(handler);
    }

    @Test
    public void testLoadData() {
        DBHandler handler = getTestHandler();
        createSchemas(handler);
        Statement s = null;
        try {
            s = handler.getConnection().createStatement();
            handler.loadDbEntriesFromFile(s, "/DatabaseImports/DBInitialImports.txt");
        } catch (SQLException e) {
            e.printStackTrace();
            fail();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }

        dropSchemas(handler);
    }

}
