package gameClient;

import api.DWGraph_DS;
import api.game_service;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

public class MyFrame extends JFrame implements Runnable
{
    private MyPanel panel;
    private DWGraph_DS graph_ds;
    private ArrayList<CL_Pokemon> pokemons;
    private ArrayList<CL_Agent> agents;
    private Dimension screen_size;
    private double fractionX;
    private double fractionY;
    private String graph_json;
    private String pokemons_json;
    private String agents_json;
    private JButton gobackbutton;
    private game_service game;


    public MyFrame(double fractionX,double fractionY)
    {
        super(); //create the frame
        setLayout(null); // set the layout as null
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // set the default close operation
        setResizable(true); // you can change the size
        //set size
        screen_size = Toolkit.getDefaultToolkit().getScreenSize(); // get dimensions of screen
        setFractionX(fractionX);
        setFractionY(fractionY);
        setBounds(0,0,(int)(fractionX*screen_size.width),(int)(fractionY*screen_size.height));
        panel = new MyPanel(fractionX,fractionY); // create a panel object
        add(panel); // add panel to component
        // set defaults for all of this
        graph_json = "";
        pokemons_json = "";
        agents_json = "";
        // create a go back button
        gobackbutton = new JButton("Go Back");
        gobackbutton.setBounds(this.getWidth()-100,0,100,30);// set the bounds of the button
        gobackbutton.addActionListener(new ActionListener() { // create action when button is pressed
            @Override
            public void actionPerformed(ActionEvent e) { // if button "go back" pressed then close the frame
                game.stopGame(); // stop the game
                setVisible(false); // set visible false
            }
        });
        add(gobackbutton); // add button to frame
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) { // resize the screen code and everything in it
                panel.setBounds(5,20,getWidth(),getHeight()); // set the panel new bounds
                panel.update_xr_yr(getWidth(),getHeight()); // update the xr and yr of the panel
                gobackbutton.setBounds(getWidth()-100,0,100,30); // set the bounds of the go back pnael
                panel.repaint(); // repaint the panel
            }
        });
    }

    /**
     * updating the frame information
     * @param jgraph
     * @param jpokemon
     * @param jagent
     * @param time
     */
    public void updateFrame (String jgraph, String jpokemon, List<CL_Agent> jagent, long time)
    {
        if (jgraph!=null)
        {
            if (!jgraph.equals(graph_json)) { //update the graph
                Gson gson = new Gson(); // create a gson object
                graph_ds = new DWGraph_DS((DWGraph_DS.WrapDWGraph_DS) gson.fromJson(jgraph, DWGraph_DS.WrapDWGraph_DS.class));
                graph_json=jgraph;
            }
        }
        if (jpokemon!=null)
        {
            if (!pokemons_json.equals(jpokemon)) // update the pokemons
            {
                try
                {
                    ArrayList<CL_Pokemon> pokarray = new ArrayList<CL_Pokemon>(); // create a pokemon array
                    JSONArray jsonpokemonArray = (JSONArray) (new JSONObject(jpokemon)).get("Pokemons"); // create a JSON array
                    for (int i =0;i<jsonpokemonArray.length();i++) //loop through
                    {
                        pokarray.add(CL_Pokemon.init_from_json(jsonpokemonArray.get(i).toString()));  // create  a pokemon and add it to the array list
                    }
                    pokemons =  pokarray; // set pokemons
                }
                catch (JSONException e)
                {
                }
                pokemons_json = jpokemon; // update string
            }
        }
        if(jagent!=null) // update the agents
        {
            agents = (ArrayList<CL_Agent>) jagent; // pass agents
        }
        panel.updatePanel(graph_ds,pokemons,agents,time); // call update panel and update the panel
    }

    /**
     * Override the run function
     */
    public void run()
    {
        setVisible(true);
        while (isVisible()) // while the screen is visible wait
        {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public double getFractionX() {
        return fractionX;
    }

    /**
     * set X size of the graph
     * @param fractionX
     */

    public void setFractionX(double fractionX) {
        this.fractionX = fractionX;
    }

    public double getFractionY() {
        return fractionY;
    }

    /**
     * set Y size of the graph
     * @param fractionY
     */

    public void setFractionY(double fractionY) {
        this.fractionY = fractionY;
    }

    /**
     * set game
     * @param g
     */
    public void setGame(game_service g)
    {
        this.game = g;
        setTitle(g.toString());
    }
}
