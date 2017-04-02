package com.cs3733.teamd.Model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Me on 4/1/2017.
 */
public class TagTest {
    @Test
    public void testTagToString(){
        Tag t = new Tag("Testing");
        assertEquals("Testing",t.toString());
    }

    @Test
    public void testTagNameChanged(){
        Tag t = new Tag("Wrong");
        t.tagName = "Right";
        assertEquals("Right",t.tagName);
    }
}
