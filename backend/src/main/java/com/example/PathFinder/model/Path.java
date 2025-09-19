package com.example.PathFinder.model;

import java.util.*;

public final class Path {
    private List<Node> nodes;
    private double distance;

    public Path(List<Node> nodes, double distance) {
        this.nodes = nodes;
        this.distance = distance;
    }

    public List<Node> getNodes() {
        return nodes;
    }


    public double getDistance() {
        return distance;
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
        double tolerance = 0.001; // 0.1%
        if (Math.abs(this.distance - path.distance) > tolerance * Math.max(this.distance, path.distance))
           return false;
        return Objects.equals(nodes, path.nodes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodes, distance);
    }
}
