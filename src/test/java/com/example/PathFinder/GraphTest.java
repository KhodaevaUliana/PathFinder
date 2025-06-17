package com.example.PathFinder;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

public class GraphTest {
    private Graph graph;
    private Node A, B, C, D, E, F, G;

    @BeforeEach
    public void setUp() {
        A = new Node("A", 50.0, 10.0);
        B = new Node("B", 50.0, 12.0);
        C = new Node("C", 49.0, 10.0);
        D = new Node("D", 50.0, 10.0);
        E = new Node("E", 48.0, 13.0);
        F = new Node("F", 30.0, 50.0);
        G = new Node("G", 30.0, 48.0);
        graph = MockGraphProvider.createSampleGraph();
    }

    @Test
    public void shortestPathAB() {
        Path result = null;
        try {
            result = Graph.shortestPath(this.graph, "A", "B");
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
            result = Graph.shortestPath(this.graph, "A", "E");
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
            result = Graph.shortestPath(this.graph, "D", "A");
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
            result = Graph.shortestPath(this.graph, "E", "C");
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
            result = Graph.shortestPath(this.graph, "X", "A");
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
            result = Graph.shortestPath(this.graph, "F", "G");
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
            result = Graph.shortestPath(this.graph, "A", "G");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Path answer = new Path(new ArrayList<>(), Double.POSITIVE_INFINITY);
        assertEquals(result, answer);
    }
}
