package gameClient.util;

import api.geo_location;

/**
 * This class represents a simple world 2 frame conversion (both ways).
 * @author boaz.benmoshe
 *
 */

public class Range2Range {
	private Range2D _world, _frame;

	/**
	 * @param w Range 2d object
	 * @param f Range 2d object
	 */
	public Range2Range(Range2D w, Range2D f) {
		_world = new Range2D(w);
		_frame = new Range2D(f);
	}

	/**
	 *
	 * @param p point
	 * @return the point equals to the frame
	 */
	public geo_location world2frame(geo_location p) {
		Point3D d = _world.getPortion(p);
		Point3D ans = _frame.fromPortion(d);
		return ans;
	}

	/**
	 *
	 * @param p point in frame
	 * @return point equal in the world
	 */
	public geo_location frame2world(geo_location p) {
		Point3D d = _frame.getPortion(p);
		Point3D ans = _world.fromPortion(d);
		return ans;
	}

	/**
	 * @return get the world
	 */
	public Range2D getWorld() {
		return _world;
	}

	/**
	 * @return get the frame
	 */
	public Range2D getFrame() {
		return _frame;
	}
}
