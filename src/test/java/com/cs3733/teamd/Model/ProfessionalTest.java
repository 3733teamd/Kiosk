package com.cs3733.teamd.Model;

/**
 * Created by sdmichelini on 4/1/17.
 */

import org.junit.Test;
import static org.junit.Assert.*;

public class ProfessionalTest {
    private static Professional.Title doctor = Professional.Title.DR;

    @Test
    public void testProfessionalNotNull() {
        Professional p = new Professional("Test Person", doctor, "Radiology");
        assertNotNull(p);
    }

    @Test
    public void testProfessionalString() {
        Professional p = new Professional("Brown", doctor, "Radiology");
        assertEquals("Dr. Brown (Radiology)",p.toString());
    }
}
