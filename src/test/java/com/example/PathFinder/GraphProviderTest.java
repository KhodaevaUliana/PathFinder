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

    @Test
    public void testShortestPathInMonaco() {
        //coordinates of Tunnel Larvotto
        double latStart = 43.739749;
        double longStart = 7.429885;
        //coordinates of Villa Sauber
        double latFinish = 43.744493;
        double longFinish = 7.430804;
        Path path = null;
        try {
            path = graphProvider.shortestPathBetweenTwoPoints(latStart, longStart, latFinish,
                    longFinish);
            System.out.println(path.getDistance());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertNotNull(path);

    }

    @Test
    public void testShortestPathInMonaco2() {
        //coordinates of Place du Canton
        double latStart = 43.731579;
        double longStart = 7.417277;
        //coordinates of Villa Sauber
        double latFinish = 43.744493;
        double longFinish = 7.430804;
        Path path = null;
        try {
            path = graphProvider.shortestPathBetweenTwoPoints(latStart, longStart, latFinish,
                    longFinish);
            System.out.println(path.getDistance());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertNotNull(path);

    }

    @Test
    public void testShortestPathOutsideMonaco() {
        //coordinates of Tunnel Larvotto
        double latStart = 43.739749;
        double longStart = 7.429885;
        //coordinates sth in the North
        double latFinish = 63.744493;
        double longFinish = 7.430804;
        Path path = null;
        try {
            path = graphProvider.shortestPathBetweenTwoPoints(latStart, longStart, latFinish,
                    longFinish);
            System.out.println(path.getDistance());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertNull(path);

    }

}
