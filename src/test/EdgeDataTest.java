package test;

import org.junit.Test;
import api.EdgeData;
import static org.junit.Assert.*;

public class EdgeDataTest {

    @Test
    public void getSrc() {
        EdgeData e = new EdgeData(5,6,9);
        assertEquals(5,e.getSrc());
    }

    @Test
    public void getDest()
    {
        EdgeData e = new EdgeData(5,6,9);
        assertEquals(6,e.getDest());
    }

    @Test
    public void getWeight()
    {
        EdgeData e = new EdgeData(5,6,9);
        assertEquals(9,e.getWeight(),0);
    }

    @Test
    public void getInfo()
    {
        EdgeData e = new EdgeData(5,6,9,0,"some info");
        assertEquals("some info",e.getInfo());
    }

    @Test
    public void setInfo()
    {
        EdgeData e = new EdgeData(5,6,9,0,"");
        assertEquals("",e.getInfo());
        e.setInfo("some info");
        assertEquals("some info",e.getInfo());
    }

    @Test
    public void getTag()
    {
        EdgeData e = new EdgeData(5,6,9,0,"");
        assertEquals(0,e.getTag());
    }

    @Test
    public void setTag()
    {
        EdgeData e = new EdgeData(5,6,9,0,"");
        assertEquals(0,e.getTag());
        e.setTag(8);
        assertEquals(8,e.getTag());
    }

    @Test
    public void testEquals()
    {
        EdgeData e1 = new EdgeData(5,6,8);
        EdgeData e2 = new EdgeData(5,6,8);
        assertEquals(e1,e2);
    }
}