package gameClient;

import Server.Game_Server_Ex2;
import api.game_service;
import com.google.gson.JsonObject;
import org.json.JSONException;
import org.json.JSONObject;

public class Ex2
{
    private MyFrame2 myFrame;
    private Thread framethread;
    public static void main(String [] args)
    {
        game_service g  = Game_Server_Ex2.getServer(14);
        g.addAgent(0);
        g.addAgent(1);
        g.addAgent(2);
        Ex2 o = new Ex2();

        o.init();
        while(o.framethread.isAlive())
        {
            try
            {
                Thread.sleep(500);
                o.myFrame.updateFrame(g.getGraph(),g.getPokemons(),g.getAgents());
                o.myFrame.repaint();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

    }
    public  void init()
    {
        this.myFrame = new MyFrame2(0.5,0.5);
        this.framethread = new Thread(this.myFrame);
        this.framethread.start();
    }
}
