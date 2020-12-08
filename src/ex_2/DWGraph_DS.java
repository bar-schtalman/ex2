package ex_2;

import java.util.Collection;
import java.util.HashMap;

public class DWGraph_DS implements directed_weighted_graph{
    private HashMap<Integer,node_data> hash;
    private HashMap<Integer,HashMap<Integer,edge_data>> hashEdge;
    private int nodeSize;
    private int edgeSize;
    private int mc;

    public DWGraph_DS(){
        this.hash=new HashMap<Integer,node_data>();
        this.nodeSize=0;
        this.edgeSize=0;
        this.mc=0;
        this.hashEdge= new HashMap <Integer,HashMap<Integer,edge_data>> ();
    }
    public DWGraph_DS(DWGraph_DS graph){
        this.hash = new HashMap<Integer, node_data>();
        this.hashEdge= new HashMap <Integer,HashMap<Integer,edge_data>> ();
        for (node_data temp : graph.getV()) {
            this.hash.put(temp.getKey(), temp);
        }
        this.mc = graph.mc;
        this.nodeSize = graph.nodeSize();
        this.edgeSize = graph.edgeSize;

    }
    @Override
    public node_data getNode(int key) {
        if (hash.containsKey(key))
            return hash.get(key);
         return null;
    }

    @Override
    public edge_data getEdge(int src, int dest) {
        if (hash.containsKey(src)&&hash.containsKey(dest)){
            if(hashEdge.get(src).containsKey(dest))
             return hashEdge.get(src).get(dest);
        }
        return null;
    }

    @Override
    public void addNode(node_data n) {
        if (this.hash.containsKey(n.getKey()))
            return;
        this.hash.put(n.getKey(),n);
    }
    @Override
    public void connect(int src, int dest, double w) {
        if (hash.containsKey(src)&&hash.containsKey(dest)){
            if(hashEdge.get(src).containsKey(dest))
                return;
            hashEdge.put(src).put(dest,)
    }

    @Override
    public Collection<node_data> getV() {
        return null;
    }

    @Override
    public Collection<edge_data> getE(int node_id) {
        return ;
    }

    @Override
    public node_data removeNode(int key) {
        return null;
    }

    @Override
    public edge_data removeEdge(int src, int dest) {
        return null;
    }

    @Override
    public int nodeSize() {
        return 0;
    }

    @Override
    public int edgeSize() {
        return 0;
    }

    @Override
    public int getMC() {
        return 0;
    }

}
