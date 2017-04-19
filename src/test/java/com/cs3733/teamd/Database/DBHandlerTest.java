package com.cs3733.teamd.Database;

import com.cs3733.teamd.Model.Entities.Node;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
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
    private static DBHandler getTestHandler() {
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

    @Ignore("We are deprecating the old DB Insert files") @Test
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

    @Test
    public void testSaveNode() {
        DBHandler handler = getTestHandler();
        createSchemas(handler);
        int result = handler.saveNode(1,5,7);
        try {
            assertNotEquals(-1, result);
        } catch(AssertionError e) {
            dropSchemas(handler);
            fail();
        }
        dropSchemas(handler);
    }

    @Test
    public void testSaveNodeAndLoad() {
        DBHandler handler = getTestHandler();
        createSchemas(handler);
        int result = handler.saveNode(1,5,7);
        List<Node> nodes = new ArrayList<Node>();
        try {
            assertNotEquals(-1, result);
            handler.load();
            nodes = handler.nodes;

        } catch(SQLException e) {
            dropSchemas(handler);
            fail();
        } catch (AssertionError e) {
            dropSchemas(handler);
            fail();
        }
        dropSchemas(handler);
        assertNotNull(nodes);
        assertEquals(1, nodes.size());
        assertEquals(result, nodes.get(0).getID());
        assertEquals(1, nodes.get(0).getX());
        assertEquals(5, nodes.get(0).getY());
        assertEquals(7, nodes.get(0).getFloor());
    }

    @Test
    public void testDeleteNode() {
        DBHandler handler = getTestHandler();
        createSchemas(handler);
        int result = handler.saveNode(1,5,7);

        try {
            assertNotEquals(-1, result);
            assertTrue(handler.deleteNode(result));
        } catch(AssertionError e) {
            dropSchemas(handler);
            fail();
        }
        dropSchemas(handler);
    }

    @Test
    public void testUpdateNode() {
        DBHandler handler = getTestHandler();
        createSchemas(handler);

        int result = handler.saveNode(1,5,7);

        try {
            assertNotEquals(-1, result);
            assertTrue(handler.updateNode(6,7,result));
        } catch(AssertionError e) {
            dropSchemas(handler);
            fail();
        }
        dropSchemas(handler);
    }

    @Test
    public void testSaveTag() {
        DBHandler handler = getTestHandler();
        createSchemas(handler);
        int result = handler.saveTag("my-tag");
        try {
            assertNotEquals(-1, result);
        } catch(AssertionError e) {
            dropSchemas(handler);
            fail();
        }
        dropSchemas(handler);
    }

    @Test
    public void testDeleteTag() {
        DBHandler handler = getTestHandler();
        createSchemas(handler);
        int result = handler.saveTag("my-tag");

        try {
            assertNotEquals(-1, result);
            assertTrue(handler.deleteTag(result));
        } catch(AssertionError e) {
            dropSchemas(handler);
            fail();
        }
        dropSchemas(handler);
    }

    @Test
    public void testUpdateTag() {
        DBHandler handler = getTestHandler();
        createSchemas(handler);

        int result = handler.saveTag("my-result");

        try {
            assertNotEquals(-1, result);
            assertTrue(handler.updateTag("my-result-2",result));
        } catch(AssertionError e) {
            dropSchemas(handler);
            fail();
        }
        dropSchemas(handler);
    }

    @Test
    public void testSaveProfessional() {
        DBHandler handler = getTestHandler();
        createSchemas(handler);
        int result = handler.saveProfessional("my-prof");
        try {
            assertNotEquals(-1, result);
        } catch(AssertionError e) {
            dropSchemas(handler);
            fail();
        }
        dropSchemas(handler);
    }

    @Test
    public void testDeleteProfessional() {
        DBHandler handler = getTestHandler();
        createSchemas(handler);
        int result = handler.saveProfessional("my-tag");

        try {
            assertNotEquals(-1, result);
            assertTrue(handler.deleteProfessional(result));
        } catch(AssertionError e) {
            dropSchemas(handler);
            fail();
        }
        dropSchemas(handler);
    }

    @Test
    public void testUpdateProfessional() {
        DBHandler handler = getTestHandler();
        createSchemas(handler);

        int result = handler.saveProfessional("my-result");

        try {
            assertNotEquals(-1, result);
            assertTrue(handler.updateProfessional("my-result-2",result));
        } catch(AssertionError e) {
            dropSchemas(handler);
            fail();
        }
        dropSchemas(handler);
    }

}
