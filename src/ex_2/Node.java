package ex_2;

import java.util.Collection;
import java.util.HashMap;

public class Node implements node_data{
    private int key;
    private geo_location geo;
    private double weight;
    private int tag;
    private String info;
    private HashMap <Integer,edge_data> hashE;
    public Node(){
        this.key=0;
        this.geo=null;
        this.weight=0;
        this.tag=-1;
        this.info="white";
        this.hashE=new HashMap<Integer,edge_data>();
    }
    public Node (Node node){
        this.key=node.key;
        this.geo=node.geo;
        this.weight=node.weight;
        this.tag=node.tag;
        this.info= node.info;
        this.hashE=new HashMap<Integer,edge_data>();
    }
    public HashMap getHash(){
        return this.hashE;
    }
    @Override
    public int getKey() {
        return this.key;
    }

    @Override
    public geo_location getLocation() {
        return this.geo;
    }

    @Override
    public void setLocation(geo_location p) {
        this.geo=p;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public void setWeight(double w) {
        this.weight=w;
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