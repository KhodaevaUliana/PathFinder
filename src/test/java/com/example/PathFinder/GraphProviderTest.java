package com.example.PathFinder;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GraphProviderTest {
    @Autowired
    private GraphProvider graphProvider;

    @Test
    public void testFindNearestGivenNodeInMonaco() {
        double testLat = 43.7384;
        double testLon = 7.4246;
        Node nearest = graphProvider.findNearestNode(testLat, testLon);
        assertNotNull(nearest);
    }

    @Test
    public void testFindNearestGivenNodeOutsideMonaco() {
        double testLat = 60.7384;
        double testLon = 7.4246;
        Node nearest = graphProvider.findNearestNode(testLat, testLon);
        assertNull(nearest);
    }

}
