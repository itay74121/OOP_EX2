package api;

public class EdgeData implements edge_data {
    private int src; // key of source node
    private int dest; // key of destanation node
    private double weight; // weight of the edge between them
    private int tag;
    private String info;

    /**
     *  A constructor for the class that takes src dest and weight.
     * @param src
     * @param dest
     * @param weight
     */
    public EdgeData(int src,int dest,double weight)
    {
        this(src,dest,weight,0,""); // using the bigger constructor but with constants
    }

    /**
     * The complete constructor for this class taking input for all the field.
     * @param src
     * @param dest
     * @param weight
     * @param tag
     * @param info
     */
    public EdgeData(int src, int dest,double weight, int tag, String info)
    {
        // set all the fields
        this.src = src;
        this.dest = dest;
        this.weight = weight;
        setTag(tag);
        setInfo(info);
    }

    /**
     * Copy constructor taking another edge and copying it.
     * @param other
     */
    public EdgeData(EdgeData other)
    {
        this(other.src,other.dest,other.weight,other.tag,other.info);
    }

    /**
     * Copy constructor that changes just the weight of the edge .
     * @param other
     * @param weight
     */
    public EdgeData(EdgeData other,double weight)
    {
        this(other.src,other.dest,weight,other.tag,other.info);

    }

    @Override
    public int getSrc()
    {
        return src;
    }

    @Override
    public int getDest()
    {
        return dest;
    }

    @Override
    public double getWeight()
    {
        return weight;
    }

    @Override
    public String getInfo()
    {
        return info;
    }

    @Override
    public void setInfo(String s)
    {
        info = s;
    }

    @Override
    public int getTag()
    {
        return tag;
    }

    @Override
    public void setTag(int t)
    {
        tag = t;
    }

    /**
     * Override the equals methode.
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof EdgeData)
        {
            EdgeData e = (EdgeData)obj;
            // since the data inside the tag and info fields is changeable im not going to include it in this comparison
            // just for the simplicity.
            return e.src==src&&e.dest==dest&&e.weight==weight; // return true if this is true
        }
        return false; // otherwise if not
    }

    /**
     * A wrap class for the EdgeData class, representing all the important information that needs saving
     */
    public class WrapEdgeData
    {
        private int src; // taking source
        private double w; // taking weight
        private int dest; // taking destination
        public WrapEdgeData()
        {
            // set them right form the outer class of that object
            this.src = EdgeData.this.src;
            this.w = weight;
            this.dest = EdgeData.this.dest;
        }

        public int getSrc() {
            return src;
        }

        public double getW() {
            return w;
        }

        public int getDest() {
            return dest;
        }
    }

}
