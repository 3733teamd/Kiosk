package com.cs3733.teamd.Model;

/**
 * Created by sdmichelini on 4/1/17.
 */

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ProfessionalTest {
    ArrayList<Title> titles =
            new ArrayList<>(Arrays.asList(new Title[]{Title.MD, Title.CPNP}));

    @Test
    public void testProfessionalNotNull() {
        ArrayList<Title> titles = new ArrayList<Title>();
        titles.add(Title.MD);
        Professional p = new Professional("Test Person", titles);
        assertNotNull(p);
    }

    @Test
    public void testProfessionalString() {
        Professional p = new Professional("Brown", titles);
        assertEquals("[MD, CPNP] Brown",p.toString());
    }

    @Test
    public void testProfessionalSetTitles() {
        ArrayList<Title> titles = new ArrayList<Title>();
        titles.add(Title.MD);
        Professional p = new Professional("John", titles);
        assertEquals("[] John",p.toString());
        p.addTitle(Title.DO);
        assertEquals("[DO] John",p.toString());

    }
}
