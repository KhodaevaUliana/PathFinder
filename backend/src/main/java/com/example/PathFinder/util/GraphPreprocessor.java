package com.example.PathFinder.util;

import com.example.PathFinder.model.Edge;
import com.example.PathFinder.model.Node;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class GraphPreprocessor {
    //we run it locally only once, so that we don't need to parse a huge osm file every time
    public static void main(String[] args) throws Exception {
        try {
            if (args.length < 2) {
                System.out.println("Two arguments are needed, for instance '/src/main/resources/Muenchen.osm.pbf /src/main/resources/munich-graph.json'");
                System.exit(1);
            }

            String inputPath = args[0];
            String outputPath = args[1];

            //parse osm file here
            OsmParser osmParser = new OsmParser(inputPath);
            //extract the adjacency list
            HashMap<Node, List<Edge>> graphAdjacencyList = osmParser.getAdjacencyList();
            //serialize adjacency list
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File(outputPath), graphAdjacencyList);
        } catch (Error err) {
            System.out.println(err.getMessage());
        }
    }

}
