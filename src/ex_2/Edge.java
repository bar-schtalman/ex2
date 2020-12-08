package ex_2;

import java.util.HashMap;

public class Edge implements edge_data{
    private node_data src;
    private node_data dest;
    private String info;
    private double weight;
    private int tag;

    public Edge (int src, int dest, double w){
        this.src.;
        this.dest=null;
        this.info=null;
        this.weight=0;
        this.tag=-1;
    }
    public Edge(Edge edge){
        this.src=edge.src;
        this.dest=edge.dest;
        this.info=edge.info;
        this.weight=edge.weight;
        this.tag=edge.tag;
    }
    @Override
    public int getSrc() {
        return this.src.getKey();
    }

    @Override
    public int getDest() {
        return this.dest.getKey();
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String s) {
        this.info=s;
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag=t;
    }
}