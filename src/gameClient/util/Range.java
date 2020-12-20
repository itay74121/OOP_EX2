package gameClient.util;
/**
 * This class represents a simple 1D range of shape [min,max]
 * @author boaz_benmoshe
 *
 */
public class Range {
	private double _min, _max;

	/**
	 *
	 * @param min minimum
	 * @param max maximum
	 */
	public Range(double min, double max) {
		set_min(min);
		set_max(max);
	}

	/**
	 *
	 * @param x another Range object
	 */
	public Range(Range x) {
		this(x._min, x._max);
	}

	/**
	 * @return String representation of range
	 */
	public String toString() {
		String ans = "["+this.get_min()+","+this.get_max()+"]";
		if(this.isEmpty()) {ans = "Empty Range";}
		return ans;
	}

	/**
	 * Tells if range is empty
	 * @return true if range is empty
	 */
	public boolean isEmpty() {
		return this.get_min()>this.get_max();
	}

	/**
	 * @return the max
	 */
	public double get_max() {
		return _max;
	}

	/**
	 * @return the length
	 */
	public double get_length() {
		return _max-_min;
	}

	/**
	 * @param _max set the max
	 */
	private void set_max(double _max) {
		this._max = _max;
	}

	/**
	 * get the min
	 * @return the min
	 */
	public double get_min() {
		return _min;
	}

	/**
	 * set the min
	 * @param _min minimum
	 */
	private void set_min(double _min) {
		this._min = _min;
	}

	/**
	 *
	 * @param d point to take porion from
	 * @return the ratio of portion
	 */
	public double getPortion(double d)
	{
		double d1 = d-_min;
		double ans = d1/get_length();
		return ans;
	}

	/**
	 * @param p portion
	 * @return the point on range
	 */
	public double fromPortion(double p) {
		return _min+p* get_length();
	}
}
