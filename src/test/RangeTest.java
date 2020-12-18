package test;

import gameClient.util.Range;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RangeTest {
    @Test
    void isEmpty() {
        Range r1 = new Range(0,-1);
        Range r2 = new Range(0,1);
        assertTrue(r1.isEmpty());
        assertFalse(r2.isEmpty());
    }


    @Test
    void get_length()
    {
        Range r2 = new Range(0,1);
        assertEquals(1,r2.get_length());
    }


    @Test
    void getPortion()
    {
        Range r2 = new Range(0,1);
        assertEquals(0.5, r2.getPortion(0.5));
    }

    @Test
    void fromPortion() {
        Range r2 = new Range(0,1);
        assertEquals(0.5, r2.fromPortion(0.5));
    }
}