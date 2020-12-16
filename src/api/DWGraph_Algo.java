package api;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.*;



public class DWGraph_Algo implements dw_graph_algorithms, Serializable {

	directed_weighted_graph DWGraph;

	public DWGraph_Algo()
	{
		this.DWGraph = null;
	}

	@Override
	public void init(directed_weighted_graph g) 
	{
		this.DWGraph = g;
	}

	@Override
	public directed_weighted_graph getGraph() 
	{
		// TODO Auto-generated method stub
		return this.DWGraph;
	}

	@Override
	public directed_weighted_graph copy() 
	{
		// TODO Auto-generated method stub
		return new DWGraph_DS((DWGraph_DS)this.DWGraph);
	}

	@Override
	public boolean isConnected() 
	{
		// TODO Auto-generated method stub
		if(this.DWGraph == null || this.DWGraph.nodeSize() <= 1)
			return true;
		if(this.DWGraph.nodeSize() > 1 && this.DWGraph.edgeSize() == 0)
			return false;
		Iterator<node_data> it = this.DWGraph.getV().iterator();
		node_data temp = it.next();
		return this.isConnected(temp.getKey() , it.next().getKey());
	}

	public boolean isConnected(int node_id1, int node_id2) 
	{
		// TODO Auto-generated method stub
		directed_weighted_graph graphCopy1 = this.copy();
		directed_weighted_graph graphCopy2 = this.copy();

		bfs(graphCopy1.getNode(node_id1));
		bfs(graphCopy2.getNode(node_id2));

		for(node_data node : graphCopy1.getV())
		{
			if(node.getKey() == Integer.MAX_VALUE)
				return false;
		}

		for(node_data node : graphCopy2.getV())
		{
			if(node.getKey() == Integer.MAX_VALUE)
				return false;
		}

		return true;
	}

	@Override
	public double shortestPathDist(int src, int dest) 
	{
		// TODO Auto-generated method stub
		directed_weighted_graph graphCopy = this.copy();

		if(src == dest)
			return 0;

		if(graphCopy.getNode(src) != null && graphCopy.getNode(dest) != null)
			Dijkstra(graphCopy.getNode(src));

		return graphCopy.getNode(dest).getWeight();
	}

	@Override
	public List<node_data> shortestPath(int src, int dest) 
	{
		// TODO Auto-generated method stub

		directed_weighted_graph graphCopy = this.copy();
		LinkedList<node_data> pathList = new LinkedList<node_data>();

		if(src == dest)
			return null;

		if(graphCopy.getNode(src) != null && graphCopy.getNode(dest) != null)
		{
			Dijkstra(graphCopy.getNode(src));
			if(graphCopy.getNode(dest).getWeight() != -1)
			{
				Node temp = (Node) graphCopy.getNode(dest);
				while(temp.getParent() != null)
				{
					pathList.addFirst(temp);
					temp = (Node) temp.getParent();
				}
			}
			else 
				return null;
		}
		return pathList;
	}

	@Override
	public boolean save(String file)
	{
		JsonObject graph= new JsonObject();
		JsonArray edges= new JsonArray();
		JsonArray nodes= new JsonArray();


		for (node_data n: DWGraph.getV()){
			JsonObject node= new JsonObject();
			String pos= n.getLocation().x()+","+n.getLocation().y()+","+n.getLocation().z();
			node.addProperty("pos", pos);
			node.addProperty("id", n.getKey());
			nodes.add(node);
		}
		graph.add("Edges",edges);
		graph.add("Nodes",nodes);

		try
		{
			Gson gson = new Gson();
			PrintWriter pw = new PrintWriter(new File(file));
			pw.write(gson.toJson(graph));
			pw.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;



	}

	@Override
	public boolean load(String file)
	{
		try {
			GsonBuilder builder = new GsonBuilder();
			builder.registerTypeAdapter(DWGraph_DS.class, new DW_GraphJsonDeserializer());
			Gson gson = builder.create();

			FileReader reader = new FileReader(file);
			this.DWGraph= gson.fromJson(reader, DWGraph_DS.class);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}


	//	a function that gets a node from the graph and travel on the graph 
	//	by the edges and neighbours and changes their values for connectivity checks 
	private void bfs(node_data node)
	{
		Queue<node_data> q = new LinkedList<node_data>(); // the queue of nodes 
		node.setTag(0); 
		q.add(node);

		while(!q.isEmpty()) 
		{
			node_data next = q.remove(); // the next node

			if(next.getInfo().equals("white")) //if the nodes info is white then we need to deal with it
				next.setInfo("gray");

			for(edge_data edge : this.DWGraph.getE(next.getKey()))
			{
				Node temp = (Node)this.DWGraph.getNode(edge.getDest());
				if(temp.getInfo() == "white")
				{
					temp.setTag(next.getTag()+1);
					temp.setParent(next);
					temp.setInfo("gray");
					q.add(temp);
				}
			}
			next.setInfo("black"); //when finished with a node he's info changes to black 
		}
	}


	public void Dijkstra (node_data node)
	{
		//An algorithm that goes through the vertexes of the graph
		//after the algorithm is done, we can check this graph features

		PriorityQueue<node_data> q = new PriorityQueue<node_data>(); // the queue of nodes 
		node.setWeight(0);
		q.add(node);

		while(!q.isEmpty()) 
		{
			node_data next = q.poll(); // the next node
			for(edge_data edge : this.DWGraph.getE(next.getKey())) //changes the nodes neighbours data and adds them to the queue
			{
				Node tempNei = (Node)this.DWGraph.getNode(edge.getDest());
				if(tempNei.getWeight() == -1 || tempNei.getWeight() > next.getWeight() + edge.getWeight()) 
				{
					tempNei.setWeight(next.getWeight() + edge.getWeight());
					tempNei.setParent(next);

				}
				if(tempNei.getInfo() == "white")
				{
					tempNei.setInfo("gray");
					q.add(tempNei);
				}

				next.setInfo("black"); //when finished with a node he's info changes to black 
			}
		}
	}
}
