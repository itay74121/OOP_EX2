package gameClient;

import Server.Game_Server_Ex2;
import api.game_service;
import com.google.gson.JsonObject;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ObjectOutputStream;
import java.util.concurrent.locks.Lock;

public class Ex2
{
    private MyFrame2 myFrame;
    private LoginFrame loginFrame;
    private Thread loginframethread;
    private Thread framethread;
    private SharedLevelBuffer sharedLevelBuffer;
    private Lock lockscreentime;

    public static void main(String [] args)
    {

        game_service g  = Game_Server_Ex2.getServer(1);
        g.addAgent(9);
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
    public  void init()  {
        this.sharedLevelBuffer = new SharedLevelBuffer();
        this.loginFrame = new LoginFrame(0.5,0.5,this.sharedLevelBuffer);
        this.myFrame = new MyFrame2(0.5,0.5);
        this.loginframethread = new Thread(loginFrame);
        this.framethread = new Thread(this.myFrame);

        this.loginframethread.start();
        try {
            this.loginframethread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.framethread.start();
    }
    public class SharedLevelBuffer
    {
        private String id;
        private int level;
        public SharedLevelBuffer()
        {
            this("",-1);
        }
        public SharedLevelBuffer(String id,int level)
        {
            setId(id);
            setLevel(level);
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }
    }
}
