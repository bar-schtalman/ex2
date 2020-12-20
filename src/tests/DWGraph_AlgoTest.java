package tests;

import api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


public class DWGraph_AlgoTest {
    private static Random _rnd = null;

    private directed_weighted_graph g;
    private dw_graph_algorithms g2;
    @BeforeEach
    void Build() {
        g =new DWGraph_DS();
        for (int i=0;i<10;i++){
            node_data n = new Node(i);
            g.addNode(n);
        }
        g.connect(0,1,1);
        g.connect(0,8,2);
        g.connect(9,0,1);
        g.connect(1,5,25);
        g.connect(1,4,3);
        g.connect(4,3,1);
        g.connect(2,4,1.5);
        g.connect(3,2,0.5);
        g.connect(7,6,1);
        g.connect(6,7,1);
        g.connect(5,7,4);
        g.connect(8,5,3);
        g.connect(2,1,5);
        g.connect(7,8,2.5);
        g.connect(5,9,1.5);
        g2=new DWGraph_Algo();
        g2.init(g);
    }

    @Test
    void init(){
        directed_weighted_graph g = new DWGraph_DS();
        dw_graph_algorithms ga = new DWGraph_Algo();
        ga.init(g);
        assertEquals(g, new DWGraph_DS());//not working
        g.addNode(new Node(2));
        assertNotEquals(g, new DWGraph_DS());
        assertEquals(g, ga.getGraph());
    }
    @Test
    void copy(){
        directed_weighted_graph g = new DWGraph_DS();
        dw_graph_algorithms ga = new DWGraph_Algo();

    }

    @Test
    void saveLoad() {
        assertTrue(g2.save("graph_test.json"));
        assertTrue(g2.load("graph_test.json"));
        directed_weighted_graph loadGraph =g2.getGraph();
        assertEquals(g,loadGraph);
        int rnd = (int) (Math.random()*10);
        g.removeNode(rnd);
        assertNotEquals(g,loadGraph);
    }

}
