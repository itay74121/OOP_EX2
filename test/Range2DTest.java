package test;

import api.GeoLocation;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Range2DTest {
    public Range xr;
    public Range yr;
    public Range2D part;
    @BeforeEach
    void init()
    {
        xr = new Range(0,1);
        yr = new Range(0,1);
        part = new Range2D(xr,yr);
    }
    @Test
    void getPortion() {
        assertEquals(new Point3D(0.5,0.5,0),part.getPortion(new GeoLocation(0.5,0.5,0)));

    }

    @Test
    void fromPortion() {
        assertEquals(new Point3D(0.5,0.5,0), part.fromPortion(new Point3D(0.5,0.5,0)));
    }
}