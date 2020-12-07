package api;

import java.io.*;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import org.w3c.dom.Node;

public class DWGraph_Algo implements dw_graph_algorithms {
    private DWGraph_DS g;
    public DWGraph_Algo()
    {
        init(null);
    }
    public DWGraph_Algo(directed_weighted_graph g)
    {
        init(g);
    }

    @Override
    public void init(directed_weighted_graph g)
    {
        this.g = (DWGraph_DS)g;
    }


    @Override
    public directed_weighted_graph getGraph()
    {
        return g;
    }

    @Override
    public directed_weighted_graph copy()
    {
        return new DWGraph_DS(g);
    }

    @Override
    public boolean isConnected()
    {
        Iterator<node_data> iterator = g.getV().iterator();
        node_data start;
        if(iterator.hasNext())
            start = iterator.next();
        else
            return true;
        for (node_data n: g.getV())
        {
            n.setTag(0);
        }
        dfs(g,start);
        for (node_data n:g.getV())
        {
            if(n.getTag()==0)
                return false;
        }
        //
        DWGraph_DS gT = new DWGraph_DS(g,true);
        iterator = gT.getV().iterator();
        start = iterator.next();
        for (node_data n: gT.getV())
        {
            n.setTag(0);
        }
        dfs(gT,start);
        for (node_data n:gT.getV())
        {
            if(n.getTag()==0)
                return false;
        }
        return true;
    }
    private void  dfs(DWGraph_DS g,node_data v)
    {
        if(v==null)
            return;
        v.setTag(1);
        for(edge_data e:g.getE(v.getKey()))
        {
            if(g.getNode(e.getDest()).getTag() == 0)
                dfs(g,g.getNode(e.getDest()));
        }
    }

    @Override
    public double shortestPathDist(int src, int dest)
    {
        if(g==null)
            return -1;
        if(g.getNode(src)==null || g.getNode(dest)==null)
            return -1;

        PriorityQueue<NodeData> PQ = new PriorityQueue<NodeData>(g.getV().size(),(NodeData)g.getNode(src));
        for(node_data n:g.getV())
        {
            n.setWeight(-1);
        }
        NodeData source = (NodeData) g.getNode(src);
        source.setWeight(0);
        PQ.add(source);
        while(!PQ.isEmpty())
        {
            NodeData n = PQ.poll();
            for (edge_data e: g.getE(n.getKey()))
            {
                node_data temp = g.getNode(e.getDest());
                if(temp.getWeight()==-1)//means we never got to it
                {
                    temp.setWeight(e.getWeight()+n.getWeight());
                    PQ.add((NodeData)temp);
                }
                if(temp.getWeight()>e.getWeight()+n.getWeight())
                {
                    temp.setWeight(e.getWeight()+n.getWeight());
                    PQ.remove((NodeData)temp);
                    PQ.add((NodeData)temp);
                }
            }
        }
        return g.getNode(dest).getWeight();
    }

    @Override
    public List<node_data> shortestPath(int src, int dest) {
        if(g==null)
            return null;
        if(g.getNode(src)==null || g.getNode(dest)==null)
            return null;
        HashMap<NodeData,NodeData> parent = new HashMap<NodeData,NodeData>();
        PriorityQueue<NodeData> PQ = new PriorityQueue<NodeData>(g.getV().size(),(NodeData)g.getNode(src));

        for(node_data n:g.getV())
        {
            n.setWeight(-1);
        }
        NodeData source = (NodeData) g.getNode(src);
        source.setWeight(0);
        PQ.add(source);
        while(!PQ.isEmpty())
        {
            NodeData n = PQ.poll();
            for (edge_data e: g.getE(n.getKey()))
            {
                node_data temp = g.getNode(e.getDest());
                if(temp.getWeight()==-1)//means we never got to it
                {
                    temp.setWeight(e.getWeight()+n.getWeight());
                    PQ.add((NodeData)temp);
                    parent.put((NodeData)temp,n);
                }
                if(temp.getWeight()>e.getWeight()+n.getWeight())
                {
                    temp.setWeight(e.getWeight()+n.getWeight());
                    PQ.remove((NodeData)temp);
                    PQ.add((NodeData)temp);
                    parent.put((NodeData)temp,n);
                }
            }
        }
        if (!parent.containsKey(g.getNode(dest)))
            return null;
        ArrayList<node_data> ret = new ArrayList<node_data>();
        NodeData nodeData = (NodeData) g.getNode(dest);
        while (!parent.get(nodeData).equals(g.getNode(src)))
        {
            ret.add(nodeData);
            nodeData = parent.get(nodeData);
        }
        ret.add(nodeData);
        ret.add(parent.get(nodeData));
        Collections.reverse(ret);
        return ret;
    }

    @Override
    public boolean save(String file) {
        if (g==null)
            return false;
        try
        {
            File f = new File(file);
            if (f.exists())
                f.delete();
            f.createNewFile();
            FileWriter fw = new FileWriter(file);
            Gson  gson = new GsonBuilder().setPrettyPrinting().create();
            DWGraph_DS.WrapDWGraph_DS temp = g.new WrapDWGraph_DS();
            String towrite = gson.toJson(temp);
            if (f.canWrite())
            {
                fw.write(towrite);
            }
            else
                throw new IOException("Cant write to file: "+file);
            fw.close();
            return true;
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean load(String file) {
        try
        {
            File f = new File(file);
            if(!f.canRead())
                return false;
            FileReader fr = new FileReader(file);
            Gson gson = new Gson();
            JsonReader jr = new JsonReader(fr);
            g = new DWGraph_DS((DWGraph_DS.WrapDWGraph_DS) gson.fromJson(jr,DWGraph_DS.WrapDWGraph_DS.class));
            return true;
        }
        catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
    }
}
