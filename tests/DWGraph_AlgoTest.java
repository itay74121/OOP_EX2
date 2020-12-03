package tests;

import api.DWGraph_Algo;
import api.DWGraph_DS;
import api.NodeData;
import org.junit.Test;

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
    public void init() {
    }

    @Test
    public void getGraph() {
    }

    @Test
    public void copy() {
    }

    @Test
    public void isConnected()
    {
    }

    @Test
    public void shortestPathDist() {
    }

    @Test
    public void shortestPath() {
    }

    @Test
    public void save()
    {
        before();
        ga.save("c:\\users\\itay\\ideaprojects\\oop_ex2\\src\\try.json");
    }

    @Test
    public void load() {
        before();
        ga.load("c:\\users\\itay\\ideaprojects\\oop_ex2\\src\\try.json");
        System.out.println();

    }
}