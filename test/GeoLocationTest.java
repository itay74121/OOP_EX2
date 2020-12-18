package test;

import api.GeoLocation;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GeoLocationTest {
    private GeoLocation l1;
    private GeoLocation l2;
    @Before
    public void init()
    {
        l1 = new GeoLocation( 1,1,1);
        l2 = new GeoLocation(2,2,2);
    }
    @Test
    public void x()
    {
        assertEquals(l1.x(),1,0);
    }

    @Test
    public void y() {
        assertEquals(l1.y(),1,0);
    }

    @Test
    public void z() {
        assertEquals(l1.z(),1,0);

    }

    @Test
    public void distance() {
        assertEquals(l1.distance(l2),Math.sqrt(3),5);
        assertEquals(l1.distance(l2),l2.distance(l1),5);
    }

    @Test
    public void testEquals()
    {
        assertEquals(l1,new GeoLocation(1,1,1));
        assertNotEquals(l1,l2);
    }
}