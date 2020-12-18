package gameClient;

import Server.Game_Server_Ex2;
import api.game_service;

import java.util.concurrent.locks.Lock;

public class Ex2
{
    private MyFrame myFrame;
    private LoginFrame loginFrame;
    private Thread loginframethread;
    private Thread framethread;
    private SharedLevelBuffer sharedLevelBuffer;
    private AgentCommander a;
    private Thread commander;
    private game_service game;

    public static void main(String [] args)
    {
        SharedLevelBuffer a=null;
        boolean flag = false;
        if (args.length==2)
        {
            flag = true;
        }
        while(true) {
            Ex2 o = new Ex2();
            if(flag)
            {
                o.sharedLevelBuffer = o.new SharedLevelBuffer(args[0],Integer.parseInt(args[1])); // create a shared buffer
                o.myFrame = new MyFrame(0.5,0.5); // create a game frame
                o.framethread = new Thread(o.myFrame); // create a thread for game frame
                flag = false;
            }
            else
            {
                o.init();
            }
            game_service g = Game_Server_Ex2.getServer(o.sharedLevelBuffer.level);// get level that the user wanted
            //g.login(Long.parseLong(o.sharedLevelBuffer.id));
            o.a = new AgentCommander(g); // create an agent commander
            o.myFrame.setGame(g); // set g as game of nyframe
            o.game = g; // set game
            g.startGame(); // start the game
            o.commander = new Thread(o.a); // create new thread to agent commander
            o.commander.start(); // start the commander
            o.framethread.start(); // start the frame
            Thread t = new Thread(new Runnable() { // make the move
                @Override
                public void run() {
                    while (g.isRunning()) {
                        g.move(); // call move
                        try {
                            // doing only 10 moves a second gets less points in the end though
                            Thread.sleep(1000/10); // 10 moves for a second
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            t.start();
            while (g.isRunning()) {
                // update the frame with the new information and repaint it
                o.myFrame.updateFrame(g.getGraph(), g.getPokemons(), o.a.getAgents(), o.game.timeToEnd());
                o.myFrame.repaint();
                try {
                    Thread.sleep(1000/40); // updating frame 60 times a second
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try { // joining threads since program ended
                t.join();
                o.framethread.join(); // join frame thread
                o.commander.join(); // join commander thread
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This function init the the different screens
     */
    public  void init()
    {
        this.sharedLevelBuffer = new SharedLevelBuffer(); // create a shared buffer
        this.loginFrame = new LoginFrame(0.5,0.5,this.sharedLevelBuffer); // create a login frame
        this.myFrame = new MyFrame(0.5,0.5); // create a game frame
        this.loginframethread = new Thread(loginFrame); // create a thread for login frame
        this.framethread = new Thread(this.myFrame); // create a thread for game frame
        this.loginframethread.start(); // start the login frame thread
        try
        {
            this.loginframethread.join(); // wait for the login frame thread to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * This class is a shared buffer of information between the login screen and the agent commander
     */
    public class SharedLevelBuffer
    {
        private String id; // id of the user
        private int level; // level that they want to try

        /**
         * Default constructor
         */
        public SharedLevelBuffer()
        {
            this("",-1); // set default values
        }

        /**
         * Constuctor that takes an id and a level
         * @param id
         * @param level
         */
        public SharedLevelBuffer(String id,int level)
        {
            setId(id);
            setLevel(level);
        }
        /** get id **/
        public String getId() {
            return id;
        }
        /** set id **/
        public void setId(String id) {
            this.id = id;
        }

        public int getLevel() {
            return level;
        }
        /** set the level you want to play **/
        public void setLevel(int level) {
            this.level = level;
        }
    }
}
