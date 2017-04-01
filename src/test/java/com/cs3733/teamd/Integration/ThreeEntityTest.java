package com.cs3733.teamd.Integration;

import com.cs3733.teamd.Model.Professional;
import com.cs3733.teamd.Model.Tag;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Me on 4/1/2017.
 */
public class ThreeEntityTest {
    @Test
    public void addTagToProf(){
        Professional p = new Professional("Bob", Professional.Title.DR,"Bones");
        Tag t = new Tag("Testing");
        p.addTag(t);
        assertTrue(t.containsProf(p) && p.containsTag(t));
    }
}
