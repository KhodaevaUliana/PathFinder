package com.example.PathFinder.model;

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




}
