package ex2;

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
   
    public Node()
    {
        this.key = 0;
        this.tag = Integer.MAX_VALUE;
        this.weight = -1;
        this.info ="white";
        this.parent = null;
        this.geo = null;
        this.hashEdgeIn = new HashMap<Integer, edge_data>();
        this.hashEdgeOut = new HashMap<Integer, edge_data>();
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

    @Override
    public int getKey() { return this.key; }

    @Override
    public geo_location getLocation() { return this.geo; }

    @Override
    public void setLocation(geo_location p) { this.geo = p; }

    @Override
    public double getWeight() { return this.weight; }

    @Override
    public void setWeight(double w) { this.weight = w; }

    @Override
    public String getInfo() { return this.info; }

    @Override
    public void setInfo(String s) { this.info = s; }

    @Override
    public int getTag() { return this.tag; }

    @Override
    public void setTag(int t) { this.tag = t; }

    public node_data getParent() { return parent; }

	public void setParent(node_data next) { this.parent = next;	}

}