package com.example.PathFinder.util;

import com.example.PathFinder.model.Node;

public class DistanceCalculator {
    public static double calculateDistanceBetweenTwoNodesInMeters(Node firstNode, Node secondNode) {
        double deltaLatitude = (firstNode.getLatitude() - secondNode.getLatitude()) * 111320;
        double deltaLongitude = (firstNode.getLongitude() - secondNode.getLongitude())
                * 111320 * Math.cos(Math.toRadians(0.5*(firstNode.getLatitude() + secondNode.getLatitude())));
        return Math.sqrt(deltaLatitude * deltaLatitude + deltaLongitude * deltaLongitude);
    }
}
