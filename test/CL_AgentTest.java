package test;

import Server.Game_Server_Ex2;
import api.DWGraph_DS;
import api.GeoLocation;
import api.game_service;
import com.google.gson.Gson;
import gameClient.CL_Agent;

import static org.junit.jupiter.api.Assertions.*;

class CL_AgentTest {

    @org.junit.jupiter.api.Test
    void update()
    {
        game_service g = Game_Server_Ex2.getServer(0);
        Gson gson = new Gson(); // create a gson object
        DWGraph_DS graph_ds = new DWGraph_DS((DWGraph_DS.WrapDWGraph_DS) gson.fromJson(g.getGraph(), DWGraph_DS.WrapDWGraph_DS.class));
        CL_Agent agent1 = new CL_Agent(graph_ds,1,0,0,1,new GeoLocation(0,1,1));
        CL_Agent agent2 = new CL_Agent(graph_ds,5);
        agent2.update(agent1.toJSON());
        assertEquals(agent1,agent2);
    }


    @org.junit.jupiter.api.Test
    void setNextNode()
    {
        game_service g = Game_Server_Ex2.getServer(0);
        Gson gson = new Gson(); // create a gson object
        DWGraph_DS graph_ds = new DWGraph_DS((DWGraph_DS.WrapDWGraph_DS) gson.fromJson(g.getGraph(), DWGraph_DS.WrapDWGraph_DS.class));
        CL_Agent agent1 = new CL_Agent(graph_ds,1,0,0,1,new GeoLocation(0,1,1));
        assertEquals(agent1.getNextNode(),-1);
    }

    @org.junit.jupiter.api.Test
    void setCurrNode()
    {
        game_service g = Game_Server_Ex2.getServer(0);
        Gson gson = new Gson(); // create a gson object
        DWGraph_DS graph_ds = new DWGraph_DS((DWGraph_DS.WrapDWGraph_DS) gson.fromJson(g.getGraph(), DWGraph_DS.WrapDWGraph_DS.class));
        CL_Agent agent1 = new CL_Agent(graph_ds,1,0,0,1,new GeoLocation(0,1,1));
        agent1.setCurrNode(5);
        assertEquals(agent1.getSrcNode(),5);
        agent1.setCurrNode(-5);
        assertNotEquals(-5,agent1.getSrcNode());
    }

    @org.junit.jupiter.api.Test
    void getID()
    {
        game_service g = Game_Server_Ex2.getServer(0);
        Gson gson = new Gson(); // create a gson object
        DWGraph_DS graph_ds = new DWGraph_DS((DWGraph_DS.WrapDWGraph_DS) gson.fromJson(g.getGraph(), DWGraph_DS.WrapDWGraph_DS.class));
        CL_Agent agent1 = new CL_Agent(graph_ds,1,0,0,1,new GeoLocation(0,1,1));
        assertEquals(agent1.getID(),1);
    }

    @org.junit.jupiter.api.Test
    void getLocation() {
        game_service g = Game_Server_Ex2.getServer(0);
        Gson gson = new Gson(); // create a gson object
        DWGraph_DS graph_ds = new DWGraph_DS((DWGraph_DS.WrapDWGraph_DS) gson.fromJson(g.getGraph(), DWGraph_DS.WrapDWGraph_DS.class));
        CL_Agent agent1 = new CL_Agent(graph_ds,1,0,0,1,new GeoLocation(0,1,1));
        assertEquals(new GeoLocation(0,1,1),agent1.getLocation());
    }

    @org.junit.jupiter.api.Test
    void getValue() {
        game_service g = Game_Server_Ex2.getServer(0);
        Gson gson = new Gson(); // create a gson object
        DWGraph_DS graph_ds = new DWGraph_DS((DWGraph_DS.WrapDWGraph_DS) gson.fromJson(g.getGraph(), DWGraph_DS.WrapDWGraph_DS.class));
        CL_Agent agent1 = new CL_Agent(graph_ds,1,0,0,1,new GeoLocation(0,1,1));
        assertEquals(0,agent1.getValue());
    }

    @org.junit.jupiter.api.Test
    void getNextNode() {
        game_service g = Game_Server_Ex2.getServer(0);
        Gson gson = new Gson(); // create a gson object
        DWGraph_DS graph_ds = new DWGraph_DS((DWGraph_DS.WrapDWGraph_DS) gson.fromJson(g.getGraph(), DWGraph_DS.WrapDWGraph_DS.class));
        CL_Agent agent1 = new CL_Agent(graph_ds,1,0,0,1,new GeoLocation(0,1,1));
        assertEquals(-1,agent1.getNextNode());
    }

    @org.junit.jupiter.api.Test
    void getSpeed()
    {
        game_service g = Game_Server_Ex2.getServer(0);
        Gson gson = new Gson(); // create a gson object
        DWGraph_DS graph_ds = new DWGraph_DS((DWGraph_DS.WrapDWGraph_DS) gson.fromJson(g.getGraph(), DWGraph_DS.WrapDWGraph_DS.class));
        CL_Agent agent1 = new CL_Agent(graph_ds,1,0,0,1,new GeoLocation(0,1,1));
        assertEquals(agent1.getSpeed(),1);
    }

    @org.junit.jupiter.api.Test
    void setSpeed()
    {
        game_service g = Game_Server_Ex2.getServer(0);
        Gson gson = new Gson(); // create a gson object
        DWGraph_DS graph_ds = new DWGraph_DS((DWGraph_DS.WrapDWGraph_DS) gson.fromJson(g.getGraph(), DWGraph_DS.WrapDWGraph_DS.class));
        CL_Agent agent1 = new CL_Agent(graph_ds,1,0,0,1,new GeoLocation(0,1,1));
        agent1.setSpeed(3);
        assertEquals(3,agent1.getSpeed());
    }
}