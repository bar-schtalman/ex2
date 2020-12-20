package api;

import java.util.Collection;
import java.util.HashMap;

public class Node implements node_data{
  
	private int key;
    private int tag;
    private double weight;
    private String info;
    private node_data parent;
    private geo_location geo;
    private HashMap<Integer, edge_data> hashEdgeIn;
    private HashMap<Integer, edge_data> hashEdgeOut;

   
    public Node(int key)
    {
        this.key = key;
        this.tag = Integer.MAX_VALUE;
        this.weight = -1;
        this.info ="white";
        this.parent = null;
        this.geo = null;
        this.hashEdgeIn = new HashMap<Integer, edge_data>();
        this.hashEdgeOut = new HashMap<Integer, edge_data>();
        this.geo = new geoLocation(0,0,0);
    }
    
    public Node(Node node)
    {
        this.key = node.key;
        this.tag = node.tag;
        this.weight = node.weight;
        this.info = node.info;
        this.parent = node.getParent();
        this.geo = node.geo;
        this.hashEdgeIn = new HashMap<Integer, edge_data>();
        this.hashEdgeOut = new HashMap<Integer, edge_data>();
        this.geo = node.geo;
    }
        
    public boolean hasEdge(int key)
    {
    	return this.hashEdgeIn.containsKey(key);
    }

    public edge_data getEdge(int key)
    {
    	if(this.hashEdgeIn.containsKey(key))
    		return this.hashEdgeIn.get(key);
    	return null;
    }
    
    public boolean addEdgeOut(Node node , double w)
    {
    	if(!this.hasEdge(node.getKey()))
    	{
    		Edge e = new Edge(this.key , node.key , w);
    		this.hashEdgeOut.put(node.getKey() , e);
    		return true;
    	}
    	return false;
    }
    
    public boolean addEdgeIn(Node node , double w)
    {
    	if(!node.hasEdge(this.key))
    	{
    		Edge e = new Edge(node.key , this.key , w);
    		node.hashEdgeIn.put(this.key , e);
    		return true;
    	}
    	return false;
    }
    
    public Collection<edge_data> getNeighboursEdgesOut()
    {
    	return this.hashEdgeOut.values();
    }
    
    public Collection<edge_data> getNeighboursEdgesIn()
    {
    	return this.hashEdgeIn.values();
    }
    
    public boolean deletEdge(Node node)
    {
    	if(this.hasEdge(node.key))
    	{
    		this.hashEdgeOut.remove(node.key);
    		node.hashEdgeIn.remove(this.key);
    		return true;
    	}
    	return false;
    }
    /**
     * Returns the key (id) associated with this node.
     * @return
     */
    @Override
    public int getKey() { return this.key; }
    /** Returns the location of this node, if
     * none return null.
     *
     * @return
     */
    @Override
    public geo_location getLocation() { return this.geo; }
    /** Allows changing this node's location.
     * @param p - new new location  (position) of this node.
     */
    @Override
    public void setLocation(geo_location p) { this.geo = p; }
    /**
     * Returns the weight associated with this node.
     * @return
     */
    @Override
    public double getWeight() { return this.weight; }
    /**
     * Allows changing this node's weight.
     * @param w - the new weight
     */
    @Override
    public void setWeight(double w) { this.weight = w; }
    /**
     * Returns the remark (meta data) associated with this node.
     * @return
     */
    @Override
    public String getInfo() { return this.info; }
    /**
     * Allows changing the remark (meta data) associated with this node.
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
     * Allows setting the "tag" value for temporal marking an node - common
     * practice for marking by algorithms.
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) { this.tag = t; }

    public node_data getParent() { return parent; }

	public void setParent(node_data next) { this.parent = next;	}

    public boolean equals(Object obj)
    {
        if(obj instanceof node_data && obj != null)
        {
            Node n = (Node)obj;
            if(n.getKey() != this.key)
                return false;

            for(edge_data e : n.getNeighboursEdgesOut())
            {
                if(this.hashEdgeOut.containsKey(e.getDest()))
                {
                    edge_data temp = this.hashEdgeOut.get(e.getDest());
                    if(!temp.equals(e))
                        return false;
                }
                else
                    return false;
            }
            return true;
        }
        return false;
    }
    public int compareTo(Node o) {
        if (this.weight > o.getWeight())
            return 1;
        if (this.weight < o.getWeight()) {
            return -1;
        }
        return 0;
    }
    public String toString (){
        return "Node -"+this.key+"-";
    }
}

