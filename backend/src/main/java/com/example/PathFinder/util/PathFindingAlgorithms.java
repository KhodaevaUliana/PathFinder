package com.example.PathFinder.util;

import com.example.PathFinder.model.Graph;
import com.example.PathFinder.model.Edge;
import com.example.PathFinder.model.Node;
import com.example.PathFinder.model.Path;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public final class PathFindingAlgorithms {

    public static Path shortestPathDijkstra (Graph graph, Node startNode, Node finishNode)  {
        HashMap<Node, Double> distances = new HashMap<>();

        distances.put(startNode, 0.0);

        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingDouble(distances::get));
        HashMap<Node, Node> previous = new HashMap<>();
        queue.add(startNode);


        //Dijkstra algorithm
        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            if (currentNode.equals(finishNode)) {
                break;
            }
            for (Edge edge : graph.getAdjacencyList().get(currentNode)) {
                Node neighbour = edge.getTo();
                double newDistance = distances.get(currentNode) + edge.getDistance();
                if (newDistance < distances.getOrDefault(neighbour, Double.POSITIVE_INFINITY)) {
                    distances.put(neighbour, newDistance);
                    previous.put(neighbour, currentNode);
                    queue.add(neighbour);
                }
            }
        }
        if (distances.getOrDefault(finishNode, Double.POSITIVE_INFINITY) == Double.POSITIVE_INFINITY) {
            return new Path(new ArrayList<>(), Double.POSITIVE_INFINITY);
        }

        //trace the path back
        ArrayList<Node> path = new ArrayList<>();
        path.add(finishNode);
        Node currNode = finishNode;
        while (!currNode.equals(startNode)) {
            Node nextNode = previous.get(currNode);
            path.addFirst(nextNode);
            currNode = nextNode;
        }
        return new Path(path, distances.get(finishNode));


    }
}
