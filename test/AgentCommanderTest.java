package test;

import Server.Game_Server_Ex2;
import api.game_service;
import gameClient.AgentCommander;
import gameClient.CL_Agent;
import gameClient.CL_Pokemon;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AgentCommanderTest {

    @Test
    void spawn() {
        game_service g = Game_Server_Ex2.getServer(0);
        AgentCommander a = new AgentCommander(g);
        a.spawn(5);
        assertEquals(6,a.getAgents().size()); // since there is already one agent in level 0
    }

    @Test
    void updateEdges()
    {
        game_service g = Game_Server_Ex2.getServer(0);
        AgentCommander a = new AgentCommander(g);
        a.updateEdges();
        for (CL_Pokemon pokemon:a.getPokemons())
        {
            assertNotNull(pokemon.get_edge());
        }
    }

}