package com.example.PathFinder;

import java.util.*;

public class Graph {
    private HashMap<Node, List<Edge>> adjacencyList;

    public Graph (HashMap<Node, List<Edge>> adjacencyList) {
        this.adjacencyList = adjacencyList;
    }


    public HashMap<Node, List<Edge>> getAdjacencyList() {
        return adjacencyList;
    }

    public void setAdjacencyList(HashMap<Node, List<Edge>> adjacencyList) {
        this.adjacencyList = adjacencyList;
    }

    public static Path shortestPath (Graph graph, long startNodeID, long finishNodeID) throws IllegalArgumentException {
        Set<Node> nodes = graph.getAdjacencyList().keySet();
        HashMap<Node, Double> distances = new HashMap<>();
        Node startNode = null;
        Node finishNode = null;
        for (Node node : nodes) {
            distances.put(node, Double.POSITIVE_INFINITY);
            if (node.getId() == startNodeID) {
                startNode = node;
            }
            if (node.getId() == finishNodeID) {
                finishNode = node;
            }
        }
        if (startNode == null) {
            throw new IllegalArgumentException("incorrect start node ID");
        }
        if (finishNode == null) {
            throw new IllegalArgumentException("incorrect finish node ID");
        }
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
                if (newDistance < distances.get(neighbour)) {
                    distances.put(neighbour, newDistance);
                    previous.put(neighbour, currentNode);
                    queue.add(neighbour);
                }
            }
        }
        if (distances.get(finishNode) == Double.POSITIVE_INFINITY) {
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

    public static Path shortestPath (Graph graph, String startNodeIDString, String finishNodeIDString) throws IllegalArgumentException {
        long startNodeID = Long.parseLong(startNodeIDString);
        long finishNodeID = Long.parseLong(finishNodeIDString);
        return shortestPath(graph, startNodeID, finishNodeID);
    }



}
