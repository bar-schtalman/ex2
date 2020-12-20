package api;

public class Edge implements edge_data
{
    private int src;
    private int dest;
    private String info;
    private double weight;
    private int tag;

    public Edge (int src, int dest, double w)
    {
        this.src = src;
        this.dest = dest;
        this.info = null;
        this.weight = w;
        this.tag = -1;
    }
    
    public Edge(Edge edge)
    {
        this.src = edge.src;
        this.dest = edge.dest;
        this.info = edge.info;
        this.weight = edge.weight;
        this.tag = edge.tag;
    }
    /**
     * The id of the source node of this edge.
     * @return
     */
    @Override
    public int getSrc() { return this.src; }
    /**
     * The id of the destination node of this edge
     * @return
     */
    @Override
    public int getDest() { return this.dest; }
    /**
     * @return the weight of this edge (positive value).
     */
    @Override
    public double getWeight() { return this.weight; }
    /**
     * Returns the remark (meta data) associated with this edge.
     * @return
     */
    @Override
    public String getInfo() { return this.info; }
    /**
     * Allows changing the remark (meta data) associated with this edge.
     * @param s
     */
    @Override
    public void setInfo(String s) { this.info = s; }
    /**
     * Temporal data (aka color: e,g, white, gray, black)
     * which can be used be algorithms
     * @return
     */
    @Override
    public int getTag() { return this.tag; }
    /**
     * This method allows setting the "tag" value for temporal marking an edge - common
     * practice for marking by algorithms.
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) { this.tag = t; }

    public void setWeight(double weight) { this.weight = weight; }

    public boolean equals(Object obj)
    {
        if(obj instanceof edge_data && obj != null)
            return this.src == ((edge_data) obj).getSrc() &&
                    this.dest == ((edge_data) obj).getDest() &&
                    this.weight == ((edge_data) obj).getWeight();

        return false;
    }
    public String toString (){
        return "Edge from node "+this.src+" to "+this.dest+" ,weight: "+this.getWeight();
    }
}