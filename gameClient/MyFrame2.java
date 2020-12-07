package gameClient;

import Server.Game_Server_Ex2;
import api.DWGraph_DS;
import api.game_service;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MyFrame2 extends JFrame implements Runnable
{
    private MyPanel panel;
    private DWGraph_DS graph_ds;
    private ArrayList<CL_Pokemon> pokemons;
    private ArrayList<CL_Agent> agents;
    private Dimension screen_size;
    private double fractionX;
    private double fractionY;
    private String graph_json;
    private String pokemons_json;
    private String agents_json;
    public MyFrame2(double fractionX,double fractionY)
    {
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen_size = Toolkit.getDefaultToolkit().getScreenSize();
        setFractionX(fractionX);
        setFractionY(fractionY);
        setBounds(0,0,(int)(fractionX*screen_size.width),(int)(fractionY*screen_size.height));
        panel = new MyPanel(fractionX,fractionY);
        add(panel);
        graph_json = "";
        pokemons_json = "";
        agents_json = "";
    }
    public void updateFrame (String jgraph, String jpokemon, String jagent)
    {
        if (jgraph!=null)
        {
            if (!jgraph.equals(graph_json)) {
                Gson gson = new Gson();
                graph_ds = new DWGraph_DS((DWGraph_DS.WrapDWGraph_DS) gson.fromJson(jgraph, DWGraph_DS.WrapDWGraph_DS.class));
                graph_json=jgraph;
            }
        }
        if (jpokemon!=null)
        {
            if (!pokemons_json.equals(jpokemon))
            {
                try
                {
                    ArrayList<CL_Pokemon> pokarray = new ArrayList<CL_Pokemon>();
                    JSONArray jsonpokemonArray = (JSONArray) (new JSONObject(jpokemon)).get("Pokemons");
                    for (int i =0;i<jsonpokemonArray.length();i++)
                    {
                        pokarray.add(CL_Pokemon.init_from_json(jsonpokemonArray.get(i).toString()));
                    }
                    pokemons =  pokarray;
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
                pokemons_json = jpokemon;
            }
        }
        if(jagent!=null)
        {
            if (!agents_json.equals(jagent))
            {
                try
                {
                    ArrayList<CL_Agent> arr = new ArrayList<CL_Agent>();
                    JSONArray Agentarray = (JSONArray) (new JSONObject(jagent)).get("Agents");
                    for (int i = 0; i < Agentarray.length(); i++)
                    {
                        arr.add(CL_Agent.fromjsontoCLAgent((JSONObject) Agentarray.get(i), graph_ds));
                    }
                    agents = arr;
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
                agents_json = jagent;
            }
        }
        panel.updatePanel(graph_ds,pokemons,agents);
    }
    public static void main(String [] args)
    {

    }
    public void run()
    {
        panel.setVisible(true);
        setVisible(true);
        while (true)
        {
            repaint();
        }
    }

    public double getFractionX() {
        return fractionX;
    }

    public void setFractionX(double fractionX) {
        this.fractionX = fractionX;
    }

    public double getFractionY() {
        return fractionY;
    }

    public void setFractionY(double fractionY) {
        this.fractionY = fractionY;
    }
}
