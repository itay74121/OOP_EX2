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
        g.startGame();
        Ex2 o = new Ex2();
        int c = 0;
        o.init();
        while(g.isRunning())
        {

//                c++;
//                if(c%10==0)
//                {
//                    g.move();
//                    c=0;
//                }
                //Thread.sleep(10);
                c++;
                if(c%2==0)
                    g.chooseNextEdge(0,8);
                else
                    g.chooseNextEdge(0,9);

                o.myFrame.updateFrame(g.getGraph(),g.getPokemons(),g.move());
                o.myFrame.repaint();

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
