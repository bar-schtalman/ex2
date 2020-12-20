package api;

import java.util.Collection;
import java.util.HashMap;

public class DWGraph_DS implements directed_weighted_graph{
	/**
	 * This class represents a directional weighted graph.
	 * The interface has a road-system or communication network in mind -
	 * and should support a large number of nodes (over 100,000).
	 * The implementation should be based on an efficient compact representation
	 * (should NOT be based on a n*n matrix).
	 *
	 */
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
	/**
	 * returns the node_data by the node_id,
	 * @param key - the node_id
	 * @return the node_data by the node_id, null if none.
	 */
	@Override
	public node_data getNode(int key) 
	{
		if (hashNode.containsKey(key))
			return hashNode.get(key);
		return null;
	}
	/**
	 * returns the data of the edge (src,dest), null if none.
	 * Note: this method should run in O(1) time.
	 * @param src
	 * @param dest
	 * @return
	 */
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
	/**
	 * adds a new node to the graph with the given node_data.
	 * Note: this method should run in O(1) time.
	 * @param n
	 */
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
	/**
	 * Connects an edge with weight w between node src to node dest.
	 * * Note: this method should run in O(1) time.
	 * @param src - the source of the edge.
	 * @param dest - the destination of the edge.
	 * @param w - positive weight representing the cost (aka time, price, etc) between src-->dest.
	 */
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

	/**
	 * This method returns a pointer (shallow copy) for the
	 * collection representing all the nodes in the graph.
	 * Note: this method should run in O(1) time.
	 * @return Collection<node_data>
	 */
	@Override
	public Collection<node_data> getV()
	{
		return hashNode.values();
	}
	/**
	 * This method returns a pointer (shallow copy) for the
	 * collection representing all the edges getting out of
	 * the given node (all the edges starting (source) at the given node).
	 * Note: this method should run in O(k) time, k being the collection size.
	 * @return Collection<edge_data>
	 */
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


	/**
	 * Deletes the node (with the given ID) from the graph -
	 * and removes all edges which starts or ends at this node.
	 * This method should run in O(k), V.degree=k, as all the edges should be removed.
	 * @return the data of the removed node (null if none).
	 * @param key
	 */
	@Override
	public node_data removeNode(int key)
	{
		if(this.hashNode.containsKey(key))
		{
			this.edgeSize -= this.getE(key).size();

			for(edge_data temp : getEIn(key))
				this.removeEdge(temp.getSrc(), temp.getDest());

			this.nodeSize--;
			this.mc++;
			return this.hashNode.remove(key);
		}
		return null;
	}
	/**
	 * Deletes the edge from the graph,
	 * Note: this method should run in O(1) time.
	 * @param src
	 * @param dest
	 * @return the data of the removed edge (null if none).
	 */
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
	public String toString() {
		return "DWGraph[" +
				",Vertexes amount = " + this.nodeSize +
				", edges amount = " + this.edgeSize +
				", Mode count = " + mc +
				", vertexes = " + this.hashNode +
				']';
	}

	public boolean equals(Object obj)
	{
		if(obj != null && obj instanceof directed_weighted_graph)
		{
			DWGraph_DS g = (DWGraph_DS)obj;
			if(this.edgeSize == g.edgeSize() && this.nodeSize == g.nodeSize())
			{
				for(node_data node : g.getV())
				{
					if(this.hashNode.containsKey(node.getKey()))
					{
						Node temp = (Node) this.hashNode.get(node.getKey());
						if(!temp.equals(node))
							return false;
					}
					else
						return false;
				}
			}
			else
				return false;
		}
		return true;
	}

	/** Returns the number of vertices (nodes) in the graph.
	 * Note: this method should run in O(1) time.
	 * @return
	 */
	@Override
	public int nodeSize() { return this.nodeSize; }
	/**
	 * Returns the number of edges (assume directional graph).
	 * Note: this method should run in O(1) time.
	 * @return
	 */
	@Override
	public int edgeSize() {	return this.edgeSize; }
	/**
	 * Returns the Mode Count - for testing changes in the graph.
	 * @return
	 */
	@Override
	public int getMC() { return this.mc; }

}
