package gameClient;

import api.*;
import gameClient.util.Point3D;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CL_Agent  implements Runnable
{
	public static final double EPS = 0.0001;
	private static int dest = 0;
	private static int _seed = 3331;
	private int id;
	private geo_location pos;
	private double speed;
	private edge_data currEdge;
	private node_data currNode;
	private directed_weighted_graph graph;
	private CL_Pokemon currFruit;
	private long sgDt;
	private double value;
	private game_service gameservice;
	private ArrayList<CL_Pokemon> Priority_pokemons;
	private AgentCommander commander;
	private boolean confirmmoving = false;

	/**
	 * constructor for agent that gets graph and starting node
	 * @param g
	 * @param start_node
	 */
	public CL_Agent(directed_weighted_graph g, int start_node) {
		graph = g;
		setMoney(0);
		this.currNode = graph.getNode(start_node);
		pos = currNode.getLocation();
		id = -1;
		setSpeed(0);
	}

	/**
	 * constructor that getting graph, id for agent, value, speed, location, and start node
	 * @param g
	 * @param id
	 * @param src_node
	 * @param value
	 * @param speed
	 * @param pos
	 */
	public CL_Agent(directed_weighted_graph g, int id,int src_node, double value, double speed, geo_location pos)
	{
		this.pos = pos;
		this.speed = speed;
		this.value = value;
		this.currNode = g.getNode(src_node);
		this.graph = g;
		this.id = id;
	}

	/**
	 * updating agent information using json format
	 * @param json
	 */
	public void update(String json) {
		JSONObject line;
		try {
			// "GameServer":{"graph":"A0","pokemons":3,"agents":1}}
			line = new JSONObject(json); // create JSON object
			JSONObject ttt = line.getJSONObject("Agent"); // take what inside the agent field in json
			int id = ttt.getInt("id"); // take id
			if(id==this.getID() || this.getID() == -1) { // if id isn't initialized
				if(this.getID() == -1) {
					this.id = id;}
				double speed = ttt.getDouble("speed");// take speed
				String p = ttt.getString("pos"); // take position
				Point3D pp = new Point3D(p);
				int src = ttt.getInt("src"); // take src
				int dest = ttt.getInt("dest"); // take dest
				this.dest = dest;
				double value = ttt.getDouble("value"); // take value
				this.pos = new GeoLocation(pp.x(),pp.y(),pp.z());
				this.setCurrNode(src);
				this.setSpeed(speed);
				this.setMoney(value);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> get_info()
	{
		ArrayList<String> result = new ArrayList<String>();
		result.add("agent: "+id);
		result.add("speed: "+speed);
		result.add("value: "+value);
		result.add("src: "+getSrcNode());
		result.add("dest: "+ dest);
		return result;
	}

	/**
	 * getting the nose you are standing on
	 * @return
	 */
	public int getSrcNode() {
		return this.currNode.getKey();
	}

	/**
	 * Conbert agent into json
	 * @return
	 */
	public String toJSON() {
		int d = this.getNextNode();
		String ans = "{\"Agent\":{"
				+ "\"id\":"+this.id +","
				+ "\"value\":"+this.value +","
				+ "\"src\":"+this.currNode.getKey()+","
				+ "\"dest\":"+d+","
				+ "\"speed\":"+this.getSpeed()+","
				+ "\"pos\":\""+ ((GeoLocation)pos).toString()+"\""
				+ "}"
				+ "}";
		return ans;
	}

	/**
	 * setting agents value
	 * @param v
	 */
	private void setMoney(double v)
	{
		value = v;
	}

	/**
	 * setting agents current node
	 * @param src
	 */
	public void setCurrNode(int src) {
		if (graph.getNode(src)==null)
			return;
		this.currNode = graph.getNode(src);
	}

	/**
	 * OOverriding the toString method
	 */
	public String toString() {
		return toJSON();
	}

	/**
	 * returning agents id
	 * @return
	 */
	public int getID() {
		return this.id;
	}

	/**
	 * returning agents geolocation
	 * @return
	 */
	public geo_location getLocation() {
		return pos;
	}

	/**
	 * returning the value
	 * @return
	 */
	public double getValue() {
		return this.value;
	}

	/**
	 * returning the dest node of the edge the agent standing on
	 * @return
	 */
	public int getNextNode() {
		int ans = -2;
		if(this.currEdge ==null) {
			ans = -1;}
		else {
			ans = this.currEdge.getDest();
		}
		return ans;
	}

	/**
	 * returning agents speed
	 * @return
	 */
	public double getSpeed() {
		return this.speed;
	}

	/**
	 * setting agents speed
	 * @param v
	 */
	public void setSpeed(double v) {
		this.speed = v;
	}

	/**
	 * returning the pokemon that this agent following right now
	 * @return
	 */
	public CL_Pokemon getCurrFruit() {
		return currFruit;
	}

	/**
	 * setting the pokemon that the agent will start to follow
	 * @param curr_fruit
	 */
	public void setCurrFruit(CL_Pokemon curr_fruit) {
		this.currFruit = curr_fruit;
	}

	/**
	 * returning the edge the agent standing on
	 * @return
	 */
	public edge_data getCurrEdge() {
		return this.currEdge;
	}
	public void setSgDt(long sgDt) {
		this.sgDt = sgDt;
	}

	/**
	 * the main function. all that need to happen while the agent is working in the background
	 */
	@Override
	public void run()
	{
		ArrayList<node_data> path=null;
		DWGraph_Algo ga = new DWGraph_Algo(graph);
		ga.init(ga.copy());
		boolean no=false;
		// algorithm of an agent
		while (!gameservice.isRunning()) // if game hasn't started the agent is waiting for it to start
		{

		}
		while(gameservice.isRunning()) // loop while game is running
		{
			if(this.currFruit==null||path==null) // if the agent dont follow any pokemon or he hasnt a path
			{
				while (dest!=-1&&gameservice.isRunning())// while the agent travel om the edge wait
				{
					try
					{
						Thread.sleep(1); // sleep for 1 millisecond
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
				try {
					updatePriority(gameservice.getPokemons()); // updating the array with the best Pokemon's to get
				} catch(Exception e){

				}
				//System.out.println(id+" "+Priority_Pokemon's);
				if(Priority_pokemons.size()==0) // if there the array is empty that means there is no more Pokemon's and the agent will not do anything
				{

				}
				else
				{
					CL_Pokemon pokemon = Priority_pokemons.remove(0); // take a pokemon from the array
					setCurrFruit(pokemon); // setting the pokemon as the pokemon that the agent need to follow
					path = (ArrayList<node_data>) ga.shortestPath(getSrcNode(), pokemon.get_edge().getSrc()); // calculate path to pokemon
					//System.out.println(path);
					if (path.size() >= 1) // delete the node that the agent already was on it
						path.remove(0);
					path.add(graph.getNode(pokemon.get_edge().getDest())); // add last node to move to
				}
			}
			if (path!=null) // if there is a path
			{
				while (-1!=dest&&gameservice.isRunning()) // while agent is traveling on the edge
				{
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
				if (path.size()==0) // if the path is empty stop the agent
				{
					setCurrFruit(null);
					path=null;
				}
				else //set the next node in the path that the agent need to go to
				{
					node_data next = path.remove(0); // take next stop
					gameservice.chooseNextEdge(this.id, next.getKey()); // tell server to move agent to there
				}
			}
			else
			{
				setCurrFruit(null);
			}

		}
	}

	/**
	 * setting game service
	 * @param g
	 */
	public void setGameService(game_service g)
	{
		this.gameservice = g;
	}

	public void signalStop()
	{
		this.confirmmoving = false;
	}

	/**
	 * setting agents commander (there is only 1 so its starting the commander)
	 * @param commander
	 */
	public void setCommander(AgentCommander commander) {
		this.commander = commander;
	}

	/**
	 * updating the array list that sorting the pokemons, the first pokemon is the best to go to and the last pokemon is the worse
	 * @param pokemons
	 */
	public void updatePriority(String pokemons)
	{
		DWGraph_Algo ga = new DWGraph_Algo(graph); // create a graph algo with graph
		ArrayList<CL_Pokemon> arr = Arena.json2Pokemons(pokemons); // create pokemon array
		for (CL_Pokemon pok:arr) // go over pokemon
		{
			Arena.updateEdge(pok,graph); // update the pokemon edge
			pok.setMin_dist(ga.shortestPathDist(this.currNode.getKey(),pok.get_edge().getDest())); // set the minimum distance from this agent to this agent
		}
		this.Priority_pokemons = new ArrayList<CL_Pokemon>(); // create a new arraylist of pokemons
		Collections.sort(arr, new Comparator<CL_Pokemon>() {
			@Override
			/**
			 * comparing 2 pokemons to sort them
			 */
			public int compare(CL_Pokemon o1, CL_Pokemon o2) {
				return Double.compare(o1.getMin_dist(),o2.getMin_dist());
			}
		});
		for (CL_Pokemon p:arr) // add Pokemon's into array list
		{
			this.Priority_pokemons.add(p);
		}
	}

	/**
	 * returning the destination
	 * @return
	 */
	public int getDest()
	{
		return dest;
	}

	@Override
	/**
	 * returning true if the object is equal to agent
	 */
	public boolean equals(Object a){
		if(a instanceof CL_Agent) {
			CL_Agent b = (CL_Agent)a;
			return id == b.getID() && speed == b.getSpeed() && value == b.getValue() && pos.equals(b.getLocation()) && this.getSrcNode() == b.getSrcNode() && dest == b.getDest();
		}
		return false; // return false otherwise
	}
}
