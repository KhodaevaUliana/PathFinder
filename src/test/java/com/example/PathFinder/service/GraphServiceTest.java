package com.example.PathFinder.service;

import com.example.PathFinder.model.Edge;
import com.example.PathFinder.model.Graph;
import com.example.PathFinder.model.Node;
import com.example.PathFinder.repository.GraphRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(MockitoExtension.class)
public class GraphServiceTest {
    @Mock
    private GraphRepository graphRepository;

    private GraphService graphService;

    //Nodes for our Mock Graph
    private Node marienplatz = new Node(1, 48.1371, 11.5754);
    private Node chinesischerTurm = new Node(2, 48.1585, 11.6035);
    private Node flughafen = new Node(2, 48.3538, 11.7861);
    private Node nearMarienplatz = new Node(2, 48.137110, 11.575410);

    @BeforeEach
    public void setUp() {
        HashMap<Node, List<Edge>> adjacencyList = new HashMap<>();
        adjacencyList.put(marienplatz, List.of(new Edge(marienplatz, nearMarienplatz),
                new Edge(marienplatz, chinesischerTurm), new Edge(marienplatz, flughafen)));
        adjacencyList.put(chinesischerTurm, List.of(new Edge(chinesischerTurm, marienplatz)));
        adjacencyList.put(flughafen, List.of(new Edge(flughafen, marienplatz)));
        adjacencyList.put(nearMarienplatz, List.of(new Edge(nearMarienplatz, marienplatz)));

        Graph mockGraph = new Graph(adjacencyList);

        when(graphRepository.getGraph()).thenReturn(mockGraph); //replacing an actual huge graph with a small mock

        graphService = new GraphService(graphRepository);

    }

    @Test
    public void getGraphReturnsIntendedGraph() {
        Graph returnedGraph = graphService.getGraph();
        assertTrue(returnedGraph.getAdjacencyList().containsKey(marienplatz));
        assertTrue(returnedGraph.getAdjacencyList().containsKey(flughafen));
        assertTrue(returnedGraph.getAdjacencyList().containsValue(List.of(new Edge(flughafen, marienplatz))));
        assertTrue(!returnedGraph.getAdjacencyList().containsValue(List.of(new Edge(flughafen, nearMarienplatz))));
    }


}
