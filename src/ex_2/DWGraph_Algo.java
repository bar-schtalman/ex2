package ex2;

import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

import ex1.WGraph_DS;



public class DWGraph_Algo implements dw_graph_algorithms{

	directed_weighted_graph graph;

	public DWGraph_Algo()
	{
		this.graph = null;
	}

	@Override
	public void init(directed_weighted_graph g) {
		this.graph = g;	
	}

	@Override
	public directed_weighted_graph getGraph() {
		// TODO Auto-generated method stub
		return this.graph;
	}

	@Override
	public directed_weighted_graph copy() {
		// TODO Auto-generated method stub
		return new DWGraph_DS((DWGraph_DS)this.graph);
	}

	@Override
	public boolean isConnected() {
		// TODO Auto-generated method stub


		return false;
	}

	@Override
	public double shortestPathDist(int src, int dest) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<node_data> shortestPath(int src, int dest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean save(String file) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean load(String file) {
		// TODO Auto-generated method stub
		return false;
	}

	public void tarijan(node_data node)
	{
		node.setTag(0);
		node.setWeight(0);
		PriorityQueue<node_data> q = new PriorityQueue<node_data>();
		q.add(node);

		while(!q.isEmpty())
		{
			node = (Node) q.remove();
			if(node.getInfo() == "white")
			{
				for(edge_data tempEdge : this.graph.getE(node.getKey()))
				{
					node_data temp = this.graph.getNode(tempEdge.getDest());
					if(temp.getWeight() > node.getWeight() + tempEdge.getWeight())
						temp.setWeight(node.getWeight() + tempEdge.getWeight());
					temp.setInfo("gray");
					q.add(temp);
				}
			}
			node.setInfo("black");
		}
	}

}
