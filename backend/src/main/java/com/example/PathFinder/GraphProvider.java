package com.example.PathFinder;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;

import java.util.*;

@Service
public class GraphProvider {
    private Graph graph;

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
        } catch (URISyntaxException exception) {
            System.out.println(exception.getMessage());
        }

    }

    public Graph getGraph() {
        return graph;
    }
}
