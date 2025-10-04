package com.example.PathFinder.repository;

import com.example.PathFinder.model.Edge;
import com.example.PathFinder.model.Graph;
import com.example.PathFinder.model.Node;
import com.example.PathFinder.util.NodeUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;


@Repository
@Profile({"dev", "test"})
public class OsmGraphRepositoryImplDev implements GraphRepository{

    @Value("${osm.graph.file:munich-graph.json}")
    private String graphFile;
    private Graph graph;

    @PostConstruct
    private void init() {
        this.loadGraph();
    }

    private void loadGraph() {
        try {
            URL resource = getClass().getClassLoader().getResource(graphFile);
            if (resource == null) {
                throw new IllegalArgumentException("Graph file not found in classpath: " + graphFile);
            }
            File inputFile = new File(resource.toURI());
            if (!inputFile.exists()) {
                throw new Exception("file not found");
            }
            ObjectMapper objectMapper = new ObjectMapper();
            HashMap<String, List<Edge>> rawAdjacencyList = objectMapper.readValue(inputFile, new TypeReference<HashMap<String, List<Edge>>>() {});
            //now, let's deserialize nodes
            HashMap<Node, List<Edge>> adjacencyList = new HashMap<>();
            for (String nodeStr : rawAdjacencyList.keySet()) {
                Node node = NodeUtils.parseNodeFromString(nodeStr);
                adjacencyList.put(node, rawAdjacencyList.get(nodeStr));
            }

            this.graph = new Graph(adjacencyList);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load graph from JSON: " + graphFile, e);
        }

    }



    @Override
    public Graph getGraph() {
        return this.graph;
    }
}
