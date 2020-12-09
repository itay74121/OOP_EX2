package gameClient;

import Server.Game_Server_Ex2;
import api.DWGraph_DS;
import api.game_service;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AgentCommander implements  Runnable{
    ArrayList<CL_Agent> agents;
    ArrayList<CL_Pokemon> pokemons;
    DWGraph_DS graph;

    game_service game;
    public AgentCommander(game_service g)
    {
        game = g;
        agents = new ArrayList<CL_Agent>();
        pokemons = new ArrayList<CL_Pokemon>();
    }

    @Override
    public void run()
    {
        Gson gson = new Gson();
        graph = new DWGraph_DS((DWGraph_DS.WrapDWGraph_DS) gson.fromJson(game.getGraph(), DWGraph_DS.WrapDWGraph_DS.class));
        String info = game.toString();
        int agents = 0;
        try {
             JSONObject obj = new JSONObject(info);
             agents = ((JSONObject)obj.get("GameServer")).getInt("agents");
             obj = new JSONObject(game.getPokemons());
             JSONArray arr = (JSONArray)obj.get("Pokemons");
             for (int i = 0;i<arr.length();i++)
             {
                 pokemons.add(CL_Pokemon.init_from_json(arr.get(i).toString()));
                 Arena.updateEdge(pokemons.get(i),graph);
             }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (agents<1)
            return;

        for (int i = 0; i < agents; i++) {
            this.agents.add(new CL_Agent(graph,0));
        }
//        for (int i = 0; i <agents ; i++) {
//            game.addAgent()
//        }




    }
    public static void main(String args[]) throws InterruptedException {
        game_service g = Game_Server_Ex2.getServer(1);
        g.startGame();
        AgentCommander ac = new AgentCommander(g);
        Thread t = new Thread(ac);
        t.start();
        t.join();
    }

}
