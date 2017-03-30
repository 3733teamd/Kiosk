package com.cs3733.teamd.Model;

/**
 * Created by Stephen on 3/30/2017.
 */
import org.junit.Test;
import static org.junit.Assert.*;

public class LocationTest {
    @Test
    public void LocationNotNull() {
        Location l = new Location(4, 1, "test");
        assertNotNull(l);
    }
}
