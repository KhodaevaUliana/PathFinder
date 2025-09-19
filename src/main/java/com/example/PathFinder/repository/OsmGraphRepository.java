package com.example.PathFinder.repository;

import com.example.PathFinder.model.Graph;
import com.example.PathFinder.util.OsmParser;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.net.URISyntaxException;
import java.util.Objects;


@Repository
public class OsmGraphRepository implements GraphRepository{
    //private static final String OSM_FILE = "monaco-latest.osm.pbf";
    //private static final String OSM_FILE = "oberbayern-latest.osm.pbf";
    @Value("${osm.graph.file:Muenchen.osm.pbf}")
    private String osmFile;
    private Graph graph;



    @PostConstruct
    private void init() {
        this.loadGraph();
    }


    private void loadGraph() {
        try {
            java.nio.file.Path path = java.nio.file.Paths.get(Objects.requireNonNull(getClass()
                            .getClassLoader()
                            .getResource(osmFile))
                    .toURI());

            String filePath = path.toString();
            OsmParser parser = new OsmParser(filePath);
            this.graph = new Graph(parser.getAdjacencyList());
        } catch (URISyntaxException exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public Graph getGraph() {
        return this.graph;
    }
}
