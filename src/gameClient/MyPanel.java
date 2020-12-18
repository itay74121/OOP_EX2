package gameClient;

import api.DWGraph_DS;
import api.edge_data;
import api.geo_location;
import api.node_data;
import gameClient.util.Range;
import gameClient.util.Range2D;
import gameClient.util.Range2Range;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Give this panel a graph and it would draw it */
public class MyPanel extends JPanel
{
    private Dimension screensize;
    private double fractionX;
    private double fractionY;
    private DWGraph_DS graph;
    private ArrayList<CL_Pokemon> pokemons;
    private ArrayList<CL_Agent> agents;
    private Range xr;
    private Range yr;
    private long time;

    /**
     * create the panel
     * @param fractionX
     * @param fractionY
     */
    public MyPanel(double fractionX,double fractionY)
    {
        super(); //create the panel
        //set size
        setFractionX(fractionX);
        setFractionY(fractionY);
        screensize = Toolkit.getDefaultToolkit().getScreenSize(); //  get dimensions of screen
        setBounds(0,0,(int)(screensize.width*fractionX),(int)(screensize.height*fractionY)); // set bounds of panel
        xr = new Range(120,this.getWidth()-130); // set the x range
        yr = new Range(80,this.getHeight()-100); // set the y range
        setBackground(Color.white); // set background as white
        setVisible(true); // set visible true
        repaint(); //  repaint
    }

    @Override
    /**
     * draw the graph with the pokemons and agents
     */
    public void paint(Graphics g)
    {
        Graphics2D g1 = (Graphics2D)g;
        g1.setStroke(new BasicStroke(5));
        g.setColor(Color.BLUE);
        g.setFont(new Font("Arial",Font.BOLD,14));
        int start = 30;
        g.drawString("Time: "+ TimeUnit.MILLISECONDS.toSeconds(this.time)+"."+this.time%1000,0,start);
        start+=g.getFontMetrics().getHeight();
        int s = 0;
        for (CL_Agent a:agents)
            s+=a.getValue();
        g.drawString("Score: "+ s,0,start);
        for (CL_Agent a:agents)
        {
            for(String j:a.get_info())
            {
                start+=g.getFontMetrics().getHeight();
                g.drawString(j,0,start);
            }
        }
        g1.drawLine(0,0,100,0);
        g1.setStroke(new BasicStroke(4));
        g1.drawLine(0,start+10,100,start+10);
        g1.drawLine(100,0,100,start+10);
        g1.setStroke(new BasicStroke(1));
        if(graph!=null)
        {
            Range2Range t = Arena.w2f(graph,new Range2D(xr,yr)); // get the world to frame
            g.setFont(new Font("arial",Font.BOLD,14)); // set font
            for (node_data n: graph.getV()) // draw the nodes
            {
                g.setColor(Color.BLUE);
                geo_location l1 = t.world2frame(n.getLocation());
                g.fillOval((int)l1.x()-5,(int)l1.y()-5,10,10); // paint the node
                for(edge_data e: graph.getE(n.getKey())) // draw the lines of the edges
                {
                    g.setColor(Color.BLACK);
                    geo_location l2 = t.world2frame(graph.getNode(e.getDest()).getLocation()); // get location l2
                    g.drawLine((int)l1.x(),(int)l1.y(),(int)l2.x(),(int)l2.y()); // draw the line
                }
                g.setColor(Color.RED); // set color as red
                g.drawString(Integer.toString(n.getKey()),(int)l1.x(),(int)l1.y()-10); // draw number of node above it
            }
        }
        else
            return; // if there is no graph terminate
        if (pokemons!=null) // draw the Pokemon's on the panel
        {
            Range2Range t = Arena.w2f(graph,new Range2D(xr,yr)); // get the range
            for(CL_Pokemon p : pokemons) // loop through Pokemon's
            {
                if(p.getType()==-1) // draw the green pokemon
                {
                    g.setColor(new Color(5,152,25));
                }
                else // draw the yellow pokemon
                {
                    g.setColor(new Color(229,160,0));
                }
                geo_location l1 = t.world2frame(p.getLocation()); // get the location on the frame
                g.fillOval((int)l1.x()-10,(int)l1.y()-10,20,20); // draw the Pokemon
                g.drawString(p.getValue()+"",(int)l1.x(),(int)l1.y()-15); // draw the value of the pokemon above it
            }
        }
        if (agents!=null) // draw agents
        {
            Range2Range t = Arena.w2f(graph,new Range2D(xr,yr)); // get the location
            g.setColor(Color.RED); // set color red
            for(CL_Agent agent: agents) // loop through agents
            {
                geo_location l1 = t.world2frame(agent.getLocation()); // get world to frame location
                g.fillOval((int)l1.x()-7,(int)l1.y()-7,15,15); // paint the agent
            }
        }
    }

    /**
     * method to update the panel with new information
     * @param g
     * @param pokemons
     * @param agents
     * @param time
     */
    public void updatePanel(DWGraph_DS g, ArrayList<CL_Pokemon>pokemons, ArrayList<CL_Agent> agents, long time)
    {
        this.graph = g;
        this.pokemons = pokemons;
        this.agents = agents;
        this.time = time;
        this.repaint(); // repaint the panel
    }

    public double getFractionX() {
        return fractionX;
    }

    /**
     * set the X size of the frame
     * @param fractionX
     */
    public void setFractionX(double fractionX) {
        this.fractionX = fractionX;
    }

    public double getFractionY() {
        return fractionY;
    }

    /**
     * set the Y size of the frame
     * @param fractionY
     */
    public void setFractionY(double fractionY) {
        this.fractionY = fractionY;
    }

    /**
     * if the size of the frame changed the function changing the graph size
     * @param w
     * @param h
     */
    public void update_xr_yr(int w,int h)
    {
        xr = new Range(120,this.getWidth()-120);
        yr = new Range(80,this.getHeight()-100);
    }
}
