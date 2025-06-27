package com.example.PathFinder;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;

import java.util.*;
import org.locationtech.jts.index.strtree.*;
import org.locationtech.jts.geom.Envelope;

@Service
public class GraphProvider {
    private Graph graph;
    private STRtree spatialIndex; //R-tree enabling a quick search of the nearest index

    @PostConstruct
    public void init() {
        try {
            java.nio.file.Path path = java.nio.file.Paths.get(Objects.requireNonNull(getClass()
                            .getClassLoader()
                            .getResource("monaco-latest.osm.pbf"))
                    .toURI());

            String filePath = path.toString();
            OsmParser parser = new OsmParser(filePath);
            this.graph = new Graph(parser.getAdjacencyList());
            buildSpatialIndex(parser.getNodes().values());
        } catch (URISyntaxException exception) {
            System.out.println(exception.getMessage());
        }

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
        double searchRadiusLatitude = 0.001; //~100 m
        //longitude search window depends on the latitude, but we also keep it ~100 m
        double searchRadiusLongitude = 0.001/Math.cos(Math.toRadians(0.5*(queryParamLatitude + queryParamLongitude)));

        Node nearestNode = null;
        double nearestDistance = Double.MAX_VALUE;

        //search area: a rectangle ~200m X 200m with the point in the query as the center
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
            double distance = Node.calculateDistanceBetweenTwoNodesInMeters(node, queryNode);
            if (distance < nearestDistance) {
                nearestNode = node;
                nearestDistance = distance;
            }
        }
        return nearestNode;


    }
}
