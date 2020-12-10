package gameClient;

import Server.Game_Server_Ex2;
import api.DWGraph_Algo;
import api.DWGraph_DS;
import api.game_service;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class AgentCommander implements  Runnable, Comparator<CL_Pokemon> {
    private ArrayList<CL_Agent> agents;
    private ArrayList<CL_Pokemon> pokemons;
    private HashMap<CL_Pokemon,CL_Agent> map;
    private DWGraph_DS graph;
    private game_service game;
    private ArrayList<Thread> agentsthreads;

    public AgentCommander(game_service g)
    {
        game = g;
        //re create graph
        Gson gson = new Gson();
        graph = new DWGraph_DS((DWGraph_DS.WrapDWGraph_DS) gson.fromJson(game.getGraph(), DWGraph_DS.WrapDWGraph_DS.class));
        // init other fields
        agents = new ArrayList<CL_Agent>();
        map = new HashMap<CL_Pokemon,CL_Agent>(); // hashmap from pokemon to agent
        JSONObject info;
        int a = 0;
        try {
            info = new JSONObject(g.toString());
            a = ((JSONObject)info.get("GameServer")).getInt("agents");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (a<1)
            return;
        spawn(a);
        // re create the graph
        // re create pokemon
        pokemons = Arena.json2Pokemons(g.getPokemons());
        this.updateEdges();
        Collections.sort(pokemons,this);
        for (int i =0;i<a;i++)
        {
            game.addAgent(pokemons.get(pokemons.size()-1-i).get_edge().getSrc());
        }
        for (int i = 0;i<pokemons.size();i++)
        {
            // putting null at first at initiation
            map.put(pokemons.get(i),null);
        }
        for (CL_Agent agent: agents)
        {
            Thread t = new Thread(agent);
            agentsthreads.add(t);
        }
    }

    @Override
    public void run()
    {
        while (!game.isRunning())
        {

        }
        while(game.isRunning())
        {

        }
    }

    /***
     * @param agents
     */
    public void spawn(int agents)
    {
        for (int i = 0; i <agents ; i++)
        {
            CL_Agent agent = new CL_Agent(graph,0);
            agent.setGameService(game);
            agent.setCommander(this);
            this.agents.add(agent);
        }
    }
    public void updateEdges()
    {
        if (pokemons!=null&&graph!=null )
        {
            for (CL_Pokemon p: pokemons)
            {
                Arena.updateEdge(p,graph);
            }
        }
    }

    public static void main(String args[]) throws InterruptedException {

        game_service g = Game_Server_Ex2.getServer(5);
        g.startGame();
        AgentCommander ac = new AgentCommander(g);
        Thread t = new Thread(ac);
        t.start();
        t.join();
    }

    @Override
    public int compare(CL_Pokemon o1, CL_Pokemon o2) {
        return Double.compare(o1.getValue(),o2.getValue());
    }

    /**
     *
     * @param pokemon
     * @return true if commander allowed agent to catch pokemon
     * false otherwise.
     */
    public boolean request(CL_Pokemon pokemon, CL_Agent other)
    {
        if(map.get(pokemon)==null)
        {
            return true;
        }
        else
        {
            CL_Agent agent1 = map.get(pokemon); // is already running after the pokemon
            int dest = agent1.getCurrEdge().getDest();
            int src = other.getCurrEdge().getSrc();
            DWGraph_Algo ga = new DWGraph_Algo(graph);
            double weight1 = ga.shortestPathDist(dest,pokemon.get_edge().getSrc()) / agent1.getSpeed(); // agent1
            double weight2 = ga.shortestPathDist(src,pokemon.get_edge().getSrc())/ other.getSpeed(); // agent2
            if (weight1<weight2)
            {
                return false;
            }
            if (weight1==weight2)
            {
                return false;
            }
            else
            {
                agent1.signalStop(pokemon); // makes the agent stop following after this pokemon
                map.put(pokemon,other);
                return true;
            }
        }
    }
}
