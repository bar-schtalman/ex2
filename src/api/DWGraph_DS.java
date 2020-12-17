package api;

import java.util.Collection;
import java.util.HashMap;

public class DWGraph_DS implements directed_weighted_graph{

	private int nodeSize;
	private int edgeSize;
	private int mc;
	private HashMap<Integer,node_data> hashNode;


	public DWGraph_DS()
	{
		this.nodeSize = 0;
		this.edgeSize = 0;
		this.mc = 0;
		this.hashNode = new HashMap<Integer,node_data>();
	}

	public DWGraph_DS(DWGraph_DS graph)
	{

		this.mc = graph.mc;
		this.nodeSize = graph.nodeSize();
		this.edgeSize = graph.edgeSize;
		this.hashNode = new HashMap<Integer, node_data>();

		for (node_data temp : graph.getV()) {
			this.hashNode.put(temp.getKey(), temp);
		}
	}

	@Override
	public node_data getNode(int key) 
	{
		if (hashNode.containsKey(key))
			return hashNode.get(key);
		return null;
	}

	@Override
	public edge_data getEdge(int src, int dest) 
	{
		if (this.hashNode.containsKey(src) && this.hashNode.containsKey(dest) && src != dest)
		{
			Node source = (Node) this.hashNode.get(src);
			if(source.hasEdge(dest))
				return source.getEdge(dest);
		}
		return null;
	}

	@Override
	public void addNode(node_data n) 
	{
		if (!this.hashNode.containsKey(n.getKey()))
		{
			this.hashNode.put(n.getKey(),n);
			this.nodeSize++;
			this.mc++;
		}
	}

	@Override
	public void connect(int src, int dest, double w) 
	{
		if (hashNode.containsKey(src) && hashNode.containsKey(dest) && src != dest && w != 0)
		{
			Node source = (Node) this.hashNode.get(src);
			if(!source.hasEdge(dest))
			{
				Node destiny = (Node) this.hashNode.get(dest);
				source.addEdgeOut(destiny, w);
				destiny.addEdgeIn(source, w);
				this.edgeSize++;
				this.mc++;
			}
			else
			{
				Edge temp =	(Edge)source.getEdge(dest);
				temp.setWeight(w);
			}
		}
	}


	@Override
	public Collection<node_data> getV()
	{
		return hashNode.values();
	}

	@Override
	public Collection<edge_data> getE(int node_id) 
	{
		Node node = (Node) hashNode.get(node_id);
		return node.getNeighboursEdgesOut();
	}

	public Collection<edge_data> getEIn(int node_id) 
	{
		Node node = (Node) hashNode.get(node_id);
		return node.getNeighboursEdgesIn();
	}

	@Override
	public node_data removeNode(int key) 
	{
		if(this.hashNode.containsKey(key))
		{
			for(edge_data temp : getE(key))
				this.removeEdge(temp.getSrc(), temp.getDest());

			for(edge_data temp : getEIn(key))
				this.removeEdge(temp.getSrc(), temp.getDest());

			this.nodeSize--;
			this.mc++;
			return this.hashNode.remove(key);
		}
		return null;
	}

	@Override
	public edge_data removeEdge(int src, int dest) 
	{
		if(getEdge(src,dest) != null)
		{
			edge_data toDelete = getEdge(src,dest);
			Node source = (Node) this.hashNode.get(src);
			source.deletEdge((Node) this.hashNode.get(dest));
			this.edgeSize--;
			this.mc++;
			return toDelete;
		}
		return null;
	}

	public boolean equals(object obj)
	{

	}
	@Override
	public int nodeSize() { return this.nodeSize; }

	@Override
	public int edgeSize() {	return this.edgeSize; }

	@Override
	public int getMC() { return this.mc; }

}
