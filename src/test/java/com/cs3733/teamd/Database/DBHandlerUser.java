package com.cs3733.teamd.Database;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by sdmichelini on 4/9/17.
 */
public class DBHandlerUser {
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
    public void testUserCanBeCreated() {
        int result = handler.createUser("jim", "xyz");
        assertNotEquals(0, result);
        assertNotEquals(-1, result);
    }

    @Test
    public void testUserCanBeFetched() {
        int result = handler.createUser("jim", "xyz");
        assertNotEquals(0, result);
        assertNotEquals(-1, result);

        int user_id = handler.getUser("jim", "xyz");

        assertEquals(result,user_id);
    }

    @Test
    public void testUserCannotBeFetched() {
        int user_id = handler.getUser("jim", "xyz");

        assertEquals(0,user_id);
    }

    @Test
    public void testRoleAddedToUser() {
        int result = handler.createUser("jim", "xyz");
        assertNotEquals(0, result);
        assertNotEquals(-1, result);

        assertTrue(handler.addRoleToUser(result,"admin"));
    }

    @Test
    public void testRoleRetrievedFromUser() {
        int result = handler.createUser("jim", "xyz");
        assertNotEquals(0, result);
        assertNotEquals(-1, result);

        assertTrue(handler.addRoleToUser(result,"admin"));

        List<String> roles = handler.getRolesForUser(result);

        assertEquals(1, roles.size());
        assertEquals("admin", roles.get(0));
    }

    @Test
    public void testRoleDeletedFromUser() {
        int result = handler.createUser("jim", "xyz");
        assertNotEquals(0, result);
        assertNotEquals(-1, result);

        assertTrue(handler.addRoleToUser(result,"admin"));

        List<String> roles = handler.getRolesForUser(result);

        assertEquals(1, roles.size());
        assertEquals("admin", roles.get(0));

        assertTrue(handler.removeRoleFromUser(result,"admin"));

        roles = handler.getRolesForUser(result);

        assertEquals(0, roles.size());
    }
}
