package gameClient;

import api.directed_weighted_graph;
import api.edge_data;
import api.geo_location;
import api.node_data;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;
import gameClient.util.Range2Range;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class represents a multi Agents Arena which move on a graph - grabs Pokemons and avoid the Zombies.
 * @author boaz.benmoshe
 *
 */
public class Arena {

	public static final double EPS1 = 0.001, EPS2=EPS1*EPS1, EPS=EPS2;
	private directed_weighted_graph _gg; // graph object field
	private List<CL_Agent> _agents; // agenst list
	private List<CL_Pokemon> _pokemons; // pokemon list
	private static Point3D MIN = new Point3D(0, 100,0);
	private static Point3D MAX = new Point3D(0, 100,0);

	public Arena() {
	}
	private Arena(directed_weighted_graph g, List<CL_Agent> r, List<CL_Pokemon> p) {
		_gg = g;
		this.setAgents(r);
		this.setPokemons(p);
	}

	/**
	 * set pokemons list
	 * @param f pokemon list
	 */
	public void setPokemons(List<CL_Pokemon> f) {
		this._pokemons = f;
	}

	/**
	 * set agents list
	 * @param f agents list
	 */
	public void setAgents(List<CL_Agent> f) {
		this._agents = f;
	}

	/**
	 * set graph
	 * @param g graph object
	 */
	public void setGraph(directed_weighted_graph g) {this._gg =g;}//init();}
	private void init( ) {
		MIN=null; MAX=null;
		double x0=0,x1=0,y0=0,y1=0;
		Iterator<node_data> iter = _gg.getV().iterator();
		while(iter.hasNext())
		{
			geo_location c = iter.next().getLocation();
			if(MIN==null) {x0 = c.x(); y0=c.y(); x1=x0;y1=y0;MIN = new Point3D(x0,y0);}
			if(c.x() < x0) {x0=c.x();}
			if(c.y() < y0) {y0=c.y();}
			if(c.x() > x1) {x1=c.x();}
			if(c.y() > y1) {y1=c.y();}
		}
		double dx = x1-x0, dy = y1-y0;
		MIN = new Point3D(x0-dx/10,y0-dy/10);
		MAX = new Point3D(x1+dx/10,y1+dy/10);

	}
	public List<CL_Agent> getAgents() {return _agents;}
	public List<CL_Pokemon> getPokemons() {return _pokemons;}

	/**
	 * return the graph
	 * @return the graph
	 */
	public directed_weighted_graph getGraph() {
		return _gg;
	}

	/**
	 * return array list of pokemons converted from json
	 * @param fs json of pokemons from server
	 * @return array list of pokemons converted from json
	 */
	public static ArrayList<CL_Pokemon> json2Pokemons(String fs) {
		ArrayList<CL_Pokemon> ans = new  ArrayList<CL_Pokemon>(); // create array
		try {
			JSONObject ttt = new JSONObject(fs);
			JSONArray ags = ttt.getJSONArray("Pokemons"); // get json pokemon array
			for(int i=0;i<ags.length();i++) { // loop through array
				JSONObject pp = ags.getJSONObject(i);
				JSONObject pk = pp.getJSONObject("Pokemon"); //get info pokemon in index i
				int t = pk.getInt("type"); // get the type of it
				double v = pk.getDouble("value"); // get the value of the pokemon
				//double s = 0;//pk.getDouble("speed");
				String p = pk.getString("pos"); // get the position of the
				CL_Pokemon f = new CL_Pokemon(new Point3D(p), t, v, 0, null); // create a pokemon object
				ans.add(f); // add it to array
			}
		}
		catch (JSONException e) {e.printStackTrace();}
		return ans; // return array
	}

	/**
	 * updating pokemons edge he is standing on
	 * @param fr pokemon object
	 * @param g graph object
	 */
	public static void updateEdge(CL_Pokemon fr, directed_weighted_graph g) {
		//	oop_edge_data ans = null;
		Iterator<node_data> itr = g.getV().iterator(); // create iterator
		while(itr.hasNext()) { // go over iterator
			node_data v = itr.next(); // take node from iterator
			Iterator<edge_data> iter = g.getE(v.getKey()).iterator(); // create an edge iterator
			while(iter.hasNext()) { // go over it
				edge_data e = iter.next(); // take edge from iterator
				boolean f = isOnEdge(fr.getLocation(), e,fr.getType(), g); // determine if pokemon is on this edge if so
				if(f) {fr.set_edge(e);} // set it as it's edge
			}
		}
	}

	/**
	 * return if the pokemon standing on specific edge
	 * @param p location
	 * @param src location of src
	 * @param dest location of dest
	 * @return if the pokemon standing on specific edge
	 */
	private static boolean isOnEdge(geo_location p, geo_location src, geo_location dest ) {

		boolean ans = false;
		double dist = src.distance(dest);//get the physical distance between src and dest
		double d1 = src.distance(p) + p.distance(dest);
		if(dist>d1-EPS2) {ans = true;}
		return ans;
	}

	/**
	 * return if the pokemon standing on specific edge
	 * @param p location of pokemon
	 * @param s key of src
	 * @param d key of dest
	 * @param g graph
	 * @return if the pokemon standing on specific edge
	 */
	private static boolean isOnEdge(geo_location p, int s, int d, directed_weighted_graph g) {
		geo_location src = g.getNode(s).getLocation();
		geo_location dest = g.getNode(d).getLocation();
		return isOnEdge(p,src,dest); // use the above isOnEdge but takes different parameters
	}

	/**
	 * return if the pokemon standing on specific edge
	 * @param p location of pokemon
	 * @param e edge
	 * @param type type of pokemon
	 * @param g graph
	 * @return if the pokemon standing on specific edge
	 */
	private static boolean isOnEdge(geo_location p, edge_data e, int type, directed_weighted_graph g) {
		int src = g.getNode(e.getSrc()).getKey();
		int dest = g.getNode(e.getDest()).getKey();
		if(type<0 && dest>src) {return false;}
		if(type>0 && src>dest) {return false;}
		return isOnEdge(p,src, dest, g);// use the above isOnEdge but takes different parameters
	}

	/**
	 *	Gets the physical range of the plane on which the graph exists
	 * @param g graph
	 * @return
	 */
	private static Range2D GraphRange(directed_weighted_graph g)
	{
		Iterator<node_data> itr = g.getV().iterator(); // iterate over nodes
		double x0=0,x1=0,y0=0,y1=0; // create variables for two points p1 and p2
		boolean first = true; // assume true first
		while(itr.hasNext()) { // go over nodes
			geo_location p = itr.next().getLocation(); // get next nodes geo location
			if(first) { // init the first
				x0=p.x(); x1=x0;
				y0=p.y(); y1=y0;
				first = false; // set as false since not first anymore
			}
			else {
				if(p.x()<x0) {x0=p.x();} // if smaller x0 found take it
				if(p.x()>x1) {x1=p.x();} // do so with other in similar way
				if(p.y()<y0) {y0=p.y();}
				if(p.y()>y1) {y1=p.y();}
			}
		}
		Range xr = new Range(x0,x1); // create range xr
		Range yr = new Range(y0,y1); // create range yr
		return new Range2D(xr,yr); // return Range2d from xr and yr
	}

	/**
	 * This function converts world to frame on 2d plane using the GraphRange
	 * @param g graph
	 * @param frame Range of frame
	 * @return
	 */
	public static Range2Range w2f(directed_weighted_graph g, Range2D frame)
	{
		Range2D world = GraphRange(g);
		Range2Range ans = new Range2Range(world, frame);
		return ans;
	}

}
