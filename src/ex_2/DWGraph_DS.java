package ex_2;

import java.util.Collection;
import java.util.HashMap;

public class DWGraph_DS implements directed_weighted_graph{
    private HashMap<Integer,node_data> hashNode;
    private HashMap<Integer,HashMap<Integer,edge_data>> hashEdgeIn;
    private HashMap<Integer,HashMap<Integer,edge_data>> hashEdgeOut;
    private int nodeSize;
    private int edgeSize;
    private int mc;

    public DWGraph_DS(){
        this.hashNode = new HashMap<Integer,node_data>();
        this.nodeSize = 0;
        this.edgeSize = 0;
        this.mc = 0;
        this.hashEdgeIn = new HashMap <Integer,HashMap<Integer,edge_data>> ();
    }
    public DWGraph_DS(DWGraph_DS graph){
        this.hashNode = new HashMap<Integer, node_data>();
        this.hashEdgeIn = new HashMap <Integer,HashMap<Integer,edge_data>> ();
        for (node_data temp : graph.getV()) {
            this.hashNode.put(temp.getKey(), temp);
        }
        this.mc = graph.mc;
        this.nodeSize = graph.nodeSize();
        this.edgeSize = graph.edgeSize;

    }
    @Override
    public node_data getNode(int key) {
        if (hashNode.containsKey(key))
            return hashNode.get(key);
         return null;
    }

    @Override
    public edge_data getEdge(int src, int dest) {
        if(src == dest)return null;
        if (hashNode.containsKey(src) && hashNode.containsKey(dest)){
            if(hashEdgeIn.get(src).containsKey(dest))
             return hashEdgeIn.get(src).get(dest);
        }
        return null;
    }

    @Override
    public void addNode(node_data n) {
        if (this.hashNode.containsKey(n.getKey()))
            return;
        this.hashNode.put(n.getKey(),n);
        this.nodeSize++;
        this.mc++;
    }
    @Override
    public void connect(int src, int dest, double w) {
        if (hashNode.containsKey(src) && hashNode.containsKey(dest) && src!=dest) {
            if (hashEdgeIn.get(src).containsKey(dest))
                return;
            node_data s = hashNode.get(src);
            node_data d = hashNode.get(dest);
            Edge e = new Edge(s, d, w);
            hashEdgeIn.get(src).put(dest, e);
            hashEdgeOut.get(dest).put(src,e);
            s.setWeight(s.getWeight()+1);
            this.edgeSize++;
            this.mc++;
        }
    }
    @Override
    public Collection<node_data> getV() {
        return hashNode.values();
    }

    @Override
    public Collection<edge_data> getE(int node_id) {
        return hashEdgeIn.get(node_id).values();
    }

    @Override
    public node_data removeNode(int key) {
        if(!this.hashNode.containsKey(key)) return null;
            this.edgeSize-=hashNode.get(key).getWeight();
            this.hashEdgeIn.remove(key);
            for(edge_data temp : hashEdgeOut.get(key).values()){
               this.hashEdgeOut.get(key).remove(temp.getSrc());
               this.hashEdgeIn.get(temp.getSrc()).remove(key);
               this.edgeSize--;
               this.mc++;
            }
            this.nodeSize--;
            this.mc++;
        this.hashEdgeOut.remove(key);
        return hashNode.remove(key);
    }

    @Override
    public edge_data removeEdge(int src, int dest) {
        if(getEdge(src,dest)!=null){
            this.edgeSize--;
            this.mc++;
            return hashEdgeIn.get(src).remove(dest);
        }
        return null;
    }

    @Override
    public int nodeSize() {
        return this.nodeSize;
    }

    @Override
    public int edgeSize() {
        return this.edgeSize;
    }

    @Override
    public int getMC() {
        return this.mc;
    }

}
