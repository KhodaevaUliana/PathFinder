package com.example.PathFinder;

import java.util.Objects;

public class Node {
    private String id;
    private double latitude;
    private double longitude;

    public Node(String id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public static double calculateDistanceBetweenTwoNodesInMeters(Node firstNode, Node secondNode) {
        double deltaLatitude = (firstNode.getLatitude() - secondNode.getLatitude()) * 111320;
        double deltaLongitude = (firstNode.getLongitude() - secondNode.getLongitude())
                * 111320 * Math.cos(Math.toRadians(0.5*(firstNode.getLatitude() + secondNode.getLatitude())));
        return Math.sqrt(deltaLatitude * deltaLatitude + deltaLongitude * deltaLongitude);
    }

    @Override
    public String toString() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Node node)) return false;
        return Double.compare(latitude, node.latitude) == 0 && Double.compare(longitude, node.longitude) == 0 && Objects.equals(id, node.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, latitude, longitude);
    }
}
