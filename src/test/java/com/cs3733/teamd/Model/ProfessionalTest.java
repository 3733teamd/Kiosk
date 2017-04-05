package com.cs3733.teamd.Model;

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
        Professional p = new Professional("Test Person", titles,19);
        assertNotNull(p);
    }

    @Test
    public void testProfessionalString() {
        Professional p = new Professional("Brown", titles,10);
        assertEquals("[MD, CPNP] Brown",p.toString());
    }

    @Test
    public void testProfessionalSetTitles() {
        ArrayList<Title> titles = new ArrayList<Title>();
        titles.add(Title.MD);
        Professional p = new Professional("John", titles,10);
        assertEquals("[MD] John",p.toString());
        p.addTitle(Title.DO);
        assertEquals("[MD, DO] John",p.toString());

    }
}

