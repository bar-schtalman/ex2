package api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DWGraph_AlgoTest {
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
    void isConnected(){

    }
    @Test
    void shortestPathDist(){

    }
    @Test
    void shortestPath(){

    }
}
