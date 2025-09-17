package com.example.PathFinder.util;

import com.example.PathFinder.Edge;
import com.example.PathFinder.Graph;
import com.example.PathFinder.Node;

import java.util.HashMap;
import java.util.List;

public class MockGraphProvider {
    public static Graph createSampleGraph() {
        Node A = new Node("1", 50.0, 10.0);
        Node B = new Node("2", 50.0, 12.0);
        Node C = new Node("3", 49.0, 10.0);
        Node D = new Node("4", 50.0, 10.0);
        Node E = new Node("5", 48.0, 13.0);
        Node F = new Node("6", 30.0, 50.0);
        Node G = new Node("7", 30.0, 48.0);
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
