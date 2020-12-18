package api;

public class GeoLocation implements geo_location
{
    private double x;
    private double y;
    private double z;
    public GeoLocation()
    {
        this(0,0,0);
    }

    /**
     * geo location constructor
     * @param x
     * @param y
     * @param z
     */
    public GeoLocation(double x, double y, double z)
    {
        setX(x);
        setY(y);
        setZ(z);
    }

    @Override
    public double x() {
        return x;
    }

    @Override
    public double y() {
        return y;
    }

    @Override
    public double z() {
        return z;
    }

    @Override
    public double distance(geo_location g) { // calculate distance between points
        return Math.sqrt(Math.pow(g.x()-x(),2)+Math.pow(g.y()-y(),2)+Math.pow(g.z()-z(),2));
    }

    /**
     * set x location
     * @param x
     */
    private void setX(double x) {
        this.x = x;
    }

    /**
     * set y location
     * @param y
     */
    private void setY(double y) {
        this.y = y;
    }

    /**
     * set z location
     * @param z
     */
    private void setZ(double z) {
        this.z = z;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof GeoLocation)
        {
            GeoLocation t = (GeoLocation) obj;
            return x==t.x && y == t.y && z==t.z; // if they have the same location they are equal
        }
        else // otherwise false
            return false;
    }

    @Override
    public String toString() {
        return x+","+y+","+z;
    }
}
