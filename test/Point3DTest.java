package test;

import gameClient.util.Point3D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Point3DTest {

    @Test
    void distance() {
        Point3D p1 = new Point3D(0,0,0);
        Point3D p2 = new Point3D(2,2,2);
        assertEquals(Math.sqrt(12),p1.distance(p2));
    }

    @Test
    void testEquals()
    {
        Point3D p1 = new Point3D(0,0,0);
        Point3D p2 = new Point3D(2,2,2);
        Point3D p3 = new Point3D(0,0,0);
        assertNotEquals(p1,p2);
        assertEquals(p1,p3);
    }
}