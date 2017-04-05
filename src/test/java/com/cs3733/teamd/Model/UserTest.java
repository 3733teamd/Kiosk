package com.cs3733.teamd.Model;

/**
 * Created by sdmichelini on 4/1/17.
 */
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.LinkedList;

public class UserTest {

    @Test
    public void usernameInitialized() {
        User u = new User("my-username", null);
        assertEquals("my-username", u.getUsername());
    }

    @Test
    public void rolesInitialized() {
        List<String> roles = new LinkedList<String>();
        roles.add("admin");
        roles.add("user");

        User u = new User("my-username", roles);

        assertEquals(roles.size(), u.getRoles().size());
        List<String> user_roles = u.getRoles();
        assertTrue(user_roles.containsAll(roles));
    }

    @Test
    public void hasRoleWorks() {
        List<String> roles = new LinkedList<String>();
        roles.add("admin");
        roles.add("user");

        User u = new User("my-username", roles);

        for(String role: roles) {
            assertTrue(u.hasRole(role));
        }
    }

    @Test
    public void roleAdded() {
        List<String> roles = new LinkedList<String>();

        User u = new User("my-username", roles);

        u.addRole("admin");

        assertTrue(u.hasRole("admin"));
    }

    @Test
    public void hashWorks() {
        try{
            String outputString = User.calculateHash("test");
            assertEquals("098f6bcd4621d373cade4e832627b4f6", outputString);
        }
        catch(Exception e){
            fail("Exception thrown");
        }

    }
}
