package com.cs3733.teamd.Database;

import com.cs3733.teamd.Model.Node;
import org.junit.Test;

import java.io.IOException;
import java.sql.*;
import java.util.List;

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
            handler.loadDbEntriesFromFileIntoDb(s, "/DatabaseImports/DBInitialImports.txt");
            handler.load();
        } catch (SQLException e) {
            e.printStackTrace();
            fail();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }

        dropSchemas(handler);
    }

    @Test
    public void testLoadAdjacentNodes() {
        DBHandler handler = getTestHandler();
        createSchemas(handler);
        Statement s = null;
        try {
            s = handler.getConnection().createStatement();
            handler.loadDbEntriesFromFileIntoDb(s, "/DatabaseImports/AdjacentNodeTestData.txt");
            handler.load();
            List<Node> nodes = handler.nodes;
            for(Node n: nodes) {
                if(n.getID() == 1) {
                    assertEquals(2, n.getNodes().size());
                    for(Node n2: n.getNodes()) {
                        assertTrue( "ID is: "+n2.getID(),n2.getID() == 2 || n2.getID() == 5);
                    }
                } else if(n.getID() == 2) {
                    assertEquals(1, n.getNodes().size());
                } else if(n.getID() == 5) {
                    assertEquals(1, n.getNodes().size());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            dropSchemas(handler);
            fail();
        } catch (IOException e) {
            e.printStackTrace();
            dropSchemas(handler);
            fail();
        } catch(AssertionError e) {
            dropSchemas(handler);
            fail();
        }
        dropSchemas(handler);
    }

}
