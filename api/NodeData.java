package api;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.HashMap;

public class NodeData implements node_data
{
    private int key;
    private int tag;
    private double weight;
    private String info;
    private GeoLocation location;
    private HashMap<Integer, NodeData> neighbors;
    private HashMap<Integer, EdgeData> neighborsedges;
    private static int numofnodes = 0;


    public NodeData(int key,double weight)
    {
        this(key,0,weight,"",null);
    }
    public NodeData(int key, int tag, double weight)
    {
        this(key,tag,weight,"",null);
    }
    public NodeData(int key, int tag,double weight,String info)
    {
        this(key,tag,weight,info,null);
    }

    public NodeData(int key, int tag, double weight,String info, GeoLocation location)
    {
        setKey(key);
        setTag(tag);
        setWeight(weight);
        setInfo(info);
        setLocation(location);
        neighbors =new HashMap<Integer, NodeData>();
        neighborsedges = new HashMap<Integer, EdgeData>();
        numofnodes++;
    }

    @Override
    public int getKey()
    {
        return key;
    }

    @Override
    public geo_location getLocation()
    {
        return this.location;
    }

    @Override
    public void setLocation(geo_location p)
    {
        this.location = (GeoLocation) p;
    }

    @Override
    public double getWeight()
    {
        return this.weight;
    }

    @Override
    public void setWeight(double w)
    {
        weight = w;
    }

    @Override
    public String getInfo() {
        return info;
    }

    @Override
    public void setInfo(String s)
    {
        info = s;
    }

    @Override
    public int getTag() {
        return tag;
    }

    @Override
    public void setTag(int t)
    {
        tag = t;
    }

    private void setKey(int key) {
        this.key = key;
    }
    //todo hasNi

    /**
     * ask if two nodes are neighbors.
     * @param node
     * @return if two nodes are adjacent that means that there is an edge from one of them to the other.
     */
    public boolean hasNi(NodeData node)
    {
        return neighbors.containsKey(node.getKey())&&(hasEdge(node)||node.hasEdge(this));
    }
    /**
     * ask if there is an edge from this node to the other node.
     * @param node
     * @return
     */
    public boolean hasEdge(NodeData node)
    {
        return neighborsedges.containsKey(node.getKey());
    }
    //todo addNi
    public boolean addNi(NodeData other,double weight)
    {
        if(weight<0)
            return false;
        if (other.getKey()==key) return false;
        if(!hasNi(other))
        {
            neighbors.put(other.getKey(),other);
            other.neighbors.put(getKey(),this);
        }
        if (hasEdge(other)) // update the weight in that edge
        {
            EdgeData edge = new EdgeData(neighborsedges.get(other.getKey()),weight);
            neighborsedges.put(other.getKey(),edge);
            return true;
        }
        else // create a new edge with this weight between them
        {
            EdgeData edge = new EdgeData(this.getKey(),other.getKey(),weight);
            neighborsedges.put(other.getKey(),edge);
            return true;
        }
    }

    //todo removeNi_complete
    public boolean removeNi_complete(NodeData node)
    {
        if(!hasNi(node))
        {
            return false;
        }
        else
        {
            boolean flag=false;
            if(neighbors.containsKey(node.getKey()))
            {
                neighbors.remove(node.getKey());
                neighborsedges.remove(node.getKey());
                flag=true;
            }
            if(node.neighbors.containsKey(getKey()))
            {
                node.neighbors.remove(getKey());
                node.neighborsedges.remove(getKey());
                flag=true;
            }
            return flag;
        }
    }
    //todo removeNi_oneway this->other
    public boolean removeNi_oneway(NodeData node)
    {
        if(hasNi(node)&&hasEdge(node))
        {
            neighborsedges.remove(node.getKey());
            if(!hasNi(node))
            {
                neighbors.remove(node.getKey());
                node.neighbors.remove(getKey());
            }
            return true;
        }
        else
            return false;
    }
    //todo removeNi_otherway this<-other
    public boolean removeNi_otherway(NodeData node)
    {
        if(hasNi(node)&&node.hasEdge(this))
        {
            node.neighborsedges.remove(getKey());
            if(!hasNi(node))
            {
                neighbors.remove(node.getKey());
                node.neighbors.remove(getKey());
            }
            return true;
        }
        else
            return false;
    }
    //todo getWeight
    public double getWeight(NodeData node)
    {
        if(hasEdge(node)) // if there is an edge between them
            return neighborsedges.get(node.getKey()).getWeight();
        else
            return -1; // symbols that there is no edge between them
    }
    public EdgeData getEdge(NodeData node)
    {
        return neighborsedges.get(node.getKey());
    }
    public EdgeData getEdge(int key)
    {
        return neighborsedges.get(key);
    }
    public HashMap<Integer,NodeData> getNi()
    {
        return neighbors;
    }
    public ArrayList<NodeData> copyOfNodes()
    {
        ArrayList<NodeData> arr = new ArrayList<NodeData>();
        for (NodeData n: neighbors.values())
        {
            arr.add(n);
        }
        return arr;
    }
    public HashMap<Integer,EdgeData> getNiEdges()
    {
        return neighborsedges;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof NodeData)
        {
            NodeData t = (NodeData) obj;
            if (t.key!=key) return false;
            for(EdgeData e:t.getNiEdges().values())
            {
                if(!(this.getEdge(e.getDest()).equals(e))) return false;
            }
            for(EdgeData e : neighborsedges.values())
            {
                if(!(t.getEdge(e.getDest()).equals(e))) return false;
            }
            // in the end return true;
            return true;
        }
        return false;
    }
}










