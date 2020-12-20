/**
 * This class represents a 3D point in space.
 */
package gameClient.util;

import api.geo_location;

import java.io.Serializable;

/**
 * This class represent a point in space with x,y,z 3 dimensions.
 */
public class Point3D implements geo_location, Serializable{
	private static final long serialVersionUID = 1L;
	/**
     * Simple set of constants - should be defined in a different class (say class Constants).*/
    public static final double EPS1 = 0.001, EPS2 = Math.pow(EPS1,2), EPS=EPS2;
    /**
     * This field represents the origin point:[0,0,0]
     */
    public static final Point3D ORIGIN = new Point3D(0,0,0);
    private double _x,_y,_z;

    /**
     *
     * @param x coordinate
     * @param y coordinate
     * @param z coordinate
     */
    public Point3D(double x, double y, double z) {
        _x=x;
        _y=y;
        _z=z;
    }

    /**
     *
     * @param p point object
     */
    public Point3D(Point3D p) {
       this(p.x(), p.y(), p.z());
    }

    /**
     * constructor
     * @param x coordinate
     * @param y coordinate
     */
    public Point3D(double x, double y) {this(x,y,0);}

    /**
     * constructor
     * @param s string to init from
     */
    public Point3D(String s) { try {
            String[] a = s.split(",");
            _x = Double.parseDouble(a[0]);
            _y = Double.parseDouble(a[1]);
            _z = Double.parseDouble(a[2]);
        }
        catch(IllegalArgumentException e) {
            System.err.println("ERR: got wrong format string for POint3D init, got:"+s+"  should be of format: x,y,x");
            throw(e);
        }
    }

    /**
     * get the x
     * @return return the coordinate
     */
    @Override
    public double x() {return _x;}
    /**
     * get the y
     * @return return the coordinate
     */
    @Override
    public double y() {return _y;}
    /**
     * get the z
     * @return return the coordinate
     */
    @Override
    public double z() {return _z;}

    /**
     * String representation
     * @return string representation
     */
    public String toString() { return _x+","+_y+","+_z; }

    /**
     * calculated distance between location and point which is location too.
     * @param p2 location
     * @return the distance calculated by function
     */
    @Override
    public double distance(geo_location p2) {
        double dx = this.x() - p2.x();
        double dy = this.y() - p2.y();
        double dz = this.z() - p2.z();
        double t = (dx*dx+dy*dy+dz*dz);
        return Math.sqrt(t);
    }

    /**
     * tells if another is equal to this one
     * @param p another point object
     * @return true if equal false if not
     */
    public boolean equals(Object p) {
        if(p==null || !(p instanceof geo_location)) {return false;}
        Point3D p2 = (Point3D)p;
        return ( (_x==p2._x) && (_y==p2._y) && (_z==p2._z) );
    }

}

