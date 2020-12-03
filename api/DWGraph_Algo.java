package api;

import java.io.*;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

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
        return false;
    }

    @Override
    public double shortestPathDist(int src, int dest)
    {
        return 0;
    }

    @Override
    public List<node_data> shortestPath(int src, int dest) {
        return null;
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
            FileReader fr = new FileReader(file);
            Gson gson = new Gson();
            JsonReader jr = new JsonReader(fr);
            g.setTemp(gson.fromJson(jr,DWGraph_DS.WrapDWGraph_DS.class));
            return true;
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
    }
}
