package test;

import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;
import gameClient.util.Range2Range;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Range2RangeTest {
    public Range xr;
    public Range yr;
    public Range2D part;
    public Range2Range r2r;
    @BeforeEach
    void init()
    {
        xr = new Range(0,1);
        yr = new Range(0,1);
        part = new Range2D(xr,yr);
        xr = new Range(0,2);
        yr = new Range(0,2);
        r2r = new Range2Range(part,new Range2D(xr,yr));
    }
    @Test
    void world2frame()
    {
        Point3D p1 = new Point3D(1,1,0);
        assertEquals(new Point3D(2,2,0),r2r.world2frame(p1));
    }

    @Test
    void frame2world()
    {
        Point3D p1 = new Point3D(2,2,0);
        assertEquals(new Point3D(1,1,0),r2r.frame2world(p1));
    }

}