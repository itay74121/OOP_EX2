package tests;

import api.DWGraph_Algo;
import api.DWGraph_DS;
import api.NodeData;
import api.node_data;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class DWGraph_AlgoTest {
    DWGraph_DS g;
    DWGraph_Algo ga;
    private void before()
    {
        g = new DWGraph_DS();
        g.addNode(new NodeData(1,5));
        g.addNode(new NodeData(2,5));
        g.addNode(new NodeData(3,5));
        g.connect(1,2,5);
        g.connect(1,3,6);
        g.connect(2,1,8);
        ga = new DWGraph_Algo(g);
    }

    @Test
    public void copy() {
        before();
        DWGraph_DS g1 =(DWGraph_DS) ga.getGraph();
        DWGraph_DS g2=(DWGraph_DS) ga.getGraph();
        assertEquals(g1,g2);
    }

    @Test
    public void isConnected()
    {
        before();
        assertFalse(ga.isConnected());
        g.connect(3,1,8);
        assertTrue(ga.isConnected());
    }

    @Test
    public void shortestPathDist()
    {
        before();
        g.connect(2,3,4);
        assertEquals(ga.shortestPathDist(2,3),4,0);
    }

    @Test
    public void shortestPath()
    {
        before();
        ArrayList<node_data> arr1 = (ArrayList<node_data>) ga.shortestPath(2,3);
        ArrayList<node_data> arr2 = new ArrayList<node_data>();
        arr2.add(g.getNode(2));
        arr2.add(g.getNode(1));
        arr2.add(g.getNode(3));
        for (int i = 0; i < 3; i++)
        {
            assertEquals((NodeData)arr1.get(i),arr2.get(i));
        }
    }

    @Test
    public void save_load()
    {
        save();
        DWGraph_DS g = (DWGraph_DS) ga.getGraph();
        load();
        assertEquals(g,ga.getGraph());
        ga.load("E:\\documents\\ex2\\src\\tests\\A0.json");
        assertNotEquals(g,ga.getGraph());
    }

    @Test
    public void save()
    {
        before();
        ga.save("E:\\documents\\ex2\\src\\tests\\try.json");
    }

    @Test
    public void load() {
        before();
        ga.load("E:\\documents\\ex2\\src\\tests\\try.json");
        System.out.println();

    }
}