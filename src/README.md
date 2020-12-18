# OOP_EX2


## Class AgentCommander

### Constructor

AgentCommander(game_service g) constructing commander by getting graph

### Method Summary

int	compare (CL_Pokemon o1, CL_Pokemon o2)	 
java.util.ArrayList<CL_Agent>	getAgents()	 returning arraylist with agents
static void	main (java.lang.String[] args)	 the main function
boolean	request (CL_Pokemon pokemon)	 return 1 if the first pokemon has better value, 0 if they both the same and -1 if the second is has better value
void	run()	 
void	spawn (int agents)	 
int	sum()	the total score
void	updateEdges()	setteng the efficiency of every edge with the pokemon


## Class Arena

### Constructor Summary

Arena() default constructor

### Method Summary

directed_weighted_graph	getGraph()	return the graph
static java.util.ArrayList<CL_Pokemon>	json2Pokemons(java.lang.String fs)	 return array list of pokemons converted from json	 
void	setAgents(java.util.List<CL_Agent> f)	set agents list
void	setGraph(directed_weighted_graph g)	set graph
void	setPokemons(java.util.List<CL_Pokemon> f)	set pokemons list
static void	updateEdge(CL_Pokemon fr, directed_weighted_graph g)	updating pokemons edge he is standing on
static Range2Range	w2f(directed_weighted_graph g, Range2D frame)	 
boolean isOnEdge() return if the pokemon standing on specific edge

## Class CL_Agent

### Constructor Summary

CL_Agent(directed_weighted_graph g, int start_node)	constructor for agent that gets graph and starting node
CL_Agent(directed_weighted_graph g, int id, int src_node, double value, double speed, geo_location pos)	constructor that getting graph, id for agent, value, speed, location, and start node

### Method Summary

boolean	equals(java.lang.Object a)	 
edge_data	getCurrEdge()	returning the edge the agent standing on
CL_Pokemon	getCurrFruit()	returning the pokemon that this agent following right now
int	getDest()	returning the destination
int	getID()	returning agents id
geo_location	getLocation()	returning agents geolocation
int	getNextNode()	returning the dest node of the edge the agent standing on
double	getSpeed()	returning agents speed
int	getSrcNode()	getting the nose you are standing on
double	getValue()	returning the value
boolean	isMoving()	returning the current edge the agent standing on
void	run()	the main function.
void	setCommander(AgentCommander commander)	setting agents commander (there is only 1 so its starting the commander)
void	setCurrFruit(CL_Pokemon curr_fruit)	setting the pokemon that the agent will start to follow
void	setCurrNode(int src)	setting agents current node
void	setGameService(game_service g)	setting game service
void	setSpeed(double v)	setting agents speed 
void	update(java.lang.String json)	updating from JSON to regular veriabels
void	updatePqueue(java.lang.String pokemons)	updating the array list that sorting the pokemons, the first pokemon is the best to go to and the last pokemon is the worse

## Class CL_Pokemon

### Constructor Summary

CL_Pokemon(Point3D p, int t, double v, double s, edge_data e)	pokemon constructor

### Method Summary

boolean	equals(java.lang.Object obj)	 checking if  obj is pokemon
edge_data	get_edge()	pokemon edge he is standing on 
Point3D	getLocation()	 pokemons location
double	getMin_dist()	 minimal distance to the pokemon
int	getType()	 pokemons type
double	getValue()	 pokemons value 
static CL_Pokemon	init_from_json(java.lang.String json)	 create pokemon from json
void	set_edge(edge_data _edge)	set edge to pokemon where he will stand
void	setMin_dist(double mid_dist)	set minimale distance to the pokemon
void	setMin_ro(double min_ro)	
java.lang.String	toString()	 converte to string

## Class MyPanel

### Constructor Summary

MyPanel(double fractionX, double fractionY)	create the panel using X and Y

### Method Summary

double	getFractionX()	 get the X
double	getFractionY()	 get the Y
void	paint (java.awt.Graphics g)	 draw the graph with the pokemons and agents
void	setFractionX (double fractionX)	set the X size of the frame
void	setFractionY(double fractionY)	set the Y size of the frame
void	update_xr_yr(int w, int h)	if the size of the frame changed the function changing the graph size


## Class LoginFrame

### Constructor Summary

LoginFrame(double X, double Y, Ex2.SharedLevelBuffer sharedLevelBuffer)	constructoe of the frame with ID and Level

### Method Summary

void	actionPerformed(java.awt.event.ActionEvent e)	 what happen when you make actions
void	run()   the main function, locking the frame

## Class MyFrame2

### Constructor Summary

MyFrame2(double fractionX, double fractionY) create the panel using X and Y

### Method Summary

double	getFractionX()	 get the X
double	getFractionY()	 get the Y
static void	main(java.lang.String[] args)	 
void	run()	 
void	setFractionX(double fractionX)	set X size of the graph
void	setFractionY(double fractionY)	set Y size of the graph
void	setGame(game_service g)	set game
void	updateFrame(java.lang.String jgraph, java.lang.String jpokemon, java.util.List<CL_Agent> jagent, long time)	updating the frame









