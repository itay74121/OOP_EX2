package gameClient;

import api.edge_data;
import gameClient.util.Point3D;
import org.json.JSONObject;

public class CL_Pokemon {
	private edge_data _edge;
	private double _value;
	private int _type;
	private Point3D _pos;
	private double min_dist;

	/**
	 * pokemon constructor
	 * @param p
	 * @param t
	 * @param v
	 * @param s
	 * @param e
	 */

	public CL_Pokemon(Point3D p, int t, double v, double s, edge_data e) {
		_type = t;
		_value = v;
		set_edge(e);
		_pos = p;
		min_dist = -1;
	}

	/**
	 * create pokemon from json
	 * @param json
	 * @return
	 */
	public static CL_Pokemon init_from_json(String json) {
		CL_Pokemon ans = null;
		try {
			JSONObject p = (JSONObject) new JSONObject(json).get("Pokemon");
			String arr [] = p.getString("pos").split(",");
			ans = new CL_Pokemon(new Point3D(Double.parseDouble(arr[0]),Double.parseDouble(arr[1]),Double.parseDouble(arr[2])),p.getInt("type"),p.getDouble("value"),0,null);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return ans;
	}
	public String toString() {return "F:{v="+_value+", t="+_type+"}";}

	/**
	 *
	 * @return pokemon edge he is standing on
	 */
	public edge_data get_edge() {
		return _edge;
	}

	/**
	 * set edge to pokemon where he will stand
	 * @param _edge
	 */
	public void set_edge(edge_data _edge) {
		this._edge = _edge;
	}

	/**
	 *
	 * @return pokemons location
	 */
	public Point3D getLocation() {
		return _pos;
	}

	/**
	 *
	 * @return pokemons type
	 */
	public int getType() {return _type;}
//	public double getSpeed() {return _speed;}

	/**
	 *
	 * @return pokemons value
	 */
	public double getValue() {return _value;}

	/**
	 *
	 * @return minimal distance to the pokemon
	 */
	public double getMin_dist() {
		return min_dist;
	}

	/**
	 * set minimale distance to the pokemon
	 * @param mid_dist
	 */
	public void setMin_dist(double mid_dist) {
		this.min_dist = mid_dist;
	}

	@Override
	/** checking if  obj is pokemon **/
	public boolean equals(Object obj) {
		if(obj instanceof CL_Pokemon)
		{
			CL_Pokemon p = (CL_Pokemon) obj;
			return p.getLocation().equals(getLocation()) && p.getValue()==getValue();
		}
		return false;
	}

}
