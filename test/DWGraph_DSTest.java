package test;

import api.DWGraph_DS;
import api.EdgeData;
import api.NodeData;
import api.node_data;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DWGraph_DSTest {
    DWGraph_DS g;


    private void init()
    {
        g = new DWGraph_DS();
        g.addNode(new NodeData(1,5));
        g.addNode(new NodeData(2,5));
        g.addNode(new NodeData(3,5));
        g.connect(1,2,5);
        g.connect(1,3,6);
        g.connect(2,1,8);
    }

    @Test
    public void getNode()
    {
        init();
        assertEquals(g.getNode(1).getKey(),1);
        assertNull(g.getNode(4));
    }

    @Test
    public void getEdge()
    {
        init();
        assertEquals(g.getEdge(1,2),new EdgeData(1,2,5));
        assertNull(g.getEdge(3,1));
    }

    @Test
    public void addNode()
    {
        init();
        int s = g.nodeSize();
        g.addNode(new NodeData(5,8));
        assertEquals(s+1,g.nodeSize());
        s+=1;
        g.addNode(new NodeData(2,8));
        assertEquals(s,g.nodeSize());
    }

    @Test
    public void connect()
    {
        init();
        assertNull(g.getEdge(3,1));
        g.connect(3,1,8);
        assertNotNull(g.getEdge(3,1));

    }

    @Test
    public void removeNode()
    {
        init();
        int s = g.nodeSize();
        g.removeNode(8);
        assertEquals(s,g.nodeSize());
        g.removeNode(1);
        assertEquals(s-1,g.nodeSize());
    }

    @Test
    public void removeEdge()
    {
        init();
        g.removeEdge(1,3);
        assertNull(g.getEdge(1,3));
    }

}