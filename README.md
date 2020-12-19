# OOP_EX2

### The Interfaces we used in our project

* directed_weighted_graph
* dw_graph_algorithms
* edge_data
* game_service
* geo_location
* node_data

### The classes that uses the interfaces

* DWGraph_Algo
* DWGraph_DS
* EdgeData
* GeoLocation
* NodeData


## Class AgentCommander

### Fields in the class

* private ArrayList<CL_Agent> agents;   array list of agents
* private ArrayList<CL_Pokemon> pokemons;   array list of pokemons
* private HashMap<CL_Pokemon,Boolean> map;   hashmap from pokemon to agent
* private DWGraph_DS graph;   the graph
* private game_service game;   the game service
* private ArrayList<Thread> agentsthreads;   thread that will help us start all the agents

### Constructor

* AgentCommander(game_service g) - constructing commander by getting graph

### Method Summary

* java.util.ArrayList<CL_Agent>	getAgents()	 returning arraylist with agents
* void	run()	 
* void	spawn (int agents)	initiate the cl_agents arraylist and set the agents some objects information 
* int	sum()	the total score of all agents 
* void	updateEdges()	setting the edge for every pokemon.


## Class Arena

### Fields in the class

* public static final double EPS1 = 0.001, EPS2=EPS1*EPS1;   double that will help us check distances
* private directed_weighted_graph _gg;   the graph 
* private List<CL_Agent> _agents;   list of agents
* private List<CL_Pokemon> _pokemons;   list of agents

### Constructor Summary

* Arena() default constructor

### Method Summary

* directed_weighted_graph	getGraph()	return the graph
* static java.util.ArrayList<CL_Pokemon>	json2Pokemons(java.lang.String fs)	 return array list of pokemons converted from json	 
* void	setAgents(java.util.List<CL_Agent> f)	set agents list
* void	setGraph(directed_weighted_graph g)	set graph
* void	setPokemons(java.util.List<CL_Pokemon> f)	set pokemons list
* static void	updateEdge(CL_Pokemon fr, directed_weighted_graph g)	updating pokemons edge he is standing on
* static Range2Range	w2f(directed_weighted_graph g, Range2D frame)   returning range between frame and point on the field 
* boolean isOnEdge() return if the pokemon standing on specific edge

## Class CL_Agent

### Fields in the class

* private static int dest = 0;   the destination the agent need to go to
* private int id;   id of agent
* private geo_location pos;   location of agent
* private double speed;   speed of an agent
* private edge_data currEdge;   current edge the agent standing on
* private node_data currNode;   current node the agent standing on
* private directed_weighted_graph graph;   the graph
* private CL_Pokemon currFruit;   current pokemon the agent will follow
* private double value;   the score of the agent
* private game_service gameservice;   the game service
* private ArrayList<CL_Pokemon> Priority_pokemons;   array list of pokemons sorted by priority
### Constructor Summary

* CL_Agent(directed_weighted_graph g, int start_node)	constructor for agent that gets graph and starting node
* CL_Agent(directed_weighted_graph g, int id, int src_node, double value, double speed, geo_location pos)	constructor that getting graph, id for agent, value, speed, location, and start node

### Method Summary

* boolean	equals(java.lang.Object a)	 
* edge_data	getCurrEdge()	returning the edge the agent standing on
* CL_Pokemon	getCurrFruit()	returning the pokemon that this agent following right now
* int	getDest()	returning the destination
* int	getID()	returning agents id
* geo_location	getLocation()	returning agents geolocation
* int	getNextNode()	returning the dest node of the edge the agent standing on
* double	getSpeed()	returning agents speed
* int	getSrcNode()	getting the nose you are standing on
* double	getValue()	returning the value
* boolean	isMoving()	returning the current edge the agent standing on
* void	run()	the main function.
* void	setCommander(AgentCommander commander)	setting agents commander (there is only 1 so its starting the commander)
* void	setCurrFruit(CL_Pokemon curr_fruit)	setting the pokemon that the agent will start to follow
* void	setCurrNode(int src)	setting agents current node
* void	setGameService(game_service g)	setting game service
* void	setSpeed(double v)	setting agents speed 
* void	update(java.lang.String json)	updating from JSON to regular veriabels
* void	updatePqueue(java.lang.String pokemons)	updating the array list that sorting the pokemons, the first pokemon is the best to go to and the last pokemon is the worse

## Class CL_Pokemon

### Fields in the class

* private edge_data _edge;   the edge where the pokemon is standing
* private double _value;   the value of the pokemon
* private int _type;   type of the pokemon (if he is going from the bigger node to the smaller or reverse)
* private Point3D _pos;   cordinations of the pokemon
* private double min_dist;   minimal distance to the pokemon

### Constructor Summary

* CL_Pokemon(Point3D p, int t, double v, double s, edge_data e)	pokemon constructor

