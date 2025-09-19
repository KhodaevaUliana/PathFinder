package com.example.PathFinder.model;

import java.util.Objects;

public class Node {
    private long id;
    private double latitude;
    private double longitude;

    public Node() {

    }

    public Node(long id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Node(String id, double latitude, double longitude) {
        this.id = Long.parseLong(id);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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


    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    private double round6(double value) {
        return Math.round(value * 1_000_000.0) / 1_000_000.0;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Node node)) return false;

        return Objects.equals(id, node.id)
                && round6(latitude) == round6(node.latitude)
                && round6(longitude) == round6(node.longitude);
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, latitude, longitude);
    }
}
