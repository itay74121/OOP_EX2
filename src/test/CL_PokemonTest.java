package test;

import Server.Game_Server_Ex2;
import api.GeoLocation;
import api.game_service;
import gameClient.CL_Pokemon;
import gameClient.util.Point3D;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CL_PokemonTest {

    @Test
    void init_from_json()
    {
        String json  = "{\"Pokemon\":{\"type\":-1, \"pos\":\"1.0,1.0,1.0\" ,\"value\":5.0}}";
        CL_Pokemon pokemon1 = CL_Pokemon.init_from_json(json);
        CL_Pokemon pokemon2 = new CL_Pokemon(new Point3D(1.0,1.0,1.0),-1,5,0,null);
        assertEquals(pokemon1,pokemon2);
    }

    @Test
    void testEquals() {
        CL_Pokemon pokemon1 = new CL_Pokemon(new Point3D(1.0,1.0,1.0),-1,5,0,null);
        CL_Pokemon pokemon2 = new CL_Pokemon(new Point3D(1.0,1.0,1.0),-1,5,0,null);
        assertEquals(pokemon1,pokemon2);
    }
}