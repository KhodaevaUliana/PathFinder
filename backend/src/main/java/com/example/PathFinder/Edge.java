package com.example.PathFinder;

public class Edge {
    private Node from;
    private Node to;
    private double distance;

    private double findDistance() {
        return Node.calculateDistanceBetweenTwoNodesInMeters(this.from, this.to);
    }

    public Edge (Node from, Node to) {
        this.from = from;
        this.to = to;
        this.distance = this.findDistance();
    }

    public Edge (Node from, Node to, double distance) {
        this.from = from;
        this.to = to;
        this.distance = distance;
    }



    public Node getFrom() {
        return from;
    }

    public void setFrom(Node from) {
        this.from = from;
    }

    public Node getTo() {
        return to;
    }

    public void setTo(Node to) {
        this.to = to;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "from=" + from +
                ", to=" + to +
                ", distance=" + distance +
                '}';
    }
}
