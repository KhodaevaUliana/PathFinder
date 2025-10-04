package com.example.PathFinder.repository;

import com.example.PathFinder.model.Edge;
import com.example.PathFinder.model.Graph;
import com.example.PathFinder.model.Node;
import com.example.PathFinder.util.OsmParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Repository
@Profile({"dev", "test"})
public class OsmGraphRepositoryImplDev implements GraphRepository{

    @Value("${osm.graph.file:/src/main/resources/munich-graph.json}")
    private String graphFile;
    private Graph graph;

    @PostConstruct
    private void init() {
        this.loadGraph();
    }

    private void loadGraph() {
        try {
            File inputFile = new File(graphFile);
            if (!inputFile.exists()) {
                throw new Exception("file not found");
            }
            ObjectMapper objectMapper = new ObjectMapper();
            HashMap<String, List<Edge>> rawAdjacencyList = objectMapper.readValue(inputFile, new TypeReference<HashMap<String, List<Edge>>>() {});

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Node parseNodeFromString(String serializedNode) {
        //serializedNode looks like
        // "Node{id=361030269, latitude=48.4145053, longitude=11.719848200000001}"
        serializedNode.replace("}", "");
        //now we have "Node{id=361030269, latitude=48.4145053, longitude=11.719848200000001"
        String[] chunksBeforeSpaces = serializedNode.split(", ");
        //we have ["Node{id=361030269", "latitude=48.4145053", "longitude=11.719848200000001"]
        long id = Long.parseLong(chunksBeforeSpaces[0].split("=")[1]);
        double latitude = Double.parseDouble(chunksBeforeSpaces[1].split("=")[1]);
        double longitude = Double.parseDouble(chunksBeforeSpaces[2].split("=")[1]);
        return new Node(id, latitude, longitude);

    }

    @Override
    public Graph getGraph() {
        return this.graph;
    }
}
