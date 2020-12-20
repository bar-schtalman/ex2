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

/**
 * This class represents a Directed (positive) Weighted Graph Theory Algorithms including:
 * 0. clone(); (copy)
 * 1. init(graph);
 * 2. isConnected(); // strongly (all ordered pais connected)
 * 3. double shortestPathDist(int src, int dest);
 * 4. List<node_data> shortestPath(int src, int dest);
 * 5. Save(file); // eJSON fil
 * 6. Load(file); // JSON file
 *
 * @author boaz.benmoshe
 *
 */

public class DWGraph_Algo implements dw_graph_algorithms, Serializable {

	directed_weighted_graph DWGraph;

	public DWGraph_Algo()
	{
		this.DWGraph = null;
	}
	/**
	 * Init the graph on which this set of algorithms operates on.
	 * @param g
	 */
	@Override
	public void init(directed_weighted_graph g) 
	{
		this.DWGraph = g;
	}
	/**
	 * Return the underlying graph of which this class works.
	 * @return
	 */
	@Override
	public directed_weighted_graph getGraph() 
	{
		// TODO Auto-generated method stub
		return this.DWGraph;
	}
	/**
	 * Compute a deep copy of this weighted graph.
	 * @return
	 */
	@Override
	public directed_weighted_graph copy() 
	{
		// TODO Auto-generated method stub
		return new DWGraph_DS((DWGraph_DS)this.DWGraph);
	}
	/**
	 * Returns true if and only if (iff) there is a valid path from each node to each
	 * other node. NOTE: assume directional graph (all n*(n-1) ordered pairs).
	 * @return
	 */
	@Override
	public boolean isConnected()
	{
		// TODO Auto-generated method stub
		if(this.DWGraph == null || this.DWGraph.nodeSize() <= 1)
			return true;
		if(this.DWGraph.nodeSize() > 1 && this.DWGraph.edgeSize() == 0)
			return false;

		Iterator<node_data> it = this.DWGraph.getV().iterator();

		directed_weighted_graph graphCopy1 = this.copy();
		directed_weighted_graph graphCopy2 = this.copy();

		bfs(it.next());
		int id = 0;
		int range = Integer.MAX_VALUE;


		for(node_data node : graphCopy1.getV())
		{
			if(node.getKey() == Integer.MAX_VALUE)
				return false;
			else
			{
				if(node.getTag() < range) {
					range = node.getTag();
					id = node.getKey();
				}
			}
		}

		bfs(graphCopy2.getNode(id));

		for(node_data node : graphCopy2.getV())
		{
			if(node.getKey() == Integer.MAX_VALUE)
				return false;
		}

		return true;
	}
	/**
	 * returns the length of the shortest path between src to dest
	 * Note: if no such path --> returns -1
	 * @param src - start node
	 * @param dest - end (target) node
	 * @return
	 */
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
	/**
	 * returns the the shortest path between src to dest - as an ordered List of nodes:
	 * src--> n1-->n2-->...dest
	 * see: https://en.wikipedia.org/wiki/Shortest_path_problem
	 * Note if no such path --> returns null;
	 * @param src - start node
	 * @param dest - end (target) node
	 * @return
	 */
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
	/**
	 * Saves this weighted (directed) graph to the given
	 * file name - in JSON format
	 * @param file - the file name (may include a relative path).
	 * @return true - iff the file was successfully saved
	 */
	@Override
	public boolean save(String file) {
		//Making json object
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(DWGraph);


		//write json to file
		try {
			PrintWriter pw = new PrintWriter(new File("file.json"));
			pw.write(json);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * This method load a graph to this graph algorithm.
	 * if the file was successfully loaded - the underlying graph
	 * of this class will be changed (to the loaded one), in case the
	 * graph was not loaded the original graph should remain "as is".
	 * @param file - file name of JSON file
	 * @return true - iff the graph was successfully loaded.
	 */
	@Override
	public boolean load(String file) {
		//Making json object
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(DWGraph_DS.class, new DW_GraphJsonDeserializer());
		Gson gson = builder.create();

		//read from json
		try {
			FileReader fr = new FileReader(file);
			directed_weighted_graph loadedGraph = gson.fromJson(fr, DWGraph_DS.class);
			this.init(loadedGraph);
		}
		catch (IOException e) {
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
