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
import java.util.Collection;
import java.util.List;
import api.*;

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
    public MyPanel(double fractionX,double fractionY)
    {
        super();
        setFractionX(fractionX);
        setFractionY(fractionY);
        screensize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0,0,(int)(screensize.width*fractionX),(int)(screensize.height*fractionY));
        xr = new Range(20,(int)(screensize.width*fractionX)-50);
        yr = new Range(20,(int)(screensize.height*fractionY)-50);
        setBackground(Color.white);
        setVisible(true);
        repaint();
    }

    @Override
    public void paint(Graphics g)
    {
        //g.clearRect(0,0,getBounds().width,getBounds().height);
        if(graph!=null)
        {
            Range2Range t = Arena.w2f(graph,new Range2D(xr,yr));
            g.setFont(new Font("arial",Font.BOLD,14));
            for (node_data n: graph.getV())
            {
                g.setColor(Color.BLUE);
                geo_location l1 = t.world2frame(n.getLocation());
                g.fillOval((int)l1.x()-5,(int)l1.y()-5,10,10);
                for(edge_data e: graph.getE(n.getKey()))
                {
                    g.setColor(Color.BLACK);
                    geo_location l2 = t.world2frame(graph.getNode(e.getDest()).getLocation());
                    g.drawLine((int)l1.x(),(int)l1.y(),(int)l2.x(),(int)l2.y());
                }
                g.setColor(Color.RED);
                g.drawString(Integer.toString(n.getKey()),(int)l1.x(),(int)l1.y()-10);
            }
        }
        else
            return;
        if (pokemons!=null)
        {
            Range2Range t = Arena.w2f(graph,new Range2D(xr,yr));
            for(CL_Pokemon p : pokemons)
            {
                if(p.getType()==-1)
                {
                    g.setColor(new Color(5,152,25));
                }
                else
                {
                    g.setColor(new Color(229,160,0));
                }
                geo_location l1 = t.world2frame(p.getLocation());
                g.fillOval((int)l1.x()-10,(int)l1.y()-10,20,20);
                g.drawString(p.getValue()+"",(int)l1.x(),(int)l1.y()-15);
            }
        }
        if (agents!=null)
        {
            Range2Range t = Arena.w2f(graph,new Range2D(xr,yr));
            g.setColor(Color.RED);
            for(CL_Agent agent: agents)
            {
                geo_location l1 = t.world2frame(agent.getLocation());
                g.fillOval((int)l1.x()-7,(int)l1.y()-7,15,15);
            }
        }
    }
    public void updatePanel(DWGraph_DS g, ArrayList<CL_Pokemon>pokemons, ArrayList<CL_Agent> agents)
    {
        this.graph = g;
        this.pokemons = pokemons;
        this.agents = agents;
        this.repaint();
    }

    public double getFractionX() {
        return fractionX;
    }

    public void setFractionX(double fractionX) {
        this.fractionX = fractionX;
    }

    public double getFractionY() {
        return fractionY;
    }

    public void setFractionY(double fractionY) {
        this.fractionY = fractionY;
    }
}
