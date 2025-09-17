package com.example.PathFinder.service;

import com.example.PathFinder.Graph;
import com.example.PathFinder.model.Node;
import com.example.PathFinder.model.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PathService {
    private final GraphService graphService;

    @Autowired
    public PathService(GraphService graphService) {
        this.graphService = graphService;
    }

    public Path shortestPathBetweenTwoPoints(double latitudeStart, double longitudeStart,
                                             double latitudeFinish, double longitudeFinish) throws IllegalArgumentException {
        //find the nearest node to the start
        Node startNode = graphService.findNearestNode(latitudeStart, longitudeStart);
        if (startNode == null) {
            throw new IllegalArgumentException("Start of the route is outside the map");
        }
        //find the nearest node to the finish
        Node finishNode = graphService.findNearestNode(latitudeFinish, longitudeFinish);
        if (finishNode == null) {
            throw new IllegalArgumentException("Finish of the route is outside the map");
        }

        //replace Dijkstra with A*!!!
        return Graph.shortestPath(graphService.getGraph(), startNode, finishNode);

    }
}
