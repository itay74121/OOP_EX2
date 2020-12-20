package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

/**
 * This class is agent commander. The agents threads take all the information that they need form here.
 * The commander is running in a parallel thread and updates the other agents about their locations on the map.
 */
public class AgentCommander implements  Runnable {
    private ArrayList<CL_Agent> agents; // agents array
    private ArrayList<CL_Pokemon> pokemons; // pokemons array
    private DWGraph_DS graph; // graph object
    private game_service game;// game object
    private ArrayList<Thread> agentsthreads; // array of all agents threads

    /**
     * Creates a commander
     * @param g game service object
     */
    public AgentCommander(game_service g)
    {
        game = g; // init game
        //re create graph
        Gson gson = new Gson(); // create gson object to
        graph = new DWGraph_DS((DWGraph_DS.WrapDWGraph_DS) gson.fromJson(game.getGraph(), DWGraph_DS.WrapDWGraph_DS.class)); // get graph from json server gave
        // init other fields
        agents = new ArrayList<CL_Agent>(); // create agent array
        agentsthreads = new ArrayList<Thread>(); // create agents threads array
        JSONObject info;
        int a = 0; // a is agent num
        try {
            info = new JSONObject(g.toString()); // get info g in jaon form
            a = ((JSONObject)info.get("GameServer")).getInt("agents"); // get the number agents from the info
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (a<1) // make sure it worked
            return; // if not terminate
        spawn(a); // call spawn function
        // re create the graph
        // re create pokemon
        pokemons = Arena.json2Pokemons(g.getPokemons()); // get array of Pokemon's from json
        this.updateEdges(); // update the edges in the
        Collections.sort(pokemons, new Comparator<CL_Pokemon>() { // sort the Pokemon's
            @Override
            public int compare(CL_Pokemon o1, CL_Pokemon o2) { //sort according to value
                if (o1.getValue()>o2.getValue())
                    return -1;
                if(o1.getValue()==o2.getValue())
                    return 0;
                else
                    return 1;
            }
        });
        for (int i =0;i<a;i++) // put the agents on the map of the server
        {
            game.addAgent(pokemons.get(i%a).get_edge().getSrc());
        }
        for (CL_Agent agent: agents) // create an agent thread for every thread
        {
            Thread t = new Thread(agent);
            agentsthreads.add(t);
        }
    }

    /**
     * Override the run function for the agent commander thread
     */
    @Override
    public void run() // override of the run method
    {
        for (Thread t: agentsthreads) // starts all agents
        {
            t.start(); // start all agents threads
        }
        while (!game.isRunning()) // wait for game to start
        {

        }
        while(game.isRunning()) // while the game is running
        {
            String h=""; // create string h
            h = game.getAgents(); // let h take the agents info
            try {
                JSONObject object = new JSONObject(h); // create JSON object
                JSONArray arr = object.getJSONArray("Agents"); // get the Agents in JSON array
                for(int i = 0;i<arr.length();i++) // loop through
                {
                    agents.get(i).update(arr.get(i).toString()); // update the agents using the update function
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            try {
//                Thread.sleep(1000/30);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
        for(Thread t: agentsthreads) // join all of the agents threads
        {
            try {
                t.join(); // join agent thread
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * This method creates the agents and gives them the game object and the commander
     * @param agents number of agents
     */
    public void spawn(int agents)
    {
        for (int i = 0; i <agents ; i++)
        {
            CL_Agent agent = new CL_Agent(graph,0); // create an agent object
            agent.setGameService(game); //set the game service
            agent.setCommander(this); // set the commander for that agent
            this.agents.add(agent); // add agent to list
        }
    }

    /**
     * update all the edges to the pokemons
     */
    public void updateEdges()
    {
        if (pokemons!=null&&graph!=null )
        {
            for (CL_Pokemon p: pokemons) // loop on Pokemon's
            {
                Arena.updateEdge(p,graph); // update the arena
            }
        }
    }

    /**
     *
     * @return the Pokemon's arraylist
     */
    public ArrayList<CL_Pokemon> getPokemons()
    {
        return pokemons;
    }

    /**
     * gives the sum of values of all agents
     * @return sum of values of agents
     */
    public int sum()
    {
        int s = 0;
        for(CL_Agent a:agents)
            s+=a.getValue();
        return s;
    }

    /**
     * get the agents list
     * @return agents list
     */
    public ArrayList<CL_Agent> getAgents()
    {
        return this.agents;
    }
}
