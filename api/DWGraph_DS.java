package api;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class DWGraph_DS implements directed_weighted_graph
{
    private HashMap<Integer, node_data> nodes;
    private int MC;
    private WrapDWGraph_DS temp;
    public DWGraph_DS()
    {
        nodes = new HashMap<Integer, node_data>();
        MC = 0;
    }

    public DWGraph_DS( DWGraph_DS g)
    {
        this();
        if (g==null)
            return;
        for(node_data n: g.getV())
        {
            addNode(new NodeData(n.getKey(),n.getWeight()));
        }
        for(node_data n: g.getV())
        {
            for(edge_data e: getE(n.getKey()))
            {
                connect(e.getSrc(),e.getDest(),e.getWeight());
            }
        }
        MC = g.getMC();
    }
    @Override
    public node_data getNode(int key)
    {
        return nodes.get(key);
    }


    @Override
    public edge_data getEdge(int src, int dest)
    {
        NodeData source = (NodeData) getNode(src);
        return source.getEdge(dest);
    }

    @Override
    public void addNode(node_data n)
    {
        nodes.put(n.getKey(),n);
        MC++;
    }

    @Override
    public void connect(int src, int dest, double w)
    {
        if (w<0)
            return;
        if (getNode(src) == null || getNode(dest)==null)
            return;
        NodeData source = (NodeData)getNode(src);
        source.addNi((NodeData) getNode(dest),w);
        MC++;
    }

    @Override
    public Collection<node_data> getV()
    {
        return nodes.values();
    }

    @Override
    public Collection<edge_data> getE(int node_id)
    {
        if (getNode(node_id)==null)
            return null;
        ArrayList<edge_data> arr = new ArrayList<edge_data>();
        NodeData node = (NodeData) getNode(node_id);
        for(edge_data e:node.getNiEdges().values())
        {
            arr.add(e);
        }
        return arr;
    }

    @Override
    public node_data removeNode(int key)
    {
        if (getNode(key)==null)
            return null;
        NodeData node = (NodeData) getNode(key);
        for(NodeData n: node.copyOfNodes())
        {
            node.removeNi_complete(n);
            MC+=2;
        }
        nodes.remove(key);
        MC++;
        return node;
    }

    @Override
    public edge_data removeEdge(int src, int dest) {
        if(getNode(src)==null||getNode(dest)==null)
            return null;
        NodeData source = (NodeData)getNode(src);
        if(source.hasEdge((NodeData) getNode(dest)))
        {
            edge_data e = source.getEdge(dest);
            source.removeNi_oneway((NodeData)getNode(dest));
            MC++;
            return e;
        }
        else
            return null;
    }

    @Override
    public int nodeSize() {
        return nodes.size();
    }

    @Override
    public int edgeSize()
    {
        int s = 0;
        for(node_data n : getV())
        {
            s+=getE(n.getKey()).size();
        }
        return s;
    }

    @Override
    public int getMC()
    {
        return MC;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof DWGraph_DS)
        {
            DWGraph_DS other = (DWGraph_DS) obj;
            for(node_data node: other.getV())
            {
                if(this.getNode(node.getKey())==null) return false;
                if(!((NodeData)this.getNode(node.getKey())).equals(node)) return false;

            }
            for(node_data node: getV())
            {
                if(other.getNode(node.getKey())==null) return false;
                if(!((NodeData)other.getNode(node.getKey())).equals(node)) return false;
            }
            return true;
        }
        else
        {
            return false;
        }
    }

    public void setTemp(WrapDWGraph_DS temp) {
        this.temp = temp;
    }

    public class WrapDWGraph_DS
    {
        private ArrayList<NodeData.WrapNodeData> Nodes;
        private ArrayList<EdgeData.WrapEdgeData> Edges;
        public WrapDWGraph_DS()
        {
            Nodes = new ArrayList<NodeData.WrapNodeData>();
            Edges = new ArrayList<EdgeData.WrapEdgeData>();
            for(node_data n : getV())
            {
                NodeData t = (NodeData) n;
                NodeData.WrapNodeData t2 = t.new WrapNodeData();
                Nodes.add(t2);
                for(edge_data e: getE(n.getKey()))
                {
                    EdgeData t3 = (EdgeData)e;
                    EdgeData.WrapEdgeData t4 = t3.new WrapEdgeData();
                    Edges.add(t4);
                }
            }
        }
    }
}
