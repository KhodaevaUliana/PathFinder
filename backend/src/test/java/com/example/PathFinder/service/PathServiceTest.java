package com.example.PathFinder.service;

import com.example.PathFinder.model.Edge;
import com.example.PathFinder.model.Graph;
import com.example.PathFinder.model.Node;
import com.example.PathFinder.model.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PathServiceTest {
    private PathService pathService;

    @Mock
    private GraphService graphService;

    //Nodes for our Mock Graph
    private Node marienplatz = new Node(1, 48.1371, 11.5754);
    private Node chinesischerTurm = new Node(2, 48.1585, 11.6035);
    private Node flughafen = new Node(3, 48.3538, 11.7861);
    private Node nearMarienplatz = new Node(4, 48.137110, 11.575410);
    private Node freising = new Node(5, 48.39515, 11.74457);
    private Node neufahrn = new Node(6, 48.32131, 11.66068);

    @BeforeEach
    public void setUp() {
        HashMap<Node, List<Edge>> adjacencyList = new HashMap<>();
        //central connected component
        adjacencyList.put(marienplatz, List.of(new Edge(marienplatz, nearMarienplatz),
                new Edge(marienplatz, chinesischerTurm), new Edge(marienplatz, flughafen)));
        adjacencyList.put(chinesischerTurm, List.of(new Edge(chinesischerTurm, marienplatz)));
        adjacencyList.put(flughafen, List.of(new Edge(flughafen, marienplatz)));
        adjacencyList.put(nearMarienplatz, List.of(new Edge(nearMarienplatz, marienplatz)));

        //another remote connected component
        adjacencyList.put(freising, List.of(new Edge(freising, neufahrn)));
        adjacencyList.put(neufahrn, List.of(new Edge(neufahrn, freising)));
        Graph mockGraph = new Graph(adjacencyList);

        lenient().when(graphService.getGraph()).thenReturn(mockGraph); //replacing an actual huge graph with a small mock
        when(graphService.findNearestNode(48.137110, 11.575410))
                .thenReturn(nearMarienplatz);
        when(graphService.findNearestNode(48.1585, 11.6035))
                .thenReturn(chinesischerTurm);
        //when(graphService.findNearestNode(48.39515, 11.74457))
           //     .thenReturn(freising);
        pathService = new PathService(graphService);
    }

    @Test
    public void shortestPathBetweenTwoPointsReturnsPath() {
        //between "near Marienplatz" and Chinesischer Turm
        Path returnedPath = pathService.shortestPathBetweenTwoPoints(48.137110, 11.575410,
                    48.1585, 11.6035);

        Path expectedPath = new Path(List.of(nearMarienplatz, marienplatz, chinesischerTurm), 3168.53);
        assertTrue(returnedPath.equals(expectedPath));

    }
}
