package tests;

import api.EdgeData;
import api.GeoLocation;
import api.NodeData;
import org.junit.Test;

import static org.junit.Assert.*;

public class NodeDataTest {

    @Test
    public void getKey()
    {
        NodeData n1 = new NodeData(5,8);
        assertEquals(n1.getKey(),5);
    }

    @Test
    public void getLocation()
    {
        GeoLocation location = new GeoLocation(6,5,4);
        NodeData n1 = new NodeData(5,8,8,"some info",new GeoLocation(6,5,4));
        assertEquals(n1.getLocation(),location);
    }

    @Test
    public void setLocation()
    {
        GeoLocation location = new GeoLocation(6,5,4);
        NodeData n1 = new NodeData(5,8);
        assertNull(n1.getLocation());
        n1.setLocation(location);
        assertEquals(n1.getLocation(),location);
    }

    @Test
    public void getWeight()
    {
        NodeData n1 = new NodeData(5,8);
        assertEquals(n1.getWeight(),8,3);
    }

    @Test
    public void setWeight() {
        NodeData n1 = new NodeData(5,8);
        assertEquals(n1.getWeight(),8,3);
        n1.setWeight(9);
        assertEquals(n1.getWeight(),9,3);

    }

    @Test
    public void getInfo()
    {
        NodeData n1 = new NodeData(5,8,9,"some info",null);
        assertEquals(n1.getInfo(),"some info");

    }

    @Test
    public void setInfo()
    {
        NodeData n1 = new NodeData(5,8);
        assertEquals(n1.getInfo(),"");
        n1.setInfo("some info");
        assertEquals(n1.getInfo(),"some info");
    }

    @Test
    public void getTag()
    {
        NodeData n1 = new NodeData(5,8,9,"some info",null);
        assertEquals(n1.getTag(),8);
    }

    @Test
    public void setTag()
    {
        NodeData n1 = new NodeData(5,8,9,"some info",null);
        assertEquals(n1.getTag(),8);
        n1.setTag(0);
        assertEquals(0,n1.getTag());
    }

    @Test
    public void hasNi()
    {
        NodeData n1 = new NodeData(5,8);
        NodeData n2 = new NodeData(6,8);
        n1.addNi(n2,4);
        // according to my implementation two nodes are neighbors as long as there is a way from one of them to the other
        assertTrue(n1.hasNi(n2));
        assertTrue(n2.hasNi(n1));
        n2.addNi(n1,8);
        n1.removeNi_oneway(n2);
        assertTrue(n2.hasNi(n1)||n1.hasNi(n2));
        n2.removeNi_oneway(n1);
        assertFalse(n2.hasNi(n1)||n1.hasNi(n2));
    }

    @Test
    public void hasEdge()
    {
        NodeData n1 = new NodeData(5,8);
        NodeData n2 = new NodeData(6,8);
        n1.addNi(n2,4);
        assertTrue(n1.hasEdge(n2));
        assertFalse(n2.hasEdge(n1));
        n1.removeNi_oneway(n2);
        assertFalse(n1.hasEdge(n2));
    }

    @Test
    public void addNi()
    {
        NodeData n1 = new NodeData(5,8);
        NodeData n2 = new NodeData(6,8);
        n1.addNi(n2,4);
        assertTrue(n1.hasNi(n2));
    }

    @Test
    public void removeNi_complete()
    {
        NodeData n1 = new NodeData(5,8);
        NodeData n2 = new NodeData(6,8);
        n1.addNi(n2,4);
        n2.addNi(n1,9);
        assertTrue(n1.hasNi(n2)&& n2.hasNi(n1));
        n1.removeNi_complete(n2);
        assertFalse(n1.hasNi(n2) || n2.hasNi(n1));
    }

    @Test
    public void removeNi_oneway()
    {
        // remove from node1 --> node2
        NodeData n1 = new NodeData(5,8);
        NodeData n2 = new NodeData(6,8);
        n1.addNi(n2,4);
        assertTrue(n1.hasNi(n2));
        n1.removeNi_oneway(n2);
        assertFalse(n1.hasNi(n2));
    }

    @Test
    public void removeNi_otherway()
    {
        NodeData n1 = new NodeData(5,8);
        NodeData n2 = new NodeData(6,8);
        n2.addNi(n1,4);
        assertTrue(n2.hasNi(n1));
        n1.removeNi_otherway(n2);
        assertFalse(n2.hasEdge(n1));
    }

    @Test
    public void testGetWeight()
    {
        NodeData n1 = new NodeData(5,8);
        NodeData n2 = new NodeData(6,8);
        n1.addNi(n2,4);
        assertEquals(n1.getWeight(n2),4,2);
    }

    @Test
    public void getEdge()
    {
        NodeData n1 = new NodeData(5,8);
        NodeData n2 = new NodeData(6,8);
        n1.addNi(n2,4);
        EdgeData e = new EdgeData(5,6,4);
        assertEquals(e,n1.getEdge(n2));
    }

    @Test
    public void testGetEdge()
    {
        NodeData n1 = new NodeData(5,8);
        NodeData n2 = new NodeData(6,8);
        n1.addNi(n2,4);
        EdgeData e = new EdgeData(5,6,4);
        assertEquals(e,n1.getEdge(6));
    }

    @Test
    public void testEquals()
    {
        NodeData n1 = new NodeData(5,8);
        NodeData n2 = new NodeData(6,8);
        n1.addNi(n2,4);
        NodeData n3 = new NodeData(5,8);
        NodeData n4 = new NodeData(6,8);
        n3.addNi(n4,4);
        assertEquals(n1,n3);
    }
}