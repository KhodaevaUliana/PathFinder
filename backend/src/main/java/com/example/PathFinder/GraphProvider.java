package com.example.PathFinder;

public class GraphProvider {
    private Graph graph;

    public GraphProvider(String path) {
        OsmParser parser = new OsmParser(path);
        Graph graph = new Graph(parser.getAdjacencyList());
    }

    public Graph getGraph() {
        return graph;
    }
}
