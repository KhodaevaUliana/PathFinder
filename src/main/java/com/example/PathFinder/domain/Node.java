package com.example.PathFinder.domain;

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
