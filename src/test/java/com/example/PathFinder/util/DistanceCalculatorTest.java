package com.example.PathFinder.util;

import com.example.PathFinder.model.Node;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DistanceCalculatorTest {

    @Test
    public void twoPointsMediumDistance() {
        Node marienplatz = new Node(1, 48.1371, 11.5754);
        Node chinesischerTurm = new Node(2, 48.1585, 11.6035);
        double expected = 3167;
        assertEquals(expected,
                DistanceCalculator.calculateDistanceBetweenTwoNodesInMeters(marienplatz, chinesischerTurm),
                1); //allow ~0.1% error
    }

    @Test
    public void twoPointsLongDistance() {
        Node marienplatz = new Node(1, 48.1371, 11.5754);
        Node flughafen = new Node(2, 48.3538, 11.7861);
        double expected = 28736;
        System.out.println(DistanceCalculator.calculateDistanceBetweenTwoNodesInMeters(marienplatz, flughafen));
        assertEquals(expected,
                DistanceCalculator.calculateDistanceBetweenTwoNodesInMeters(marienplatz, flughafen),
                10);  //allow ~0.1% error
    }

    @Test
    public void twoPointsShortDistnace() {
        Node marienplatz = new Node(1, 48.1371, 11.5754);
        Node fewMetersAway = new Node(2, 48.137110, 11.575410);
        double expected = 1.32;
        assertEquals(expected,
                DistanceCalculator.calculateDistanceBetweenTwoNodesInMeters(marienplatz, fewMetersAway),
                0.1);
    }
}
