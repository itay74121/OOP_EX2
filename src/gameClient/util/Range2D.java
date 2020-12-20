package gameClient.util;
import api.geo_location;

/**
 * This class represents a 2D Range, composed from two 1D Ranges.
 */
public class Range2D {
	private Range _y_range;
	private Range _x_range;

	/**
	 *
	 * @param x Range object x
	 * @param y Range object y
	 */
	public Range2D(Range x, Range y) {
		_x_range = new Range(x);
		_y_range = new Range(y);
	}

	/**
	 *
	 * @param w another Range 2d
	 */
	public Range2D(Range2D w) {
		_x_range = new Range(w._x_range);
		_y_range = new Range(w._y_range);
	}

	/**
	 *
	 * @param p point to get portion from
	 * @return point with ratios
	 */
	public Point3D getPortion(geo_location p) {
		double x = _x_range.getPortion(p.x());
		double y = _y_range.getPortion(p.y());
		return new Point3D(x,y,0);
	}

	/**
	 *
	 * @param p point with ratios
	 * @return the point it was originally taken from
	 */
	public Point3D fromPortion(geo_location p) {
		double x = _x_range.fromPortion(p.x());
		double y = _y_range.fromPortion(p.y());
		return new Point3D(x,y,0);
	}	
}
