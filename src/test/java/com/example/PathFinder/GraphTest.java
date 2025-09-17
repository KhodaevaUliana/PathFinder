package com.example.PathFinder;

import com.example.PathFinder.model.Node;
import com.example.PathFinder.model.Path;
import com.example.PathFinder.util.MockGraphProvider;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

public class GraphTest {
    private Graph graph;
    private Node A, B, C, D, E, F, G;

    @BeforeEach
    public void setUp() {
        A = new Node("1", 50.0, 10.0);
        B = new Node("2", 50.0, 12.0);
        C = new Node("3", 49.0, 10.0);
        D = new Node("4", 50.0, 10.0);
        E = new Node("5", 48.0, 13.0);
        F = new Node("6", 30.0, 50.0);
        G = new Node("7", 30.0, 48.0);
        graph = MockGraphProvider.createSampleGraph();
    }

    @Test
    public void shortestPathAB() {
        Path result = null;
        try {
            result = Graph.shortestPath(this.graph, "1", "2");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Path answer = new Path(List.of(A, E, C, B), 6);
        assertEquals(result, answer);
    }

    @Test
    public void shortestPathAE() {
        Path result = null;
        try {
            result = Graph.shortestPath(this.graph, "1", "5");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Path answer = new Path(List.of(A, E), 1);
        assertEquals(result, answer);
    }

    @Test
    public void shortestPathDA() {
        Path result = null;
        try {
            result = Graph.shortestPath(this.graph, "4", "1");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Path answer = new Path(List.of(D, E, A), 8);
        assertEquals(result, answer);
    }

    @Test
    public void shortestPathEC() {
        Path result = null;
        try {
            result = Graph.shortestPath(this.graph, "5", "3");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Path answer = new Path(List.of(E, C), 2);
        assertEquals(result, answer);
    }

    @Test
    public void shortestPathFail() {
        Path result = null;
        try {
            result = Graph.shortestPath(this.graph, "12", "1");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Path answer = null;
        assertEquals(result, answer);
    }

    @Test
    public void shortestPathFG() {
        Path result = null;
        try {
            result = Graph.shortestPath(this.graph, "6", "7");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Path answer = new Path(List.of(F, G), 10);
        assertEquals(result, answer);
    }

    @Test
    public void shortestPathAG() {
        Path result = null;
        try {
            result = Graph.shortestPath(this.graph, "1", "7");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Path answer = new Path(new ArrayList<>(), Double.POSITIVE_INFINITY);
        assertEquals(result, answer);
    }
}
