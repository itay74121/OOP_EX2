package gameClient;

import api.*;
import gameClient.util.Point3D;
import org.json.JSONException;
import org.json.JSONObject;

public class CL_Agent  implements Runnable{
		public static final double EPS = 0.0001;
		private static int _count = 0;
		private static int _seed = 3331;
		private int id;
	//	private long _key;
		private geo_location pos;
		private double speed;
		private edge_data currEdge;
		private node_data currNode;
		private directed_weighted_graph graph;
		private CL_Pokemon currFruit;
		private long sgDt;
		
		private double value;
		
		
		public CL_Agent(directed_weighted_graph g, int start_node) {
			graph = g;
			setMoney(0);
			this.currNode = graph.getNode(start_node);
			pos = currNode.getLocation();
			id = -1;
			setSpeed(0);
		}
		public CL_Agent(directed_weighted_graph g, int id,int src_node, double value, double speed, geo_location pos)
		{
			this.pos = pos;
			this.speed = speed;
			this.value = value;
			this.currNode = g.getNode(src_node);
			this.graph = g;
			this.id = id;
		}

		public void update(String json) {
			JSONObject line;
			try {
				// "GameServer":{"graph":"A0","pokemons":3,"agents":1}}
				line = new JSONObject(json);
				JSONObject ttt = line.getJSONObject("Agent");
				int id = ttt.getInt("id");
				if(id==this.getID() || this.getID() == -1) {
					if(this.getID() == -1) {
						this.id = id;}
					double speed = ttt.getDouble("speed");
					String p = ttt.getString("pos");
					Point3D pp = new Point3D(p);
					int src = ttt.getInt("src");
					int dest = ttt.getInt("dest");
					double value = ttt.getDouble("value");
					this.pos = pp;
					this.setCurrNode(src);
					this.setSpeed(speed);
					this.setMoney(value);
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		//@Override
		public int getSrcNode() {return this.currNode.getKey();}
		public String toJSON() {
			int d = this.getNextNode();
			String ans = "{\"Agent\":{"
					+ "\"id\":"+this.id +","
					+ "\"value\":"+this.value +","
					+ "\"src\":"+this.currNode.getKey()+","
					+ "\"dest\":"+d+","
					+ "\"speed\":"+this.getSpeed()+","
					+ "\"pos\":\""+ pos.toString()+"\""
					+ "}"
					+ "}";
			return ans;	
		}
		private void setMoney(double v)
		{
			value = v;
		}
	
		public boolean isNextNode(int dest)
		{
			boolean ans = false;
			int src = this.currNode.getKey();
			this.currEdge = graph.getEdge(src, dest);
			if(currEdge !=null) {
				ans=true;
			}
			else {
				currEdge = null;}
			return ans;
		}
		public void setCurrNode(int src) {
			this.currNode = graph.getNode(src);
		}
		public boolean isMoving() {
			return this.currEdge !=null;
		}
		public String toString() {
			return toJSON();
		}
		public String toString1() {
			String ans=""+this.getID()+","+ pos +", "+isMoving()+","+this.getValue();
			return ans;
		}
		public int getID() {
			// TODO Auto-generated method stub
			return this.id;
		}
	
		public geo_location getLocation() {
			// TODO Auto-generated method stub
			return pos;
		}
		public static CL_Agent fromjsontoCLAgent(JSONObject agnetdata,directed_weighted_graph g)
		{
			CL_Agent ans = null;
			try {
				JSONObject o =(JSONObject) agnetdata.get("Agent");
				int src = o.getInt("src");
				String pos [] = o.getString("pos").split(",");
				geo_location l1 = new GeoLocation(Double.parseDouble(pos[0]),Double.parseDouble(pos[1]),Double.parseDouble(pos[2]));
				double speed = o.getDouble("speed");
				double value = o.getDouble("value");
				int id = o.getInt("id");
				ans = new CL_Agent(g,id,src,value,speed,l1);
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return ans;
		}
		
		public double getValue() {
			// TODO Auto-generated method stub
			return this.value;
		}



		public int getNextNode() {
			int ans = -2;
			if(this.currEdge ==null) {
				ans = -1;}
			else {
				ans = this.currEdge.getDest();
			}
			return ans;
		}

		public double getSpeed() {
			return this.speed;
		}

		public void setSpeed(double v) {
			this.speed = v;
		}
		public CL_Pokemon getCurrFruit() {
			return currFruit;
		}
		public void setCurrFruit(CL_Pokemon curr_fruit) {
			this.currFruit = curr_fruit;
		}
		public void set_SDT(long ddtt) {
			long ddt = ddtt;
			if(this.currEdge !=null) {
				double w = getCurrEdge().getWeight();
				geo_location dest = graph.getNode(getCurrEdge().getDest()).getLocation();
				geo_location src = graph.getNode(getCurrEdge().getSrc()).getLocation();
				double de = src.distance(dest);
				double dist = pos.distance(dest);
				if(this.getCurrFruit().get_edge()==this.getCurrEdge()) {
					 dist = currFruit.getLocation().distance(this.pos);
				}
				double norm = dist/de;
				double dt = w*norm / this.getSpeed(); 
				ddt = (long)(1000.0*dt);
			}
			this.setSgDt(ddt);
		}
		
		public edge_data getCurrEdge() {
			return this.currEdge;
		}
		public long getSgDt() {
			return sgDt;
		}
		public void setSgDt(long sgDt) {
			this.sgDt = sgDt;
		}

	@Override
	public void run()
	{
		// algorithm of an agent
	}
}
