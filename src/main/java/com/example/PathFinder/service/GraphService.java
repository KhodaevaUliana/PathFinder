package com.example.PathFinder.service;

import com.example.PathFinder.model.Graph;
import com.example.PathFinder.model.Node;
import com.example.PathFinder.repository.GraphRepository;
import com.example.PathFinder.util.DistanceCalculator;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.index.strtree.STRtree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class GraphService {
    private final GraphRepository graphRepository;
    private final Graph graph;
    private STRtree spatialIndex; //R-tree enabling a quick search of the nearest index

    @Autowired
    public GraphService(GraphRepository graphRepository) {
        this.graphRepository = graphRepository;
        this.graph = this.graphRepository.getGraph();
        buildSpatialIndex(this.graph.getAdjacencyList().keySet());
    }

    public Graph getGraph() {
        return graph;
    }

    private void buildSpatialIndex(Collection<Node> nodes) {
        STRtree stRtree = new STRtree();
        for (Node node : nodes) {
            double latitude = node.getLatitude();
            double longitude = node.getLongitude();

            Envelope envelope = new Envelope(longitude, longitude, latitude, latitude);
            stRtree.insert(envelope, node);
        }
        stRtree.build();
        this.spatialIndex = stRtree;
    }

    public Node findNearestNode(double queryParamLatitude, double queryParamLongitude) {
        double searchRadiusLatitude = 0.0005; //~50 m
        //longitude search window depends on the latitude, but we also keep it ~50 m
        double searchRadiusLongitude = 0.0005/Math.cos(Math.toRadians(0.5*(queryParamLatitude + queryParamLongitude)));

        Node nearestNode = null;
        double nearestDistance = Double.MAX_VALUE;

        //search area: a rectangle ~100m X 100m with the point in the query as the center
        Envelope searchEnvelope = new Envelope(queryParamLongitude - searchRadiusLongitude,
                queryParamLongitude + searchRadiusLongitude, queryParamLatitude - searchRadiusLatitude,
                queryParamLatitude + searchRadiusLatitude);

        //select nodes inside the search area
        @SuppressWarnings("unchecked")
        List<Node> candidates = spatialIndex.query(searchEnvelope);
        if (candidates.isEmpty()) {
            //it means that there no nodes in the 100m-vicinity of the point in the query
            return null;
        }

        //create a node object from query parameters
        Node queryNode = new Node("0", queryParamLatitude, queryParamLongitude);

        for (Node node : candidates) {
            double distance = DistanceCalculator.calculateDistanceBetweenTwoNodesInMeters(node, queryNode);
            if (distance < nearestDistance) {
                nearestNode = node;
                nearestDistance = distance;
            }
        }
        return nearestNode;

    }


}
