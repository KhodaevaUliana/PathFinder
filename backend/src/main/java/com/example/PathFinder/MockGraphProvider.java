package com.example.PathFinder;

import java.util.HashMap;
import java.util.List;

public class MockGraphProvider {
    public static Graph createSampleGraph() {
        Node A = new Node("A", 50.0, 10.0);
        Node B = new Node("B", 50.0, 12.0);
        Node C = new Node("C", 49.0, 10.0);
        Node D = new Node("D", 50.0, 10.0);
        Node E = new Node("E", 48.0, 13.0);
        Node F = new Node("F", 30.0, 50.0);
        Node G = new Node("G", 30.0, 48.0);
        HashMap<Node, List<Edge>> adjacencyList = new HashMap<>();

        adjacencyList.put(A, List.of(new Edge(A, B, 7), new Edge(A, E, 1)));
        adjacencyList.put(B, List.of(new Edge(B, A, 7), new Edge(B, E, 8),
                new Edge(B, C, 3)));
        adjacencyList.put(C, List.of(new Edge(C, B, 3), new Edge(C, D, 6),
                new Edge(C, E, 2)));
        adjacencyList.put(D, List.of(new Edge(D, C, 6), new Edge(D, E, 7)));
        adjacencyList.put(E, List.of(new Edge(E, A, 1), new Edge(E, B, 8),
                new Edge(E, C, 2), new Edge(E, D, 7)));

        //second connected component
        adjacencyList.put(F, List.of(new Edge(F, G, 10)));
        adjacencyList.put(G, List.of(new Edge(G, F, 10)));
        return new Graph(adjacencyList);
    }
}
