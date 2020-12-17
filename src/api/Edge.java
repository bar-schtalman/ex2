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
    
    @Override
    public int getSrc() { return this.src; }

    @Override
    public int getDest() { return this.dest; }

    @Override
    public double getWeight() { return this.weight; }

    @Override
    public String getInfo() { return this.info; }

    @Override
    public void setInfo(String s) { this.info = s; }

    @Override
    public int getTag() { return this.tag; }

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
}