### Method Summary

* boolean	equals(java.lang.Object obj)	 checking if  obj is pokemon
* edge_data	get_edge()	pokemon edge he is standing on 
* Point3D	getLocation()	 pokemons location
* double	getMin_dist()	 minimal distance to the pokemon
* int	getType()	 pokemons type
* double	getValue()	 pokemons value 
* static CL_Pokemon	init_from_json(java.lang.String json)	 create pokemon from json
* void	set_edge(edge_data _edge)	set edge to pokemon where he will stand
* void	setMin_dist(double mid_dist)	set minimale distance to the pokemon
* void	setMin_ro(double min_ro)	
* java.lang.String	toString()	 converte to string

## Class MyPanel

### Fields in the class

* private Dimension screensize;   the size of the screen
* private double fractionX;   the X of the panel
* private double fractionY;   the Y of the panel
* private DWGraph_DS graph;   the graph
* private ArrayList<CL_Pokemon> pokemons;   list of pokemons
* private ArrayList<CL_Agent> agents;   list of agents
* private Range xr;   the x distance to the frame
* private Range yr;   the y distance to the frame
* private long time;   how much time the program is running

### Constructor Summary

* MyPanel(double fractionX, double fractionY)	create the panel using X and Y

### Method Summary

* double	getFractionX()	 get the X
* double	getFractionY()	 get the Y
* void	paint (java.awt.Graphics g)	 draw the graph with the pokemons and agents
* void	setFractionX (double fractionX)	set the X size of the frame
* void	setFractionY(double fractionY)	set the Y size of the frame
* void	update_xr_yr(int w, int h)	if the size of the frame changed the function changing the graph size


## Class LoginFrame

### Fields in the class

* private Dimension screen_size;   the size of the screen
* private double fractionX;   the X size of the frame
* private double fractionY;   the Y size of the frame
* private JButton login;   button to press when you entered ID and level
* private JTextArea textArea_id;   take user id
* private JTextArea textArea_level;   take the scenario
* private JLabel label_id;   label with "ID"
* private JLabel label_level;   label with "Level"
* private JLabel messagebox;   message if the level is wrong
* private int login_framex;   the size of the frame
* private int login_framey;   the size of the frame
* private Ex2.SharedLevelBuffer sharedLevelBuffer;   connecting between the level and the class

* private Lock lockscreen;   locking the screen
* public Condition condition;   if condition of the frame

### Constructor Summary

* LoginFrame(double X, double Y, Ex2.SharedLevelBuffer sharedLevelBuffer)	constructoe of the frame with ID and Level

### Method Summary

* void	actionPerformed(java.awt.event.ActionEvent e)	 what happen when you make actions
* void	run()   the main function, locking the frame

## Class MyFrame2

### Fields in the class

* private MyPanel panel;   the panel
* private DWGraph_DS graph_ds;   the graph
* private ArrayList<CL_Pokemon> pokemons;   list of pokemons
* private ArrayList<CL_Agent> agents;   list of agents
* private Dimension screen_size;   the screen size
* private double fractionX;   the Y size
* private double fractionY;   the X size 
* private String graph_json;   the String of the graph
* private String pokemons_json;   the String of pokemons
* private JButton gobackbutton;   go back to login frame
* private game_service game;   the game service

### Constructor Summary

* MyFrame2(double fractionX, double fractionY) create the panel using X and Y

### Method Summary

* double	getFractionX()	 get the X
* double	getFractionY()	 get the Y
* static void	main(java.lang.String[] args)	 
* void	run()	 
* void	setFractionX(double fractionX)	set X size of the graph
* void	setFractionY(double fractionY)	set Y size of the graph
* void	setGame(game_service g)	set game
* void	updateFrame(java.lang.String jgraph, java.lang.String jpokemon, java.util.List<CL_Agent> jagent, long time)	updating the frame

## Class Ex2

### Fields in the class

* private MyFrame2 myFrame;   the frame with the graph
* private LoginFrame loginFrame;   the login frame
* private Thread loginframethread;   thread for login frame
* private Thread framethread;   thread for frame with the graph
* private SharedLevelBuffer sharedLevelBuffer;
* private Lock lockscreentime;   time to lock the screen
* private AgentCommander a;   the agent commander
* private Thread commander;   thread for the agent commander 
* private game_service game;   the game service

### Method Summary

* public static void main(String [] args)   the main function
* public  void init()   the init function

## Class SharedLevelBuffer

### Fields in the class

* private String id;   the ID 
* private int level;   the level

### Constructor Summary

* public SharedLevelBuffer()   the default constructor
* public SharedLevelBuffer(String id,int level)   constructor with ID and level

### Methos in the summary

* public String getId()   return the ID
* public void setId(String id)   set the ID
* public void setLevel(int level)   set the level




