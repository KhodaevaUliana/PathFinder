package com.example.PathFinder.util;

import com.example.PathFinder.model.Edge;
import com.example.PathFinder.model.Node;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class OsmParserTest {
    private final String path = "src/test/resources/test-map.osm.pbf";
    private final OsmParser parser = new OsmParser(path);;


    @Test
    public void getNodes() {
        int numOfNodes = parser.getNodes().size();
        System.out.println("Number of nodes: " + numOfNodes);
        assertTrue(numOfNodes > 0);
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
