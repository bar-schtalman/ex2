package tests;

import api.DWGraph_DS;
import api.Node;
import api.directed_weighted_graph;
import api.node_data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class DWGraph_DSTest {
    private static Random _rnd = null;
    private directed_weighted_graph g;

    @BeforeEach
    void generateGraph() {
        g = graph_creator(10, 0,1);
    }
    @Test
    void getEdge(){

        g.connect(0,1,4);
        g.connect(0,2,5);
        g.connect(0,2,6);
        g.connect(1,0,21);
        g.connect(2,1,20);
        g.connect(2,0,7);
        assertEquals(6,g.getEdge(0,2).getWeight());
        assertEquals(21,g.getEdge(1,0).getWeight());
        assertEquals(4,g.getEdge(0,1).getWeight());
    }
    @Test
        void addNode() {
            assertEquals(g.nodeSize(), 10);
            g = graph_creator(120, 64, 1);
            assertEquals(g.nodeSize(), 120);
            assertEquals(g.edgeSize(), 64);
        }

    @Test
    void connect(){
        assertEquals(0,g.edgeSize());
        g.connect(0,1,55);
        assertEquals(1,g.edgeSize());
        g.connect(2,3,12);
        assertEquals(2,g.edgeSize());
        g.connect(2,3,11);
        assertEquals(2,g.edgeSize());
    }
//    @Test
//    void removeNode(){
//        g.removeNode(0);
//        g.removeNode(1);
//        g.removeNode(2);
//        assertEquals(7,g.nodeSize());
//        for(node_data node : g.getV()){
//            g.
//        }
//        assertEquals(0,g.nodeSize());
//    }
    @Test
    void removeEdge(){
        g.connect(0,1,1);
        g.connect(0,2,2);
        g.connect(1,5,2);
        g.connect(0,1,3);
        g.removeEdge(0,1);
        assertEquals(2,g.edgeSize());
        g.connect(1,4,6);
        g.removeEdge(0,2);
        g.removeEdge(1,5);
        assertEquals(1,g.edgeSize());
    }



    private static int nextRnd(int min, int max) {
        double v = nextRnd(0.0 + min, (double) max);
        int ans = (int) v;
        return ans;
    }

    private static double nextRnd(double min, double max) {
        double d = _rnd.nextDouble();
        double dx = max - min;
        double ans = d * dx + min;
        return ans;
    }
    private static int[] nodes(directed_weighted_graph g) {
        int size = g.nodeSize();
        Collection<node_data> V = g.getV();
        node_data[] nodes = new node_data[size];
        V.toArray(nodes); // O(n) operation
        int[] ans = new int[size];
        for (int i = 0; i < size; i++) {
            ans[i] = nodes[i].getKey();
        }
        Arrays.sort(ans);
        return ans;
    }


    public static directed_weighted_graph graph_creator(int v_size, int e_size, int seed) {
        directed_weighted_graph g = new DWGraph_DS();
        _rnd = new Random(seed);
        for (int i = 0; i < v_size; i++) {
            g.addNode(new Node(i));
        }
        // Iterator<node_data> itr = V.iterator(); // Iterator is a more elegant and generic way, but KIS is more important
        int[] nodes = nodes(g);
        while (g.edgeSize() < e_size) {
            int a = nextRnd(0, v_size);
            int b = nextRnd(0, v_size);
            int i = nodes[a];
            int j = nodes[b];
            g.connect(i, j, _rnd.nextDouble());
        }
        return g;
    }
}
