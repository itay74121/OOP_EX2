package api;

public class EdgeData implements edge_data {
    private int src;
    private int dest;
    private double weight;
    private int tag;
    private String info;


    public EdgeData(int src,int dest,double weight)
    {
        this(src,dest,weight,0,"");
    }

    public EdgeData(int src, int dest,double weight, int tag, String info)
    {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
        setTag(tag);
        setInfo(info);
    }
    public EdgeData(EdgeData other)
    {
        this(other.src,other.dest,other.weight,other.tag,other.info);
    }
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

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof EdgeData)
        {
            EdgeData e = (EdgeData)obj;
            // since the data inside the tag and info fields is changeable im not going to include it in this comparison
            // just for the simplicity.
            return e.src==src&&e.dest==dest&&e.weight==weight;
        }
        return false;
    }
    public class WrapEdgeData
    {
        private int src;
        private double w;
        private int dest;
        public WrapEdgeData()
        {
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
