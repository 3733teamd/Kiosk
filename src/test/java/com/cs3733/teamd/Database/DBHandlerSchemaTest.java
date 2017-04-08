package com.cs3733.teamd.Database;

import com.cs3733.teamd.Model.Node;
import com.cs3733.teamd.Model.Professional;
import com.cs3733.teamd.Model.Tag;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by sdmichelini on 4/8/17.
 */
public class DBHandlerSchemaTest {
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

    private DBHandler handler;

    @Before
    public void setupTables() {
        handler = getTestHandler();
        createSchemas(handler);
    }

    @After
    public void dropTables() {
        dropSchemas(handler);
    }

    @Test
    public void testAddEdgeAndLoadsIt() {
        int result1 = handler.saveNode(1,2,6);
        int result2 = handler.saveNode(3,4,5);

        assertNotEquals(-1, result1);
        assertNotEquals(-1, result2);

        assertTrue(handler.addEdge(result1, result2));

        // Now let's check the underlyinh structure
        List<Node> nodes = new ArrayList<Node>();
        try {
            handler.load();
        } catch(SQLException e) {
            e.printStackTrace();
            fail();
        }
        nodes = handler.nodes;
        for(Node n: nodes) {
            // 1 node
            assertEquals(1, n.getNodes().size());
            if(n.getFloor() == 5) {
                assertEquals(3,n.getX());
                assertEquals(4, n.getY());
            } else if(n.getFloor() == 6) {
                assertEquals(1, n.getX());
                assertEquals(2,n.getY());
            } else {
                fail("Unexpected floor of: "+n.getFloor());
            }
        }
    }

    @Test
    public void testDeleteEdge() {
        int result1 = handler.saveNode(1,2,6);
        int result2 = handler.saveNode(3,4,5);

        assertNotEquals(-1, result1);
        assertNotEquals(-1, result2);

        assertTrue(handler.removeEdge(result1, result2));

        try {
            handler.load();
        } catch(SQLException e) {
            e.printStackTrace();
            fail();
        }
        // The nodes should have zero edges.
        for(Node n: handler.nodes) {
            assertEquals(0, n.getNodes().size());
        }
    }

    @Test
    public void testAddTagToProfessional() {
        int resultP = handler.saveProfessional("Wong");
        int resultT = handler.saveTag("my-tag");

        assertNotEquals(-1, resultP);
        assertNotEquals(-1, resultT);

        assertTrue(handler.addTagToProfessional(resultT, resultP));

        // Now we need to see if the tag was actually added...

        try {
            handler.load();
        } catch(SQLException e) {
            e.printStackTrace();
            fail();
        }

        List<Professional> professionals = handler.professionals;
        List<Tag> tags = handler.tags;

        assertEquals(1, professionals.size());
        assertEquals(1, professionals.get(0).getTags().size());

        assertEquals(1, tags.size());
        List<Tag> profTags = professionals.get(0).getTags();
        assertEquals(profTags.get(0).getId(), tags.get(0).getId());
    }

    @Test
    public void testRemoveTagFromProfessional() {
        int resultP = handler.saveProfessional("Wong");
        int resultT = handler.saveTag("my-tag");

        assertNotEquals(-1, resultP);
        assertNotEquals(-1, resultT);

        assertTrue(handler.addTagToProfessional(resultT, resultP));

        assertTrue(handler.removeTagFromProfessional(resultT, resultP));

        try {
            handler.load();
        } catch(SQLException e) {
            e.printStackTrace();
            fail();
        }

        List<Professional> professionals = handler.professionals;
        List<Tag> tags = handler.tags;

        assertEquals(1, professionals.size());
        assertEquals(0, professionals.get(0).getTags().size());
    }

    @Ignore("We don't yet have a way of creating new titles")@Test
    public void testAddTitleToProfessional() {
        int resultP = handler.saveProfessional("Wong");

        assertNotEquals(-1, resultP);

        assertTrue(handler.addTitleToProfessional(1,resultP));

        // Now we need to see if the tag was actually added...

        try {
            handler.load();
        } catch(SQLException e) {
            e.printStackTrace();
            fail();
        }

        List<Professional> professionals = handler.professionals;
        List<Tag> tags = handler.tags;

        assertEquals(1, professionals.size());
        assertEquals(1, professionals.get(0).getTitles().size());
    }

    @Ignore("We don't yet have a way of creating new titles")@Test
    public void testRemoveTitleFromProfessional() {
        int resultP = handler.saveProfessional("Wong");

        assertNotEquals(-1, resultP);

        assertTrue(handler.addTitleToProfessional(1, resultP));

        assertTrue(handler.removeTagFromProfessional(1, resultP));

        try {
            handler.load();
        } catch(SQLException e) {
            e.printStackTrace();
            fail();
        }

        List<Professional> professionals = handler.professionals;
        List<Tag> tags = handler.tags;

        assertEquals(1, professionals.size());
        assertEquals(0, professionals.get(0).getTitles().size());
    }

    @Test
    public void testAddNodeTag() {
        int resultN = handler.saveNode(1,3,7);
        int resultT = handler.saveTag("Bathroom");

        assertNotEquals(-1, resultN);
        assertNotEquals(-1, resultT);

        assertTrue(handler.addNodeTag(resultN,resultT));

        try {
            handler.load();
        } catch(SQLException e) {
            e.printStackTrace();
            fail();
        }

        List<Node> nodes = handler.nodes;
        List<Tag> tags = handler.tags;

        assertEquals(1, nodes.size());
        assertEquals(1, tags.size());

        assertTrue(nodes.get(0).containsTag(tags.get(0)));
    }

    @Test
    public void testRemoveNodeTag() {
        int resultN = handler.saveNode(1,3,7);
        int resultT = handler.saveTag("Bathroom");

        assertNotEquals(-1, resultN);
        assertNotEquals(-1, resultT);

        assertTrue(handler.addNodeTag(resultN,resultT));
        assertTrue(handler.removeNodeTag(resultN, resultT));

        try {
            handler.load();
        } catch(SQLException e) {
            e.printStackTrace();
            fail();
        }

        List<Node> nodes = handler.nodes;
        List<Tag> tags = handler.tags;

        assertEquals(1, nodes.size());
        assertEquals(1, tags.size());

        assertFalse(nodes.get(0).containsTag(tags.get(0)));
    }

}
