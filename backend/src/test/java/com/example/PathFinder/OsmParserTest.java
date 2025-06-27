package com.example.PathFinder;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

public class OsmParserTest {
    private final String path = "src/main/resources/monaco-latest.osm.pbf";
    private final OsmParser parser = new OsmParser(path);;


    @Test
    public void getNodes() {
        int numOfNodes = parser.getNodes().size();
        System.out.println("Number of nodes: " + numOfNodes);
        assertEquals(true, numOfNodes > 0);
    }

   @Test
    public void getMap() {
        HashMap<Node, List<Edge>> adjacencyList = parser.getAdjacencyList();
        System.out.println("Number of nodes in adjacency list: " + adjacencyList.size());
        int numOfEdges = adjacencyList.keySet().stream().
                map(el -> adjacencyList.get(el).size()).reduce(0, Integer::sum);
        System.out.println("Number of edges: " + numOfEdges);
        assertEquals(true, numOfEdges > 0);

    }



}
