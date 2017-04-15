package com.cs3733.teamd.Model;

import com.cs3733.teamd.Model.Entities.ProTitle;
import com.cs3733.teamd.Model.Entities.Professional;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ProfessionalTest {
    List<ProTitle> titles =
            new ArrayList<ProTitle>(Arrays.asList(new ProTitle[]{new ProTitle("xxx","xxx",1)}));

    @Test
    public void testProfessionalNotNull() {
        List<ProTitle> titles = new ArrayList<ProTitle>();
        titles.add(new ProTitle("xxx","xxx",1));
        Professional p = new Professional("Test Person", titles,19);
        assertNotNull(p);
    }

    @Test
    public void testProfessionalString() {
        Professional p = new Professional("Brown", titles,10);
        assertEquals("[MD, CPNP] Brown",p.toString());
    }
    
}

