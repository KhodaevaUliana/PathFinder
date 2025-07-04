package com.example.PathFinder;

import java.util.*;

public class Path {
    private List<Node> nodes;
    private double distance;

    public Path(List<Node> nodes, double distance) {
        this.nodes = nodes;
        this.distance = distance;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Path{" +
                "nodes=" + nodes +
                ", distance=" + distance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Path path)) return false;
        return Double.compare(distance, path.distance) == 0 && Objects.equals(nodes, path.nodes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodes, distance);
    }
}
